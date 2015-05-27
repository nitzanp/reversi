package reversi;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cell implements MouseListener {
	

	private Game game;
	private Cord cord;
	private Color color;
	private JButton button;
	private Disk disk;
	private Map<Disk, Vector<Cell>> willChange;
	private boolean whiteLegal;
	private boolean blackLegal;
	private Board board;
	private Vector<Cell> changed;
	
	public Cell() {
		//game = game;
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
		this.changed = new Vector<Cell>();
		button = new JButton();

		button.setBackground(color);
		//button.setText(cord.toString());
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
	
	public boolean isEmpty(){
		return this.disk == Disk.NONE;
	}
	
	public void setIconByDisk(Disk disk) {
		String temp = this.getClass().getClassLoader().getResource("").getPath(); //need to check if works in every computer
		System.out.println(temp);
		String BLACK_PIC = temp + "black.png";
	    String WHITE_PIC = temp + "white.png";
		if (disk != Disk.NONE) {
			String pic = (disk == Disk.BLACK) ? BLACK_PIC : WHITE_PIC;
			ImageIcon img = new ImageIcon(pic);
			button.setIcon(img);
			
			
		}
		else {
			button.setIcon(null);
		}
	}
	
	public void flip() {
		setDisk(this.disk.getOpposite());
		backToOrigin();
	}

	public Map<Disk, Vector<Cell>>  getCellWillChange (){
		return willChange;
	}
	
	public void markAsLegal() {
		button.setBackground(Color.pink);
	}
	
	public void backToOrigin() {
		button.setBackground(color);
	}
	
	@Override
	public String toString() {
		return cord.toString();
	}

	
	@Override
	public void mouseExited(MouseEvent e) {
		Disk curr = game.getCurrPlayer().getDisk();
//		for (Cell cell : willChange.get(curr)) {
//			cell.backToOrigin();
//		}

//		boolean legal = (curr == Disk.BLACK && blackLegal) || (curr == Disk.WHITE && whiteLegal);

		if (changed.size() > 0) {
			for (Cell cell : changed) {
				cell.backToOrigin();
			}
			changed = new Vector<Cell>();
		}
		//			if (legal) {
		else {
			for (Cell cell : willChange.get(curr)) {
				cell.backToOrigin();
			}
			backToOrigin();
		}

	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		Disk curr = game.getCurrPlayer().getDisk();
		//int flipped = 0;
		boolean change = false;
		for (Cell cell : willChange.get(curr)) {
			if (this.getDisk().equals(Disk.NONE)){
				//flipped++;
				cell.flip();
				changed.add(cell);
				change = true;
			}
		}
		if (change) {
			this.setDisk(curr);
			backToOrigin();
			
			for (Cell cell : willChange.get(curr)) {
		        cell.button.setBackground(cell.color);
			}
			board.cellChanged(this);
			board.calcWillChange();
			
			//game.setScore(flipped);
			game.setScore(changed.size());
			game.switchPlayer();
		}		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Disk curr = game.getCurrPlayer().getDisk();
		boolean legal = (curr == Disk.BLACK && blackLegal) || (curr == Disk.WHITE && whiteLegal);
		if (legal) {
			for (Cell cell : willChange.get(curr)) {
				cell.markAsLegal();
			}
			markAsLegal();
		}
		
		
//		for (Cell cell : willChange.get(curr)) {
//			if (this.getDisk().equals(Disk.NONE)){
//				cell.button.setBackground(Color.pink);
//			}
//			
//		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public Color getColor() {
		return this.color;
	}

			
		
	
}
