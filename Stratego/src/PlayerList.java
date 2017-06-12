import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class PlayerList implements Serializable{
	private static final long serialVersionUID = 1L;

	private static PlayerList instance = null;
	
	private ArrayList<Player> list;
	
	private PlayerList(){
		list = new ArrayList<Player>();
	}
	
	public static PlayerList getInstance() {
	      if(instance == null) {
	         instance = new PlayerList();
	      }
	      return instance;
	   }
	
	public void addUser(String username, String password){
		username = username.trim();
		username = username.replaceAll("\\s+", "");
		if(userExists(username)){
		}
		else{
			String s = "";
			try {
				byte[] bytesOfMessage = password.getBytes("UTF-8");
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] thedigest = md.digest(bytesOfMessage);
				for(byte b : thedigest){
					s += b;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			Player p1 = new Player(username,s);
			list.add(p1);
		}
	}
	
	public boolean userExists(String username){
		for(Player p : list){
			if(p.getName().equals(username)){
				return true;
			}
		}
		return false;
	}
	
	public void savePlayers(){
		FileOutputStream fout;
		ObjectOutputStream oos;
		
		String name = "ser/Players.ser";
		try {
			fout = new FileOutputStream(name);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(this.list);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadPlayers(){
		try{
		      InputStream file = new FileInputStream("ser/Players.ser");
		      InputStream buffer = new BufferedInputStream(file);
		      ObjectInput input = new ObjectInputStream (buffer);
		      try{
		        list = (ArrayList<Player>)input.readObject();
		      }
		      finally{
		        input.close();
		      }
		    }
		    catch(ClassNotFoundException e){
		    	e.printStackTrace();
		    }
		    catch(IOException e){
		    	e.printStackTrace();
		    }
	}
	
	public void printNames(){
		System.out.println("-------------------");
		for(Player p : list){
			System.out.println(p.getName());
		}
		System.out.println("-------------------");
	}
	
	public int checkUser(String username, String password){
		Player p = null; //wrong user:0, wrong pass:1, correct:2
		for(Player pl : list){
			if(username.equalsIgnoreCase(pl.getName())){
				p=pl;
			}
		}
		if(p == null){
			return 0;
		}
		else{
			String s = "";
			try {
				byte[] bytesOfMessage = password.getBytes("UTF-8");
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] thedigest = md.digest(bytesOfMessage);
				for(byte b : thedigest){
					s += b;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			if(p.getPassword().equals(s)){
				return 2;
			}
			else{
				return 1;
			}
		}
	}
	
	public Player getPlayer(String username){
		for(Player player : list){
			if(player.getName().equalsIgnoreCase(username)){
				return player;
			}
		}
		System.out.println("LUL");
		return null;
	}
	
	public ArrayList<Player> getList(){
		return list;
	}
}
