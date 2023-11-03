package Visit;

import tree.tree;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Deque;
import table.table;
import Visit.check_visitor;
import Visit.make_table_visit;
/*class build{
	static HashMap<String,HashMap<String,String>> variable_list = new HashMap<>();
	 static String nowprogram=null;
	 static String nowtype=null;
	 public static void next_table(String name) {/*新しい変数表*
		 variable_list.put(name,new HashMap<>());
		 nowprogram=name;
	 }
	 public static void add_name_table(String varia_name) {/*変数表に追加 変数の重複は許さない
		if(variable_list.get(nowprogram).containsKey(varia_name)||variable_list.get("program").containsKey(varia_name)) {
			
		}
		variable_list.get(nowprogram).put(varia_name,"nowtype");
	 }
	 public static void type_name(String name) {/*型判明
			nowtype=name;
		 }
		 
	}*/
public abstract class  Visitor {
	public abstract void visit(tree name);
	}

