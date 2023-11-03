package Visit;

import java.util.HashMap;
import java.util.LinkedList;

import enshud.s4.compiler.String_table;
import tree.tree;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
class newlist{
	static String nowprogramname="program";
	
	public static String check_prog() {
		
		return nowprogramname;
	}
	public  static void change_prog(String name) {
		nowprogramname=name;
	}	
}
class formal_type{
	static int type_num=0;/*0は入出力 1は代入*　ifは２ whileは3 手続きは4*/
	static int check_type() {
		return  type_num;
	}
	static void change_type(int num) {
		type_num=num;
	}
}
class list_array{
	static ArrayList<String> array_name = new ArrayList<>();
	public static void add_list(String name) {
		array_name.add(name);
	}
	public  static void clear_list() {
		array_name=new ArrayList<>();
	}
}
public  class make_chart_visit extends Visitor{
	
	public void visit(tree methodtree) {
		switch(methodtree.treename) {
		
		case "block" :
			for(int i=0;i<methodtree.children.size();i++) {
				  methodtree.children.get(i).accept(this);
			  }
			newlist.change_prog("program");/*main文が続くため*/
			break;
		case "subprohead":
			newlist.change_prog(methodtree.children.get(0).children.get(0).terminal);
			build.substack_reset();
			for(int i=0;i<methodtree.children.size();i++) {
				  methodtree.children.get(i).accept(this);
			  }
			break;
		case  "calip_name":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			build.next_kariparnum(newlist.check_prog(), methodtree.children.get(0).terminal);
			break;
		case "calipara":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			build.end_karipara(newlist.check_prog());
			break;
		case"mulcalip_name":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
		case "arrangement" :
			for(int i=0;i<list_array.array_name.size();i++) {
			build.array_info(newlist.check_prog(),list_array.array_name.get(i),Integer.parseInt(methodtree.children.get(0).terminal),Integer.parseInt(methodtree.children.get(1).terminal));
				
			}
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
	
			break;
		case "multivariable_name":
			list_array.clear_list();
			for(int i=0;i<methodtree.children.size();i++) {
				list_array.add_list(methodtree.children.get(i).children.get(0).terminal);
				methodtree.children.get(i).accept(this);
			}
			break;
		case"stringof":
			if(formal_type.check_type()==0) {/*入出力分*/
				String_table.add_table(methodtree.terminal);
			}
		break;
		case "iftex":
			formal_type.change_type(2);
			
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
        case "whiletext":
        	formal_type.change_type(3);
			
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
        case "assign":
			
        	formal_type.change_type(1);
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
        case "procedural":
			
        	formal_type.change_type(4);
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
        case "output":
			
        	formal_type.change_type(0);
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
		default:
			if(methodtree.children!=null) {
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			 break;
			}
        }
	}
}