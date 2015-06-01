package reversi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class SettingsFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Game currGame;
	private JSpinner heightSpinner;
	private JSpinner widthSpinner;
	private JTextField player1Name;
	private JTextField player2Name;
	private JButton save;
	private JButton discard;
	private JRadioButton player1Human;
	private JRadioButton player1Computer;
	private ButtonGroup player1Group;
	private JRadioButton player2Human;
	private JRadioButton player2Computer;
	private ButtonGroup player2Group;
	private ScoreTable scores;

	public SettingsFrame(Game game, ScoreTable scores) {
		super("Settings");
		this.currGame = game;
		this.scores = scores;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(5, 1));
		String path = this.getClass().getClassLoader().getResource("").getPath();
		ImageIcon img = new ImageIcon(path + "image.png");
		this.setIconImage(img.getImage());


		SpinnerNumberModel heightModel = new SpinnerNumberModel(Settings.instance().getBoardHeight(), 4, 10, 1);
		SpinnerNumberModel widthModel = new SpinnerNumberModel(Settings.instance().getBoardWidth(), 4, 14, 1);
		heightSpinner = new JSpinner(heightModel);
		widthSpinner = new JSpinner(widthModel);
		JPanel sizePane = makeSizePanel(heightSpinner, widthSpinner);

		player1Name = new JTextField();
		player1Name.setColumns(10);
		player1Name.setText(Settings.instance().getPlayer1Name());

		player2Name = new JTextField();
		player2Name.setColumns(10);
		player2Name.setText(Settings.instance().getPlayer2Name());

		boolean is1Computer = Settings.instance().get1IsComputer();

		player1Human = new JRadioButton("Human");
		player1Human.setActionCommand("player1Human");
		player1Human.setSelected(!is1Computer);		

		player1Computer = new JRadioButton("Computer");
		player1Computer.setActionCommand("player1Computer");
		player1Computer.setSelected(is1Computer);

		player1Group = new ButtonGroup();
		player1Group.add(player1Human);
		player1Group.add(player1Computer);

		JPanel player1Panel = makePlayerPanel(player1Name, player1Human, player1Computer);

		boolean is2Computer = Settings.instance().get2IsComputer();

		player2Human = new JRadioButton("Human");
		player2Human.setActionCommand("player2Human");
		player2Human.setSelected(!is2Computer);			

		player2Computer = new JRadioButton("Computer");
		player2Computer.setActionCommand("player2Computer");
		player2Computer.setSelected(is2Computer);

		player2Group = new ButtonGroup();
		player2Group.add(player2Human);
		player2Group.add(player2Computer);

		JPanel player2Panel = makePlayerPanel(player2Name, player2Human, player2Computer);

		save = new JButton("SAVE");
		save.addActionListener(this);

		discard = new JButton("DISCARD");
		discard.addActionListener(this);

		getContentPane().add(sizePane);
		getContentPane().add(player1Panel);
		getContentPane().add(player2Panel);
		getContentPane().add(save);
		getContentPane().add(discard);

		setSize(300, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	private JPanel makeSizePanel(JSpinner height, JSpinner width) {
		JPanel tPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tPane.setMaximumSize(new Dimension(250, 50));
		tPane.setMinimumSize(new Dimension(250, 50));
		tPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel label1 = new JLabel("Board size:");
		JLabel label2 = new JLabel("*");
		tPane.add(label1);
		tPane.add(height);
		tPane.add(label2);
		tPane.add(width);

		return tPane;
	}

	private JPanel makePlayerPanel(JTextField name, JRadioButton human, JRadioButton computer) {
		JPanel tPane = new JPanel(new BorderLayout());
		tPane.setMaximumSize(new Dimension(250, 50));
		tPane.setMinimumSize(new Dimension(250, 50));

		tPane.add(name, BorderLayout.NORTH);
		tPane.add(human, BorderLayout.WEST);
		tPane.add(computer, BorderLayout.EAST);

		return tPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(save)) {
			String player1 = player1Name.getText();
			Settings.instance().setPlayer1Name(player1);

			String player2 = player2Name.getText();
			Settings.instance().setPlayer2Name(player2);

			Settings.instance().setBoardHeight((Integer)heightSpinner.getValue());
			Settings.instance().setBoardWidth((Integer)widthSpinner.getValue());

			boolean player1IsComputer = (player1Computer.isSelected()) ? true : false;
			boolean player2IsComputer = (player2Computer.isSelected()) ? true : false;	
			Settings.instance().set1IsComputer(player1IsComputer);
			Settings.instance().set2IsComputer(player2IsComputer);
			
			new Menu(null, scores);
			
			if (this.scores != null)
				this.scores.setVisible(false);
			scores = new ScoreTable(player1, player2);
			this.dispose();
		}

		if (e.getSource().equals(discard)) {
			new Menu(currGame, currGame.getScores());
			this.dispose();
		}
	}

}
