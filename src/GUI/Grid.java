package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
import java.applet.*;

@SuppressWarnings("serial")
public class Grid extends Applet implements ActionListener{

	Color bgColor; //back ground color
	Color sqColor; //color of cells
	
	int appLength; //applet horizontal length
	int appWidth; //applet vertical width
	
	int cellInitX; //initial x coordinate of cell
	int cellInitY; //initial y coordinate of cell
	
	Button confirm; //button to confirm parameters
	Button newGen; //button to calculate new generation and draw next gen of cells
	
	TextField adjAlive; //text fields to input # of adjacent cells of initial cell
	TextField initX, initY; //initial x and y coordinates of cell
	
	int adjacentCells; //the number of adjacent cells of initial cell
	int cellSize = 30; //how many pixels wide/long each cell is
	
	private Image dbImage; //used for double buffering
	private Graphics dbg; //used for double buffering

	//initialization of state variables
	public void init(){
		
		confirm = new Button ("Confirm"); //create confirm button object
		newGen = new Button ("Generate next generation");
		adjAlive = new TextField ("1-4"); //create textfield for adjacent cells alive
		initX = new TextField ("Cell Initial X");
		initY = new TextField ("Cell Initial Y");
		
		confirm.addActionListener(this); //confirm button action listener
		
		add (confirm); //draw the confirm button
		add (adjAlive); //draw the adjAlive textfield
		add (newGen); //draw the next generation button
		add (initX); 
		add (initY); //draw initX/initY textfields
		
		appLength = 900; 
		appWidth = 601;
		
		bgColor = Color.WHITE;
		sqColor = Color.BLACK;
		
		setBackground(bgColor);
		setSize (appLength, appWidth);
	}

	//graphics
	public void paint(Graphics g){
		
		super.paint(g);
		
		//draw initial grid
		g.drawRect (0,0,600,600);
		
		//draw horizontal grd lines
		for (int x=0; x<=600; x+=cellSize){
			g.drawLine(0, x, 600, x);
		}
		
		//draw vertical grd lines
		for (int y=0; y<=600; y+=cellSize){
			g.drawLine(y, 0, y, 600);
		}

		//locations and sizes of buttons and textfields
		confirm.setLocation (700,200);
		confirm.setSize(100, 50);
		adjAlive.setLocation(680, 180);
		adjAlive.setSize(140, 20);
		initX.setLocation(680,160);
		initY.setLocation(750,160);
		initX.setSize(70, 20);
		initY.setSize(70, 20);

		//draw the cells 
		g.fillRect(cellInitX, cellInitY, cellSize, cellSize);
		
		
	}

	//implement double buffering
	public void update(Graphics g) {
		if (dbImage == null) {
			dbImage = createImage (this.getSize ().width, this.getSize ().height);
			dbg = dbImage.getGraphics();
		}
		
		// clear screen in background
		dbg.setColor (getBackground ());
		dbg.fillRect (0, 0, this.getSize ().width, this.getSize ().height);

		// draw elements in background
		dbg.setColor (getForeground ());
		paint(dbg);
		
		// draw image on the screen
		g.drawImage(dbImage, 0, 0, this); 
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		//if the confirm button is pressed
		if (evt.getSource()==confirm){
			
			//get the text from textfields and convert to integer type
			adjacentCells = Integer.parseInt(adjAlive.getText());
			cellInitX = Integer.parseInt(initX.getText());
			cellInitY = Integer.parseInt(initY.getText());
		}
		
		repaint(); 

	}


}
