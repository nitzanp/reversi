package reversi;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Game extends JFrame implements ActionListener {
	
	public static Disk currPlayer;
	
	private Board board;
    private JButton newGame;
    private JButton backToMenu;
    private JButton exitGame;
    public static int row;
    public static int col;
    

    public Game(int N){
    	
    	super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

        int size = 8;
        getContentPane().setLayout(new BorderLayout());
        board = new Board(size);
        
        currPlayer = Disk.BLACK;
       
        JPanel optionsPanel = new JPanel();
        JPanel boardPanel = new JPanel();
        
        
        
        // Create buttons
        newGame = new JButton("NEW GAME");
        //newGame.setBounds(60, 50, 220, 50);
        backToMenu = new JButton("MENU");
        //backToMenu.setBounds(280, 50, 220, 50);
        exitGame = new JButton("EXIT");
        //exitGame.setBounds(500, 50, 220, 50);
        
        
        
        // Add action listeners
        newGame.addActionListener(this);
        exitGame.addActionListener(this);
        backToMenu.addActionListener(this);
        
        optionsPanel.setLayout(new FlowLayout());
        optionsPanel.add(exitGame);
        optionsPanel.add(newGame);
        optionsPanel.add(backToMenu);
        
        boardPanel.add(board);
        
        getContentPane().add(optionsPanel, BorderLayout.NORTH);
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        
        
        //Place at center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int jframeWidth = this.getSize().width;
        int jframeHeight = this.getSize().height;
        int X = (dim.width - jframeWidth)/2 - 100;
        int Y = (dim.height - jframeHeight)/4;
        this.setLocation(X, Y);
 
        pack();
        setVisible(true);
    }
    	
    	
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource().equals(newGame)){
	        System.out.println("starting new game");
	        //TODO number from player
	        Game game = new Game(8);
	        this.dispose();
	    }
	    if (e.getSource().equals(exitGame)){
	    	System.out.println("exiting...");
	        System.exit(0);
	    }
	    if (e.getSource().equals(backToMenu)){
	    	
	    	GraphicMenu menu = new GraphicMenu();
	    	
	        this.dispose();
	    }
	}
    	
    	public void playGame () {
    		while(true) {
    			
    		}
    	}

   
}