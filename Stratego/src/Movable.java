
public class Movable extends Pawn{

	private int blocksPawnCanMove;
	//private Position[] positionsPawnCanMoveTo;
	private int positionsPawnCanMoveTo;
	
	public Movable(String pawnType, String pawnColor, int blocksPawnCanMoveTo) {
		super(pawnType, pawnColor);
		this.blocksPawnCanMove = blocksPawnCanMoveTo;
	}
	
	public int attackEnemyPawn(Pawn defender){          //1:attacker wins, 0:both lose, -1:defender wins, 2:attacking pawn wins the game
		String defType = defender.getPawnType();
		String attackerType = this.getPawnType();
		
		if(defType.equals("B")){
			if(attackerType.equals("3")){
				return 1;
			}
			return -1;
		}
		else if(defType.equals("F")){
			return 2;
		}
		else{
			int defNum = Integer.parseInt(defType);
			int attackerNum = Integer.parseInt(attackerType);
			if(attackerNum==1 && defNum==10){
				return 1;
			}
			else{
				if(attackerNum>defNum){
					return 1;
				}
				else if(attackerNum==defNum){
					return 0;
				}
				return -1;
			}
		}
	}
}
