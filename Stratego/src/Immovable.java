import java.io.Serializable;


public class Immovable extends Pawn implements Serializable{

	private static final long serialVersionUID = 1L;
	private int blocksPawnCanMove = 0;

	public Immovable(String pawnType, String pawnColor) {
		super(pawnType, pawnColor);
	}
	
}
