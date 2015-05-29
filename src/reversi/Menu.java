package reversi;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {

	private JButton newGame;
	private JButton resumeGame;
	private JButton settings;
	private JButton exitGame;
	private Game game;

	public Menu(Game game) {
		super("MainMenu");
		this.game = game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(4, 1));

		setSize(300, 300);
		setLocationRelativeTo(null);

		newGame = new JButton("NEW GAME");
		resumeGame = new JButton("RESUME GAME");
		settings = new JButton("SETTINGS");
		exitGame = new JButton("EXIT");

		if (game == null) {
			resumeGame.setEnabled(false);
		}

		newGame.addActionListener(this);
		resumeGame.addActionListener(this);
		settings.addActionListener(this);
		exitGame.addActionListener(this);

		getContentPane().add(newGame);
		getContentPane().add(resumeGame);
		getContentPane().add(settings);
		getContentPane().add(exitGame);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(newGame)) {
			Player player1 = (Settings.instance().get1IsComputer()) ? new Computer(Disk.WHITE, 1) : new Human(Disk.WHITE, 1);
			Player player2 = (Settings.instance().get2IsComputer()) ? new Computer(Disk.BLACK, 2) : new Human(Disk.BLACK, 2);
			this.game = new Game(player1, player2);
			this.dispose();
		}

		if (e.getSource().equals(exitGame)) {
			System.exit(0);
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