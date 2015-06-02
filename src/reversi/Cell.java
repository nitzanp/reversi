package reversi;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Cell implements MouseListener, Serializable{

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
		String path = this.getClass().getClassLoader().getResource("").getPath(); //TODO need to check if works in every computer
		String BLACK_PIC = path + "black.png";
		String WHITE_PIC = path + "white.png";

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

	public void makeMove() {
		Disk curr = game.getCurrPlayer().getDisk();
		boolean change = false;
		for (Cell cell : willChange.get(curr)) {
			if (this.getDisk().equals(Disk.NONE)){
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
			board.calcWillChange();

			game.setScore(changed.size());
			game.switchPlayer();
		}
	}

	@Override
	public String toString() {
		return cord.toString();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Disk curr = game.getCurrPlayer().getDisk();

		if (changed.size() > 0) {
			for (Cell cell : changed) {
				cell.backToOrigin();
			}
			changed = new Vector<Cell>();
		}
		else {
			for (Cell cell : willChange.get(curr)) {
				cell.backToOrigin();
			}
			backToOrigin();
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		makeMove();
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
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
