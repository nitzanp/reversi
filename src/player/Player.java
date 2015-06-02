package player;

import java.io.Serializable;

import board.Board;
import services.Disk;
import settings.Settings;

@SuppressWarnings("serial")
public abstract class Player implements Serializable {

	private int num;

	public Player(int num) {
		this.num = num;
	}

	public abstract Disk getDisk();

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
