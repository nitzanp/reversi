package reversi;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

public class Board extends JPanel {
	private Game game;
	private final int N;
	private Map<Integer, Map<Integer, Cell>> allCells;
	
	public Board (int size, Game game) {
	
	    setLayout(new GridLayout(size,size));
	    this.game = game;
	    Color color;
	    Cell cell;
	    Cord cord;
	    Disk disk;
	    Map<Integer, Cell> row;
	    allCells = new HashMap<Integer, Map<Integer, Cell>>();
		N = size;
				
		for (int i = 0; i < N; i++) {
			row = new HashMap<Integer, Cell>();
    		if(i % 2 == 0)
    			color = Color.cyan;
    		else
    			color = Color.DARK_GRAY;
    		
    		for (int j = 0; j < N; j++) {
    			cord = new Cord(i, j);
    			
    			cell = new Cell(cord, color, Disk.NONE, this, game);
    			
    			if (color.equals(Color.DARK_GRAY))
    				color = Color.cyan;
    			else
    				color = Color.DARK_GRAY;
    			
    			row.put(j, cell);
    			add(cell.getButton());
    		
    		}
    		allCells.put(i,row);
    	}
		calcInit();
		calcWillChange();
	}
	
	private void calcInit() {
		int middle = N / 2;
		allCells.get(middle-1).get(middle-1).setDisk(Disk.WHITE);
		allCells.get(middle).get(middle).setDisk(Disk.WHITE);
		allCells.get(middle).get(middle-1).setDisk(Disk.BLACK);
		allCells.get(middle-1).get(middle).setDisk(Disk.BLACK);
	}
	
	public void calcWillChange() {
		for (Map<Integer, Cell> row : allCells.values()) {
			for (Cell cell : row.values()) {
				calcWillChange(cell);
			}
		}
	}
	
	public void calcWillChange(Cell cell) {
		if (cell == null)
			return;
		Map<Disk, Vector<Cell>> willChange = new HashMap<Disk, Vector<Cell>>();
		Map<Disk, Vector<Cell>> directionAns;
		
		willChange.put(Disk.BLACK, new Vector<Cell>());
		willChange.put(Disk.WHITE, new Vector<Cell>());
		willChange.put(Disk.NONE, new Vector<Cell>());
		
		if (cell.getDisk() != Disk.NONE) {
			cell.setWillChange(willChange);
			return;
		}
		
		directionAns = search(cell, Direction.RIGHT);
		addToMap(directionAns, willChange);
		
		directionAns = search(cell, Direction.LEFT);
		addToMap(directionAns, willChange);
		
		directionAns = search(cell, Direction.UP);
		addToMap(directionAns, willChange);
		
		directionAns = search(cell, Direction.DOWN);
		addToMap(directionAns, willChange);
		
		directionAns = search(cell, Direction.UPLEFT);
		addToMap(directionAns, willChange);
		
		directionAns = search(cell, Direction.UPRIGHT);
		addToMap(directionAns, willChange);
		
		directionAns = search(cell, Direction.DOWNLEFT);
		addToMap(directionAns, willChange);
		
		directionAns = search(cell, Direction.DOWNRIGHT);
		addToMap(directionAns, willChange);
		
		cell.setWillChange(willChange);	
		
		if (willChange.get(Disk.BLACK).size() > 0 || willChange.get(Disk.WHITE).size() > 0) {
			System.out.println(cell.getCord().toString() + " will change " + willChange.toString());
		}

	}
	
	public void addToMap(Map<Disk, Vector<Cell>> directionAns, Map<Disk, Vector<Cell>> willChange) {
		Disk disk = directionAns.keySet().iterator().next();
		Vector<Cell> vec = willChange.get(disk);
		vec.addAll(directionAns.get(disk));
		willChange.put(disk, vec);
	}
	
	public boolean isInBounds(Cord cord) {
		return cord.getI() > -1 && cord.getI() < N && cord.getJ() > -1 && cord.getI() < N;
	}
	
	public Cell getCellByCord(Cord cord) {
		if (!isInBounds(cord))
			return null;
		return allCells.get(cord.getI()).get(cord.getJ());
	}
	
	public Map<Integer, Map<Integer, Cell>> getAllCells(){
		return allCells;
	}
	
	public Map<Disk, Vector<Cell>> search(Cell cell, Direction dir) {
		Cord nextCord = cell.getCord().getNextCord(dir);
		Cell neighbour = getCellByCord(nextCord);
		Vector<Cell> vec = new Vector<Cell>();
		Map<Disk, Vector<Cell>> ans = new HashMap<Disk, Vector<Cell>>();
		if (neighbour != null && neighbour.getDisk() != Disk.NONE) {
			vec = searchHelper(nextCord, neighbour.getDisk().getOpposite(), dir);
			ans.put(neighbour.getDisk().getOpposite(), vec);	
		}
		else {
			ans.put(Disk.NONE, vec);
		}
		return ans;
	}
	
	public Vector<Cell> searchHelper(Cord cord, Disk changeTo, Direction dir) {
		Cell cell = getCellByCord(cord);
		Disk disk = cell.getDisk();
		Vector<Cell> ans = new Vector<Cell>();
		Cord nextCord;

		while (disk.isOppisite(changeTo)) {
			ans.add(cell);
			nextCord = cell.getCord().getNextCord(dir);
			cell = getCellByCord(nextCord);
			if (cell == null)
				return new Vector<Cell>();
			disk = cell.getDisk();
		}
		
		if (disk == changeTo) {
			return ans;
		}
		
		return new Vector<Cell>();
	}
	
	public void cellChanged(Cell cell) {
		Cord cord = cell.getCord();
		Cell neighbour;
		
		System.out.println(game.getCurrPlayer().getDisk() + " changed " + cord.toString());
		
		neighbour = getCellByCord(cord.getNextCord(Direction.UP));
		calcWillChange(neighbour);
		
		neighbour = getCellByCord(cord.getNextCord(Direction.DOWN));
		calcWillChange(neighbour);
		
		neighbour = getCellByCord(cord.getNextCord(Direction.LEFT));
		calcWillChange(neighbour);
		
		neighbour = getCellByCord(cord.getNextCord(Direction.RIGHT));
		calcWillChange(neighbour);
		
		neighbour = getCellByCord(cord.getNextCord(Direction.UPLEFT));
		calcWillChange(neighbour);
		
		neighbour = getCellByCord(cord.getNextCord(Direction.UPRIGHT));
		calcWillChange(neighbour);
		
		neighbour = getCellByCord(cord.getNextCord(Direction.DOWNLEFT));
		calcWillChange(neighbour);
		
		neighbour = getCellByCord(cord.getNextCord(Direction.DOWNRIGHT));
		calcWillChange(neighbour);
	}
	

	
}