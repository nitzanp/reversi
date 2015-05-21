package reversi;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cell implements ActionListener {
	
	private static final String BLACK_URL = "https://dl.dropboxusercontent.com/u/30035546/black.png";
	private static final String WHITE_URL = "https://dl.dropboxusercontent.com/u/30035546/white.png";
	
	private Cord cord;
	private Color color;
	JButton button;
	Disk disk;
	Map<Disk, Vector<Cell>> willChange;
	boolean whiteLegal;
	boolean blackLegal;
	Board board;
	
	public Cell() {
		cord = new Cord();
		color = Color.WHITE;
		button = new JButton(cord.getI() + "," + cord.getJ());
		whiteLegal = false;
		blackLegal = false;
		willChange = new HashMap<Disk, Vector<Cell>>();
		board = null;
	}
	
	public Cell(Cord cord, Color color, Disk disk, Board board) {
		this.cord = cord;
		this.color = color;
		this.disk = disk;
		this.whiteLegal = false;
		this.blackLegal = false;
		this.willChange = new HashMap<Disk, Vector<Cell>>();
		this.board = board;
		button = new JButton();

		button.setBackground(color);
		button.setSize(250, 250);
		button.addActionListener(this);
		button.setText(cord.toString());
		button.addMouseListener(this);
		setIconByDisk(disk);
	}
	
	public JButton getButton() {
		return this.button;
	}
	
	public Disk getDisk() {
		return this.disk;
	}
	
	public void setDisk(Disk changeTo) {
		this.disk = changeTo;
		setIconByDisk(disk);
	}
	
	public Cord getCord() {
		return this.cord;
	}
	
	public void setWillChange(Map<Disk, Vector<Cell>> willChange) {
		this.willChange = willChange;
		blackLegal =  (willChange.get(Disk.BLACK).size() > 0) ? true : false;
		whiteLegal =  (willChange.get(Disk.WHITE).size() > 0) ? true : false;

	}
	
	public boolean isLegal(Disk disk) {
		if (disk == Disk.WHITE)
			return whiteLegal;
		if (disk == Disk.BLACK)
			return blackLegal;
		return false;
	}
	
	public void setIconByDisk(Disk disk) {
		if (disk != Disk.NONE) {
			URL url = null;
			String urlStr = (disk == Disk.BLACK) ? BLACK_URL : WHITE_URL;
			try {
				url = new URL(urlStr);
			} catch (MalformedURLException e) {	}
			Icon icon = new ImageIcon(url);
			//ImageIcon icon = new ImageIcon("black.png");
			button.setIcon(icon);
		}
		else {
			button.setIcon(null);
		}
	}
	
	public void flip() {
		setDisk(this.disk.getOpposite());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//if (e.getActionCommand() == 
		Disk curr = Game.currPlayer;
		boolean change = false;
		for (Cell cell : willChange.get(curr)) {
			cell.flip();
			change = true;
		}
		if (change) {
			this.setDisk(curr);
		
			//board.cellChanged(this);
			board.calcWillChange();
			Game.currPlayer = curr.getOpposite();
		}

	}
	
	@Override
	public String toString() {
		return cord.toString();
	}
}
