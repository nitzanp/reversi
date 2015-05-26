package reversi;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame implements ActionListener {
	
	private  Player currPlayer;
	
	private Board board;
    private JButton newGame;
    private JButton backToMenu;
    private JButton exitGame;
    private JButton player1Button;
    private JButton player2Button;
    public static int row;
    public static int col;
    private Player player1;
    private Player player2;
    private JPanel boardPanel;
    
    private int pass;		//TODO - temp
    

    public Game(int N, Player player1, Player player2){
    	
    	super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

        int size = 4;
        getContentPane().setLayout(new BorderLayout());
        this.pass = 0;
        board = new Board(size, this);
        this.player1 = player1;
        this.player2 = player2;
        currPlayer = player1;
       
        JPanel optionsPanel = new JPanel();
        JPanel boardPanel = new JPanel();

        
        // Create buttons
        newGame = new JButton("NEW GAME");
        //newGame.setBounds(60, 50, 220, 50);
        backToMenu = new JButton("MENU");
        //backToMenu.setBounds(280, 50, 220, 50);
        exitGame = new JButton("EXIT");
        //exitGame.setBounds(500, 50, 220, 50);
        player1Button = new JButton(scoreString(player1));
        player1Button.setBackground(Color.RED);
        player2Button = new JButton(scoreString(player2));
        //player2Button.setBackground();
       
        
        // Add action listeners
        newGame.addActionListener(this);
        exitGame.addActionListener(this);
        backToMenu.addActionListener(this);
        player1Button.addActionListener(this);
        player2Button.addActionListener(this);
        
        optionsPanel.setLayout(new FlowLayout());
        optionsPanel.add(player1Button);
        optionsPanel.add(exitGame);
        optionsPanel.add(newGame);
        optionsPanel.add(backToMenu);
        optionsPanel.add(player2Button);
        
        boardPanel.add(board);
        
        getContentPane().add(optionsPanel, BorderLayout.NORTH);
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        
        
        //Place at center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int jframeWidth = this.getSize().width;
        int jframeHeight = this.getSize().height;
        int X = (dim.width - jframeWidth)/2 - 450;
        int Y = (dim.height - jframeHeight)/4 - 120;
        this.setLocation(X, Y);
 
        pack();
        setVisible(true);
    }
    	
    	
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource().equals(newGame)){
	        Game game = new Game(8, new Human(Disk.WHITE), new Human(Disk.BLACK));
	        this.dispose();
	    }
	    if (e.getSource().equals(exitGame)){
	        System.exit(0);
	    }
	    if (e.getSource().equals(backToMenu)){
	    	Menu menu = new Menu(this);	
	        this.setVisible(false);
	    }
	}
    	
    	
    	public int validMoves(Player player) {
    		int moves = 0;
    		Disk disk = player.getDisk();
    		for (Map<Integer, Cell> row : board.getAllCells().values()) {
    			for (Cell cell : row.values()) {
    				if (cell.getCellWillChange().get(disk).size() > 0 && cell.isLegal(disk) && cell.isEmpty()) {
    					moves++;
    				}
    			}
    		}
    		return moves;
    	}


		public void switchPlayer() {
			
			boolean checkBlack, checkWhite;
//			for (Map<Integer, Cell> row : board.getAllCells().values()) {
//				for (Cell cell : row.values()) {
//					if (cell.getCellWillChange().size() > 0 && cell.isLegal(Disk.BLACK) && cell.getDisk().equals(Disk.NONE))
//							checkBlack = true;
//					if (cell.getCellWillChange().size() > 0 && cell.isLegal(Disk.WHITE) && cell.getDisk().equals(Disk.NONE))
//							checkWhite = true;
//				}
//			}
			
			checkWhite = (validMoves(player1) > 0) ? true : false;
			checkBlack = (validMoves(player2) > 0) ? true : false;
			
//			currPlayer = (currPlayer.isEqual(player1)) ? player2 : player1;
//			if (validMoves(currPlayer) == 0) {
//				System.out.println("NO VALID MOVES!");		//TODO - popup a message
//				//switchPlayer();
//			}
//			
//			if (currPlayer.isEqual(player1) && checkBlack) {
//				player2Button.setBackground(Color.RED);
//				player1Button.setBackground(SystemColor.text);
//				currPlayer = player2;
//			}
//			else {
//				if (currPlayer.isEqual(player2) && checkWhite){
//					currPlayer = player1;
//					player1Button.setBackground(Color.RED);
//					player2Button.setBackground(SystemColor.text);
//				}
//				else
//					endGame();
//			}
			
			currPlayer = (currPlayer.isEqual(player1)) ? player2 : player1;
			if (validMoves(currPlayer) == 0) {
				System.out.println("NO VALID MOVES!");		//TODO - popup a message
				pass++;
				if (pass == 2) {
					System.out.println("GAME ENDED!!");
					endGame();
				}
				else {
					switchPlayer();
				}
				
			}
			if (currPlayer.isEqual(player1)){
				player1Button.setBackground(Color.RED);
				player2Button.setBackground(SystemColor.text);
			}
			else {
				player2Button.setBackground(Color.RED);
				player1Button.setBackground(SystemColor.text);
				currPlayer = player2;
			}

			
		}
		
		public Player getCurrPlayer(){
			return currPlayer;
		}


		public void endGame(){
		    String name;
			if (player1.getScore() > player2.getScore()) {
				System.out.println("player1 wins");
				name = "Player1 WINS! - click for restart";
			}
			else{
				if (player2.getScore() > player1.getScore()){ 
					System.out.println("player2 wins");
					name ="Player1 WINS! - click for restart";
				}
				else{
					System.out.println("it's a tie!");
					name = "it's a TIE! - click for restart";
				}
			}
			JPanel displayB = new JPanel();
			
			JButton b = new JButton(name);
			displayB.add(b);
			b.addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent evt) {
			        	Game game = new Game(8, new Human(Disk.WHITE), new Human(Disk.BLACK));
		    	        dispose();
			        }
			 });
			//jLayeredPane2.invalidate();
			board.setVisible(false);
			getContentPane().add(displayB, BorderLayout.CENTER);
			
		}
		public void setScore(int flipped) {
			 if (currPlayer.isEqual(player1)){
				 player1.setScore(player1.getScore() + flipped + 1);
				 player2.setScore(player2.getScore() - flipped);
			 }
			 else{
				 player2.setScore(player2.getScore() + flipped + 1);
				 player1.setScore(player1.getScore() - flipped);
			 }
//			 player1Button.setText("PLAYER 1: " + player1.getScore());
//			 player2Button.setText("PLAYER 2: " + player2.getScore());
			 player1Button.setText(scoreString(player1));
			 player2Button.setText(scoreString(player2));
		}
		
		
		public String scoreString(Player player) {
			StringBuilder sb = new StringBuilder();
			String name = (player == player1) ? "PLAYER1" : "PLAYER2";
			sb.append(name).append(" - ").append(player.getDisk().toString());
			sb.append(" : ").append(player.getScore());
			return sb.toString();			
		}
		
		public void addButton(String name) {
		   

		   
		}
	
   
}