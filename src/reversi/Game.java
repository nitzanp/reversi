package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener, Serializable {
	 
	private Player currPlayer;
	private Board board;
	private JButton newGame;
	private JButton backToMenu;
	private JButton save;
	private JButton exitGame;
	private JButton gameEnded;
	private JButton player1Button;
	private JButton player2Button;
	public static int row;
	public static int col;
	private Player player1;
	private Player player2;
	
	public Game(Player player1, Player player2) {
		super("Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon("images//image.png");
		this.setIconImage(img.getImage());

		int height = Settings.instance().getBoardHeight();
		int width = Settings.instance().getBoardWidth();
		getContentPane().setLayout(new BorderLayout());
		board = new Board(height, width, this);
		this.player1 = player1;
		this.player2 = player2;
		currPlayer = player1;
		
		gameInit();
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		this.startGame();
	}
	
	/* builder for load */
	public Game(Game loadedGame) {
		super("Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		this.board = loadedGame.board;
		this.player1 = loadedGame.player1;
		this.player2 = loadedGame.player2;
		this.currPlayer = loadedGame.currPlayer;
		
		gameInit();

		if (loadedGame.currPlayer.isComputer()) 
			loadedGame.currPlayer.play(board);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void gameInit() {
		JPanel optionsPanel = new JPanel();
		JPanel boardPanel = new JPanel();
		
		newGame = new JButton("NEW GAME");
		backToMenu = new JButton("MENU");
		save = new JButton("SAVE GAME");
		exitGame = new JButton("EXIT");
		player1Button = new JButton(scoreString(player1));
		player1Button.setBackground(Color.RED);
		player2Button = new JButton(scoreString(player2));
		player2Button.setBackground(Color.WHITE);

		newGame.addActionListener(this);
		exitGame.addActionListener(this);
		save.addActionListener(this);
		backToMenu.addActionListener(this);
		player1Button.addActionListener(this);
		player2Button.addActionListener(this);

		optionsPanel.setLayout(new FlowLayout());
		optionsPanel.add(player1Button);
		optionsPanel.add(exitGame);
		optionsPanel.add(newGame);
		optionsPanel.add(save);
		optionsPanel.add(backToMenu);
		optionsPanel.add(player2Button);

		boardPanel.add(board);

		getContentPane().add(optionsPanel, BorderLayout.NORTH);
		getContentPane().add(boardPanel, BorderLayout.CENTER);
	}
	
	
	/*Getters*/
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player1;
	}

	public Player getCurrPlayer(){
		return currPlayer;
	}
	
	/* game logic*/
	public void startGame() {
		if (player1.isComputer() && player2.isComputer()) {
			/*after random*/
			player1.play(board); 
		
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
		Player otherPlayer = (currPlayer.equals(player1)) ? player2 : player1;

		if (validMoves(otherPlayer) == 0) {
			endGame();
		}

		else {
			noValidMovesDialog();
			switchPlayer();
		}
	}

	public void noValidMovesDialog() {
		final JDialog dialog = new JDialog(this, "No valid moves", true);
		dialog.setSize(200,100);
		dialog.setLayout(new BorderLayout());

		String name = (currPlayer == player1) ? Settings.instance().getPlayer1Name() : Settings.instance().getPlayer2Name();

		dialog.add(new JLabel(name + " has no valid moves!", SwingConstants.CENTER), BorderLayout.CENTER);
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


	public void switchPlayer() {
		currPlayer = (currPlayer.equals(player1)) ? player2 : player1;

		if (validMoves(currPlayer) == 0) {
			noValidMoves();
			return;
		}

		if (currPlayer.equals(player1)){
			player1Button.setBackground(Color.RED);
			player2Button.setBackground(SystemColor.text);	
		}
		else {
			player2Button.setBackground(Color.RED);
			player1Button.setBackground(SystemColor.text);
		}	

		currPlayer.play(board);
	}


	public void endGame() {
		String msg; 
		String pic = "images//win.png";
		
		if (player1.getScore() == player2.getScore()) {
			msg = "It's a TIE!";
		}	    
		else {
			Player winner = (player1.getScore() > player2.getScore()) ? player1 : player2;
			String winnerName = (winner.equals(player1)) ? player1.getName() : player2.getName();
			msg = winnerName + " WINS!"; 			//TODO- add points? align to center
		}

		final JDialog dialog = new JDialog(this, msg, true);
		dialog.setSize(400,400);
		dialog.setLayout(new BorderLayout());

		JLabel text = new JLabel(msg, SwingConstants.CENTER);
		text.setFont(text.getFont().deriveFont(40.0f));
		dialog.add(text, BorderLayout.NORTH);
		dialog.add(new JLabel(new ImageIcon(pic)), BorderLayout.EAST);
		gameEnded = new JButton("BACK TO MENU");
		gameEnded.addActionListener(this);
		dialog.add(gameEnded, BorderLayout.PAGE_END);

		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	public void setScore(int flipped) {
		if (currPlayer.equals(player1)){
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
		String name = player.getName();
		sb.append(name).append(" - ").append(player.getDisk().toString()).append(" : ").append(player.getScore());
		return sb.toString();			
	}
	
	private void setTurns(int rand){
		/*computer vs human*/
		if (Settings.instance().get2IsComputer() && !Settings.instance().get1IsComputer()){
			player1 = new Human(Disk.WHITE, 1);
			player2 = new Computer(Disk.BLACK, 2);
			new Game(player1, player2);
		}
		if (Settings.instance().get1IsComputer() && !Settings.instance().get2IsComputer()) {
			player1 = new Computer(Disk.BLACK, 1);
			player2 = new Human(Disk.WHITE, 2);	
			new Game(player2, player1);
		}
		/*computer vs computer*/
		if (Settings.instance().get1IsComputer() && Settings.instance().get2IsComputer()) {
			if (rand == 0){
				player1 = new Computer(Disk.WHITE, 1);
				player2 = new Computer(Disk.BLACK, 2);
				new Game(player1, player2);
			}
			else {
				player1 = new Computer(Disk.WHITE, 1);
				player2 = new Computer(Disk.BLACK, 2);
				new Game(player2, player1);
			}
		}
		/*both humans*/
		if(!Settings.instance().get1IsComputer() && !Settings.instance().get2IsComputer()) {
			if (rand == 0){
				player1 = new Human(Disk.WHITE, 1);
				player2 = new Human(Disk.BLACK, 2);
				new Game(player1, player2);
			}
			else {
				player1 = new Human(Disk.BLACK, 1);
				player2 = new Human(Disk.WHITE, 2);	
				new Game(player2, player1);
			}
		}
	}
	private void saveGame() {
		try {
			FileOutputStream fout = new FileOutputStream("\\savedGame.sav");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		if (e.getSource().equals(backToMenu)) {
			new Menu(this);	
			this.setVisible(false);
		}
		if (e.getSource().equals(save)) {
			saveGame();
		}

		if (e.getSource().equals(gameEnded)) {
			new Menu(this);
			this.setVisible(false);
		}
	}
}

