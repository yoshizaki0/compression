package enshud.s4.compiler;

import java.util.ArrayDeque;
import java.util.Deque;

public class formal_stuck {
	static String type;
	static Deque<String> type_log = new ArrayDeque<String>();
	/*static int first_type=0;/*最初の型が欲しい*/
	
	public static void get_type(String name) {
			if(name.equals("unsignint")) {
				type="integer";
			}
			else if(name.equals("stringof")) {
				type="String";
			}
			else if(name.equals("trueorfalse")) {
				type="boolean";
			}
			else if(name.equals("integer")) {
				type="integer";
			}else if(name.equals("char")) {
				type="char";
			}else if(name.equals("boolean")) {
				type="boolean";
			}else {
				type="unknown";
			}
	}
	public static void clear() {
		type=null;
	}
	public static void push_formal() {
		type_log.addLast(type);	
	}
	public static String pop_formal() {
		return type_log.removeLast();
		
	}
	public static void show_stuck() {
		System.out.println(type_log);
	}

}
