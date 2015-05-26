package reversi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Menu extends JFrame implements ActionListener {
 
    private JButton newGame;
    private JButton resumeGame;
    private JButton exitGame;
    private Game game;

    public Menu(Game game){
    	
        super("MainMenu");
        this.game = game;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
  
        // Create buttons
        newGame = new JButton("NEW GAME");
        resumeGame = new JButton("RESUME GAME");
        exitGame = new JButton("EXIT");
        
        // Add action listeners
        newGame.addActionListener(this);
        exitGame.addActionListener(this);
        resumeGame.addActionListener(this);
        
        // Add all objects to Content Pane
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(newGame);
        getContentPane().add(exitGame);
        getContentPane().add(resumeGame);
      
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
    	    	Game game = new Game(8, new Human(Disk.WHITE), new Human(Disk.BLACK));
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
    	}
  
}