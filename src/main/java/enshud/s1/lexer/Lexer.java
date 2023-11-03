package enshud.s1.lexer;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
public class Lexer {

	/**
	 * サンプルmainメソッド．
	 * 単体テストの対象ではないので自由に改変しても良い．
	 */
	public static void main(final String[] args) {
		// normalの確認
		new Lexer().run("data/pas/normal50.pas", "tmp/normal50.ts");
		/*new Lexer().run("data/pas/normal02.pas", "tmp/out2.ts");
		new Lexer().run("data/pas/normal11.pas", "tmp/out5.ts");*/
	}

	/**
	 * TODO
	 * 
	 * 開発対象となるLexer実行メソッド．
	 * 以下の仕様を満たすこと．
	 * 
	 * 仕様:
	 * 第一引数で指定されたpasファイルを読み込み，トークン列に分割する．
	 * トークン列は第二引数で指定されたtsファイルに書き出すこと．
	 * 正常に処理が終了した場合は標準出力に"OK"を，
	 * 入力ファイルが見つからない場合は標準エラーに"File not found"と出力して終了すること．
	 * 
	 * @param inputFileName 入力pasファイル名
	 * @param outputFileName 出力tsファイル名
	 */
	
	public void run(final String inputFileName, final String outputFileName) {
		
		int count=1;
		File file = new File(inputFileName);
		File outfile = new File(outputFileName);
		String str= null;
		BufferedReader br;
		FileWriter content;
		int flag=0;
		int onof=0;
		
		try {
			 br = new BufferedReader(new FileReader(file));
			 content = new FileWriter(outfile);
			 BufferedWriter outtext =  new BufferedWriter(content);
			 while((br.ready())){
				 	
				 					 str = br.readLine();
				 					 
									 String text= str + "\n";/*段落*/
									 if(flag==0) {
									flag=analysis(text,count,outtext);
									 }
									 if(flag==1)
									 {
										onof=serch(text) ;
										if(onof!=-1)
										{
											flag=0;
											text=text.substring(onof+1,text.length());
											analysis(text,count,outtext);
										}
									 }
									 if(flag==2)
									 {
										 return;
									 }
					
									
									
									count++;
									
									
								}
			
			br.close();
			outtext.close();
			content.close();
			
			System.out.println("OK");
			
			
			
		}
		catch (Exception FileNotFoundException) {
			System.err.println("File not found");
		}
		
	 

		// TODO

	}
	private int analysis(final String textline, int para,BufferedWriter outtext) {
		int head=0;
		int tail=1;
		String[] symbol = { "=", "<", ">","<=",">=","+","-","*","(",")","[","]",";",":","..",":=",",",".","/","'","{" };
		String[] singlesymbol = {"=","+","-","/","*","(",")","[","]",";",","};
		
		while(head<textline.length()) {
			
			if(Character.isLetter(textline.charAt(head)))
			{   
				if(tail!=textline.length())
				{
					while((Character.isLetter(textline.charAt(tail)))||(Character.isDigit(textline.charAt(tail))))
					{
						tail++;
						if(tail==textline.length())
						{
							break;
						}
					}
				}
				 String word = textline.substring(head, tail);
				 alphabet_recoder(word,para,outtext);
				 head=tail;
				 tail++;
				 
				 
			}
			
			else if(Character.isDigit(textline.charAt(head)))
			{
				if(textline.charAt(head)=='0')
				{
					String num = textline.substring(head, tail);
					number_recoder(num,para,outtext);
					head=tail;
					tail++;
				}
				else  
				{
					if(tail!=textline.length())
					{
						while(Character.isDigit(textline.charAt(tail)))
						{
							tail++;
							if(tail==textline.length())
							{
								break;
							}
						}
					}
					String num = textline.substring(head, tail);
					number_recoder(num,para,outtext);
					head=tail;
					tail++;
				}
			}
			else if(Arrays.asList(symbol).contains(String.valueOf(textline.charAt(head))))
			{
				
				if(Arrays.asList(singlesymbol).contains(String.valueOf(textline.charAt(head))))
				{
					String sym = textline.substring(head, tail);
					symbol_recoder(sym,para,outtext);
					head=tail;
					tail++;
				}
				else if(String.valueOf(textline.charAt(head)).equals("'")) {/*文字列*/		       
							if(tail!=textline.length()) 
							{
								while(!(String.valueOf(textline.charAt(tail)).equals("'")))
								{
								
									if(tail==textline.length())
									{
										/*System.err.println("syntax error 1");*/
										return 2;
									}
									tail++;
								}
							
							tail++;
							String tex = textline.substring(head, tail);
							text_recoder(tex,para,outtext);
							head=tail;
							tail++;
							}
							else
							{
								
								return 2;
							}
						}
				else if(String.valueOf(textline.charAt(head)).equals("{"))
				{ 
					if(tail!=textline.length()) 
					{
						
						while(!(String.valueOf(textline.charAt(tail)).equals("}")))
						{
							tail++;
							if(tail==textline.length())
							{
								/*System.err.println("syntax error 1");*/
								head=tail;
								
								return 1;
							}
							
						}
					
					tail++;
					head=tail;
					tail++;
					}
					else {
						head=textline.length();
						tail=head+1;
						return 1;
						
					}
				}
				else 
				{
					if(textline.charAt(head)=='<')
					{
						if(tail!=textline.length())
						{
							if(textline.charAt(tail)=='>')
							{   
								
								String sym = textline.substring(head, tail+1);
								symbol_recoder(sym,para,outtext);
								tail++;
								head=tail;
								tail++;
							}
							else if(textline.charAt(tail)=='=')
							{   
								
								String sym = textline.substring(head, tail+1);
								symbol_recoder(sym,para,outtext);
								tail++;
								head=tail;
								tail++;
								
							}
							else
							{
								String sym = textline.substring(head, tail);
								symbol_recoder(sym,para,outtext);
								head=tail;
								tail++;
							}
						}
						
						else
						{
							String sym = textline.substring(head, tail);
							symbol_recoder(sym,para,outtext);
							head=tail;
							tail++;
						}
						
					
					}
					if(textline.charAt(head)=='>')
					{
						if(tail!=textline.length()) 
						{
							if(textline.charAt(tail)=='=')
							{   
								
								String sym = textline.substring(head, tail+1);
								symbol_recoder(sym,para,outtext);
								 tail++;
								head=tail;
								tail++;
							}
							else
							{
								String sym = textline.substring(head, tail);
								symbol_recoder(sym,para,outtext);
								head=tail;
								tail++;
							}
						}
						 else
						 {
							String sym = textline.substring(head, tail);
							symbol_recoder(sym,para,outtext);
							head=tail;
							tail++;
						 }
						
					}
					if(textline.charAt(head)=='.')
					{
						if(tail!=textline.length())
						{
							if(textline.charAt(tail)=='.')
							{   
								
								String sym = textline.substring(head, tail+1);
								symbol_recoder(sym,para,outtext);
								 tail++;
								head=tail;
								tail++;
							}
							else
							{
								String sym = textline.substring(head, tail);
								symbol_recoder(sym,para,outtext);
								head=tail;
								tail++;
							}
						}
						else
						{
							String sym = textline.substring(head, tail);
							symbol_recoder(sym,para,outtext);
							head=tail;
							tail++;
						}
					}
					if(textline.charAt(head)==':')
					{
						if(tail!=textline.length())
						{
							if(textline.charAt(tail)=='=')
							{   
								
								String sym = textline.substring(head, tail+1);
								symbol_recoder(sym,para,outtext);
								 tail++;
								head=tail;
								tail++;
							}
							else
							{
								String sym = textline.substring(head, tail);
								symbol_recoder(sym,para,outtext);
								head=tail;
								tail++;
							}
						}
						else
						{
							
							String sym = textline.substring(head, tail);
							symbol_recoder(sym,para,outtext);
							head=tail;
							tail++;
						}
					}
					
				}
			}
			else
			{
				if(textline.charAt(head)==' ')
				{
					if(tail!=textline.length())
					{
						while(textline.charAt(tail)==' ')
						{
							tail++;
							if(tail==textline.length())
							{
								break;
							}
						}
						
					}
					head=tail;
					tail++;
				}
				else if(textline.charAt(head)=='\r')
				{
					if(tail!=textline.length())
					{
						if(textline.charAt(tail)=='\n')
						{
							head++;
							tail++;
						}
					}
					head++;
					tail++;
				}
				else if(textline.charAt(head)=='\n')
				{
					head++;
					tail++;
				}
				
				else 
				{
				/*System.err.println("syntax error 3");*/
				return 2;
				}
			}
			
			
		}
		return 0;
		
	}
		
	
	private void alphabet_recoder(final String word, int para,BufferedWriter outtext) {
		 int ID;
		 String sname;
		
		
			
		 switch(word){
			 case "and" :
				 ID=0;
				 sname="SAND";
				 break;
				 
			 case "array" :
				 ID=1;
				 sname="SARRAY";
				 break;
			 case "begin" :
				 ID=2;
				 sname="SBEGIN";
				 break;
			 case "boolean" :
				 ID=3;
				 sname="SBOOLEAN";
				 break;
			 case "char" :
				 ID=4;
				 sname="SCHAR";
				 break;
			 case "div" :
				 ID=5;
				 sname="SDIVD";
				 break;
			 case "do" :
				 ID=6;
				 sname="SDO";
				 break;
			 case "else" :
				 ID=7;
				 sname="SELSE";
				 break;
			 case "end" :
				 ID=8;
				 sname="SEND";
				 break;
			 case "false" :
				 ID=9;
				 sname="SFALSE";
				 break;
			 case "if" :
				 ID=10;
				 sname="SIF";
				 break;
			 case "integer" :
				 ID=11;
				 sname="SINTEGER";
				 break;
			 case "mod" :
				 ID=12;
				 sname="SMOD";
				 break;
			 case "not" :
				 ID=13;
				 sname="SNOT";
				 break;
			 case "of" :
				 ID=14;
				 sname="SOF";
				 break;
			 case "or" :
				 ID=15;
				 sname="SOR";
				 break;
			 case "procedure" :
				 ID=16;
				 sname="SPROCEDURE";
				 break;
			 case "program" :
				 ID=17;
				 sname="SPROGRAM";
				 break;
			 case "readln" :
				 ID=18;
				 sname="SREADLN";
				 break;
			 case "then" :
				 ID=19;
				 sname="STHEN";
				 break;
			 case "true" :
				 ID=20;
				 sname="STRUE";
				 break;
			 case "var" :
				 ID=21;
				 sname="SVAR";
				 break;
			 case "while" :
				 ID=22;
				 sname="SWHILE";
				 break;
			 case "writeln" :
				 ID=23;
				 sname="SWRITELN";
				 break;
			 default:
				 ID=43;
				 sname="SIDENTIFIER";
				 break;
				 
		 }
		 recoder(word,sname,ID,para,outtext);
		 
	}
	private void number_recoder(final String word, int para,BufferedWriter outtext) {
		
		recoder(word,"SCONSTANT",44,para,outtext);
	}
	private void symbol_recoder(final String word, int para,BufferedWriter outtext) {
		int ID;
		String sname;
		
		switch(word) {
		   
		case "<>":
			ID=25;
			sname="SNOTEQUAL";
			break;
		case "<=":
			ID=27;
			sname="SLESSEQUAL";
			break;
		case">=":
			ID=28;
			sname="SGREATEQUAL";
			break;
		case"..":
			ID=39;
			sname="SRANGE";
			break;
		case":=":
		    ID=40;
		    sname="SASSIGN";
		    break;
		case"=":
			ID=24;
			sname="SEQUAL";
			break;
		case"<":
			ID=26;
			sname="SLESS";
			break;
		case">":
			ID=29;
			sname="SGREAT";
			break;
		case"+":
			ID=30;
			sname="SPLUS";
			break;
		case"-":
			ID=31;
			sname="SMINUS";
			break;
		case"*":
			ID=32;
			sname="SSTAR";
			break;
		case"(":
			ID=33;
			sname="SLPAREN";
			break;
		case")":
			ID=34;
			sname="SRPAREN";
			break;
		case"[":
			ID=35;
			sname="SLBRACKET";
			break;
		case"]":
			ID=36;
			sname="SRBRACKET";
			break;
		case";":
			ID=37;
			sname="SSEMICOLON";
			break;
		case":":
			ID=38;
			sname="SCOLON";
			break;
		case",":
			ID=41;
			sname="SCOMMA";
			break;
		case".":
			ID=42;
			sname="SDOT";
			break;
		case"/":
			ID=5;
			sname="SDIVD";
			break;
		 default:
			 ID=0;
			 sname="Unkown";
			 break;
		
			
		   }
		recoder(word,sname,ID,para,outtext);
	}
		
	private void text_recoder(final String word, int para,BufferedWriter outtext) {
		
		recoder(word,"SSTRING",45,para,outtext);
		}
		
		
	private void recoder(final String word ,final String sname,int id,int paragrah ,BufferedWriter outtext )
	{
		try {
				outtext.write(word+'	'+sname+"	"+id+"	"+paragrah);
				outtext.newLine();
				outtext.flush();
		     }
		catch(IOException e){
			  					System.out.println(e);
			  				}
		
	}
	private  int serch(String text) {
		int posit=0;
		if(posit<text.length()) {
		while(text.charAt(posit)=='}') {
			posit++;
		
			if(posit==text.length()) 		{
												return -1;
									  		}
										}
									}
		return posit;
		
	}
	
	
}
