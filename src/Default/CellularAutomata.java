package Default;

import javax.swing.JFrame;

import GUI.Grid;

public class CellularAutomata {
	public static void main(String[] args){
		
		Grid window = new Grid();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setSize(600,600);
		window.setVisible(true);

	}
}
