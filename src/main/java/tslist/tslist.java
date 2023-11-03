package tslist;

public class tslist {
	public String code;
	public int id,line;
	public tslist(String text){
		String[] contents = new String[4];
		contents = text.split("\t");
		this.code=contents[0];
		this.id = Integer.parseInt(contents[2]);
		this.line = Integer.parseInt(contents[3]);
     
	}

}
