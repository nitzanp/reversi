package reversi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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
        //getContentPane().setLayout(new BorderLayout());
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
  
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
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(newGame);
        getContentPane().add(resumeGame);
        getContentPane().add(settings);
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
    	    	this.game = new Game(new Human(Disk.WHITE), new Human(Disk.BLACK));
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