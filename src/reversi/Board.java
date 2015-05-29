package reversi;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

public class Board extends JPanel {
	private Game game;
	private int width;
	private int height;
	private Map<Integer, Map<Integer, Cell>> allCells;
	
	public Board (int height, int width, Game game) {
	
	    setLayout(new GridLayout(height, width));
	    this.game = game;
	    this.width = width;
	    this.height = height;
	    Cell cell;
	    Cord cord;
	    Map<Integer, Cell> row;
	    allCells = new HashMap<Integer, Map<Integer, Cell>>();
		
				
		for (int i = 0; i < height; i++) {
			row = new HashMap<Integer, Cell>();
    		
    		for (int j = 0; j < width; j++) {
    			cord = new Cord(i, j);
    			cell = new Cell(cord, Color.gray, Disk.NONE, this, game);   			
    			row.put(j, cell);
    			add(cell.getButton());
    		}
    		allCells.put(i,row);
    	}
		calcInit();
		calcWillChange();
	}
	
	private void calcInit() {
		int heightMiddle = (int) Math.ceil(height / 2);		
		int widthMiddle = (int) Math.ceil(width / 2);
		allCells.get(heightMiddle-1).get(widthMiddle-1).setDisk(Disk.WHITE);
		allCells.get(heightMiddle).get(widthMiddle).setDisk(Disk.WHITE);
		allCells.get(heightMiddle).get(widthMiddle-1).setDisk(Disk.BLACK);
		allCells.get(heightMiddle-1).get(widthMiddle).setDisk(Disk.BLACK);
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
	}
	
	public void addToMap(Map<Disk, Vector<Cell>> directionAns, Map<Disk, Vector<Cell>> willChange) {
		Disk disk = directionAns.keySet().iterator().next();
		Vector<Cell> vec = willChange.get(disk);
		vec.addAll(directionAns.get(disk));
		willChange.put(disk, vec);
	}
	
	public boolean isInBounds(Cord cord) {
		return cord.getI() > -1 && cord.getI() < height && cord.getJ() > -1 && cord.getI() < width;
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
	
	Vector<Cell> getCorners() {
		Vector<Cell> corners = new Vector<Cell>();
		corners.add(allCells.get(0).get(0));
		corners.add(allCells.get(0).get(width-1));
		corners.add(allCells.get(height-1).get(0));
		corners.add(allCells.get(height-1).get(width-1));
		return corners;
	}
	
	Vector<Cell> getEdges() {
		Vector<Cell> edges = new Vector<Cell>();
		edges.addAll(allCells.get(0).values());
		edges.addAll(allCells.get(height-1).values());
		for (int i = 1; i < height-1; i++) {
			edges.add(allCells.get(i).get(0));
			edges.add(allCells.get(i).get(width-1));
		}
		return edges;
	}
	
	Vector<Cell> getInnerCells() {
		Vector<Cell> inners = new Vector<Cell>();
		for (int i = 1; i < height-1; i++) {
			for (int j = 1; j < width-1; j++) {
				inners.add(allCells.get(i).get(j));
			}
		}
		return inners;
	}
	
}