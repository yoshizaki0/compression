package tree;

import java.util.ArrayList;
import Visit.Visitor;
import tslist.tslist;
public class tree {
	public String treename;
	public String terminal=null;
	public ArrayList<tree> children = new ArrayList<>();
	public String type=null;
	public int line;
	
	public tree(String name, tslist con, ArrayList<tree> child  )
	{
		this.treename=name;
		this.children=child;
		if(con!=null) {
		this.terminal=con.code;
		this.line=con.line;
		}
		else {
			this.terminal=null;
			this.line=-1;
		}
	}
	public  void accept(Visitor visiter) {
		visiter.visit(this);
	}
	

}
