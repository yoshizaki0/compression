package Visit;

import java.util.HashMap;
import java.util.LinkedList;
import tree.tree;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Deque;
import enshud.s4.compiler.*;
class carelist{
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
public  class subpro_write_visit extends Visitor{
	
	
	public void visit(tree methodtree) {
		switch(methodtree.treename) {
		case "program":
			methodtree.children.get(1).accept(this);
		
		break;
		case"block":
			methodtree.children.get(1).accept(this);/*副プログラムだけ行く*/
			break;
		case"constant":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			formal_stuck.get_type(methodtree.children.get(0).treename);
			if(methodtree.children.get(0).treename.equals("unsignint")) {
				/*符号なし整数*/
				output.write("PUSH	"+methodtree.children.get(0).terminal,methodtree.children.get(0).line,null);
			}
			else if(methodtree.children.get(0).treename.equals("trueorfalse")) {
				/*true false*/
				if(methodtree.children.get(0).terminal.equals("true")) {
				/*true*/
				output.write("PUSH	#0000",methodtree.children.get(0).line,null);
				}else {
					output.write("PUSH	#FFFF",methodtree.children.get(0).line,null);
				}
			}else if(methodtree.children.get(0).terminal.length()==3) {
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
					if(build.return_subpro_stacknum(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal)==-1) {
					output.write("LD GR2,="+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
					output.write("LD GR1,VAR,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("PUSH 0,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"PUSH "+methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
					}else {
						output.write("LD GR4,="+build.return_subpro_stacknum(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("ADDL GR4,GR5",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("LD GR1,0,GR4",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("PUSH 0, GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					}
				}else {
					/*添え字付き変数*/
					if(build.return_subpro_stacknum(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal)==-1) {
						output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"number of soezi");
						output.write("LD GR2,="+build.return_array_start(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("SUBA GR1,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"soezinosoutaiiti");
						output.write("LD GR2,="+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("ADDA GR2,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("LD GR1, VAR, GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("PUSH 0, GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"PUSH"+methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
					}else {
						output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"number of soezi");
						output.write("LD GR2, ="+build.return_subarray_start(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("SUBA GR1,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"soezinosoutaiiti");
						output.write("LD GR4, ="+build.return_subpro_stacknum(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),0,null);
						output.write("ADDA GR4,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"soezinosoutaiiti");
						output.write("ADDL GR4,GR5",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("LD GR1,0,GR4",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
						output.write("PUSH 0, GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					}
				}
			}
			
			if(methodtree.children.get(0).treename.equals("not")) {/* not 計算 返ってきたときにやる*/
				output.write("POP GR1",methodtree.children.get(0).line,"not enzan");
				output.write("XOR GR1,=#FFFF",methodtree.children.get(0).line,null);
				output.write("PUSH 0,GR1",methodtree.children.get(0).line,null);
				
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
	
				output.write("POP GR1",methodtree.children.get(1).line,null);/*いる*/
				output.write("POP GR2",methodtree.children.get(1).line,null);
				switch(methodtree.children.get(1+i*2).terminal) {
				
				case"*":
					output.write("CALL	MULT",methodtree.children.get(1).line,"mult");
					output.write("PUSH 0,GR2",methodtree.children.get(1).line,null);
					break;
				case"/":
					output.write("CALL	DIV",methodtree.children.get(1).line,"DIV");
					output.write("PUSH 0,GR2",methodtree.children.get(1).line,null);
					break;
				case"div":
					output.write("CALL	DIV",methodtree.children.get(1).line,"DIV");
					output.write("PUSH 0,GR2",methodtree.children.get(1).line,null);
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
			}
			break;
		case"simpleformal":
			for(int i=methodtree.children.size()-1;i>-1;i--) {/*逆から*/
				methodtree.children.get(i).accept(this);
			}
			if(methodtree.children.get(0).treename.equals("sign")) {/*符号あり*/
				if(methodtree.children.get(0).terminal.equals("-")) {
					output.write("POP GR2",methodtree.children.get(0).line,"mainasu");
					output.write("LD GR1,=0",methodtree.children.get(0).line,"mainasu");
					output.write("SUBA GR1,GR2",methodtree.children.get(0).line,"mainasu");
					output.write("PUSH 0,GR1",methodtree.children.get(0).line,"mainasu");
				}
				for(int i=0;i<(methodtree.children.size()-2)/2;i++){/*加法あり  スタック順が逆に*/
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
					
				}
			}else {/*符号の有無で関係史のtreeにおける位置が変わる*/
				for(int i=0;i<(methodtree.children.size()-1)/2;i++){/*加法あり  スタック順が逆に*/
					output.write("POP GR1",methodtree.children.get(1).line,null);
					output.write("POP GR2",methodtree.children.get(1).line,null);
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
					
				}
			
			}
			break;
		case"formal":
			for(int i=0;i<methodtree.children.size();i++) {
				methodtree.children.get(i).accept(this);
			}
			formal_stuck.push_formal();
			if(methodtree.children.size()>2){/*関係演算子あり*/
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
				
			}
			break;
		case"multivariable":/*いらなくね？*/
			for(int i=0;i<methodtree.children.size();i++) {
			output.write("LD GR2,"+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"hennsuunarabi");
			output.write("LD GR1,VAR,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
			output.write("PUSH 0,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"PUSH"+methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal);
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
				if(build.return_subpro_stacknum(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal)==-1) {/*メインの変数を呼ぶ*/
					output.write("LD GR2, ="+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"left");
					output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("ST GR1, VAR, GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				}else {
					output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("LD GR4,="+build.return_subpro_stacknum(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("ADDL GR4,GR5",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("ST GR1,0,GR4",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);/*これでいいの？*/
				}
			}else {
				if(build.return_subpro_stacknum(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal)==-1) {/*配列*/
					output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"number of soezi");
					output.write("LD GR2, ="+build.return_array_start(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("SUBA GR1,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"soezinosoutaiiti");
					output.write("LD GR2, ="+build.return_stack(methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("ADDA GR2,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("ST GR1, VAR, GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				}else {/*副プロ変数*/
					
					output.write("POP GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"number of soezi");
					output.write("LD GR2, ="+build.return_subarray_start(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("SUBA GR1,GR2",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"soezinosoutaiiti");
					output.write("LD GR4, ="+build.return_subpro_stacknum(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).children.get(0).terminal),0,null);
					output.write("ADDA GR4,GR1",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,"soezinosoutaiiti");
					output.write("ADDL GR4,GR5",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
					output.write("ST GR1,0,GR4",methodtree.children.get(0).children.get(0).children.get(0).children.get(0).line,null);
				}
				
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
							output.write("CALL	WRTCH",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output char");
						}else if(stuck_pop.equals("integer")) {/*数字*/
							output.write("POP GR2",methodtree.children.get(0).children.get(i).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).line,"output int");
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
			formal_stuck.get_type(build.return_type(carelist.check_prog(),methodtree.children.get(0).children.get(0).children.get(0).terminal));
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
			break;
		case"whiletext":
			output.rabel_write("LOOP"+rable_number.start_while()+"	NOP", 0, "start while");
			rable_number.add(1);
			methodtree.children.get(0).accept(this);
			formal_stuck.pop_formal();
			output.write("POP GR1", 0, "while");
			output.write("CPL GR1 , =#FFFF", 0, "while");
			output.write("JZE	ENDLP"+rable_number.back_while(), 0, "while");
			methodtree.children.get(1).accept(this);/*複合文*/
			output.write("JUMP	LOOP"+rable_number.back_while(), 0, "while");
			output.rabel_write("ENDLP"+rable_number.end_while()+"	NOP", 0, "while");
			break;
		case"subdel":
			/*複合分のみ*/
			carelist.change_prog(methodtree.children.get(0).children.get(0).children.get(0).terminal);
			output.rabel_write("PROG"+build.subrable_num(methodtree.children.get(0).children.get(0).children.get(0).terminal)+"	NOP", methodtree.children.get(0).children.get(0).children.get(0).line, "start subpro");
			output.write("SUBL GR8, ="+build.sub_variable_num(methodtree.children.get(0).children.get(0).children.get(0).terminal), methodtree.children.get(0).children.get(0).children.get(0).line, "get stack memory");
			output.write("LD GR5, GR8", methodtree.children.get(0).children.get(0).children.get(0).line, "GR5");
			for(int i=0;i<(build.kariparnum(methodtree.children.get(0).children.get(0).children.get(0).terminal));i++) {
				output.write("LD GR3, ="+(build.sub_variable_num(methodtree.children.get(0).children.get(0).children.get(0).terminal)+1+i),methodtree.children.get(0).children.get(0).children.get(0).line, "start karipara");
				/*仮パラメーター引き渡し*/
				output.write("ADDL GR3, GR5", methodtree.children.get(0).children.get(0).children.get(0).line, "GR5");
				output.write("LD GR3, 0, GR3 ", methodtree.children.get(0).children.get(0).children.get(0).line, "GR5");
				output.write("LD GR4, ="+i, methodtree.children.get(0).children.get(0).children.get(0).line, "GR5");
				output.write("ADDL GR4, GR5", methodtree.children.get(0).children.get(0).children.get(0).line, "GR5");
				output.write("ST GR3, 0, GR4", methodtree.children.get(0).children.get(0).children.get(0).line, "GR5");
			}
			methodtree.children.get(2).accept(this);
			output.write("ADDL GR8, ="+build.sub_variable_num(methodtree.children.get(0).children.get(0).children.get(0).terminal), methodtree.children.get(0).children.get(0).children.get(0).line, "get away stack memory");
			output.write("RET", 0, null);
			
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