package reversi;

public class Human implements Player {
	
	Disk disk;
	int score;
	String name;
	
	public Human(Disk disk, String name) {
		this.disk = disk;
		this.name = name;
		this.score = 2;
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
	public String getName() {
		return null;
	}
	
}
