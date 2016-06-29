

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;
import javax.swing.JLabel;

public class MazeDemo extends JFrame {

	private JPanel contentPane;
	private Grid[][] board;
	private int row;
	private int col;
	private int boardWidth;
	private int boardDepth;
	private char[][]mazeRoute;
	
	private Maze mz;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MazeDemo frame = new MazeDemo(5,5);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public MazeDemo(int dep, int wid, Maze m) {
		row = dep;
		col = wid;
		boardDepth = dep * 2 + 1;
		boardWidth = wid * 2 + 1;
		mz = m;
		
		
		setTitle("MazeGenerator");
		//initiate the board
		board = new Grid[boardDepth][boardWidth];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 401, 451);
		setLayout(new BorderLayout());
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 7, 390, 333);
		for(int i = 0; i < boardDepth; i++){
			for(int j = 0; j < boardWidth; j++){
				board[i][j] = new Grid();
				panel.add(board[i][j]);
				board[i][j].setColor(Color.BLUE);
			}
		}
		contentPane.setLayout(null);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(boardDepth, boardWidth, 0, 0));
		
		
		//Button "Clean"
		JButton btnClean = new JButton("Clean");
		btnClean.setBounds(5, 340, 195, 83);
		btnClean.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//reSet
				mz.generatePlate();
				
				for(int r = 0; r < board.length; r++){
					for(int c = 0; c < board[0].length; c++){
						if(mz.myMaze[r][c] == 'X'){
							board[r][c].setColor(Color.BLUE);
						}else{
							board[r][c].setColor(Color.WHITE);
						}
					}
				}
				
			}
		});
		contentPane.add(btnClean);
		
		//Button "New Maze"
		JButton btnNewMaze = new JButton("New Maze");
		btnNewMaze.setBounds(200, 340, 195, 83);
		btnNewMaze.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//generate new maze
				mz.generateMaze();
				
				for(int r = 0; r < board.length; r++){
					for(int d = 0; d < board[0].length; d++){
						if(mz.myMaze[r][d] == 'X'){
							board[r][d].setColor(Color.BLUE);
						}else{
							board[r][d].setColor(Color.WHITE);
						}
					}
				}
				
				for(int r = 0; r < board.length; r++){
					for(int d = 0; d < board[0].length; d++){
						if(mz.route[r][d] == 1){
							board[r][d].setColor(Color.YELLOW);
						}
					}
				}
				
			}
		});
		contentPane.add(btnNewMaze);
		
	}
}
