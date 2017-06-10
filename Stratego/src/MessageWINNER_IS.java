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

public class MessageWINNER_IS extends JFrame{
	private JPanel panel;
	private JButton button;
	private JLabel label;
	private JLabel label2;
	private Position[][] positions = new Position[10][10];
	
	public MessageWINNER_IS(String aUser) throws IOException{
		panel = new JPanel();
		label = new JLabel();
		//...
		label.setIcon(new ImageIcon(MessageWINNER_IS.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		label.setBounds(25, 61, 32, 32);
		panel.add(label);
		label2 = new JLabel("User " + aUser + " Wins The Game");
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
		}
	}
}
