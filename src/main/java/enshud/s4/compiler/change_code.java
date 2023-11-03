package enshud.s4.compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class change_code {
	
	public void change_code(final String inputFileName, final String outputFileName) {
		
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
				 					 
				 					 
								}
			
			br.close();
			outtext.close();
			content.close();
			
			System.out.println("OK");
			
			
			
		}
		catch (Exception FileNotFoundException) {
			System.err.println("File not found");
		}
	}

}
