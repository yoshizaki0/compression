package table;

public class table {
		public  int arr;/*0なら純変数　１なら配列*/
		public  String type;/*型*/
		public int assignment=0;/*代入の有無 0で無し*/
		public int reference=0;/*参照の有無*/
		public int lines=-1;
		public int karipar=0;/*１なら仮パラメーター*/
		public int stacknum=0;
		public int startofarray=0;/*配列の最初の添え字*/
		public int sizeofarray=1;
		public table(  String typeof,int katati,int line) {

			this.arr=katati;
			this.type=typeof;	
			this.lines=line;
		}
		
		

}
