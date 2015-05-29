package reversi;

import java.util.Vector;

public class Computer extends Player  {

	private Disk disk;
	private int score;
	
	public Computer(Disk disk, int num) {
		super(num);
		this.disk=disk;
		score = 2;
	}
	

	public Disk getDisk() {
			return disk;
	}
	
	public int getScore(){
		return score;
	}

	
	public void setScore(int newScore) {
		this.score = newScore;
		
	}

	
	public boolean isEqual(Player player) {
		return this.disk == player.getDisk();
	}
	
	public Cell searchForMax(Vector<Cell> allCells) {
		int max = 0;
		Cell cellToPut = null;
		int moves = 0;
		
		for (Cell cell : allCells) {
			if (cell.isLegal(disk) && cell.isEmpty()) {
				moves = cell.getCellWillChange().get(disk).size();
			}
			if (moves > max) {
				max = moves;
				cellToPut = cell;
				moves = 0;
			}
		}
		return cellToPut;
	}
	
	public Cell searchForMove(Board board) {
		Cell cellToPut = null;
		
		cellToPut = searchForMax(board.getCorners());
		if (cellToPut != null)
			return cellToPut;
		
		cellToPut = searchForMax(board.getEdges());
		if (cellToPut != null)
			return cellToPut;
		
		cellToPut = searchForMax(board.getInnerCells());
		return cellToPut;
	}
	
	public void play(Board board) {
		Cell cellToPut = searchForMove(board);
		if (cellToPut != null)
			cellToPut.makeMove();	
	}

	public boolean isComputer() {
		return true;
	}
}


