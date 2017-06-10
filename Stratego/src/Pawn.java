
public class Pawn implements Comparable{

	private String pawnType;
	private String pawnColor;
	private int blocksPawnCanMove;
	
	public Pawn(String pawnType, String pawnColor) {
		this.pawnType = pawnType;
		this.pawnColor = pawnColor;
	}
	
	public void setPawnType(String s){
		this.pawnType = s;
	}
	
	public String getPawnType() {
		return this.pawnType;
	}
	
	public String getPawnColor(){
		return this.pawnColor;
	}
	
	public void setPawnColor(String s){
		this.pawnColor = s;
	}
	
	public int getBlocksPawnCanMove(){
		return this.blocksPawnCanMove;
	}
	
	@Override
	public int compareTo(Object arg0) {
		Pawn other = (Pawn)arg0;
		String otherType;
		String thisType;
		
		otherType = other.getPawnType() + "";
		thisType = this.getPawnType() + "";
		return thisType.compareTo(otherType);
	}
}