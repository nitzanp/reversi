package reversi;

public class Human implements Player {
	
	private Disk disk;
	private int score;
	
	public Human(Disk disk){
		this.disk=disk;
		score = 2;

	}

	public Disk getDisk() {
			return disk;
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int newScore){
		this.score = newScore;
	}
	
	public boolean isEqual(Player player) {
		return this.disk == player.getDisk();
	}

	@Override
	public boolean getComputer() {
		return false;
	}
	
}
