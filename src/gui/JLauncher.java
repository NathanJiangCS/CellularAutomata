package gui;

import javax.swing.JFrame;

public class JLauncher {
	
	static JFrame frame;
	static Grid grid;
	
	public static void main(String[] args){
		
		//Initialize JFrame and Grid-> JApplet
		grid = new Grid();
		frame = new JFrame("Cellular Automata by Vincent Hang and Edmond Lu");
		
		//Set dispose function to JFrame.EXIT_ON_CLOSE and JApplet.destroy()
		frame.setDefaultCloseOperation(onClose());

		//Set location and size of JFrame to fit Grid
		frame.setSize(920, 641);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		//Initialize the JApplet, Grid
		grid.init();
		
		//Add the grid JApplet
		frame.getContentPane().add(grid);
		
		//Set JFrame visibility to true;
		frame.setVisible(true);
	}
	
	public static int onClose(){
		//dispose JApplet, Grid
		grid.destroy();
		
		//dispose JFrame
		return JFrame.EXIT_ON_CLOSE;
	}
	
	
}
