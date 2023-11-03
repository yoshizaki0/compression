package enshud.s4.compiler;

import java.util.ArrayDeque;
import java.util.Deque;

public class rable_number {
	static int true_both=0;
	static int while_num=0;
	static int if_else=0;
	static int sub_pro=0;
	static Deque<String> while_log = new ArrayDeque<String>();
	static Deque<String> if_log = new ArrayDeque<String>();
	public static int return_true_both() {
		return true_both;
		
	}
	public static int return_subpro() {
		return sub_pro;
		
	}
	public static int start_while() {
		while_log.addLast(String.valueOf(while_num));	
		return while_num;
		
	}
	public static String back_while() {
		
		return while_log.peekLast();
		
	}
	public static String end_while() {
		
		return while_log.removeLast();
		
	}
	
	public static int start_else() {
		if_log.addLast(String.valueOf(if_else));
		return if_else;
		
	}
	public static String back_else() {
		
		return if_log.peekLast();
		
	}
	public static String end_else() {
		
		return if_log.removeLast();
		
	}
	
	public static void add(int var) {/* 0ならture  */
		if(var==0) {
			true_both++;
		}else if(var==1) {
			
			while_num++;
		}else if(var==2) {
			if_else++;
		}else if(var==3) {
			sub_pro++;
		}
		
	}
	
	public static void clear_num() {
		true_both=0;
		while_num=0;
		if_else=0;
	}

}
