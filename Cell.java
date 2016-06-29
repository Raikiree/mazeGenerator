
public class Cell {
	boolean top;
	boolean right;
	boolean bot;
	boolean left;
	
	int row;
	int col;
	
	public Cell(int row, int col, int randNum) {
		top = (randNum == 0);
		right = (randNum == 1);
		bot = (randNum == 2);
		left = (randNum == 3);
		
		this.row = row;
		this.col = col;
	}
	
	public boolean isDeactive(){
		if(top && right && bot && left)
			return true;
		else
			return false;
	}
	
	public boolean isVisited(int n){
		if(n == 0){
			return !(top ^ true);
		}else if(n == 1){
			return !(right ^ true);
		}else if(n == 2){
			return !(bot ^ true);
		}else{
			return !(left ^ true);
		}
		
	}
}
