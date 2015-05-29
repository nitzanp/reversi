package reversi;

public abstract class Player {
	
	private int num;
	
	public Player(int num) {
		this.num = num;
	}
	
	public abstract Disk getDisk();

	public abstract boolean isEqual(Player player);

	public abstract int getScore();
	
	public abstract void setScore(int score);
	
	public abstract boolean isComputer();
	
	public void play(Board board) {}
	
	public String getName() {
		String name;
		switch (num) {
		case 1:
			name = Settings.instance().getPlayer1Name();
			break;
		case 2:
			name = Settings.instance().getPlayer2Name();
			break;

		default:
			name = "Player";
			break;
		}
		return name;
	}

}
