package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.applet.*;

@SuppressWarnings("serial")
public class Grid extends Applet implements ActionListener, MouseListener, MouseMotionListener{

	Boolean isMousePressed; //for mouse to see if the left click is pressed down
	int mx,my; //tracks the mouse x and y coordinates

	Color bgColor; //back ground color
	Color sqColor; //color of cells

	int appLength; //applet horizontal length
	int appWidth; //applet vertical width

	int cellInitX; //initial x coordinate of cell
	int cellInitY; //initial y coordinate of cell

	int[][] gridArray = new int [20][20]; //grid storage

	Button confirm; //button to confirm parameters
	Button newGen; //button to calculate new generation and draw next gen of cells

	int adjacentCells; //the number of adjacent cells of initial cell
	int cellSize = 30; //how many pixels wide/long each cell is

	private Image dbImage; //used for double buffering
	private Graphics dbg; //used for double buffering

	int gameState;  //0 = startup+help, 1 = initialization, 2 = confirmed + started 

	//initialization of state variables
	public void init(){

		gameState = 0;

		isMousePressed = false; //mouse unpressed by default
		addMouseListener (this); //add mouse and motion listener
		addMouseMotionListener (this);

		confirm = new Button ("Confirm"); //create confirm button object
		newGen = new Button ("Generate next generation");

		confirm.addActionListener(this); //confirm button action listener
		newGen.addActionListener(this); //new generation button listener

		add (confirm); //draw the confirm button


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

		//before anything has been pressed, opening state
		if (gameState == 0){
			confirm.setLocation (700,200);
			confirm.setSize(100, 50);
		}


		//if the game is in initialization state
		if (gameState == 1){

			//scroll through every cell in the 20x20 array
			for (int x=0; x<20; x++){
				for (int y=0; y<20; y++){
					//if a cell in the grid array is set to 1 from clicking then a rectangle will be drawn at that location
					if (gridArray[x][y] == 1){
						g.setColor(sqColor);
						g.fillRect(x*30, y*30, cellSize, cellSize);
					}

				}
			}

		}

		//once the confirm button has been hit gameState will be 2 and game has started
		if (gameState == 2){
			newGen.setLocation(680, 200);
			newGen.setSize(140, 50);
		}
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
			add(newGen);
			confirm.setLocation(-150,0);
			gameState = 2;
			//confirm the cells in grid array which are deemed alive (1) and how many cells
			//are alive in the next generation adjacent to those initial cells


		}

		//if the new generation button is pressed
		if (evt.getSource()==newGen){

			//get the cells adjacent and set coordinates to be drawn
			//call repaint to draw new cells
		}

		//initial cell choosing

		repaint(); 

	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// called during motion when no buttons are down
		mx = e.getX ();
		my = e.getY ();
		//show the position at which the mouse is at
		showStatus ("Mouse at (" + mx + "," + my + ")");
		//repaint position of mouse
		repaint ();
		e.consume ();
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		isMousePressed = true;

		if (gameState == 0 || gameState == 1){
			//the cell number in the array is the mouse location divided by cell size
			cellInitX = mx/cellSize;
			cellInitY = my/cellSize;

			//if the selected cell is dead (0), select to be alive (1)
			if ((mx<=600 && my<=600) && gridArray[cellInitX][cellInitY] == 0){
				//set the gameState to initialization state
				gameState = 1;
				//add this cell to the grid
				gridArray[cellInitX][cellInitY] = 1;
			}
			//if the selected cell is alive (1), unselect it to be dead (0)
			else if ((mx<=600 && my<=600) && gridArray[cellInitX][cellInitY] == 1){
				//remove this cell from the grid
				gridArray[cellInitX][cellInitY] = 0;
			}
		}

		repaint();
		e.consume ();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isMousePressed = false;
		e.consume ();
	}


}
