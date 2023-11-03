package Visit;

import tree.tree;

import java.util.ArrayList;
import java.util.HashMap;
import tslist.tslist;
class nowlist{
	static String nowprogramname=null;
	
	public static String check_prog() {
		
		return nowprogramname;
	}
	public  static void change_prog(String name) {
		nowprogramname=name;
	}
	public  static String return_typeof(String name) {
		return build.return_type(nowprogramname,name);
		
	}
			
}
class simple_formal{/*単純式を検査*/
	static int relational=0;
	static String ideal;
	static ArrayList<Integer> prerelational = new ArrayList<>();
	static ArrayList<String> preideal = new ArrayList<String>();
	static int state=0;
	static int lineof=-1;
	static int inout=0;/*入出の場合式ではないのでチェックしない*/
	public static void ideal_check(String name,int line) {/*まだ型が定まっていないかわからないとき*/
		if(ideal==null) {
			ideal=name;
			lineof=line;
		}else {
			check_type(name,line);
		}
	}
	public static void check_type(String name, int line) {/*型チェック*/
		
		if(!ideal.equals(name)) {
			state=1;
			if(inout==0) {
			 errmessage.errmes(line,7);
			}
		}
	}
	public static void find_relational()  {/*関係演算子の有無*/
		relational=1;
	}
	public static void clear_all() {/*初期化*/
		relational=0;
		state=0;
		ideal=null;
		lineof=-1;
		inout=0;
	}
	public static String print_formal() {
		if(relational==1) {
			return "boolean";
		}else {
			return ideal;
		}
	}
	public static void inout_formal() {
		inout=1;
	}
	public static void sava_informal() {
		prerelational.add(relational);
		preideal.add(ideal);
	}
	public static void reload_informal(int line) {
		if(relational==1) {
			
				if(preideal.get(preideal.size()-1)==null) {
					ideal="boolean";
					relational=prerelational.get(prerelational.size()-1);
					prerelational.remove(prerelational.size()-1);
					preideal.remove(preideal.size()-1);
				}else {
					ideal=preideal.get(preideal.size()-1);
					ideal_check("boolean",line);
					relational=prerelational.get(prerelational.size()-1);
					prerelational.remove(prerelational.size()-1);
					preideal.remove(preideal.size()-1);
				}
		}else {
			if(preideal.get(preideal.size()-1)==null) {
					ideal_check(ideal,line);
					relational=prerelational.get(prerelational.size()-1);
					prerelational.remove(prerelational.size()-1);
					preideal.remove(preideal.size()-1);
			}else {
				relational=prerelational.get(prerelational.size()-1);
				ideal=preideal.get(preideal.size()-1);
				prerelational.remove(prerelational.size()-1);
				preideal.remove(preideal.size()-1);
			}
		}
	}
	
	
	
}
public  class check_visitor extends Visitor{
	 
	public void visit(tree methodtree) {
		switch(methodtree.treename) {
		case "block" :
			for(int i=0;i<methodtree.children.size();i++) {
				  methodtree.children.get(i).accept(this);
			  }
			nowlist.change_prog("program");/*main文が続くため*/
			break;
		case "subprohead":
			nowlist.change_prog(methodtree.children.get(0).children.get(0).terminal);
			for(int i=0;i<methodtree.children.size();i++) {
				  methodtree.children.get(i).accept(this);
			  }
			break;
		case "purevariable" :
			build.check_array(nowlist.check_prog(),methodtree.children.get(0).children.get(0).terminal,methodtree.children.get(0).children.get(0).line);
			simple_formal.ideal_check(nowlist.return_typeof(methodtree.children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).line);
			for(int i=0;i<methodtree.children.size();i++) {
				  methodtree.children.get(i).accept(this);
			  }
			break;
		case "suffixvariable" :
			build.check_pure(nowlist.check_prog(),methodtree.children.get(0).children.get(0).terminal,methodtree.children.get(0).children.get(0).line);
			simple_formal.ideal_check(nowlist.return_typeof(methodtree.children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).line);
			/*純変数がarrayとして使われているかも追加で検出*/
			for(int i=0;i<methodtree.children.size();i++) {
				  methodtree.children.get(i).accept(this);
			  }
			
			break;
		case "suffix":
			simple_formal.sava_informal();
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
		    }
			if(!simple_formal.print_formal().equals("integer")) {
				errmessage.errmes(simple_formal.lineof,8);/*添え字がintergerではない*/
			}
			simple_formal.reload_informal(methodtree.line);
			break;
		case "whiletext":
				methodtree.children.get(0).accept(this);
			if(!simple_formal.print_formal().equals("boolean")) {
				errmessage.errmes(simple_formal.lineof,9);/*添え字がbooleanではない*/
			}
			for(int i=1;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
		    }
			break;
		case "iftex"  :
			methodtree.children.get(0).accept(this);
			if(!simple_formal.print_formal().equals("boolean")) {
				errmessage.errmes(simple_formal.lineof,10);/*booleanではない*/
			}
			for(int i=1;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
		    }
			break;
		case "assign":
			simple_formal.clear_all();
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			if(nowlist.return_typeof(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).terminal)!=null) {/*変数が登録されている*/
				if(!nowlist.return_typeof(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).terminal).equals(simple_formal.print_formal())) {
					errmessage.errmes(simple_formal.lineof,11);/*代入文エラー*/
				}
			}
			break;
		case "formal":
			simple_formal.clear_all();
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
		    }
			break;
		case "relational":
			simple_formal.find_relational();
			break;
		case "sign" :
			simple_formal.ideal_check("integer",methodtree.line);
			
			break;
		case "additive" :
			if(methodtree.terminal.equals("+")||methodtree.terminal.equals("-")) {
				simple_formal.check_type("integer",methodtree.line);/*演算子と非演算子の型*/
			}else {
				simple_formal.check_type("boolean",methodtree.line);
			}
			break;
		case "multiplicative"	:
			if(methodtree.terminal.equals("and")) {
				simple_formal.check_type("boolean",methodtree.line);/*演算子と非演算子の型*/
			}else {
				simple_formal.check_type("integer",methodtree.line);
			}
			break;
		case "not":
			simple_formal.ideal_check("boolean",methodtree.line);
		     break;
		case "trueorfalse":
				simple_formal.ideal_check("boolean",methodtree.line);
			break;
		case "unsignint":
			simple_formal.ideal_check("integer",methodtree.line);
			break;
		case "stringof":
			simple_formal.ideal_check("char",methodtree.line);
			break;
		case "read":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
		    }
			break;
		case "insi":
			if(methodtree.children.get(0).treename.equals("formal")) {
				simple_formal.sava_informal();
			}
			for(int i=0;i<methodtree.children.size();i++) {
				  methodtree.children.get(i).accept(this);
			  }
			if(methodtree.children.get(0).treename.equals("formal")) {
				simple_formal.reload_informal(methodtree.line);
			}
			break;
		case "procedural":
			build.check_procedure(methodtree.children.get(0).children.get(0).terminal,methodtree.children.get(0).children.get(0).line);
			break;
		default:
			if(methodtree.children!=null) {
			  for(int i=0;i<methodtree.children.size();i++) {
				  methodtree.children.get(i).accept(this);
			  }
			}
			break;		
		}
			
			
		
	}
	
}
