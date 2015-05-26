package reversi;

import java.util.Map;
import java.util.Vector;

public class Computer implements Player  {

	private Disk disk;
	private int score;
	
	public Computer(Disk disk){
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
	
	public void play(Board board){
		int max=0;
		Cell cellToPut = null;
		for (Map<Integer, Cell> row : board.getAllCells().values()) {
			int moves=0;
			for (Cell cell : row.values()) {
				if (cell.getCellWillChange().get(disk).size() > 0 && cell.isLegal(disk) && cell.isEmpty()) {
					moves = cell.getCellWillChange().get(disk).size();
				}
				if (moves > max){
					
					max=moves;
					cellToPut=cell;
				}
			}
		}
		cellToPut.setDisk(disk);
		for (Cell cell : cellToPut.getCellWillChange().get(cellToPut.getDisk())) {
			cell.getButton().setBackground(cellToPut.getColor());
			cell.flip();
		}
		
		board.cellChanged(cellToPut);
		board.calcWillChange();
		board.getGame().setScore(max);
		board.getGame().switchPlayer();
			
	}

	@Override
	public boolean getComputer() {
		
		return true;
	}
}


