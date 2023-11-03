package Visit;

import java.util.ArrayList;
import java.util.HashMap;
import table.table;
import tslist.tslist;
import java.util.function.BiConsumer;

import tree.tree;
import enshud.s3.checker.*;
import enshud.s4.compiler.output;
class subpro{
	String para_num;
	String rabel_num;
	subpro(String par_num,String rab_num){
		para_num=par_num;
		rabel_num=rab_num;
	}
}
public class build {
	static HashMap<String,HashMap<String,table>> variable_list = new HashMap<>();
	static HashMap<String,subpro> paranum_list = new HashMap<>();
	 static String nowprogram=null;
	 static String nowtype=null;
	 static int kata;
	 static int stacknumber=0;
	 static int substacknum=0;
	 static int subrabel=0;
	 public static void next_table(String name) {/*新しい変数表*/
		 variable_list.put(name,new HashMap<>());
		 nowprogram=name;
	 }
	 public static void next_kariparnum(String proname,String varia_name) {/*仮パラスタック 振る*/
		 variable_list.get(proname).get(varia_name).stacknum=substacknum;
		 substacknum++;
	 }
	 public static void end_karipara(String proname) {/*仮パラスタック  個数*/
		 int max;
		 max=substacknum;
		paranum_list.put(proname,new subpro(String.valueOf(max),String.valueOf(subrabel)));
		subrabel++;
	 }
	 public static void add_name_table(String varia_name,int line) {/*変数表に追加 変数の重複は許さない*/
		if(variable_list.get(nowprogram).containsKey(varia_name)) {
			
			errmessage.errmes(line,0);
		}
		
		variable_list.get(nowprogram).put(varia_name,new table(nowtype,kata,line));
		
	 }
	 
	 public static void standard_type_name(String name) {/*型判明*/
			nowtype=name;
			kata=0;
		 }
	 public static void array_type_name() {/*型判明*/
			kata=1;
			
		 }
	 public static void print_table() {
		
	        
	 }
	 public static void clear_table() {
		 variable_list= new HashMap<>();
		 paranum_list = new HashMap<>();
		 errmessage.on=0;
		 nowprogram=null;
		 nowtype=null;
		 kata=1;
		 stacknumber=0;
		 substacknum=0;
		 subrabel=0;
	 }
	 public static void check_array(String proname,String varia_name,int line) {/*array型なのに純変数として使われている*/
		
		 if(!variable_list.get(proname).containsKey(varia_name)&&!variable_list.get("program").containsKey(varia_name)){
			 errmessage.errmes(line,1);
		 }else {
			 if(variable_list.get(proname).containsKey(varia_name)) {
				if(variable_list.get(proname).get(varia_name).arr==1) {
					errmessage.errmes(line,2);
				}
			 }else {
				 if(variable_list.get("program").get(varia_name).arr==1) {
					 errmessage.errmes(line,3);
				 }
			 }
			 
		 }
		 
	 }
	 public static void check_pure(String proname,String varia_name,int line) {/*純変数なのにarrayとして使われている*/
		 if(!variable_list.get(proname).containsKey(varia_name)&&!variable_list.get("program").containsKey(varia_name)){
			 errmessage.errmes(line,4);
		 }else {
			 if(variable_list.get(proname).containsKey(varia_name)) {
				if(variable_list.get(proname).get(varia_name).arr==0) {
					errmessage.errmes(line,5);
				}
			 }else {
				 if(variable_list.get("program").get(varia_name).arr==0) {
					 errmessage.errmes(line,6);
				 }
			 }
			 
		 }
		 
	}
	public static String return_type(String proname,String variable_name) {
		if(variable_list.get(proname).containsKey(variable_name)) {
			return variable_list.get(proname).get(variable_name).type;
		}
		else if(variable_list.get("program").containsKey(variable_name)) {
			return variable_list.get("program").get(variable_name).type;
		}else {
			return null;/*この後宣言していない変数を参照しているのでエラー*/
		}
	}
	public static void check_procedure(String name,int line) {
		if(!variable_list.containsKey(name)) {
			errmessage.errmes(line,12);
		}
	}
	public static void clip_variable(String proname, String variable_name) {
		if(variable_list.get(proname).containsKey(variable_name)) {
			variable_list.get(proname).get(variable_name).karipar=1;
		}
		else if(variable_list.get("program").containsKey(variable_name)) {
			variable_list.get("program").get(variable_name).karipar=1;
		}
		
		
	}
	public static void find_assigment(String proname, String variable_name) {
		if(variable_list.get(proname).containsKey(variable_name)) {
			variable_list.get(proname).get(variable_name).assignment=1;
		}
		else if(variable_list.get("program").containsKey(variable_name)) {
			variable_list.get("program").get(variable_name).assignment=1;
		}
		
	}
	public static void find_reference(String proname, String variable_name) {
		if(variable_list.get(proname).containsKey(variable_name)) {
			variable_list.get(proname).get(variable_name).reference=1;
		}
		else if(variable_list.get("program").containsKey(variable_name)) {
			variable_list.get("program").get(variable_name).reference=1;
		}
		
	}
	public static void nousing_warning() {
		for (String key : variable_list.keySet()) {
			for (String key2 : variable_list.get(key).keySet()) {
				if(variable_list.get(key).get(key2).assignment==0&&variable_list.get(key).get(key2).karipar==0) {/*使われていない変数*/
					System.out.println("Waring :There are no using "+key2+" line "+variable_list.get(key).get(key2).lines);
				}
				else if(variable_list.get(key).get(key2).reference==0&&variable_list.get(key).get(key2).karipar==1) {/*仮パラ使われてない*/
					System.out.println("Waring :There are no using "+key2+" line "+variable_list.get(key).get(key2).lines);
				}
			}
		}
		
	}
	public static void usenoassig_warning(String proname, String variable_name,int line) {
		if(variable_list.get(proname).containsKey(variable_name)) {
			if(variable_list.get(proname).get(variable_name).assignment==0&&variable_list.get(proname).get(variable_name).karipar==0) {
				System.out.println("Waring :using unassignment variable line "+line);
			}
		}
		
	}
	public static void stacktable(String proname,String varia_name,int size) {
		if(proname.equals("program")) {
			if(size==1) {
				variable_list.get(proname).get(varia_name).stacknum=stacknumber;
				stacknumber++;
			}else {
				variable_list.get(proname).get(varia_name).stacknum=stacknumber;
				stacknumber=stacknumber+size;/*実際のサイズ a[5]なら6*/
			}
		}else {
			if(variable_list.get(proname).get(varia_name).karipar==0) {/*仮パラは別途用意*/
				if(size==1) {
					variable_list.get(proname).get(varia_name).stacknum=substacknum;
					substacknum++;
				}else {
					variable_list.get(proname).get(varia_name).stacknum=substacknum;
					substacknum=substacknum+size;/*実際のサイズ a[5]なら6*/
				}
			}
			
		}
		
	}
	public static void array_info(String proname,String varia_name,int min,int max) {
		variable_list.get(proname).get(varia_name).startofarray=min;
		variable_list.get(proname).get(varia_name).sizeofarray=(max-min)+1;
		
	}
	public static void check_stack() {/*用配列対応*/
		for (String key : variable_list.keySet()) {
			if(!key.equals("program")) {
				substacknum=kariparnum(key);
			}
			for (String key2 : variable_list.get(key).keySet()) {
				stacktable(key,key2,variable_list.get(key).get(key2).sizeofarray);
				
			}
		}
		
	}
	public static void show_table() {/*用配列対応*/
		for (String key : variable_list.keySet()) {
			for (String key2 : variable_list.get(key).keySet()) {
				System.out.println(key+" :"+key2+" :"+variable_list.get(key).get(key2).stacknum);
				
			}
		}
		
	}
	public static void write_DS() {
		output.rabel_write("VAR	DS	"+stacknumber,0,null);
		
	}
	public static int return_stack(String varia_name) {/*メイン変数のスタック番号を返す*/
		   
			 return variable_list.get("program").get(varia_name).stacknum;
		
	}
	public static int return_subpro_stacknum(String proname,String varia_name) {/*サブプロのスタック番号　なければ-1*/
		if(variable_list.get(proname).containsKey(varia_name)) {
			
			return variable_list.get(proname).get(varia_name).stacknum;
			
		}else {
			return -1;
		}
		
	}
	public static int kariparnum(String proname) {/*仮パラの数を返す*/
		return Integer.parseInt(paranum_list.get(proname).para_num);
		
	}
	public static String subrable_num(String proname) {/*サブプロのラベル番号を返す*/
		return paranum_list.get(proname).rabel_num;
		
	}
	public static int sub_variable_num(String proname) {/*サブプロのローカル変数の数を返す*/
		int num=0;
		return variable_list.get(proname).size();
		
	}
	public static int return_array_start(String varia_name) {/*変数のスタック番号を返す*/
		
		 return variable_list.get("program").get(varia_name).startofarray;
	
	}
	public static int return_subarray_start(String proname, String varia_name) {/*変数のスタック番号を返す*/
		if(variable_list.get(proname).containsKey(varia_name)) {
		 return variable_list.get(proname).get(varia_name).startofarray;
		}else {
			return -1;
		}
	}
	
	public static void substack_reset() {
		substacknum=0;
	}
	public static void show_karipar_num() {
		for (String key : variable_list.keySet()) {
			System.out.println(paranum_list.get(key));
		}
		
	}
	
	
	
	
	
	 
	 
		
	 
}
