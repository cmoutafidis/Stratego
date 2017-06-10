import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyleConstants.ColorConstants;

import com.sun.org.apache.xalan.internal.templates.Constants;

public class TheBoard extends JFrame {

	private JPanel contentPane = new JPanel();
	private HashMap<String,JButton> btnB = new HashMap<String,JButton>();
	private ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	private Pawn thePawn;
	private int[] frequencies = new int[12];
	private static int countTurn = 0;
	private Position[][] positions = new Position[10][10];
	
	public TheBoard(Pawn pawn, ArrayList<Pawn> pawnNames, int[] freqs, Position[][] pos) {
		int i,j,z;
		Pawn somePawn = new Pawn("","");
		
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){
				positions[i][j] = new Position();
				positions[i][j].addPawnToPosition(somePawn);
				positions[i][j].addPawnToPosition(pos[i][j].getPawnOnPosition());
			}
		}

		for(z=0;z<12;z++)
			frequencies[z] = freqs[z];
		JButton button;
		pawns.addAll(pawnNames);
		thePawn = pawn;
		this.contentPane.setLayout(new GridLayout(10, 10));
		ButtonListener listener = new ButtonListener();
		countTurn++;
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){  //ekana tis 2 for mia. Kanei new button, prosthetei listener kai to vazei sto hashmap
				button = new JButton();//kai sto contentPane.
				String color = positions[i][j].getPawnOnPosition().getPawnColor();
				String type = positions[i][j].getPawnOnPosition().getPawnType();
				if(countTurn<13){
					if(i<6)
						button.setEnabled(false);
					else{
						if(!color.equals("")){
							button.setBackground(Color.CYAN);
							button.setEnabled(false);
							button.setText(type);
						}
					}
				}
				else if(countTurn<25){
					if(i>3){
						if(!color.equals("")){
							button.setBackground(Color.CYAN);
						}
						button.setEnabled(false);
					}
					else{
						if(!color.equals("")){
							button.setBackground(Color.RED);
							button.setEnabled(false);
							button.setText(type);
						}
					}
				}
				button.addActionListener(listener);
				btnB.put("B" + i + j, button);
				contentPane.add(button);
			}
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setVisible(true);
		this.setContentPane(contentPane);
	}
	
	public void closeFrame(){
		super.dispose();
	}
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//detects which button was pressed	
			int i = 0;
			int y = 0;
			int j = 0;
			boolean OK = false;
			
			for(String key : btnB.keySet()){
				if(arg0.getSource().equals(btnB.get(key))){
					if(countTurn<25){
						switch(thePawn.getPawnType()){
						case "B":
							frequencies[0]--;
							i=0;
							break;
						case "F":
							frequencies[1]--;
							i=1;
							break;
						case "10":
							frequencies[2]--;
							i=2;
							break;
						case "1":
							frequencies[3]--;
							i=3;
							break;
						case "2":
							frequencies[4]--;
							i=4;
							break;
						case "3":
							frequencies[5]--;
							i=5;
							break;
						case "4":
							frequencies[6]--;
							i=6;
							break;
						case "5":
							frequencies[7]--;
							i=7;
							break;
						case "6":
							frequencies[8]--;
							i=8;
							break;
						case "7":
							frequencies[9]--;
							i=9;
							break;
						case "8":
							frequencies[10]--;
							i=10;
							break;
						case "9":
							frequencies[11]--;
							i=11;
							break;
						}
						btnB.get(key).setText(thePawn.getPawnType());
						char[] abc = key.toCharArray();
						int xz = abc[1]-48,yz = abc[2]-48;
						positions[xz][yz].addPawnToPosition(thePawn);
						if(thePawn.getPawnColor().equals("BLUE"))
							btnB.get(key).setBackground(Color.CYAN);
						else
							btnB.get(key).setBackground(Color.RED);
						btnB.get(key).setEnabled(false);//apotrepei to xrhsth apo to na topo8ethsei se ena button ola ta pionia idias aksias tou
						if(frequencies[i]==0){
							if(countTurn!=12){
								if(countTurn!=24)
									new MainMenu(pawns,false,frequencies,positions);
								else
									OK = true;
							}
							else{
								for(Pawn pawn : pawns){
									pawn.setPawnColor("RED");
								}
								new MainMenu(pawns,true,frequencies,positions);
							}
							closeFrame();
						}
					}
				}
			}
			if(OK){
				try {
					for(i=0;i<10;i++){
						for(j=0;j<10;j++){
							String color = "";
							if(i<4)
								color = "RED";
							if(i>5)
								color = "BLUE";
							Pawn another = new Pawn(positions[i][j].getPawnOnPosition().getPawnType(),color);
							positions[i][j].addPawnToPosition(another);
						}
					}
					new MessageGET_READY(positions);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}