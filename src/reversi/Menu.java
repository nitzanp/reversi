package reversi;

import java.awt.BorderLayout;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {

	private JButton newGame;
	private JButton resumeGame;
	private JButton settings;
	private JButton exitGame;
	private JButton load;
	private Game game;

	public Menu(Game game) {

		super("MainMenu");
		this.game = game;
		ImageIcon img = new ImageIcon("images//image.png");
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
		load = new JButton ("LOAD GAME");
		settings = new JButton("SETTINGS");
		exitGame = new JButton("EXIT");

		if (game == null) {
			resumeGame.setEnabled(false);
		}

		// Add action listeners
		newGame.addActionListener(this);
		resumeGame.addActionListener(this);
		load.addActionListener(this);
		settings.addActionListener(this);
		exitGame.addActionListener(this);
		
		newGame.setFont(newGame.getFont().deriveFont(22.0f));
		resumeGame.setFont(resumeGame.getFont().deriveFont(22.0f));
		load.setFont(load.getFont().deriveFont(22.0f));
		settings.setFont(settings.getFont().deriveFont(22.0f));
		exitGame.setFont(exitGame.getFont().deriveFont(22.0f));


		// Add all objects to Content Pane
		getContentPane().add(newGame);
		getContentPane().add(resumeGame);
		getContentPane().add(load);
		getContentPane().add(settings);
		getContentPane().add(exitGame);   

		setVisible(true);
	}

	private void setTurns(int rand) {
		/*computer vs human*/
		if (Settings.instance().get2IsComputer() && !Settings.instance().get1IsComputer()) {
			Player player1 = new Human(Disk.WHITE, 1);
			Player player2 = new Computer(Disk.BLACK, 2);
			new Game(player1, player2);

		}
		if (Settings.instance().get1IsComputer() && !Settings.instance().get2IsComputer()) {
			Player player1 = new Computer(Disk.BLACK, 1);
			Player player2 = new Human(Disk.WHITE, 2);	
			new Game(player2, player1);
		}
		/*computer vs computer*/
		if (Settings.instance().get1IsComputer() && Settings.instance().get2IsComputer()) {
			if (rand == 0){
				Player player1 = new Computer(Disk.WHITE, 1);
				Player player2 = new Computer(Disk.BLACK, 2);
				new Game(player1, player2);
			}
			else {
				Player player1 = new Computer(Disk.WHITE, 1);
				Player player2 = new Computer(Disk.BLACK, 2);
				new Game(player2, player1);
			}
		}
		/*both humans*/
		if(!Settings.instance().get1IsComputer() && !Settings.instance().get2IsComputer()) {
			if (rand == 0){
				Player player1 = new Human(Disk.WHITE, 1);
				Player player2 = new Human(Disk.BLACK, 2);
				new Game(player1, player2);

			}
			else {
				Player player1 = new Human(Disk.BLACK, 1);
				Player player2 = new Human(Disk.WHITE, 2);	
				new Game(player2, player1);
			}
		}
	}

	private Game loadGame(){
		try {
			FileInputStream fin = new FileInputStream("\\savedGame.sav");
			ObjectInputStream in = new ObjectInputStream(fin);
			try {
				game =  (Game) in.readObject();
			} catch (ClassNotFoundException e) {
				loadGameFailed();
				in.close();
				return null;
			}
			in.close();

		} catch (IOException e) {
			loadGameFailed();
			return null;
		}
		return game;
	}
	
	private void loadGameFailed() {
		final JDialog dialog = new JDialog(this, "Failed to load game", true);
		dialog.setSize(200,100);
		dialog.setLayout(new BorderLayout());

		dialog.add(new JLabel("Failed to load game", SwingConstants.CENTER), BorderLayout.CENTER);
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		dialog.add(ok, BorderLayout.PAGE_END);

		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(newGame)) {
			Random rn = new Random();
			int rand = rn.nextInt(2);
			setTurns(rand);
			this.dispose();		
		}

		if (e.getSource().equals(exitGame)) {
			System.exit(0);
		}

		if (e.getSource().equals(load)) {
			if (game != null)
				game.dispose();
			Game loadedGame = loadGame();
			if (loadedGame != null)
				new Game(loadGame());
		}
		
		if (e.getSource().equals(resumeGame)) {
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
