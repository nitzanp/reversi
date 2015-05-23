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
    private JButton exitGame;

    public Menu(){
    	
        super("MainMenu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
  
        // Create buttons
        newGame = new JButton("NEW GAME");
        exitGame = new JButton("EXIT");
        
        // Add action listeners
        newGame.addActionListener(this);
        exitGame.addActionListener(this);
        
        // Add all objects to Content Pane
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(newGame);
        getContentPane().add(exitGame);
      
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
    	        System.out.println("starting new game");
    	        
    	    }
    	    if (e.getSource().equals(exitGame)){
    	    	System.out.println("exiting...");
    	        System.exit(0);
    	    }
    	}
  
}