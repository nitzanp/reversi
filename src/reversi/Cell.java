package reversi;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cell implements ActionListener, MouseListener {
	
	private static final String BLACK_URL = "https://dl.dropboxusercontent.com/u/30035546/black.png";
	private static final String WHITE_URL = "https://dl.dropboxusercontent.com/u/30035546/white.png";
	private Game game;
	private Cord cord;
	private Color color;
	JButton button;
	Disk disk;
	Map<Disk, Vector<Cell>> willChange;
	boolean whiteLegal;
	boolean blackLegal;
	Board board;
	
	public Cell() {
		game = game;
		cord = new Cord();
		color = Color.WHITE;
		button = new JButton(cord.getI() + "," + cord.getJ());
		whiteLegal = false;
		blackLegal = false;
		willChange = new HashMap<Disk, Vector<Cell>>();
		board = null;
	}
	
	public Cell(Cord cord, Color color, Disk disk, Board board, Game game) {
		this.game = game;
		this.cord = cord;
		this.color = color;
		this.disk = disk;
		this.whiteLegal = false;
		this.blackLegal = false;
		this.willChange = new HashMap<Disk, Vector<Cell>>();
		this.board = board;
		button = new JButton();

		button.setBackground(color);
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
		Disk curr = game.getCurrPlayer().getDisk();
		int flipped = 0;
		boolean change = false;
		for (Cell cell : willChange.get(curr)) {
			if (this.getDisk().equals(Disk.NONE)){
				flipped++;
				cell.flip();
				change = true;
			}
		}
		if (change) {
			this.setDisk(curr);
			
			for (Cell cell : willChange.get(curr)) {
		        cell.button.setBackground(cell.color);
			 }
			board.cellChanged(this);
			board.calcWillChange();
			
			game.setScore(flipped);
			game.switchPlayer();
		}

	}
	public Map<Disk, Vector<Cell>>  getCellWillChange (){
		return willChange;
	}
	
	@Override
	public String toString() {
		return cord.toString();
	}

	
	@Override
	  public void mouseExited(MouseEvent e) {
		  Disk curr = game.getCurrPlayer().getDisk();
		  for (Cell cell : willChange.get(curr)) 
			  cell.button.setBackground(cell.color);
		  
	  }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Disk curr = game.getCurrPlayer().getDisk();
		for (Cell cell : willChange.get(curr)) {
			if (this.getDisk().equals(Disk.NONE)){
				cell.button.setBackground(Color.pink);
				
			}
			
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
			
		
	
}
