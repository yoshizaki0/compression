package Visit;

import java.util.HashMap;
import java.util.LinkedList;
import tree.tree;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Deque;
import enshud.s4.compiler.*;
class number_stuck{
	
	static Deque<String> rumber = new ArrayDeque<String>();
	static Deque<String> type = new ArrayDeque<String>();
	
	public static String rumber_pop() {
		      
		return rumber.removeLast();
		
	}
	public static void rumber_push(String atai) {
		rumber.addLast(atai);	
	}
	public static void type_push(String atai) {
		type.addLast(atai);	
	}
	public static String return_type() {
		return type.peekLast();
		
	}
	public static String return_rum() {
		return rumber.peekLast();
		
	}
	public static String pop_type() {
		return type.removeLast();
		
	}
	public static void show_stuck() {
		System.out.println(rumber);
		
	}
	public static void showtp_stuck() {
		System.out.println(type);
		
	}
	
	 
}
public  class Opt_main extends Visitor{
	
	public void visit(tree methodtree) {
		switch(methodtree.treename) {
		
		case "programname" :
			output.rabel_write("CASL	START	BEGIN",methodtree.line,null);
			output.rabel_write("BEGIN	LAD	GR6, 0",methodtree.line,null);
			output.write("LAD	GR7, LIBBUF",methodtree.line,null);
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			break;
		case"block":
			methodtree.children.get(0).accept(this);/*副プログラムにはいかない*/
			break;
		case"program":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			output.write("RET",0,null);
			break;
		case"constant":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			formal_stuck.get_type(methodtree.children.get(0).treename);
			if(methodtree.children.get(0).treename.equals("unsignint")) {
				/*符号なし整数*/
				number_stuck.type_push("int");
				number_stuck.rumber_push(methodtree.children.get(0).terminal);
				output.write("PUSH	"+methodtree.children.get(0).terminal,methodtree.children.get(0).line,null);
			}
			else if(methodtree.children.get(0).treename.equals("trueorfalse")) {
				/*true false*/
				if(methodtree.children.get(0).terminal.equals("true")) {
				/*true*/
					number_stuck.type_push("boolean");
					number_stuck.rumber_push("true");
				output.write("PUSH	#0000",methodtree.children.get(0).line,null);
				}else {
					number_stuck.type_push("boolean");
					number_stuck.rumber_push("false");
					output.write("PUSH	#FFFF",methodtree.children.get(0).line,null);
				}
			}else if(methodtree.children.get(0).terminal.length()==3) {
				number_stuck.type_push("char");
				number_stuck.rumber_push(methodtree.children.get(0).terminal);
				output.write("LD	GR1, ="+methodtree.children.get(0).terminal,methodtree.children.get(0).line,null);
				output.write("PUSH 0 ,GR1",methodtree.children.get(0).line,null);
			}
			 break;
		case "insi":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			if(methodtree.children.get(0).treename.equals("variable")) {
				if(methodtree.children.get(0).children.get(0).treename.equals("purevariable")) {
					/*純変数*/
					number_stuck.type_push("var");
					number_stuck.rumber_push(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
					output.write("LD GR2,="+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
					output.write("LD GR1,VAR,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("PUSH 0,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"PUSH "+methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
				}else {
					/*添え字付き変数*/
					number_stuck.pop_type();
					number_stuck.rumber_pop();
					number_stuck.type_push("var");
					number_stuck.rumber_push(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
					output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"number of soezi");
					output.write("LD GR2,="+build.return_array_start(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("SUBA GR1,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"soezinosoutaiiti");
					output.write("LD GR2,="+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("ADDA GR2,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("LD GR1, VAR, GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("PUSH 0, GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"PUSH"+methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
				}
			}
			
			if(methodtree.children.get(0).treename.equals("not")) {/* not 計算 返ってきたときにやる*/
				if(number_stuck.return_rum().equals("true")) {
					number_stuck.rumber_pop();
					number_stuck.rumber_push("false");
					output.write("POP GR1",methodtree.children.get(0).line,"not enzan");
					output.write("PUSH #FFFF",methodtree.children.get(0).line,"opti"); /*最適化*/
					
				}
				else if(number_stuck.return_rum().equals("false")) {
					number_stuck.rumber_pop();
					number_stuck.rumber_push("true");
					output.write("POP GR1",methodtree.children.get(0).line,"not enzan");
					output.write("PUSH #0000",methodtree.children.get(0).line,"opti");
				}else {
					output.write("POP GR1",methodtree.children.get(0).line,"not enzan");
					output.write("XOR GR1,=#FFFF",methodtree.children.get(0).line,null);
					output.write("PUSH 0,GR1",methodtree.children.get(0).line,null);
				}
				
				
			}
			if(methodtree.children.get(0).treename.equals("formal")) {/*式の時にはPOP*/
			formal_stuck.pop_formal();
			}
			break;
		case "term":
			for(int i=methodtree.children.size()-1;i>-1;i--) {/*逆から*/
				methodtree.children.get(i).accept(this);
			}
			
			for(int i=0;i<(methodtree.children.size()-1)/2;i++){/*乗算あり*/
				if( number_stuck.pop_type().equals("int")&&number_stuck.return_type().equals("int")) {/*定数+定数*/
					number_stuck.pop_type();
					int a,b,answer;
					a=Integer.parseInt(number_stuck.rumber_pop());
					b=Integer.parseInt(number_stuck.rumber_pop());
					output.write("POP GR1",methodtree.children.get(1).line,null);/*いる*/
					output.write("POP GR2",methodtree.children.get(1).line,null);
					answer=1;
					switch(methodtree.children.get(1+i*2).terminal) {
					case"*":
						
						output.write("PUSH "+a*b,methodtree.children.get(1).line,null);/*いる*/
						answer=a*b;
						break;
					case"/":
						output.write("PUSH "+a/b,methodtree.children.get(1).line,null);
						answer=a/b;
						break;
					case"div":
						output.write("PUSH "+a/b,methodtree.children.get(1).line,null);
						answer=a/b;
						break;
					case"mod":
						output.write("PUSH "+a%b,methodtree.children.get(1).line,null);
						answer=a%b;
						break;
						/*andはいらない*/
					}
					number_stuck.rumber_push(String.valueOf(answer));
					number_stuck.type_push("int");
				}else {
					String kata=number_stuck.pop_type();
					number_stuck.rumber_pop();
					int bit;
					if(kata.equals("int")) {
						bit=Integer.parseInt(number_stuck.rumber_pop());
					}else {
						bit=0;
					}
					output.write("POP GR1",methodtree.children.get(1).line,null);/*いる*/
					output.write("POP GR2",methodtree.children.get(1).line,null);
					switch(methodtree.children.get(1+i*2).terminal) {
				
					case"*":
						if(bit==2) {
							output.write("SLA GR1, 1",methodtree.children.get(1).line,"mult");
							output.write("PUSH 0,GR1",methodtree.children.get(1).line,null);
						}else if(bit==4) {
							output.write("SLA GR1, 2",methodtree.children.get(1).line,"mult");
							output.write("PUSH 0,GR1",methodtree.children.get(1).line,null);
						}else {
							output.write("CALL	MULT",methodtree.children.get(1).line,"mult");
							output.write("PUSH 0,GR2",methodtree.children.get(1).line,null);
						}
						break;
					case"/":
						if(bit==2) {
							output.write("SRA GR1, 1",methodtree.children.get(1).line,"mult");
							output.write("PUSH 0,GR1",methodtree.children.get(1).line,null);
							
						}else if(bit==4) {
							output.write("SRA GR1, 2",methodtree.children.get(1).line,"mult");
							output.write("PUSH 0,GR1",methodtree.children.get(1).line,null);
							
						}else {
						output.write("CALL	DIV",methodtree.children.get(1).line,"DIV");
						output.write("PUSH 0,GR2",methodtree.children.get(1).line,null);
						}
						break;
					case"div":
						if(bit==2) {
							output.write("SRA GR1, 1",methodtree.children.get(1).line,"mult");
							output.write("PUSH 0,GR1",methodtree.children.get(1).line,null);
							
						}else if(bit==4) {
							output.write("SRA GR1,2",methodtree.children.get(1).line,"mult");
							output.write("PUSH 0,GR1",methodtree.children.get(1).line,null);
							
						}else {
							output.write("CALL	DIV",methodtree.children.get(1).line,"DIV");
							output.write("PUSH 0,GR2",methodtree.children.get(1).line,null);
						}
						break;
					case"mod":
						output.write("CALL	DIV",methodtree.children.get(1).line,"mod");
						output.write("PUSH 0,GR1",methodtree.children.get(1).line,null);
						break;
					case"and":
						output.write("AND GR1,GR2",methodtree.children.get(1).line,"and");
						output.write("PUSH 0,GR1",methodtree.children.get(1).line,null);
						break;
				}
					number_stuck.rumber_push("VAR");
					number_stuck.type_push("VAR");
				}
			}
			break;
		case"simpleformal":
			for(int i=methodtree.children.size()-1;i>-1;i--) {/*逆から*/
				methodtree.children.get(i).accept(this);
			}
			if(methodtree.children.get(0).treename.equals("sign")) {/*符号あり*/
				if(methodtree.children.get(0).terminal.equals("-")) {
					
					
						if(number_stuck.pop_type().equals("int")) {
							
							int var=Integer.parseInt(number_stuck.rumber_pop());
							var=var*-1;
							output.write("POP GR2",methodtree.children.get(0).line,"mainasu");
							output.write("PUSH "+var,methodtree.children.get(0).line,"mainasu");
							number_stuck.rumber_push(String.valueOf(var));
							number_stuck.type_push("int");
						}else {
							output.write("POP GR2",methodtree.children.get(0).line,"mainasu");
							output.write("LD GR1,=0",methodtree.children.get(0).line,"mainasu");
							output.write("SUBA GR1,GR2",methodtree.children.get(0).line,"mainasu");
							output.write("PUSH 0,GR1",methodtree.children.get(0).line,"mainasu");
							number_stuck.type_push("VAR");
						}
					
				}
				for(int i=0;i<(methodtree.children.size()-2)/2;i++){/*加法あり  スタック順が逆に*/
					if( number_stuck.pop_type().equals("int")&&number_stuck.return_type().equals("int")) {/*定数+定数*/
							number_stuck.pop_type();
							int a,b,answer;
							a=Integer.parseInt(number_stuck.rumber_pop());
							b=Integer.parseInt(number_stuck.rumber_pop());
							output.write("POP GR1",methodtree.children.get(2).line,null);
							output.write("POP GR2",methodtree.children.get(2).line,null);
							answer=1;
							switch(methodtree.children.get(1+i*2).terminal) {
							case"+":
							
								output.write("PUSH "+(a+b),methodtree.children.get(1).line,null);/*いる*/
								answer=a+b;
								break;
							case"-":
								output.write("PUSH "+(a-b),methodtree.children.get(1).line,null);
								answer=a-b;
								break;
							/*andはいらない*/
							}
						number_stuck.rumber_push(String.valueOf(answer));
						number_stuck.type_push("int");
					}else {
						
						number_stuck.pop_type();
						number_stuck.rumber_pop();
						number_stuck.rumber_pop();
						output.write("POP GR1",methodtree.children.get(2).line,null);
						output.write("POP GR2",methodtree.children.get(2).line,null);
						switch(methodtree.children.get(2+i*2).terminal) {
						case"+":
							output.write("ADDA GR1,GR2",methodtree.children.get(2).line,"ADDA");
							break;
						case"-":
							output.write("SUBA GR1,GR2",methodtree.children.get(2).line,"SUBA");
							break;
						case"or":
							output.write("OR GR1,GR2",methodtree.children.get(2).line,"OR");
							break;
					
						}
						output.write("PUSH 0,GR1",methodtree.children.get(2).line,"answer");
						number_stuck.rumber_push("VAR");
						number_stuck.type_push("VAR");
					}
					
					
				}
			}else {/*符号の有無で関係史のtreeにおける位置が変わる*/
				for(int i=0;i<(methodtree.children.size()-1)/2;i++){/*加法あり  スタック順が逆に*/
					
					if( number_stuck.pop_type().equals("int")&&number_stuck.return_type().equals("int")) {/*定数+定数*/
						number_stuck.pop_type();
						int a,b,answer;
						a=Integer.parseInt(number_stuck.rumber_pop());
						b=Integer.parseInt(number_stuck.rumber_pop());
						output.write("POP GR1",methodtree.children.get(2).line,null);
						output.write("POP GR2",methodtree.children.get(2).line,null);
						answer=1;
						switch(methodtree.children.get(1+i*2).terminal) {
						case"+":
						
							output.write("PUSH "+(a+b),methodtree.children.get(1).line,null);/*いる*/
							answer=a+b;
							break;
						case"-":
							output.write("PUSH "+(a-b),methodtree.children.get(1).line,null);
							answer=a-b;
							break;
						/*andはいらない*/
						}
					number_stuck.rumber_push(String.valueOf(answer));
					number_stuck.type_push("int");
				}else {
					number_stuck.pop_type();
					number_stuck.rumber_pop();
					number_stuck.rumber_pop();
					output.write("POP GR1",methodtree.children.get(2).line,null);
					output.write("POP GR2",methodtree.children.get(2).line,null);
					switch(methodtree.children.get(1+i*2).terminal) {
					case"+":
						output.write("ADDA GR1,GR2",methodtree.children.get(1).line,"ADDA");
						break;
					case"-":
						output.write("SUBA GR1,GR2",methodtree.children.get(1).line,"SUBA");
						break;
					case"or":
						output.write("OR GR1,GR2",methodtree.children.get(1).line,"OR");
						break;
				
					}
					output.write("PUSH 0,GR1",methodtree.children.get(1).line,"answer");
					number_stuck.rumber_push("VAR");
					number_stuck.type_push("VAR");
				}
					
				}
			
			}
			break;
		case"formal":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			formal_stuck.push_formal();
			if(methodtree.children.size()>2){/*関係演算子あり*/
				if( number_stuck.pop_type().equals("int")&&number_stuck.return_type().equals("int")) {
					number_stuck.pop_type();
					int a,b,answer;
					output.write("POP GR2",methodtree.children.get(1).line,null);
					output.write("POP GR1",methodtree.children.get(1).line,null);
					b=Integer.parseInt(number_stuck.rumber_pop());
					a=Integer.parseInt(number_stuck.rumber_pop());
					switch(methodtree.children.get(1).terminal) {
						case"=":
							if(a==b) {
								output.write("PUSH #0000",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("true");
								number_stuck.type_push("boolean");
							}else {
								output.write("PUSH #FFFF",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("false");
								number_stuck.type_push("boolean");
							}
								
							break;
						case"<>":
							if(a!=b) {
								output.write("PUSH #0000",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("true");
								number_stuck.type_push("boolean");
							}else {
								output.write("PUSH #FFFF",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("false");
								number_stuck.type_push("boolean");
							}
							break;
						case"<":
							if(a<b) {
								output.write("PUSH #0000",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("true");
								number_stuck.type_push("boolean");
							}else {
								output.write("PUSH #FFFF",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("false");
								number_stuck.type_push("boolean");
							}
							break;
						case"<=":
							if(a<=b) {
								output.write("PUSH #0000",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("true");
								number_stuck.type_push("boolean");
							}else {
								output.write("PUSH #FFFF",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("false");
								number_stuck.type_push("boolean");
							}
							break;
						case">":
							if(a>b) {
								output.write("PUSH #0000",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("true");
								number_stuck.type_push("boolean");
							}else {
								output.write("PUSH #FFFF",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("false");
								number_stuck.type_push("boolean");
							}
							break;
						case">=":
							if(a>=b) {
								output.write("PUSH #0000",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("true");
								number_stuck.type_push("boolean");
							}else {
								output.write("PUSH #FFFF",methodtree.children.get(1).line,null);
								number_stuck.rumber_push("false");
								number_stuck.type_push("boolean");
							}
							break;
					}
				}
				else {
					number_stuck.pop_type();
					number_stuck.rumber_pop();
					number_stuck.rumber_pop();
					output.write("POP GR2",methodtree.children.get(1).line,null);
					output.write("POP GR1",methodtree.children.get(1).line,null);
					output.write("CPA GR1,GR2",methodtree.children.get(1).line,"=,<>...");
					switch(methodtree.children.get(1).terminal) {
				
					case"=":
						output.write("JZE	TRUE"+rable_number.return_true_both(),methodtree.children.get(1).line,null);

						break;
				
					case"<>":
						output.write("JNZ	TRUE"+rable_number.return_true_both(),methodtree.children.get(1).line,null);
						break;
				
					case"<":
						output.write("JMI	TRUE"+rable_number.return_true_both(),methodtree.children.get(1).line,null);
						break;
					case"<=":
						output.write("JMI	TRUE"+rable_number.return_true_both(),methodtree.children.get(1).line,null);
						output.write("JZE	TRUE"+rable_number.return_true_both(),methodtree.children.get(1).line,null);
						break;
				
					case">":
						output.write("JPL	TRUE"+rable_number.return_true_both(),methodtree.children.get(1).line,null);
						break;
				
					case">=":
						output.write("JPL	TRUE"+rable_number.return_true_both(),methodtree.children.get(1).line,null);
						output.write("JZE	TRUE"+rable_number.return_true_both(),methodtree.children.get(1).line,null);
						break;
					
					}
					output.write("LD	GR1, =#FFFF",methodtree.children.get(1).line,null);
					output.write("JUMP	BOTH"+rable_number.return_true_both(),methodtree.children.get(1).line,null);
					output.rabel_write("TRUE"+rable_number.return_true_both()+"	LD	GR1, =#0000",methodtree.children.get(1).line,null);
					output.rabel_write("BOTH"+rable_number.return_true_both()+"	PUSH 0,GR1",methodtree.children.get(1).line,null);
					rable_number.add(0);
					number_stuck.rumber_push("VAR");
					number_stuck.type_push("VAR");
			 }
			}
			break;
		case"multivariable":/*いらなくね？*/
			for(int i=0;i<methodtree.children.size();i++) {
			output.write("LD GR2,"+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"hennsuunarabi");
			output.write("LD GR1,VAR,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
			output.write("PUSH 0,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"PUSH"+methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
			number_stuck.rumber_push("VAR");
			number_stuck.type_push("VAR");
			methodtree.children.get(i).accept(this);
			}
			break;
		case"assign":
			/*if(!methodtree.children.get(1).children.get(0).children.get(0).treename.equals("sign")) {
				if(methodtree.children.get(1).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).treename.equals("stringof")) {
				output.write("LD	GR1, ="+methodtree.children.get(1).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).terminal, 0, "assign word");
				output.write("PUSH	0, GR1", 0, "assign word");
				}
			}*/
			
			for(int i=methodtree.children.size()-1;i>-1;i--) {/*逆から*/
				methodtree.children.get(i).accept(this);
			}/*具体的な処理は左辺で実行*/
			formal_stuck.pop_formal();
			
			break;
		case"leftformal":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			if(methodtree.children.get(0).children.get(0).treename.equals("purevariable")) {
				output.write("LD GR2, ="+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"left");
				output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				output.write("ST GR1, VAR, GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				number_stuck.pop_type();
				number_stuck.rumber_pop();
			}else {
				output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"number of soezi");
				output.write("LD GR2, ="+build.return_array_start(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				output.write("SUBA GR1,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"soezinosoutaiiti");
				output.write("LD GR2, ="+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				output.write("ADDA GR2,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				output.write("ST GR1, VAR, GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				number_stuck.pop_type();
				number_stuck.rumber_pop();
				number_stuck.pop_type();
				number_stuck.rumber_pop();
				
			}
			break;
		case"output":
			String stuck_pop;
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			if(methodtree.children!=null) {
				for(int i=0;i<methodtree.children.get(0).children.size();i++) {/*出力コード*/
					    stuck_pop=formal_stuck.pop_formal();
						if(stuck_pop.equals("String")) {	
						/*出力が文字列*/
							if(methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).terminal.length()!=3) {
								output.write("LD GR1, ="+String.valueOf(methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).terminal.length()-2),methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output string");
								output.write("PUSH	0, GR1",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output");
								output.write("LAD GR2 , "+String_table.return_rabel(methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output");
								output.write("PUSH	0, GR2",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output");
								output.write("POP GR2",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output");
								output.write("POP GR1",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output");
								output.write("CALL	WRTSTR",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output");
								}else {
									output.write("POP GR2",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"String len 1");
									output.write("CALL	WRTCH",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"String len 1");
									
								}
						}else if(stuck_pop.equals("char")) {/*1文字*/
							output.write("POP GR2",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output char");
							number_stuck.pop_type();
							number_stuck.rumber_pop();
							output.write("CALL	WRTCH",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output char");
						}else if(stuck_pop.equals("integer")) {/*数字*/
							output.write("POP GR2",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output int");
							number_stuck.pop_type();
							number_stuck.rumber_pop();
							output.write("CALL	WRTINT",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output int");
						}
				}
				output.write("CALL	WRTLN",0,"END output");
			}else {/*入力無し*/
				output.write("CALL	WRTLN",methodtree.line,"output nothing");
			}
			
			break;
		case"formals":
			for(int i=methodtree.children.size()-1;i>-1;i--) {/*逆から*/
				methodtree.children.get(i).accept(this);
			}
			break;
		case"variable":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			formal_stuck.get_type(build.return_type("program",methodtree.children.get(0).children.get(0).children.get(0).terminal));
			break;
		case "suffix":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			formal_stuck.pop_formal();
			break;
		case"iftex":
			methodtree.children.get(0).accept(this);
			output.write("POP GR1", 0, "if start");
			number_stuck.pop_type();
			String valse=number_stuck.rumber_pop();
			if(valse.equals("true")) {
				methodtree.children.get(1).accept(this);
			}else if(valse.equals("false")) {
				if(methodtree.children.size()>2) {
					methodtree.children.get(2).accept(this);
				}
			}else {
				formal_stuck.pop_formal();
				output.write("CPL GR1 , =#FFFF", 0, "if");
				output.write("JZE ELSE"+rable_number.start_else(), 0, "if");
				rable_number.add(2);
				methodtree.children.get(1).accept(this);
				output.write("JUMP ELEND"+rable_number.back_else(), 0, "if");
				output.rabel_write("ELSE"+rable_number.back_else()+"	NOP", 0, "else");
				if(methodtree.children.size()>2) {/*elseあり*/
					methodtree.children.get(2).accept(this);
				}
				output.rabel_write("ELEND"+rable_number.end_else()+"	NOP", 0, "else");
			}
			break;
		case"whiletext":
			output.rabel_write("LOOP"+rable_number.start_while()+"	NOP", 0, "start while");
			rable_number.add(1);
			methodtree.children.get(0).accept(this);
			formal_stuck.pop_formal();
			output.write("POP GR1", 0, "while");
			number_stuck.pop_type();
			if(number_stuck.rumber_pop().equals("false")) {
				rable_number.end_while();
			}else {
				output.write("CPL GR1 , =#FFFF", 0, "while");
				output.write("JZE	ENDLP"+rable_number.back_while(), 0, "while");
				methodtree.children.get(1).accept(this);/*複合文*/
				output.write("JUMP	LOOP"+rable_number.back_while(), 0, "while");
				output.rabel_write("ENDLP"+rable_number.end_while()+"	NOP", 0, "while");
			}
			break;
		case"procedural":
			for(int i=methodtree.children.size()-1;i>-1;i--) {/*逆から*/
				methodtree.children.get(i).accept(this);
			}
			break;
		case"proceduralname":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			output.write("CALL	PROG"+build.subrable_num(methodtree.children.get(0).terminal), methodtree.children.get(0).line, "while");
			output.write("ADDL GR8, ="+build.kariparnum(methodtree.children.get(0).terminal), methodtree.children.get(0).line, "while");
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