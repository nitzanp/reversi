package reversi;

public interface Player {
	
	public Disk getDisk();

	public boolean isEqual(Player player);

	public int getScore();
	
	public void setScore(int score);
	
	public boolean getComputer();
		

}
