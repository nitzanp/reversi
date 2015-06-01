package reversi;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {
 
    private JButton newGame;
    private JButton resumeGame;
    private JButton settings;
    private JButton exitGame;
    private JButton load;
    private Game game;
    private ScoreTable scores;
    public Menu(Game game, ScoreTable scores){
    	
        super("MainMenu");
        this.game = game;
        this.scores = scores;
		String path = this.getClass().getClassLoader().getResource("").getPath();
		ImageIcon img = new ImageIcon(path + "image.png");
		this.setIconImage(img.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(5, 1));
  
        //set view
        this.setSize(300, 400);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.CYAN);
        
        // Create buttons
        newGame = new JButton("NEW GAME");
        resumeGame = new JButton("RESUME GAME");
        settings = new JButton("SETTINGS");
        load = new JButton ("LOAD GAME");
        exitGame = new JButton("EXIT");
       
        // Add action listeners
        newGame.addActionListener(this);
        resumeGame.addActionListener(this);
        load.addActionListener(this);
        settings.addActionListener(this);
        exitGame.addActionListener(this);
        
        
        // Add all objects to Content Pane
        getContentPane().add(newGame);
        getContentPane().add(resumeGame);
        getContentPane().add(settings);
        getContentPane().add(load);
        getContentPane().add(exitGame);   

        setVisible(true);
    }
    	
    	public void actionPerformed(ActionEvent e) {
    		
    		
    		if (e.getSource().equals(newGame)) {
    			Random rn = new Random();
    			int rand = rn.nextInt(2);
    			setTurns(rand);
    			this.dispose();
    			
    		}
    	    
    	    if (e.getSource().equals(exitGame)){
    	        System.exit(0);
    	    }
    	    if (e.getSource().equals(load)){
    	    	if(scores !=null)
    	    		this.scores.setVisible(false);
    	    	if(game != null)
    	    		game.dispose();
    	    	new Game(loadGame());
    	    	
    	    }
    	    if (e.getSource().equals(resumeGame)){
    	    	if(game != null){
    	    		game.setVisible(true);
    	    		this.dispose();
    	    	}
    	    }

    	    if (e.getSource().equals(settings)) {
    	    	new SettingsFrame(game, this.scores);
    	    	this.dispose();
    	    }
    	}
    	
    	private void setTurns(int rand){

    		/*computer vs human*/
    		if (Settings.instance().get2IsComputer() && !Settings.instance().get1IsComputer()){
    			Player player1 = new Human(Disk.WHITE, 1);
    			Player player2 = new Computer(Disk.BLACK, 2);
    			new Game(player1, player2, scores);

    		}
    		if (Settings.instance().get1IsComputer() && !Settings.instance().get2IsComputer()){
    			Player player1 = new Computer(Disk.BLACK, 1);
    			Player player2 = new Human(Disk.WHITE, 2);	
    			new Game(player2, player1, scores);
    		}
    		/*computer vs computer*/
    		if (Settings.instance().get1IsComputer() && Settings.instance().get2IsComputer()){
    			if (rand == 0){
    				Player player1 = new Computer(Disk.WHITE, 1);
    				Player player2 = new Computer(Disk.BLACK, 2);
    				new Game(player1, player2, scores);
    			}
    			else {
    				Player player1 = new Computer(Disk.WHITE, 1);
    				Player player2 = new Computer(Disk.BLACK, 2);
    				new Game(player2, player1, scores);
    			}
    		}
    			/*both humans*/
    			if(!Settings.instance().get1IsComputer() && !Settings.instance().get2IsComputer()){
    				if (rand == 0){
    					Player player1 = new Human(Disk.WHITE, 1);
    					Player player2 = new Human(Disk.BLACK, 2);
    					new Game(player1, player2, scores);

    				}
    				else {
    					Player player1 = new Human(Disk.BLACK, 1);
    					Player player2 = new Human(Disk.WHITE, 2);	
    					new Game(player2, player1, scores);
    				}
    			}
    	}
    	
    	private Game loadGame(){
    		String path = this.getClass().getClassLoader().getResource("").getPath();

    		try {
    		FileInputStream fin = new FileInputStream(path + "\\savedGame.sav");
    		ObjectInputStream in = new ObjectInputStream(fin);
    		try {
				game =  (Game) in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		in.close();
    		
    		
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return game;
    	}
  
}
