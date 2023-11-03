package Visit;

public class errmessage {
		public static int on=0;
		public static void errmes(int line,int errnum) {
			if(on==0) {
			System.err.println("Semantic error: line "+line);
			on=1;
			}
			
		}
	}
