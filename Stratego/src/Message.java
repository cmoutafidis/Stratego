import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Message extends JFrame{
	private JPanel panel;
	private JButton button;
	private JLabel label;
	private JLabel label2;
	private Position[][] positions = new Position[10][10];
	
	public Message(String aUser, Position[][] pos) throws IOException{
		int i,j;
		Pawn somePawn = new Pawn("","");
		
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){
				positions[i][j] = new Position();
				positions[i][j].addPawnToPosition(somePawn);
				positions[i][j].addPawnToPosition(pos[i][j].getPawnOnPosition());
			}
		}
		panel = new JPanel();
		label = new JLabel();
		//
		label.setIcon(new ImageIcon(Message.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		label.setBounds(25, 61, 32, 32);
		panel.add(label);
		label2 = new JLabel("User " + aUser + " Choose One Of The Right Ones");
		label2.setBounds(95, 61, 298, 32);
		panel.add(label2);
		button = new JButton("OK");
		button.setBounds(169, 132, 89, 23);
		panel.add(button);
		this.setContentPane(panel);
		
		ButtonListener listener = new ButtonListener();
		
		button.addActionListener(listener);
		this.setVisible(true);
		this.setSize(300, 190);
		this.setTitle("Message");
	}

	public void CloseFrame(){
		super.dispose();
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			CloseFrame();
			new FinalBoard(positions);
		}
	}
}
