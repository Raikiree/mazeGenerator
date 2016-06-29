import java.awt.EventQueue;
import java.util.Arrays;

public class Main {

	private final static int DEPTH = 25; 
	private final static int WIDTH = 25; 
	private final static boolean DISPLAY = false; 

	public static void main(String[] args) {
	
		final Maze myMaze = new Maze(WIDTH, DEPTH, DISPLAY); 
	
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MazeDemo frame = new MazeDemo(WIDTH,DEPTH, myMaze);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
