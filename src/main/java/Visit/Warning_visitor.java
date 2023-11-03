package Visit;

import tree.tree;
import Visit.build;
import Visit.check_visitor;
import Visit.make_table_visit;
class nowprog{
	static String nowprogramname=null;
	
	public static String check_pro() {
		
		return nowprogramname;
	}
	public  static void change_pro(String name) {
		nowprogramname=name;
	}
	/*public  static String return_typeof(String name) {
		return build.return_type(nowprogramname,name);
		
	}*/
			
}
public  class Warning_visitor extends Visitor{

	public void visit(tree methodtree) {
		switch(methodtree.treename) {
			case "block" :
				for(int i=0;i<methodtree.children.size();i++) {
					methodtree.children.get(i).accept(this);
				}
				nowprog.change_pro("program");/*main文が続くため*/
			break;
			case "subprohead":
				nowprog.change_pro(methodtree.children.get(0).children.get(0).terminal);
				for(int i=0;i<methodtree.children.size();i++) {
					methodtree.children.get(i).accept(this);
				}
			break;
			case "leftformal":/*代入を確認*/
				build.find_assigment(nowprog.check_pro(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
				
				for(int i=0;i<methodtree.children.size();i++) {
					methodtree.children.get(i).accept(this);
				}
			break;
			case "variable":
				build.usenoassig_warning(nowprog.check_pro(),methodtree.children.get(0).children.get(0).children.get(0).terminal,methodtree.children.get(0).children.get(0).children.get(0).line);
				build.find_reference(nowprog.check_pro(),methodtree.children.get(0).children.get(0).children.get(0).terminal);
				for(int i=0;i<methodtree.children.size();i++) {
					methodtree.children.get(i).accept(this);
				}
			break;
			case "calip_name":
				build.clip_variable(nowprog.check_pro(),methodtree.children.get(0).terminal);
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