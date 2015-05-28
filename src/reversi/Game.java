package reversi;


import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


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
        

    public Game(Player player1, Player player2){
    	
    	super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        
        int height = Settings.instance().getBoardHeight();
        int width = Settings.instance().getBoardWidth();
        getContentPane().setLayout(new BorderLayout());
        board = new Board(height, width, this);
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
        player2Button.setBackground(Color.WHITE);
       
        
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
        //Methods.placeAtCenter(this, this.getSize().width, this.getSize().height);

        
        if(player1.getComputer())
        	((Computer)player1).play(board);
        pack();
        setVisible(true);
    }
    	
    	
	public void actionPerformed(ActionEvent e) {
		Player player1 = (Settings.instance().get1IsComputer()) ? new Computer(Disk.WHITE) : new Human(Disk.WHITE);
		Player player2 = (Settings.instance().get2IsComputer()) ? new Computer(Disk.BLACK) : new Human(Disk.BLACK);
		
	    if (e.getSource().equals(newGame)){
	        Game game = new Game(player1, player2);

	        this.dispose();
	    }
	    if (e.getSource().equals(exitGame)){
	        System.exit(0);
	    }
	    if (e.getSource().equals(backToMenu)){
	    	new Menu(this);	
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
    	
    	public void noValidMoves() {
    		Player otherPlayer = (currPlayer.isEqual(player1)) ? player2 : player1;
    		
    		if (validMoves(otherPlayer) == 0) {
				System.out.println("GAME ENDED!!");
    			endGame();
    		}
    		
    		else {
    			noValidMovesDialog();
    			switchPlayer();
    		}
    	}
    	
    	public void noValidMovesDialog() {
    		JDialog.setDefaultLookAndFeelDecorated(true);
	 		
    		final JDialog dialog = new JDialog(this, "No valid moves", true);		//TODO - set to true?
            dialog.setSize(200,100);
            dialog.setLayout(new BorderLayout());
            
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int jframeWidth = this.getSize().width;
            int jframeHeight = this.getSize().height;
            int X = (dim.width - jframeWidth)/2;
            int Y = (dim.height - jframeHeight)/4;
            dialog.setLocation(X, Y);
            
            String name = (currPlayer == player1) ? Settings.instance().getPlayer1Name() : Settings.instance().getPlayer2Name();
            
            dialog.add(new JLabel(name + " have no valid moves!"), BorderLayout.CENTER);
            JButton ok = new JButton("OK");
            ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
				}
			});
            dialog.add(ok, BorderLayout.PAGE_END);
                        
            dialog.setVisible(true);
    	}


		public void switchPlayer() {
		
			currPlayer = (currPlayer.isEqual(player1)) ? player2 : player1;
				
			if (validMoves(currPlayer) == 0) {
				noValidMoves();
			}
			
			if (currPlayer.isEqual(player1)){
				player1Button.setBackground(Color.RED);
				player2Button.setBackground(SystemColor.text);	
				
				if(currPlayer.getComputer()){
					((Computer) currPlayer).play(board);
				}
			}
			else {
				player2Button.setBackground(Color.RED);
				player1Button.setBackground(SystemColor.text);
				
				if(currPlayer.getComputer()){
					((Computer) currPlayer).play(board);
				}
				
			}
			
		}
		
		public Player getCurrPlayer(){
			return currPlayer;
		}

		//TODO!!!
		public void endGame(){
		    //String name;
		    String msg;
		    
		    if (player1.getScore() == player2.getScore()) {
				System.out.println("it's a tie!");
				//name = "it's a TIE! - click for restart";
				msg = "it's a TIE! - click for restart";
				//TODO - dialog
				return;
		    }
		    
		    Player winner = (player1.getScore() > player2.getScore()) ? player1 : player2;
		    String winnerName = (winner.equals(player1)) ? Settings.instance().getPlayer1Name() : Settings.instance().getPlayer2Name();
		    
			System.out.println(winnerName + " wins");
			//name = "Player1 WINS! - click for restart";
			msg = winnerName + " WINS!";
			
    		JDialog.setDefaultLookAndFeelDecorated(true);
	 		
    		final JDialog dialog = new JDialog(this, "No valid moves", true);		//TODO - set to true?
            dialog.setSize(200,100);
            dialog.setLayout(new BorderLayout());
            
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int jframeWidth = this.getSize().width;
            int jframeHeight = this.getSize().height;
            int X = (dim.width - jframeWidth)/2;
            int Y = (dim.height - jframeHeight)/4;
            dialog.setLocation(X, Y);
                        
            dialog.add(new JLabel(winnerName + " WINS!"), BorderLayout.CENTER);
            JButton ok = new JButton("OK");
            
            ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
				}
			});
            
            dialog.add(ok, BorderLayout.PAGE_END);
            
            dialog.setVisible(true);

//			JPanel displayB = new JPanel();
//			
//			JButton b = new JButton(name);
//			displayB.add(b);
//			displayB.setVisible(true);
//			b.addActionListener(new java.awt.event.ActionListener() {
//			        public void actionPerformed(java.awt.event.ActionEvent evt) {
//			        	new Game(new Human(Disk.WHITE), new Human(Disk.BLACK));		//TODO
//		    	        dispose();
//			        }
//			 });
			//jLayeredPane2.invalidate();
			board.setVisible(false);
//			getContentPane().add(displayB, BorderLayout.CENTER);
//		    String name;
//			if (player1.getScore() > player2.getScore()) {
//				System.out.println("Player1 WINS!\n   click for restart");
//				name = "Player1 WINS!\n   click for restart";
//			}
//			else{
//				if (player2.getScore() > player1.getScore()){ 
//					System.out.println("Player2 WINS!\n   click for restart");
//					
//					name ="Player2 WINS!\n   click for restart";
//				}
//				else{
//					System.out.println("it's a tie!");
//					name = "it's a TIE!\n    click for restart";
//				}
//			}
//			JPanel displayMessage = new JPanel();
//			JButton b = new JButton(name);
//			b.setToolTipText(name);
//			JDialog message = new JDialog();
//			message.addWindowListener(new WindowAdapter() {
//			      public void windowClosing(WindowEvent e) {
//			      }
//			});
//			message.setTitle("game - ended");
//			message.getRootPane().setDefaultButton(b);
//			b.setPreferredSize(new Dimension(150,150) );
//			displayMessage.add(b);
//		
//			message.getContentPane().add(displayMessage);
//			
//			message.setSize(200, 200);
//		
//			message.setLocationRelativeTo(null);
//			message.show();
//			b.addActionListener(new java.awt.event.ActionListener() {
//			        public void actionPerformed(java.awt.event.ActionEvent evt) {
//			        	dispose();
//			    	    Game game = new Game(player1, player2);
//			    	    
//			        }
//			 });
//>>>>>>> b22c20580307d885563f49f1a923f77ffb09f111
			
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
		
}