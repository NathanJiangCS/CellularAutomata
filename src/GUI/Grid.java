package gui;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import algorithms.CellGenerator;

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

	CellGenerator cellGenerator;

	int[][] initArray = new int [20][20]; //initial grid
	int[][] newArray = new int [20][20]; //started grid

	//CellGenerator cellGenerator = new CellGenerator (gridArray);

	Button confirm; //button to confirm parameters
	Button newGen; //button to calculate new generation and draw next gen of cells
	Button reset; //reset the simulation
	TextField numAdj; //enter num of cells alive

	int numAlive; //the number of adjacent cells of initial cell
	int cellSize = 30; //how many pixels wide/long each cell is

	private Image dbImage; //used for double buffering
	private Graphics dbg; //used for double buffering

	int state;  //0 = startup+help, 1 = initialization, 2 = confirmed + started 
	Boolean varTypeException = false;

	//initialization of state variables
	public void init(){

		state = 0; 

		isMousePressed = false; //mouse unpressed by default
		addMouseListener (this); //add mouse and motion listener
		addMouseMotionListener (this);

		confirm = new Button ("Confirm"); //create confirm button object
		newGen = new Button ("Next Generation");
		numAdj = new TextField ("1-4");
		reset = new Button ("Reset");

		confirm.addActionListener(this); //confirm button action listener
		newGen.addActionListener(this); //new generation button listener
		reset.addActionListener(this);

		add (confirm); //draw the confirm button
		add (numAdj);
		add (reset);
		
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
		if (state == 0){
			confirm.setLocation (700,200);
			confirm.setSize(100, 50);
			numAdj.setLocation(700, 300);
			numAdj.setSize(100, 20);
			reset.setLocation(700,450);
			reset.setSize(100,20);
		}


		//if the game is in initialization state
		if (state == 1){

			//scroll through every cell in the 20x20 array
			for (int x=0; x<20; x++){
				for (int y=0; y<20; y++){
					
					//if a cell in the grid array is set to 1 from clicking then a rectangle will be drawn at that location
					if (initArray[x][y] == 1){
						g.setColor(sqColor);
						g.fillRect(x*30, y*30, cellSize, cellSize);
					}
				}
			}
		}


		//once the confirm button has been hit state will be 2 and simulation has started
		if (state == 2){
			//relocate the new generation button
			newGen.setLocation(680, 200);
			newGen.setSize(140, 50);

			//tells the user's input
			if (varTypeException){
				g.drawString ("Invalid Input - Default: 1", 690, 295);
			}
			else 
				g.drawString("Input: "+numAlive, 740, 295);


			for (int x=0; x<20; x++){
				for (int y=0; y<20; y++){
					//if a cell in the grid array is set to 1 from clicking then a rectangle will be drawn at that location
					if (newArray[x][y] == 1){
						g.setColor(sqColor);
						g.fillRect(x*30, y*30, cellSize, cellSize);
					}
				}
			}

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
			//check the input of the textfield
			try{
				//get the info from numAdj textfield
				numAlive = Integer.parseInt(numAdj.getText());
			}
			catch(NumberFormatException e){
				varTypeException = true;
			}
		
			//add new gen button and relocate confirm button, change state
			add(newGen);
			confirm.setLocation(-150,0);
			state = 2;
			numAdj.setLocation(-150, 0);
			//create new cellGenerator object
			cellGenerator = new CellGenerator(initArray);
			//if textfield input is 1-4, execute with specified parameter
			if (numAlive==1||numAlive==2||numAlive==3||numAlive==4){
				newArray = cellGenerator.generateNextByNumAlive(numAlive);
			}
			//if any other input is entered in textfield, numAlive will default to 1
			else {
				numAlive = 1;
				newArray = cellGenerator.generateNextByNumAlive(numAlive);
			}
		}

		//if the new generation button is pressed
		if (evt.getSource()==newGen){

			//get the cells adjacent and set coordinates to be drawn
			//call repaint to draw new cells
			newArray = cellGenerator.generateNextByNumAlive(numAlive);

		}

		//if the reset button is pressed
		if (evt.getSource()==reset){
			
			//set to initial app parameters 
			newGen.setLocation(-150,0);
			confirm.setLocation(700,200);
			numAdj.setLocation(700, 300);
			state=0;
			varTypeException = false;
			
			//empty the textfields
			numAdj.setText("1-4");
			
			//empty existing array of cells
			initArray = clearArray(initArray);
			newArray = clearArray(newArray);

		}

		repaint(); 

	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// called during motion when no buttons are down
		mx = e.getX ();
		my = e.getY ();
		
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

		//you can only add and remove cells during initialization states
		if (state == 0 || state == 1){
			//the cell number in the array is the mouse location divided by cell size
			cellInitX = mx/cellSize;
			cellInitY = my/cellSize;

			//if the selected cell is dead (0), select to be alive (1)
			if ((mx<=600 && my<=600) && initArray[cellInitX][cellInitY] == 0){
				//set the state to initialization state
				state = 1;
				//add this cell to the grid
				initArray[cellInitX][cellInitY] = 1;
			}
			//if the selected cell is alive (1), unselect it to be dead (0)
			else if ((mx<=600 && my<=600) && initArray[cellInitX][cellInitY] == 1){
				//remove this cell from the grid
				initArray[cellInitX][cellInitY] = 0;
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

	public int[][] clearArray (int[][] arr){
		for (int i=0; i<20; i++){
			for (int j=0; j<20; j++){
				arr[i][j] = 0;
			}
		}

		return arr;

	}



}
