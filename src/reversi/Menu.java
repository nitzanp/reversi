package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Menu extends JFrame implements ActionListener {
 
    private JButton newGame;
    private JButton resumeGame;
    private JButton settings;
    private JButton exitGame;
    private Game game;

    public Menu(Game game){
    	
        super("MainMenu");
        this.game = game;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(4, 1));
  
        //set view
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.CYAN);
        
        // Create buttons
        newGame = new JButton("NEW GAME");
        resumeGame = new JButton("RESUME GAME");
        settings = new JButton("SETTINGS");
        exitGame = new JButton("EXIT");
        
        if (game == null) {
        	resumeGame.setEnabled(false);
        }
        
        // Add action listeners
        newGame.addActionListener(this);
        resumeGame.addActionListener(this);
        settings.addActionListener(this);
        exitGame.addActionListener(this);
        
        
        // Add all objects to Content Pane
        getContentPane().add(newGame);
        getContentPane().add(resumeGame);
        getContentPane().add(settings);
        getContentPane().add(exitGame);
        
       
        setVisible(true);
    }
    	
    	public void actionPerformed(ActionEvent e) {
    		Player player1 = (Settings.instance().get1IsComputer()) ? new Computer(Disk.WHITE) : new Human(Disk.WHITE);
    		Player player2 = (Settings.instance().get2IsComputer()) ? new Computer(Disk.BLACK) : new Human(Disk.BLACK);
    		
    	    if (e.getSource().equals(newGame)){
    	    	this.game = new Game(player1, player2);
    	    	this.dispose();
    	    }
    	    
    	    if (e.getSource().equals(exitGame)){
    	        System.exit(0);
    	    }
    	    if (e.getSource().equals(resumeGame)){
    	    	if(game != null){
    	    		game.setVisible(true);
    	    		this.dispose();
    	    	}
    	    }

    	    if (e.getSource().equals(settings)) {
    	    	new SettingsFrame(game);
    	    	this.dispose();
    	    }
    	}
  
}