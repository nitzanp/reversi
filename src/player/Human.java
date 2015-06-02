package player;

import java.io.Serializable;

import services.Disk;

@SuppressWarnings("serial")
public class Human extends Player implements Serializable{

	private Disk disk;
	private int score;


	public Human(Disk disk, int num) {
		super(num);
		this.disk = disk;
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

	public boolean isComputer() {
		return false;
	}
	
}
