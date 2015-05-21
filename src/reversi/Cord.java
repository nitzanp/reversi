package reversi;

public class Cord {
	private int i;
	private int j;
	
	public Cord() {
		setI(0);
		setJ(0);
	}
	
	public Cord(int i, int j) {
		this.setI(i);
		this.setJ(j);
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	public Cord getNextCord(Direction dir) {
		switch (dir) {
		case UP:
			return new Cord(i - 1, j);
		case DOWN:
			return new Cord(i + 1, j);
		case LEFT:
			return new Cord(i, j - 1);
		case RIGHT:
			return new Cord(i, j + 1);
		case UPLEFT:
			return new Cord(i - 1, j - 1);
		case UPRIGHT:
			return new Cord(i - 1, j + 1);
		case DOWNLEFT:
			return new Cord(i + 1, j - 1);
		case DOWNRIGHT:
			return new Cord(i + 1, j + 1);
		}
		return null;
	}
	
//	public Cord getNextRightCord() {
//		return new Cord(i, j + 1);
//	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Cord) {
			return this.i == ((Cord) obj).getI() && this.j == ((Cord) obj).getJ();
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + i + "," + j + ")";
	}
}
