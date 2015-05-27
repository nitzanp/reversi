package reversi;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	
	public SettingsFrame(Game game) {
		super("Settings");
		
		this.currGame = game;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		SpinnerNumberModel heightModel = new SpinnerNumberModel(Settings.instance().getBoardHeight(), 4, 16, 1);
		heightSpinner = new JSpinner(heightModel);
		JPanel heightPane = makeSpinnerPanel("Board size:", heightSpinner);
		
		SpinnerNumberModel widthModel = new SpinnerNumberModel(Settings.instance().getBoardWidth(), 4, 16, 1);
		widthSpinner = new JSpinner(widthModel);
		JPanel widthPane = makeSpinnerPanel("*", widthSpinner);
		
		player1Name = new JTextField();
		player1Name.setColumns(10);
		player1Name.setText("Player1");
		
		player2Name = new JTextField();
		player2Name.setColumns(10);
		player2Name.setText("Player2");
		
		player1Human = new JRadioButton("Human");
		//player1Human.setMnemonic(KeyEvent.VK_B);
		player1Human.setActionCommand("player1Human");
		player1Human.setSelected(true);
		
		player1Computer = new JRadioButton("Computer");
		player1Computer.setActionCommand("player1Computer");
		player1Computer.setSelected(false);
		
		player1Group = new ButtonGroup();
		player1Group.add(player1Human);
		player1Group.add(player1Computer);
		
		save = new JButton("SAVE");
		save.addActionListener(this);
		
		discard = new JButton("DISCARD");
		discard.addActionListener(this);
		
		getContentPane().add(heightPane);
		getContentPane().add(widthPane);
		getContentPane().add(player1Name);
		getContentPane().add(player2Name);
		getContentPane().add(player1Human);
		getContentPane().add(player1Computer);
		getContentPane().add(save);
		getContentPane().add(discard);
		
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int jframeWidth = this.getSize().width;
        int jframeHeight = this.getSize().height;
        int X = (dim.width - jframeWidth)/2 - 100;
        int Y = (dim.height - jframeHeight)/4;
        this.setLocation(X, Y);
		
		setResizable(false);
		setVisible(true);
		pack();
		
	}
	
	private JPanel makeSpinnerPanel(String label, JSpinner spin) {
		JPanel tPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tPane.setMaximumSize(new Dimension(250, 50));
		tPane.setMinimumSize(new Dimension(250, 50));
		tPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel tLabel = new JLabel(label);
		tPane.add(tLabel);
		tPane.add(spin);
		return tPane;
	}
	
	public JSpinner getSizeSpinner() {
		return heightSpinner;
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
			
			boolean type = player1Group.getSelection().equals(player1Human);
			System.out.println(type);
	    	
	    	new Menu(null);	
			this.dispose();
	    }
	    
	    if (e.getSource().equals(discard)) {
	    	new Menu(currGame);
			this.dispose();
	    }
		
	}

}
