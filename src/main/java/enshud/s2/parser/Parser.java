package enshud.s2.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import tree.tree;
import tslist.tslist;
class errofts{
	static int swich=0;
	static int errline=-1;
	static String errword;
	public static void register(String code,int line){
		if(swich==0) {
			swich=1;
			errline=line;
			errword=code;
		}
	}
	public static int lines() {
		if(swich==1) {
			return errline;
		}
		else {
			return -1;
		}
	}
	public static String word() {
		if(swich==1) {
			return errword;
		}
		else {
			return null;
		}
	}
	public static  void clear(){
		swich=0;
		errline=-1;
	}
	
}
/*class tslist{
	String code;
	int id,line;
	tslist(String text){
		String[] contents = new String[4];
		contents = text.split("\t");
		this.code=contents[0];
		this.id = Integer.parseInt(contents[2]);
		this.line = Integer.parseInt(contents[3]);
     
	}
	
}*/
class textread{
	static int state=0;
	
	public  static tslist next(ArrayList<tslist> content){ 
		state++;
		if(state< content.size()) {
		return content.get(state);
		}
		else {
			return null;
		}
	}
	public static tslist same(ArrayList<tslist> content){   
		if(state< content.size()) {
	    return content.get(state);
        }
	    else {
	    	return null;
	    }
	}
	public static tslist back(ArrayList<tslist> content){
		if(state>0) {
			state=state-1;
		    return content.get(state);
	        }
		    else {
		    	return null;
		    }
	}
	
	public static void reset() {
		state=0;
	}
	
}
/* class tree{
	String treename;
	String terminal=null;
	ArrayList<tree> children = new ArrayList<>();
	tree(String name, String term, ArrayList<tree> child  )
	{
		this.treename=name;
		this.terminal=term;
		this.children=child;
		
	}
}*/
public class Parser {

	/**
	 * サンプルmainメソッド．
	 * 単体テストの対象ではないので自由に改変しても良い．
	 */
	public static void main(final String[] args) {
		// normalの確認
		new Parser().run("data/ts/semerr07.ts");
		/*new Parser().run("data/ts/normal02.ts");

		// synerrの確認
		new Parser().run("data/ts/synerr01.ts");
		new Parser().run("data/ts/synerr02.ts");*/
	}
	/**
	 * TODO
	 * 
	 * 開発対象となるParser実行メソッド．
	 * 以下の仕様を満たすこと．
	 * 
	 * 仕様:
	 * 第一引数で指定されたtsファイルを読み込み，構文解析を行う．
	 * 構文が正しい場合は標準出力に"OK"を，正しくない場合は"Syntax error: line"という文字列とともに，
	 * 最初のエラーを見つけた行の番号を標準エラーに出力すること （例: "Syntax error: line 1"）．
	 * 入力ファイル内に複数のエラーが含まれる場合は，最初に見つけたエラーのみを出力すること．
	 * 入力ファイルが見つからない場合は標準エラーに"File not found"と出力して終了すること．
	 * 
	 * @param inputFileName 入力tsファイル名
	 */
	public tree run(final String inputFileName) {
		tree past=null;
		File file = new File(inputFileName);
		ArrayList<tslist> content = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((br.ready())) {
				
				content.add(new tslist(br.readLine()));
				}
			br.close();
			textread.reset();
			past=program(content);
			if(errofts.lines()!=-1) {
				System.err.println("Syntax error: line "+String.valueOf(errofts.lines()));
				/*System.err.println(errofts.word());*/
			}
			else {
				System.out.println("OK");  	
				return past;
			}
			
			errofts.clear();
			return null;
			}	
		catch (Exception FileNotFoundException) {
			System.err.println("File not found");
			return null;
		}
	}
	public tree program(ArrayList<tslist> content) {
		ArrayList<tree> prog = new ArrayList<>();
		
		if(textread.same(content).id!=17) {/*program*/
			errofts.register(textread.same(content).code,textread.same(content).line);
			/*System.err.println("Syntax error: line"+textread.same(content).line);*/
			return null;
		}
		textread.next(content);
		prog.add(programname(content));/*プログラム名*/
		if(textread.same(content).id!=37) {/*;*/
			errofts.register(textread.same(content).code,textread.same(content).line);
			/*System.err.println("Syntax error: line"+textread.same(content).line);*/
			return null;
		}
		textread.next(content);
		prog.add(block(content));
		prog.add(compound(content));
		if(textread.same(content).id!=42) {/*.*/
			errofts.register(textread.same(content).code,textread.same(content).line);
			/*System.err.println("Syntax error: line"+textread.same(content).line);*/
			return null;
		}
		textread.next(content);
		return  new tree("program",null,prog);
		
	}
	public tree programname(ArrayList<tslist> content) {/*プログラム名*/
		ArrayList<tree> programname = new ArrayList<>();
		programname.add(identifier(content));
		return new tree("programname",null,programname);
	}
	public tree identifier(ArrayList<tslist> content) {/*識別子*/
		tslist signal;
		if(textread.same(content).id!=43) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			/*System.err.println("Syntax error: line"+textread.same(content).line);*/
			return null;
		}
		signal=textread.same(content);
		textread.next(content);
		return  new tree("identifier",signal,null);
	}
	public tree block(ArrayList<tslist> content) {/*ブロック分*/
		ArrayList<tree> block = new ArrayList<>();
		block.add(vardel(content));
		block.add(multisubdel(content));
		return new tree("block",null,block);
	}
	public tree compound(ArrayList<tslist> content) {/*複合分*/
		ArrayList<tree> comp = new ArrayList<>();
		if(textread.same(content).id!=2) {/*begin*/
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		textread.next(content);
		comp.add(texts(content));
		if(textread.same(content).id!=8) {/*end*/
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		textread.next(content);
		return new tree("compound",null,comp);
		
	}
	public tree vardel(ArrayList<tslist> content) {/*変数宣言*/
		ArrayList<tree> vardel = new ArrayList<>();
		if(textread.same(content).id==21) {
			textread.next(content);
			vardel.add(multivardel(content));
		}
		return new tree("vardeclare",null,vardel);
	}
	public tree multisubdel(ArrayList<tslist> content) {/*副プログラム宣言群*/
		ArrayList<tree> multsubdel = new ArrayList<>();
		while(textread.same(content).id==16) {
			multsubdel.add(subdel(content));
			if(textread.same(content).id!=37) {
				errofts.register(textread.same(content).code,textread.same(content).line);
				return null;
			}
			textread.next(content);
		}
		return new tree("multisubdel",null,multsubdel);
	}
	public tree multivardel(ArrayList<tslist> content) {/*変数宣言の並び*/
		ArrayList<tree> multvardel = new ArrayList<>();
		do{
			multvardel.add(multivariable(content));
			if(textread.same(content).id!=38) {
				errofts.register(textread.same(content).code,textread.same(content).line);
				return null;
			}
			textread.next(content);
			multvardel.add(tupyof(content));
			if(textread.same(content).id!=37) {
				errofts.register(textread.same(content).code,textread.same(content).line);
				return null;
			}
			textread.next(content);
		}while(textread.same(content).id==43);
		return new tree("multivardel",null,multvardel);
	}
	public tree subdel(ArrayList<tslist> content) {/*副プログラム宣言*/
		ArrayList<tree> subdel = new ArrayList<>();
		subdel.add(subprohead(content));
		subdel.add(vardel(content));
		subdel.add(compound(content));
		return new tree("subdel",null,subdel);
	}
	public tree multivariable(ArrayList<tslist> content) {/*変数名の並び*/
		ArrayList<tree> mulvar = new ArrayList<>();
		mulvar.add(variablename(content));
		while(textread.same(content).id==41) {
			textread.next(content);
			mulvar.add(variablename(content));
		}
		return new tree("multivariable_name",null,mulvar);
	}
	public tree tupyof(ArrayList<tslist> content) {/*型*/
		ArrayList<tree> tupyof = new ArrayList<>();
		if(textread.same(content).id==11||textread.same(content).id==4||textread.same(content).id==3) {
			/*標準型確定*/
			tupyof.add(standard(content));
		}
		else if(textread.same(content).id==1) {/*配列型*/
			tupyof.add(arrangement(content));
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		return new tree("tupyof",null,tupyof);
	}
    public tree standard(ArrayList<tslist> content) {/*標準型*/
		tslist signal;
		if(textread.same(content).id==11||textread.same(content).id==4||textread.same(content).id==3) {
			signal=textread.same(content);
			textread.next(content);
			return new tree("standard",signal,null);
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
	}
    public tree arrangement(ArrayList<tslist> content) {/*配列型*/
    	ArrayList<tree> arrange = new ArrayList<>();
    	if(textread.same(content).id!=1) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
    	}
    	textread.next(content);
    	if(textread.same(content).id!=35) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
    	}
    	textread.next(content);
    	arrange.add(suffix_min(content));
    	if(textread.same(content).id!=39) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
    	}
    	textread.next(content);
    	arrange.add(suffix_max(content));
    	if(textread.same(content).id!=36) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
    	}
    	textread.next(content);
    	if(textread.same(content).id!=14) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
    	}
    	textread.next(content);
    	arrange.add(standard(content));
    	return new tree("arrangement",null,arrange);
	}
    public tree suffix_min(ArrayList<tslist> content) {/*添え字の最小*/
    	tslist signal;
		if(textread.same(content).id!=44) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		signal=textread.same(content);
		textread.next(content);
		return new tree("suffix_min",signal,null);
   	}
    public tree suffix_max(ArrayList<tslist> content) {/*添え字の最大*/
    	tslist signal;
		if(textread.same(content).id!=44) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		signal=textread.same(content);
		textread.next(content);
		return new tree("suffix_max",signal,null);
   	}
    public tree subprohead(ArrayList<tslist> content) {/*副プログラム頭部*/
    	ArrayList<tree> subprohead = new ArrayList<>();
    	if(textread.same(content).id!=16) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
    	}
    	textread.next(content);
    	subprohead.add(proceduralname(content));
    	subprohead.add(calipara(content));
    	if(textread.same(content).id!=37) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
    	}
    	textread.next(content);
    	return new tree("subprohead",null,subprohead);
		
	}
    
    public tree calipara(ArrayList<tslist> content) {/*仮パラメーター*/
    	ArrayList<tree> calip = new ArrayList<>();
    	if(textread.same(content).id==33) {
    		textread.next(content);
			calip.add(mulcalipara(content));
			if(textread.same(content).id!=34) {
				errofts.register(textread.same(content).code,textread.same(content).line);
				return null;
	    	}
			textread.next(content);
    	}
    	return new tree("calipara",null,calip);
    }
    public tree mulcalipara(ArrayList<tslist> content) {/*仮パラメーターの並び*/
    	ArrayList<tree> mulcalip = new ArrayList<>();
    	mulcalip.add(mulcalip_name(content));
    	if(textread.same(content).id!=38) {
    		errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
    	}
    	textread.next(content);
    	mulcalip.add(standard(content));
    	while(textread.same(content).id==37) {
    		textread.next(content);
    		mulcalip.add(mulcalip_name(content));
        	if(textread.same(content).id!=38) {
        		errofts.register(textread.same(content).code,textread.same(content).line);
    			return null;
        	}
        	textread.next(content);
        	mulcalip.add(standard(content));
    	}
    	return new tree("mulcalipara",null,mulcalip);
    }
    public tree mulcalip_name(ArrayList<tslist> content) {/*仮パラメーター名の並び*/
    	ArrayList<tree> mulname = new ArrayList<>();
    	mulname.add(calip_name(content));
    	while(textread.same(content).id==41) {
    		textread.next(content);
    		mulname.add(calip_name(content));
    	}
    	return new tree("mulcalip_name",null,mulname);
    }
    public tree calip_name(ArrayList<tslist> content) {/*仮パラメーター名*/
    	ArrayList<tree> name = new ArrayList<>();
    	name.add(identifier(content));
    	return new tree("calip_name",null,name);
    }
	public tree texts(ArrayList<tslist> content) {/*文の並び*/
		ArrayList<tree> texts = new ArrayList<>();
		while(textread.same(content).id==10||textread.same(content).id==22||textread.same(content).id==43||textread.same(content).id==18||textread.same(content).id==23||textread.same(content).id==2) {
			/*文のFirst集合*/
			texts.add(onetext(content));
			if(textread.same(content).id!=37) {/*;*/
				errofts.register(textread.same(content).code,textread.same(content).line);
				/*System.err.println("Syntax error: line"+textread.same(content).line);*/
				return null;
			}
			textread.next(content);
		}
		return new tree("texts",null,texts);
		
	}
	public tree onetext(ArrayList<tslist> content) {/*文*/
		ArrayList<tree> one = new ArrayList<>();
		if(textread.same(content).id==10) {/*if*/
			one.add(iftext(content));
		}
		else if(textread.same(content).id==22) {/*while*/
			one.add(whiletext(content));
		}
		else {
			one.add(basic(content));
			
		}
		return new tree("onetext",null,one);
	}
	public tree iftext(ArrayList<tslist> content) {/*if文*/
		ArrayList<tree> iftext = new ArrayList<>();
		if(textread.same(content).id==10) {
			textread.next(content);
			iftext.add(formal(content));
			if(textread.same(content).id!=19) {
				errofts.register(textread.same(content).code,textread.same(content).line);
				return null;
			}
			textread.next(content);
			iftext.add(compound(content));
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		if(textread.same(content).id==7) {/*else*/
			iftext.add(elsetext(content));
		}
		return new tree("iftex",null,iftext);
	}
	public tree whiletext(ArrayList<tslist> content) {/*while文*/
		ArrayList<tree> whiletext = new ArrayList<>();
		if(textread.same(content).id==22) {
			textread.next(content);
			whiletext.add(formal(content));
			if(textread.same(content).id!=6) {
				errofts.register(textread.same(content).code,textread.same(content).line);
				return null;
			}
			textread.next(content);
			whiletext.add(compound(content));
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		return new tree("whiletext",null,whiletext);
	}
	public tree elsetext(ArrayList<tslist> content) {/*else文*/
		ArrayList<tree> elsetext = new ArrayList<>();
		if(textread.same(content).id==7) {
			textread.next(content);
			elsetext.add(compound(content));
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		return new tree("elsetext",null,elsetext);
	}
	public tree basic(ArrayList<tslist> content) {/*入出力のみ*/
		ArrayList<tree> basic = new ArrayList<>();
		if(textread.same(content).id==18||textread.same(content).id==23) {/*入出力文確定*/
			basic.add(inout(content));
		}
		else if(textread.same(content).id==2) {/*複合分確定*/
			basic.add(compound(content));
		}
		
		else if(textread.same(content).id==43) {/*LL(2)解析*/
			textread.next(content);
			if(textread.same(content).id==35||textread.same(content).id==40){/*代入文*/
				textread.back(content);
				basic.add(assign(content));
			}
			else {/*手続き文*/
				textread.back(content);
				basic.add(procedural(content));
			}
			
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			/*System.err.println("Syntax error: line"+textread.same(content).line);*/
			return null;
		}
		return new tree("basic",null,basic);
	}
	public tree assign(ArrayList<tslist> content) {/*代入文*/
		ArrayList<tree> assign = new ArrayList<>();
		assign.add(leftformal(content));
		if(textread.same(content).id!=40) {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		textread.next(content);
		assign.add(formal(content));
		return new tree("assign",null,assign);
	}
	public tree procedural(ArrayList<tslist> content) {/*手続き文*/
		ArrayList<tree> procedural = new ArrayList<>();
		procedural.add(proceduralname(content));
		if(textread.same(content).id==33) {
			textread.next(content);
			procedural.add(formals(content));
			if(textread.same(content).id!=34) {
				errofts.register(textread.same(content).code,textread.same(content).line);
				return null;
			}
			textread.next(content);
		}
		return new tree("procedural",null,procedural);
	}
	public tree leftformal(ArrayList<tslist> content) {/*左辺*/
		ArrayList<tree> left= new ArrayList<>();
		left.add(variable(content));
		return new tree("leftformal",null,left);
	}
	public tree proceduralname(ArrayList<tslist> content) {/*手続き名*/
		ArrayList<tree> proname= new ArrayList<>();
		proname.add(identifier(content));
		return new tree("proceduralname",null,proname);
	}
	public tree inout(ArrayList<tslist> content) {/*writelnのみ*/
		ArrayList<tree> inout = new ArrayList<>();
		tslist signal;
		if(textread.same(content).id==23) {/*writeln*/
			signal=textread.same(content);
			textread.next(content);
			if(textread.same(content).id==33) {/*(がある*/
				textread.next(content);
				inout.add(formals(content));
				if(textread.same(content).id!=34) {
					errofts.register(textread.same(content).code,textread.same(content).line);
					return null;
				}
				textread.next(content);
				return new tree("output",null,inout);
			}else {
				
				return new tree("output",signal,null);
			}
			
			/*writeを呼んだわけだがどうするか*/
		}
		else if(textread.same(content).id==18) {/*readln　求追加*/
				textread.next(content);
				if(textread.same(content).id==33) {/*（がある*/
					textread.next(content);
					inout.add( multivariables(content));
					if(textread.same(content).id!=34) {
						errofts.register(textread.same(content).code,textread.same(content).line);
						/*System.err.println("Syntax error: line"+textread.same(content).line);*/
						return null;
					}
				textread.next(content);
				}
				return new tree("read",null,inout);
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			/*System.err.println("Syntax error: line"+textread.same(content).line);*/
			return null;
		}
			
	
	}
	public tree multivariables(ArrayList<tslist> content) {/*変数の並び*/
		ArrayList<tree> mulvar = new ArrayList<>();
		mulvar.add(variable(content));
		while(textread.same(content).id==41) {
			textread.next(content);
			mulvar.add(variable(content));
		}
		return new tree("multivariable",null,mulvar);
	}
	public tree formals(ArrayList<tslist> content) {/*式の並び*/
		ArrayList<tree> formals = new ArrayList<>();
		formals.add(formal(content));
		while(textread.same(content).id==41){
			textread.next(content);
			formals.add(formal(content));
		}
		return new tree("formals",null,formals);
	}
	public tree formal(ArrayList<tslist> content) {/*式*/
		ArrayList<tree> formal = new ArrayList<>();
		formal.add(simpleformal(content));
		if(textread.same(content).id>23&&textread.same(content).id<30)
		{
			formal.add(relational(content));
			formal.add(simpleformal(content));
		}
		return new tree("formal",null,formal);
	}
	public tree simpleformal(ArrayList<tslist> content) {/*単純式*/
		ArrayList<tree> simple = new ArrayList<>();
		if(textread.same(content).id==30||textread.same(content).id==31) {
			simple.add(sign(content));
		}
		simple.add(term(content));
		while(textread.same(content).id==30||textread.same(content).id==31||textread.same(content).id==15) {
			/*加法演算子*/
			simple.add(additive(content));
			simple.add(term(content));
		}
		return new tree("simpleformal",null,simple);
	}
	public tree relational(ArrayList<tslist> content) {/*関係演算子*  途中*/
		if(textread.same(content).id>23&&textread.same(content).id<30) {
			   tslist signal;
			   signal=textread.same(content);
			   textread.next(content);
			   return new tree("relational",signal,null);
			}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			/*System.err.println("Syntax error: line"+textread.same(content).line);*/
			return null;
		}
				
			
		}
	public tree sign(ArrayList<tslist> content) {/*符号*/
		tslist signal;
		if(textread.same(content).id==30||textread.same(content).id==31)
		{
			signal=textread.same(content);
			textread.next(content);
			return new tree("sign",signal,null);
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		
	}
	public tree term(ArrayList<tslist> content) {/*項*/
		ArrayList<tree> term = new ArrayList<>();
		term.add(factor(content));
		while(textread.same(content).id==0||textread.same(content).id==5||textread.same(content).id==12||textread.same(content).id==32) {
			term.add(multiplicative(content));
			term.add(factor(content));
		}
		return new tree("term",null,term);
	}
	public tree multiplicative(ArrayList<tslist> content) {/*乗法演算子*/
		tslist signal;
		if(textread.same(content).id==0||textread.same(content).id==5||textread.same(content).id==12||textread.same(content).id==32) {
			signal=textread.same(content);
			textread.next(content);
			return new tree("multiplicative",signal,null);
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			/*System.err.println("Syntax error: line"+textread.same(content).line);*/
			return null;
		}
	}
	public tree factor(ArrayList<tslist> content) {/*因子*/
		ArrayList<tree> factor = new ArrayList<>();
		if(textread.same(content).id==43) {/*変数確定*/
			factor.add(variable(content));
		}
		else if(textread.same(content).id==44||textread.same(content).id==45||textread.same(content).id==9||textread.same(content).id==20) {
			/*定数確定*/
			factor.add(constant(content));
		}
		else if(textread.same(content).id==33) {/*式確定*/
			textread.next(content);
			factor.add(formal(content));
			if(textread.same(content).id!=34) {
				errofts.register(textread.same(content).code,textread.same(content).line);
				return null;
			}
			textread.next(content);
		}
		else if(textread.same(content).id==13) {/*因子確定 notを木に含むかどうか*/
			tslist not_signal;/*コンパイラ加筆*/
			not_signal=textread.same(content);
			factor.add(new tree("not",not_signal,null));
			textread.next(content);
			factor.add(term(content));
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
		return new tree("insi",null,factor);
	}
	
	public tree additive(ArrayList<tslist> content) {/*加法演算子*/
		tslist signal;
		if(textread.same(content).id==30||textread.same(content).id==31||textread.same(content).id==15) {
			signal=textread.same(content);
			textread.next(content);
			return new tree("additive",signal,null);
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
	}
	public tree variable(ArrayList<tslist> content) {/*変数 LL(2)に変更(課題３)*/
		ArrayList<tree> variable = new ArrayList<>();
		textread.next(content);
		if(textread.same(content).id==35) {/*添え字付き変数*/
			textread.back(content);
			variable.add(suffixvariable(content));
		}else {/*純変数*/
			textread.back(content);
			variable.add(purevariable(content));
		}
		return new tree("variable",null,variable);
		
	}
	public tree purevariable(ArrayList<tslist> content) {/*純変数*/
		ArrayList<tree> purevariable = new ArrayList<>();
		purevariable.add(variablename(content));
		return new tree("purevariable",null,purevariable);
		
	}
	public tree suffixvariable(ArrayList<tslist> content) {/*添え字付き変数*/
		ArrayList<tree> suffivariable = new ArrayList<>();
		suffivariable.add(variablename(content));
		if(textread.same(content).id==35) {/*添え字付き変数*/
			textread.next(content);
			suffivariable.add(suffix(content));
			if(textread.same(content).id!=36) {
				errofts.register(textread.same(content).code,textread.same(content).line);
				return null;
			}
			textread.next(content);
		}
		return new tree("suffixvariable",null,suffivariable);
		
	}
	public tree constant(ArrayList<tslist> content) {/*定数*/
		ArrayList<tree> constant = new ArrayList<>();
		if(textread.same(content).id==44) {/*符号なし整数確定*/
			constant.add(unsignint(content));
		}
		else if(textread.same(content).id==45) {/*文字列確定*/
			constant.add(stringof(content));
		}
		else if(textread.same(content).id==9||textread.same(content).id==20) {/*true false  コンパイラちぇんぎ*/
			constant.add(trueorfalse(content));
		}
		return new tree("constant",null,constant);
	}
	public tree trueorfalse(ArrayList<tslist> content) {/*treu or false*/
		if(textread.same(content).id==9||textread.same(content).id==20) {
			tslist signal;
			signal=textread.same(content);
			textread.next(content);
			return new tree("trueorfalse",signal,null);
		}else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}	
	}
	public tree variablename(ArrayList<tslist> content) {/*変数名*/
		ArrayList<tree> variname = new ArrayList<>();
		variname.add(identifier(content));
		return new tree("variablename",null,variname);
	}
	public tree suffix(ArrayList<tslist> content) {/*添え字*/
		ArrayList<tree> suffix = new ArrayList<>();
		suffix.add(formal(content));
		return new tree("suffix",null,suffix);
	}
	public tree unsignint(ArrayList<tslist> content) {/*符号なし整数*/
		tslist signal;
		if(textread.same(content).id==44) {
			signal=textread.same(content);
			textread.next(content);
			return new tree("unsignint",signal,null);
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}	
	}
	public tree stringof(ArrayList<tslist> content) {/*文字列*/
		tslist signal;
		if(textread.same(content).id==45) {
			signal=textread.same(content);
			textread.next(content);
			return new tree("stringof",signal,null);
		}
		else {
			errofts.register(textread.same(content).code,textread.same(content).line);
			return null;
		}
	}
}
