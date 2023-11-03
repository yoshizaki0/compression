package enshud.s4.compiler;

import java.util.ArrayList;
import enshud.s4.compiler.str_table;
public class String_table {
	static ArrayList<str_table> str_list = new ArrayList<>();
	static int num;
	public static void add_table(String str ){
		String rabel;
		if(!str_list.contains(str)) {
			rabel="CHART"+ String.valueOf(num);
			str_list.add(new str_table(str,rabel));
			num++;
		}
	}
	static void clear_table(){
		str_list = new ArrayList<>();
		num=0;
	}
	static void show_str_table() {
		for(int i=0;i<str_list.size();i++) {
			  System.out.println(str_list.get(i).messge+" :"+str_list.get(i).as_rabel);
		  }
	}
	static void write_CHAT() {
		for(int i=0;i<str_list.size();i++) {
			output.rabel_write(str_list.get(i).as_rabel+"	"+"DC	"+str_list.get(i).messge,0,null);
		  }
		output.rabel_write("LIBBUF	DS	256	", 0, null);
		output.write("END",0,null);
	}
	public static String return_rabel(String text) {
		for(int i=0;i<str_list.size();i++) {
			if(str_list.get(i).messge.equals(text)) {
				return str_list.get(i).as_rabel;
			}
		  }
		return null;
		
	}

}
