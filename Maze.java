
import java.util.Random;
import java.util.Stack;


public class Maze {

	public int mazeWid; 
	public int mazeDep;
	public int cellWid;
	public int cellDep;
	
	public char[][] myMaze; 
	private int[][] bone;
	public int[][] route;
	private boolean flag;

	Maze(int cellWidth, int cellDepth, boolean debug) {
		cellWid = cellWidth;
		cellDep = cellDepth;
		mazeWid = (cellWidth * 2) + 1;	
		mazeDep = (cellDepth * 2) + 1; 
		
		myMaze = new char[mazeDep][mazeWid]; 
		bone = new int[mazeDep][mazeWid];
		route = new int[mazeDep][mazeWid];
		flag = debug;
		
		generatePlate();
		generateMaze();
		if(flag)
			display();
		getRoute();
		}
	
	private void display(){
		for(int i = 0; i < mazeDep; i++){
			for(int j = 0; j < mazeWid; j++){
					System.out.print(myMaze[i][j] + " ");
			}
			System.out.print('\n');
		}
		System.out.print('\n');
	}

	private void getRoute() {
		for(int i = 0; i < mazeDep; i++){
			for(int j = 0; j < mazeWid; j++){
				if(myMaze[i][j] == 'V')
					myMaze[i][j] = ' ';
				if(route[i][j] == 1){
					System.out.print("* ");
				}else{
					System.out.print(myMaze[i][j] + " ");
				}
			}
			System.out.print('\n');
		}
	}

	//generate the Plate
	public void generatePlate() {
		//put space to all grids
		for(int i = 0; i < mazeWid; i++){
			for(int j = 0; j < mazeDep; j++){
				myMaze[j][i] = ' ';
				bone[j][i] = 0;
				route[j][i] = 0;
			}
		}
		//initialize the first col and last col
		for(int i = 0; i < mazeDep; i++){
			myMaze[i][0] = 'X';
			myMaze[i][mazeWid - 1] = 'X';
			
			bone[i][0] = 1;
			bone[i][mazeWid - 1] = 1;
		}
		
		//initialize the first row and last row
		for(int i = 0; i < mazeWid; i++){
			myMaze[0][i] = 'X';
			myMaze[mazeDep - 1][i] = 'X';
			
			bone[0][i] = 1;
			bone[mazeDep - 1][i] = 1;
		}
		
		//modify the entracne and exit
		myMaze[0][1] = ' ';
		myMaze[mazeDep - 1][mazeWid - 2] = ' ';
		//for v1.0
		bone[0][1] = 0;
		bone[mazeDep - 1][mazeWid - 2] = 0;
		
		//initialize the rest part of the plate
		for(int i = 2; i < mazeWid - 1; i = i + 2){
			for(int j = 0; j < mazeDep - 1; j++){
				myMaze[j][i] = 'X';
			}
			for(int j = 2; j < mazeDep - 1; j = j + 2){
				bone[j][i] = 1;
			}
		}
		for(int i = 2; i < mazeDep - 1; i = i + 2){
			for(int j = 0; j < mazeWid - 1; j++){
				myMaze[i][j] = 'X';
			}
		}
		
	}
	//char: myMaze
	//int:  bone route
	//bone: 0-removable, 1-boarder, 2-occupied cells
	//route: 0-None, 1-route
	public void generateMaze(){
		Stack<Cell> cells = new Stack<Cell>();
		Random rand = new Random();
		int randNum;
		int row, col;
		int countCell = 1;
		cells.push(new Cell(1, 1, 0));
		
		//***********start the LOOP************
		while(countCell != cellWid * cellDep ){
			Cell cur = cells.peek();
			row = cur.row;
			col = cur.col;
			//step recorder 'V'
			myMaze[row][col] = 'V';
			if(flag)
				display();
			if(cur.isDeactive()){
				cells.pop();
				continue;
			}
			bone[cur.row][cur.col] = 2;
			
			//*******Generate the Route*******
			if(cur.row == 2 * cellDep - 1 && cur.col == 2 * cellWid - 1){
				Stack<Cell> tmp = (Stack<Cell>) cells.clone();
				Cell first = tmp.pop();
				route[first.row][first.col] = 1;
				int lastRow = first.row;
				int lastCol = first.col;
				while(!tmp.empty()){
					Cell tmpCell = tmp.pop();
					route[tmpCell.row][tmpCell.col] = 1;
				
					if(tmpCell.row - lastRow == 0){
						if(tmpCell.col > lastCol){
							route[tmpCell.row][tmpCell.col - 1] = 1;
						}else{
							route[tmpCell.row][tmpCell.col + 1] = 1;
						}
					}
					if(tmpCell.col - lastCol == 0){
						if(tmpCell.row > lastRow){
							route[tmpCell.row - 1][tmpCell.col] = 1;
						}else{
							route[tmpCell.row + 1][tmpCell.col] = 1;
						}
					}
					lastRow = tmpCell.row;
					lastCol = tmpCell.col;
				}
			}
			
			randNum = rand.nextInt(4);
			while(cur.isVisited(randNum)){
				randNum = rand.nextInt(4);
			}
			
			if(randNum == 0){
				row -= 2;
			}
			if(randNum == 1){
				col += 2;
			}
			if(randNum == 2){
				row += 2;
			}
			if(randNum == 3){
				col -= 2;
			}
			
			//IF VALID
			if(row >= 1 && row <= 2 * cellDep - 1 && col >= 1 && col <= 2 * cellWid - 1 && bone[row][col] != 2){
				//Modify the wall
				int tmpRow, tmpCol;
				if(randNum == 0){
					tmpRow = row + 1;
					myMaze[tmpRow][col] = ' ';
					cur.top = true;
				}
				if(randNum == 1){
					tmpCol = col - 1;
					myMaze[row][tmpCol] = ' ';
					cur.right = true;
				}
				if(randNum == 2){
					tmpRow = row - 1;
					myMaze[tmpRow][col] = ' ';
					cur.bot = true;
				}
				if(randNum == 3){
					tmpCol = col + 1;
					myMaze[row][tmpCol] = ' ';
					cur.left = true;
				}
				//the randNum for next node! opps!
				cells.push(new Cell(row, col, (randNum + 2) % 4));
				countCell++;
			}else{

				if(randNum == 0){
					row += 2;
					cur.top = true;
				}
				if(randNum == 1){
					col -= 2;
					cur.right = true;
				}
				if(randNum == 2){
					row -= 2;
					cur.bot = true;
				}
				if(randNum == 3){
					col += 2;
					cur.left = true;
				}
			}
		}//END while
	}
}