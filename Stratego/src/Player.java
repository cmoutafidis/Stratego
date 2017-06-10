import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;


public class Player implements Serializable{
	
	private String name;
	private int wins;
	private int losses;
	private double fastestRound;
	private String color;
	private Position positionSelected;
	private String password;
	private ArrayList<String> playersWithOpenGames;
	private boolean isPlayersTurn;
	
	//dimiourgia antikeimenou player otan sindeetai.
	public Player(String name, int wins, int losses, double fastestRound, ArrayList<String> playerList, String password){
		this.name = name;
		this.wins = wins;
		this.losses = losses;
		this.fastestRound = fastestRound;
		this.playersWithOpenGames = playerList;
		this.password = password;
	}
	
	//dimiourgia antikeimenou player otan exoume neo xristi.
	public Player(String name, String password){
		this.name = name;
		this.wins = 0;
		this.losses = 0;
		this.fastestRound = -1;
		this.playersWithOpenGames = new ArrayList<String>();
		this.password = password;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getWins(){
		return this.wins;
	}
	
	public int getLosses(){
		return this.losses;
	}
	
	public double getWinRation(){
		if(losses == 0){
			return 100;
		}
		return this.wins/this.losses;
	}
	
	public double getFastestRound(){
		return this.fastestRound;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public Position getPositionSelected(){
		return this.positionSelected;
	}
	
	public void setPositionSelected(Position pos){
		this.positionSelected = pos;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public ArrayList<String> getPlayerList(){
		return this.playersWithOpenGames;
	}
	
	public void addToPlayerList(String name){
		this.playersWithOpenGames.add(name);
	}
	
	public boolean isPlayersTurn(){
		return this.isPlayersTurn;
	}
}
