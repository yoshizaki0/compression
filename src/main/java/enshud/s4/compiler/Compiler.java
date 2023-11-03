package enshud.s4.compiler;

import enshud.casl.CaslSimulator;
import enshud.s2.parser.Parser;
import enshud.s3.checker.Checker;
import enshud.s4.compiler.output;
import tree.tree;
import enshud.s4.compiler.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import Visit.write_code_visit;
import Visit.Visitor;
import Visit.Warning_visitor;
import Visit.build;
import Visit.check_visitor;
import Visit.make_chart_visit;
import Visit.make_table_visit;
import Visit.subpro_write_visit;
import Visit.Opt_main;
public class Compiler {

	/**
	 * サンプルmainメソッド．
	 * 単体テストの対象ではないので自由に改変しても良い．
	 */
	public static void main(final String[] args) {
		// Compilerを実行してcasを生成する
		new Compiler().run("data/ts/normal20.ts", "tmp/out.cas");
		CaslSimulator.run("tmp/out.cas", "tmp/out.ans");
		/*new Compiler().run("data/ts/semerr02.ts", "tmp/out.cas");
		CaslSimulator.run("tmp/out.cas", "tmp/out.ans");*/
		/*System.out.println("fin");*/
		/*new Compiler().run("data/ts/semerr02.ts", "tmp/out2.cas");*/
		/*CaslSimulator.run("tmp/out2.cas", "tmp/out2.ans");
		// 上記casを，CASLアセンブラ & COMETシミュレータで実行する*/
		
		
	}

	/**
	 * TODO
	 * 
	 * 開発対象となるCompiler実行メソッド．
	 * 以下の仕様を満たすこと．
	 * 
	 * 仕様:
	 * 第一引数で指定されたtsファイルを読み込み，CASL IIプログラムにコンパイルする．
	 * コンパイル結果のCASL IIプログラムは第二引数で指定されたcasファイルに書き出すこと．
	 * 構文的もしくは意味的なエラーを発見した場合は標準エラーにエラーメッセージを出力すること．
	 * （エラーメッセージの内容はChecker.run()の出力に準じるものとする．）
	 * 入力ファイルが見つからない場合は標準エラーに"File not found"と出力して終了すること．
	 * 
	 * @param inputFileName 入力tsファイル名
	 * @param outputFileName 出力casファイル名
	 */
	public void run(final String inputFileName, final String outputFileName) {
		 tree top =null;
		 top=new Checker().run(inputFileName);
		 if(top!=null) {
		/*どっちかエラーならcaslIIファイルは作成しない*/
			 File outfile = new File(outputFileName);
			 FileWriter content;
			 try {
				 content = new FileWriter(outfile);
				 BufferedWriter outtext =  new BufferedWriter(content);
				 output.get_filename(outtext);
		
			     Visitor viA = new make_table_visit();
			    /* Visitor viB = new check_visitor();*/
			     Visitor viC = new Warning_visitor();
				 Visitor viD = new make_chart_visit();
				 Visitor viE = new write_code_visit();
				 /*Visitor viE = new Opt_main();*/
				 Visitor viF = new subpro_write_visit();
			/*if(top!=null) {*/
				 top.accept(viA);
				/* top.accept(viB);*/
				 top.accept(viC);
				 top.accept(viD);
				 build.check_stack();
				 top.accept(viE);
				 top.accept(viF);
				 build.write_DS();
				 String_table.write_CHAT();
				 output.lib_write();
				/*String_table.show_str_table();*/
			
		/*	}*/
			/*最後のサブルーチン*/
				 String_table.clear_table();
				 rable_number.clear_num();
				 outtext.close();
		// TODO

			 }catch (Exception FileNotFoundException) {
			System.err.println("File not found");
			 }
			 build.clear_table();
		 }
		}
}

