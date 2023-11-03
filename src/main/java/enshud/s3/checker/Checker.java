package enshud.s3.checker;

import enshud.s2.parser.Parser;
import tree.tree;
import Visit.Visitor;
import Visit.build;
import Visit.make_table_visit;
import Visit.check_visitor;
import Visit.Warning_visitor;
import Visit.errmessage;
class SyntaxValidationVisitor{
	
}
class Symbol_table {
	
	
}
public class Checker {

	/**
	 * サンプルmainメソッド．
	 * 単体テストの対象ではないので自由に改変しても良い．
	 */
	public static void main(final String[] args) {
		// normalの確認
		new Checker().run("data/ts/normal02.ts");
		/*new Checker().run("data/ts/semerr03.ts");*/
		/*new Checker().run("data/ts/normal18.ts");*/

		// synerrの確認
		/*new Checker().run("data/ts/synerr01.ts");
		new Checker().run("data/ts/synerr02.ts");*/

		// semerrの確認
		/*new Checker().run("data/ts/semerr08.ts");
		/*new Checker().run("data/ts/semerr04.ts");*/
	}

	/**
	 * TODO
	 * 
	 * 開発対象となるChecker実行メソッド．
	 * 以下の仕様を満たすこと．
	 * 
	 * 仕様:
	 * 第一引数で指定されたtsファイルを読み込み，意味解析を行う．
	 * 意味的に正しい場合は標準出力に"OK"を，正しくない場合は"Semantic error: line"という文字列とともに，
	 * 最初のエラーを見つけた行の番号を標準エラーに出力すること （例: "Semantic error: line 6"）．
	 * また，構文的なエラーが含まれる場合もエラーメッセージを表示すること（例： "Syntax error: line 1"）．
	 * 入力ファイル内に複数のエラーが含まれる場合は，最初に見つけたエラーのみを出力すること．
	 * 入力ファイルが見つからない場合は標準エラーに"File not found"と出力して終了すること．
	 * 
	 * @param inputFileName 入力tsファイル名
	 */
	public tree run(final String inputFileName) {
        tree top =null; 
        Visitor viA = new make_table_visit();
        Visitor viB = new check_visitor();
        Visitor viC = new Warning_visitor();
		top =new Parser().run(inputFileName);
		if(top!=null) {
			top.accept(viA);
			top.accept(viB);
			top.accept(viC);
		}
		/*build.nousing_warning();*/
		if(errmessage.on==1) {
			build.clear_table();
			return null;
		}else {
		build.clear_table();
		return top;
		}
		
	}
}
