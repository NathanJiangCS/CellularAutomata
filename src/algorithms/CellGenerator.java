package algorithms;

public class CellGenerator {
	
	private int[][] cells;
	
	//default all dead cells
	public CellGenerator(){
		cells = new int[20][20];
		for(int i = 0; i < cells.length;i++){
			for(int j = 0; j < cells.length; j++){
				cells[i][j] = 0;
			}
		}
	}
	
	//overload cells array
	public CellGenerator(int[][] cells){
		this.cells = cells;
	}
	
	//generate cells function for the next generation, with numAlive adjacent rule.
	public int[][] generateNextByNumAlive(int numAlive){
		
		//generate empty dataset of cells to be returned
		int[][] cellset = new int[20][20];
		for(int i = 0; i < cellset.length; i ++){
			for(int j = 0; j < cellset.length; j++){
				cellset[i][j] = 0;
			}
		}
		
		for(int i = 0; i < cells.length; i++){
			for(int j = 0; j < cells.length; j++){
				int numalive = 0;
				int[][] checkcell = new int[4][2];
				//check up adjacent
				checkcell[0][0] = i-1 >= 0 ?i-1 : 19;
				checkcell[0][1] = j;
				//check down adjacent
				checkcell[1][0] = i+1 <= 19?i+1 : 0;
				checkcell[1][1] = j;
				//check left adjacent
				checkcell[2][0] = i;
				checkcell[2][1] = j-1 >= 0?j-1 : 19;
				//check right adjacent
				checkcell[3][0] = i;
				checkcell[3][1] = j+1 <= 19?j+1 : 0;
				
				//check co-ordinates
				for(int k = 0; k < 4; k++){
					if(cells[checkcell[k][0]][checkcell[k][1]] == 1){
						numalive += 1;
						System.out.println(checkcell[k][0] + " " + checkcell[k][1] + " is alive.");
					}
				}
				
				if(numalive == numAlive){
					cellset[i][j] = 1;
				}
				
			}
		}
		cells = cellset;
		return cellset;
	}
	
	public void setCells(int[][] cells){
		this.cells = cells;
	}
	
	public int[][] getCells(){
		return cells;
	}
}
