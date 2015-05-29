package reversi;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener {

	private  Player currPlayer;
	private Board board;
	private JButton newGame;
	private JButton backToMenu;
	private JButton exitGame;
	private JButton gameEnded;
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

		newGame = new JButton("NEW GAME");
		backToMenu = new JButton("MENU");
		exitGame = new JButton("EXIT");
		player1Button = new JButton(scoreString(player1));
		player1Button.setBackground(Color.RED);
		player2Button = new JButton(scoreString(player2));
		player2Button.setBackground(Color.WHITE);

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

		startGame();
		pack();
		setVisible(true);
	}

	public void startGame() {
		if (player1.isComputer() && !player2.isComputer()) {
			player2.play(board);
			switchPlayer();
		}
		else {
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

		dialog.add(new JLabel(name + " have no valid moves!"), BorderLayout.CENTER);
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

	public Player getCurrPlayer(){
		return currPlayer;
	}

	public void endGame(){
		String msg; 

		if (player1.getScore() == player2.getScore()) {
			msg = "It's a TIE!";
		}	    
		else {
			Player winner = (player1.getScore() > player2.getScore()) ? player1 : player2;
			String winnerName = (winner.equals(player1)) ? Settings.instance().getPlayer1Name() : Settings.instance().getPlayer2Name();
			msg = winnerName + " WINS!"; 			//TODO- add points? align to center
		}

		final JDialog dialog = new JDialog(this, msg, true);
		dialog.setSize(200,100);
		dialog.setLayout(new BorderLayout());

		dialog.add(new JLabel(msg), BorderLayout.CENTER);
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
		sb.append(name).append(" - ").append(player.getDisk().toString());
		sb.append(" : ").append(player.getScore());
		return sb.toString();			
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(newGame)) {
			Player player1 = (Settings.instance().get1IsComputer()) ? new Computer(Disk.WHITE, 1) : new Human(Disk.WHITE, 1);
			Player player2 = (Settings.instance().get2IsComputer()) ? new Computer(Disk.BLACK, 2) : new Human(Disk.BLACK, 2);
			new Game(player1, player2);
			this.dispose();
		}

		if (e.getSource().equals(exitGame)) {
			System.exit(0);
		}

		if (e.getSource().equals(backToMenu)) {
			new Menu(this);	
			this.setVisible(false);
		}

		if (e.getSource().equals(gameEnded)) {
			new Menu(null);
			this.setVisible(false);
		}
	}

}