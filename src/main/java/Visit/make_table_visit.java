package Visit;

import java.util.HashMap;
import java.util.LinkedList;
import tree.tree;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Deque;

public  class make_table_visit extends Visitor{

	public void visit(tree methodtree) {
		switch(methodtree.treename) {
		
		case "block" :
			build.next_table("program");
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
		case "multivardel" :/*変数宣言　型　が子供 mapの都合上　型からやりたい*/
			for(int i=0;i<methodtree.children.size();i=i+2) {
				methodtree.children.get(i+1).accept(this);
				
				methodtree.children.get(i).accept(this);
			}
			break;
		case "standard" :
			build.standard_type_name(methodtree.terminal);
			break;
		case "arrangement" :
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			build.array_type_name();
			break;
		case "multivariable_name" :
			for(int i=0;i<methodtree.children.size();i++) {
				build.add_name_table(methodtree.children.get(i).children.get(0).terminal,methodtree.children.get(i).children.get(0).line);
			}
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
		case "subprohead" :
			build.next_table(methodtree.children.get(0).children.get(0).terminal);
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
		case "mulcalipara":
			for(int i=0;i<methodtree.children.size();i=i+2) {
				methodtree.children.get(i+1).accept(this);
				
				methodtree.children.get(i).accept(this);
			}
			break;
		case "calip_name":
			build.add_name_table(methodtree.children.get(0).terminal,methodtree.children.get(0).line);
			break;
		default:
			if(methodtree.children!=null) {
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			 break;
			}
			break;
		}
			
		
	}
	
}