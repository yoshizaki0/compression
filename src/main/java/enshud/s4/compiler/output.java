package enshud.s4.compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class output {
	static BufferedWriter output_name;
	public static void get_filename(BufferedWriter outtext) {
		output_name=outtext;
	}
	public static void write(String text, int line,String comment) {
		try {
			if(comment==null) {
				if(line==0) {
					output_name.write("	  "+text+"	;");
					output_name.newLine();
					output_name.flush();
				}else {
					output_name.write("	  "+text+"	;"+" line"+line);
					output_name.newLine();
					output_name.flush();
				}
			}else {
				if(line==0) {
					output_name.write("	  "+text+"	;"+" "+comment);
					output_name.newLine();
					output_name.flush();
				}else {
					output_name.write("	  "+text+"	;"+" line"+line+" "+comment);
					output_name.newLine();
					output_name.flush();
			 }
			}
			
	     }
	    catch(IOException e){
		  					System.out.println(e);
		}
   }
	public static void rabel_write(String text,int line ,String comment) {
		
		
		
		try {
			if(comment==null) {
				if(line==0) {
					output_name.write(text+"	;");
					output_name.newLine();
					output_name.flush();
				}else {
					output_name.write(text+"	;"+" line"+line);
					output_name.newLine();
					output_name.flush();
				}
			}else {
				if(line==0) {
					output_name.write(text+"	;"+" "+comment);
					output_name.newLine();
					output_name.flush();
				}else {
					output_name.write(text+"	;"+" line"+line+" "+comment);
					output_name.newLine();
					output_name.flush();
			 }
			}
	     }
	    catch(IOException e){
		  					System.out.println(e);
		}
   }
	public static void lib_write() {
		File file = new File("data/cas/lib.cas");
		BufferedReader br;
		String str= null;
		try {
			br = new BufferedReader(new FileReader(file));
			 while((br.ready())){
				 str = br.readLine();
				 output_name.write(str);
				 output_name.newLine();
				 output_name.flush();
			 }
			 br.close();
	     }
	    catch(IOException e){
		  					System.out.println(e);
		} 
   }

		
}
	

