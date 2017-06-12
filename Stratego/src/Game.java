import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Game implements Serializable{
	private String gameName;
	private Player player1 = null;
	private Player player2 = null;
	private Position[][] board;
	private long timeStarted = 0; //ti wra arxise to create/load.
	private double gameTime; //posi wra trexei to paixnidi mexri twra.
	private boolean gameIsOver;
	
	private static Game instance = null;
	
	//create new game.
	
	public static Game getInstance() {
	      if(instance == null) {
	         instance = new Game();
	      }
	      return instance;
	   }
	
	public void setBoard(Position[][] b){
		this.board = b;
	}
	
	public void setInstance(Game game){
		Game.instance = game;
	}
	
	public void startTime(){
		this.timeStarted = System.currentTimeMillis();
	}
	
	private Game(){
		this.board = new Position[10][10];
		this.gameTime = 0;
		this.gameIsOver = false;
		
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				board[i][j] = new Position();
			}
		}
		
	}
	
	public void setPlayers(Player player1, Player player2){
		if(this.player1==null){
			this.player1 = player1;
			this.player2 = player2;
			this.saveGameFirstTime();
		}
	}
	
	//load game.
	public Game(Game game){
		this.loadGame(game);
	}
	
	//First Time Save
	private void saveGameFirstTime(){
		long tEnd = System.currentTimeMillis();
		gameTime += (tEnd - timeStarted)/1000.0;
		
		int counter = 1;
		
		FileOutputStream fout;
		ObjectOutputStream oos;
		
		boolean flag = true;
		String name = null;
		while(flag){
			name= "saves/GAME " + player1.getName() + "-" + player2.getName();
			if(counter!=1){
				name+= "(" + counter + ")";
			}
			else{
				name+= " ";
			}
			name+= ".ser";
			
			File f = new File(name);
			if(!f.exists())
				flag = false;
			counter++;
		}
		try {
			fout = new FileOutputStream(name);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.gameName = name;
	}
	
	public void saveGame(){
		if(timeStarted!=0){
			long tEnd = System.currentTimeMillis();
			gameTime += (tEnd - timeStarted)/1000.0;
		}
		
		FileOutputStream fout;
		ObjectOutputStream oos;
		
		String name = this.gameName;
		try {
			fout = new FileOutputStream(name);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void endGame(){
		long tEnd = System.currentTimeMillis();
		gameTime += (tEnd - timeStarted)/1000.0;
		this.gameIsOver = true;
	}
	
	
	
	public ArrayList<Game> findGames(){
		ArrayList<Game> list = new ArrayList<Game>();
		/*
		 * Diavasma tou arxeio, dimiourgia twn Games kai perasma tous stin lista.
		 */
		return list;
	}
	
	private void loadGame(Game game){
		Game.instance = game;
	}
	
	public Player getPlayer1(){
		return this.player1;
	}
	
	public Player getPlayer2(){
		return this.player2;
	}
	
	public Position[][] getBoard(){
		return this.board;
	}
	
	public void movePawnToEmptyPosition(Position firstPos, Position secondPos){
		if(secondPos.getAccess()){
			Pawn pawn = firstPos.getPawnOnPosition();
			firstPos.removePawnFromPosition();
			secondPos.addPawnToPosition(pawn);
		}
		else{
			System.out.println("You cant move there");
		}
	}
	
	public void attackBettwenPawns(Position attackingPos, Position defendingPos){
		Movable attackingPawn = (Movable) attackingPos.getPawnOnPosition();
		Pawn defendingPawn = defendingPos.getPawnOnPosition();
		
		int answer = attackingPawn.attackEnemyPawn(defendingPawn);
		
		switch(answer){
		case -1:
			attackingPos.removePawnFromPosition();
			break;
		case 0:
			attackingPos.removePawnFromPosition();
			defendingPos.removePawnFromPosition();
			break;
		case 1:
			defendingPos.removePawnFromPosition();
			defendingPos.addPawnToPosition(attackingPawn);
			attackingPos.removePawnFromPosition();
			break;
		case 2:
			defendingPos.removePawnFromPosition();
			defendingPos.addPawnToPosition(attackingPawn);
			attackingPos.removePawnFromPosition();
			this.endGame();
			break;
		}
	}
	
	public void playerClicksPosition(Player player, Position pos){
		if(player.getPositionSelected().equals(null)){
			if(!pos.getPawnOnPosition().equals(null) && !player.getColor().equals(pos.getPawnOnPosition().getPawnColor())){
				player.setPositionSelected(pos);
			}
		}
		else if(!player.getPositionSelected().getPawnOnPosition().getPawnColor().equals(pos.getPawnOnPosition().getPawnColor())){
			this.movePawn(player.getPositionSelected(), pos);
			player.setPositionSelected(null);
		}
	}
	
	public void movePawn(Position firstPos, Position secondPos){
		if(secondPos.getPawnOnPosition().equals(null)){
			this.movePawnToEmptyPosition(firstPos, secondPos);
		}
		else{
			this.attackBettwenPawns(firstPos, secondPos);
		}
	}
	
	public void buttonSelected(String value, Player player){
		String[] split = value.split("");
		int x = Integer.parseInt(split[0]);
		int y = Integer.parseInt(split[1]);
		this.playerClicksPosition(player, board[x][y]);
	}
	
	public boolean gameStatus(){
		return this.gameIsOver;
	}
	
}
