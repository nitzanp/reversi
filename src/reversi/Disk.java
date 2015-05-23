package reversi;

public enum Disk {
	BLACK, WHITE, NONE;
	
	public Disk getOpposite() {
		if (this == BLACK)
			return WHITE;
		if (this == WHITE)
			return BLACK;
		return NONE;
	}
	
	
	public boolean isOppisite(Disk disk) {
		return this.getOpposite() == disk;
	}
	
}
