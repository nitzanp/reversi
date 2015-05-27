package reversi;

public class Human implements Player {
	
<<<<<<< HEAD
	private Disk disk;
	private int score;
	
	public Human(Disk disk){
		this.disk=disk;
		score = 2;
=======
	Disk disk;
	int score;
	
	public Human(Disk disk) {
		this.disk = disk;
		this.score = 2;
>>>>>>> origin/master
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
<<<<<<< HEAD

	@Override
	public boolean getComputer() {
		return false;
	}
=======
>>>>>>> origin/master
	
}
