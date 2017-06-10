import java.io.Serializable;


public class Position implements Serializable{

	private Pawn pawnOnPosition;
	private boolean canBeAccessed;
	
	public boolean positionStatus(){
		
		return false;
		
	}
	
	public Position() {
		this.pawnOnPosition = null;
		this.canBeAccessed = true;
	}

	public void removePawnFromPosition(){
		this.pawnOnPosition = null;
	}
	
	public void addPawnToPosition(Pawn pawn){
		this.pawnOnPosition = pawn;
	}

	public Pawn getPawnOnPosition() {
		return pawnOnPosition;
	}
	
	public void setAccess(boolean flag){
		this.canBeAccessed = flag;
	}
	
	public boolean getAccess(){
		return this.canBeAccessed;
	}
}
