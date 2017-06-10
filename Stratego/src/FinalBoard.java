import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicBorders;

public class FinalBoard extends JFrame{
	
	private JPanel contentPane = new JPanel();
	private HashMap<String,JButton> btnB = new HashMap<String,JButton>();
	private ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	private Pawn thePawn;
	private static int countTurn = 0;
	private Position[][] positions = new Position[10][10];
	private static Movable attacker = new Movable("","",0);
	private static boolean blueIsOK = false;
	private static boolean redIsOK = false;
	private int blocksToMove = 1;
	
	public FinalBoard(Position[][] pos){
		int i,j;
		Pawn somePawn = new Pawn("","");
		
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){
				positions[i][j] = new Position();
				positions[i][j].addPawnToPosition(somePawn);
				positions[i][j].addPawnToPosition(pos[i][j].getPawnOnPosition());
				positions[i][j].setAccess(pos[i][j].getAccess());
			}
		}
		positions[4][2].setAccess(false);
		positions[4][2].getPawnOnPosition().setPawnType("L");
		positions[4][3].setAccess(false);
		positions[4][3].getPawnOnPosition().setPawnType("L");
		positions[5][2].setAccess(false);
		positions[5][2].getPawnOnPosition().setPawnType("L");
		positions[5][3].setAccess(false);
		positions[5][3].getPawnOnPosition().setPawnType("L");
		positions[4][6].setAccess(false);
		positions[4][6].getPawnOnPosition().setPawnType("L");
		positions[4][7].setAccess(false);
		positions[4][7].getPawnOnPosition().setPawnType("L");
		positions[5][6].setAccess(false);
		positions[5][6].getPawnOnPosition().setPawnType("L");
		positions[5][7].setAccess(false);
		positions[5][7].getPawnOnPosition().setPawnType("L");
		countTurn++;
		JButton button;
		this.contentPane.setLayout(new GridLayout(10, 10));
		ButtonListener listener = new ButtonListener();
		
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){  //ekana tis 2 for mia. Kanei new button, prosthetei listener kai to vazei sto hashmap
				button = new JButton();//kai sto contentPane.
				button.addActionListener(listener);
				if(positions[i][j].getAccess()){
					if(positions[i][j].getPawnOnPosition().getPawnColor().equals("BLUE")){
						button.setBackground(Color.CYAN);
						if(countTurn%2==1)
							button.setText(positions[i][j].getPawnOnPosition().getPawnType());
					}
					else if(positions[i][j].getPawnOnPosition().getPawnColor().equals("RED")){
						button.setBackground(Color.RED);
						if(countTurn%2==0)
							button.setText(positions[i][j].getPawnOnPosition().getPawnType());
					}
				}
				else{
					button.setEnabled(false);
					button.setBackground(Color.BLUE);
				}
				btnB.put("B" + i + j, button);
				contentPane.add(button);
			}
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setVisible(true);
		this.setContentPane(contentPane);
	}
	
	public void removeIcon(String abc) {
		//removes an icon from a button
		btnB.get(abc).setIcon(null);
	}
	
	public void addIcon(String abc) {
		//adds an icon over a button
		btnB.get(abc).setIcon(new ImageIcon(TheBoard.class.getResource("/javax/swing/plaf/metal/icons/ocean/" + abc + ".png")));//here comes the right pawn icon
	}
	
	public void closeFrame(){
		super.dispose();
	}
	
	public boolean blockManager(String key, Movable attacker){
	    ArrayList<String> blocks = new ArrayList<String>();
	    boolean OK=false;
	    int i,j;
	    char fcoords[] = key.toCharArray();
	    i=fcoords[1]-48;
	    j=fcoords[2]-48;
	    int k=i-1,l=j-1;
	    BasicBorders.ButtonBorder border = new BasicBorders.ButtonBorder(Color.YELLOW,Color.YELLOW,Color.YELLOW,Color.YELLOW);
	    
	    if(attacker.getBlocksPawnCanMove()==1){
	        blocks.add("B" + k + j);
	        k+=2;
	        blocks.add("B" + k + j);
	        blocks.add("B" + i + l);
	        l+=2;
	        blocks.add("B" + i + l);
	    }
	    else{
	        for(k=0;k<10;k++){
	            blocks.add("B" + k + j);
	            for(l=0;l<10;l++){
		        	blocks.add("B" + i + l);
		    	}
	        }
	    }

	    if(attacker.getPawnColor().equals("BLUE")){
	    	for(String akey : btnB.keySet()){
		    	if(blocks.contains(akey)){
		    		char[] fcoords2 = akey.toCharArray();
			       	if(positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnColor().equals("BLUE")
			       			||positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnType().equals("L")){
			       		btnB.get(akey).setEnabled(false);
			       	}
			       	else{
			       		btnB.get(akey).setEnabled(true);
			       		btnB.get(akey).setBorder(border);
			       		OK=true;
			       	}
			    }
		        else
		        	btnB.get(akey).setEnabled(false);
		    }
	    }
	    else{
	    	for(String akey : btnB.keySet()){
		    	if(blocks.contains(akey)){
		    		char[] fcoords2 = akey.toCharArray();
		    		if(positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnColor().equals("RED")
			       			||positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnType().equals("L")){
			       		btnB.get(akey).setEnabled(false);
			       	}
			       	else{
			       		btnB.get(akey).setEnabled(true);
			       		btnB.get(akey).setBorder(border);
			       		OK=true;
			       	}
			    }
		        else
		        	btnB.get(akey).setEnabled(false);
		    }
	    }
	    
	    return OK;
	}
	
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			int x = 0;
			String playerRED = "RED";
			String playerBLUE = "BLUE";
			boolean OK = false;
			
			if(!blueIsOK&&!redIsOK){
				for(String key : btnB.keySet()){
					char[] fcoords = key.toCharArray();
					if(arg0.getSource().equals(btnB.get(key))){
						//EDW KSEKINA H PARTIDA
						//POLY APLA:EFOSON KSEKINA PANTA O BLUE TYPOS OTAN O GYROS EINAI MONOS(TO countTurn) TOTE 8A PREPEI NA EPILEKSEI 8ESH
						//OPOU EXEI DIKO TOY PIONI, GIA NA KSEKINHSEI O GYROS, DHLADH TO PRVTO BUTTON POU EPILEGETAI NA EINAI DIKO TOY. TA IDIA
						//ISXYOUN KAI GIA TO RED TYPO, ME TH DIAFORA OTI O GYROS PREPEI NA EINAI ZYGOS ARITHMOS
						if(countTurn%2==1){
							if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnColor().equals("BLUE")){
								for(String key2 : btnB.keySet()){
									char[] fcoords2 = key2.toCharArray();
									if(positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnColor().equals("BLUE")
									||positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnType().equals("L"))//ME L SYMBOLIZOUME TH LIMNH
										btnB.get(key2).setEnabled(false);
									else{
										btnB.get(key2).setEnabled(true);
									}
								}
								if(!positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("B")&&
										!positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("F")){
									if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("2"))
										blocksToMove = 9;
									attacker = new Movable(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType(),"BLUE",blocksToMove);
									OK = blockManager(key,attacker);
									if(!OK){
										try {
											new Message(playerBLUE,positions);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										countTurn--;
										closeFrame();
									}
									else{
										positions[fcoords[1]-48][fcoords[2]-48].removePawnFromPosition();
										blueIsOK=true;
									}
								}
								else
									try {
										new Message(playerBLUE,positions);
										countTurn--;
										closeFrame();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
							else{
								try {
									new Message(playerBLUE,positions);
									countTurn--;
									closeFrame();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						else{
							if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnColor().equals("RED")){
								for(String key2 : btnB.keySet()){
									char[] fcoords2 = key.toCharArray();
									if(positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnColor().equals("RED")
									||positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnType().equals("L"))//ME L SYMBOLIZOUME TH LIMNH
										btnB.get(key2).setEnabled(false);
									else{
										btnB.get(key2).setEnabled(true);
									}
								}
								if(!positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("B")&&
										!positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("F")){
									if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("2"))
										blocksToMove = 9;
									attacker = new Movable(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType(),"RED",blocksToMove);
									OK = blockManager(key,attacker);
									if(!OK){
										try {
											new Message(playerRED,positions);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										countTurn--;
										closeFrame();
									}
									else{
										positions[fcoords[1]-48][fcoords[2]-48].removePawnFromPosition();
										redIsOK=true;
									}
								}
								else
									try {
										new Message(playerRED,positions);
										countTurn--;
										closeFrame();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
							else{
								try {
									new Message(playerRED,positions);
									countTurn--;
									closeFrame();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			else{
				if(blueIsOK){
					for(String key : btnB.keySet()){
						if(arg0.getSource().equals(btnB.get(key))){
							char[] fcoords = key.toCharArray();
							int i=fcoords[1]-48;
							int j=fcoords[2]-48;
							Pawn enemy = new Pawn("","");
							if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnColor().equals("RED")){
								enemy = positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition();
								x = attacker.attackEnemyPawn(enemy);
								switch(x){
								case 1:
									positions[i][j].addPawnToPosition(attacker);
									try {
										new ClashMessage(attacker.getPawnColor() + attacker.getPawnType() + " wins " + enemy.getPawnColor() + enemy.getPawnType(), positions);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									break;
								case 0:
									positions[i][j].removePawnFromPosition();
									try {
										new ClashMessage(attacker.getPawnColor() + attacker.getPawnType() + " and " + enemy.getPawnColor() + enemy.getPawnType() + " both lose", positions);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									break;
								case 2:
									try {
										new MessageWINNER_IS("BLUE");
										closeFrame();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
								default:
									try {
										new ClashMessage(attacker.getPawnColor() + attacker.getPawnType() + " loses to " + enemy.getPawnColor() + enemy.getPawnType(), positions);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									break;
								}
							}
							else{
								positions[i][j].addPawnToPosition(attacker);
								new FinalBoard(positions);
							}
							attacker = new Movable("","",0);
						}
					}
					blueIsOK=false;
					closeFrame();
				}
				else if(redIsOK){
					for(String key : btnB.keySet()){
						if(arg0.getSource().equals(btnB.get(key))){
							char[] fcoords = key.toCharArray();
							int i=fcoords[1]-48;
							int j=fcoords[2]-48;
							Pawn enemy = new Pawn("","");
							if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnColor().equals("BLUE")){
								enemy = positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition();
								x = attacker.attackEnemyPawn(enemy);
								switch(x){
								case 1:
									positions[i][j].addPawnToPosition(attacker);
									try {
										new ClashMessage(attacker.getPawnColor() + attacker.getPawnType() + " wins " + enemy.getPawnColor() + enemy.getPawnType(), positions);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									break;
								case 0:
									positions[i][j].removePawnFromPosition();
									try {
										new ClashMessage(attacker.getPawnColor() + attacker.getPawnType() + " wins " + enemy.getPawnColor() + enemy.getPawnType(), positions);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									break;
								case 2:
									try {
										new MessageWINNER_IS("RED");
										closeFrame();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
								default:
									try {
										new ClashMessage(attacker.getPawnColor() + attacker.getPawnType() + " loses to " + enemy.getPawnColor() + enemy.getPawnType(), positions);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									break;
								}
							}
							else{
								positions[i][j].addPawnToPosition(attacker);
								new FinalBoard(positions);
							}
							attacker = new Movable("","",0);
						}
					}
					redIsOK=false;
					closeFrame();
				}
			}
		}
	}
}
