import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class PawnList extends JFrame {

	private JPanel panel = new JPanel();
	private JList list = new JList();
	private JButton button = new JButton();
	private ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	private int[] freqs = new int[12];
	private DefaultListModel model = new DefaultListModel();
	private Position[][] positions = new Position[10][10];
	private static int vo = 0;
	private String[] array = {"B", "F", "10", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	public PawnList(ArrayList<Pawn> pawnNames, boolean empty, int[] replicas, Position[][] pos) {
		int i = 0;
		int j = 0;
		int counter = 0;
		Pawn somePawn = new Pawn("","");
		
		if(vo==0){
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){
					positions[i][j] = new Position();
					positions[i][j].addPawnToPosition(somePawn);
					pos[i][j] = new Position();
					pos[i][j].addPawnToPosition(somePawn);
					positions[i][j].addPawnToPosition(pos[i][j].getPawnOnPosition());
				}
			}
		}
		else{
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){
					positions[i][j] = new Position();
					positions[i][j].addPawnToPosition(somePawn);
					positions[i][j].addPawnToPosition(pos[i][j].getPawnOnPosition());
				}
			}
		}
		Collections.sort(pawnNames);
		ArrayList<String> pawnTypes = new ArrayList<String>();
		
		for(Pawn pawn : pawnNames){
			pawnTypes.add(pawn.getPawnType());
		}
		
		HashSet<String> noMultiples = new HashSet<String>(pawnTypes);
		
		for(String name : noMultiples){
			model.addElement(name);
		}
		pawns.addAll(pawnNames);
		list.setModel(model);
		
		ButtonListener listener = new ButtonListener();
		button.addActionListener(listener);
		i=0;
		if(empty){
			for(String name : noMultiples) {
				counter = Collections.frequency(pawnTypes, name);
				freqs[i] = counter;
				i++;
			}
		}
		else{
			for(i=0;i<12;i++){
				freqs[i] = replicas[i];
			}
		}
		
		for(int k=0;k<12;k++){
			if(freqs[k]==0){
				for(int p=0;p<model.getSize();p++){
					if(model.getElementAt(p).equals(array[k])){
						model.removeElementAt(p);
					}
				}
			}
		}
		
		vo++;
		this.setContentPane(panel);
		this.panel.add(list);
		this.panel.add(button);
		this.setVisible(true);
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void closeFrame(){
		super.dispose();
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int i = 0;
			Pawn selectedPawn = null;
			if(list.getSelectedValue()!=null){
				
				String selected = (String)list.getSelectedValue();
				
				for(int j=0;j<12;j++){
					if(array[j].equals(selected)){
						i = j;
					}
				}
				
				if(freqs[i]==0){
					
				}
				else{
					for(Pawn pawn : pawns){
						if(pawn.getPawnType().equals(selected)){
							selectedPawn = pawn;
						}
					}
					new TheBoard(selectedPawn,pawns,freqs,positions);
					closeFrame();
				}
			}
			
		}
	}
}
