package reversi;

public class Settings {

	private static Settings instance = null;

	private int height;
	private int width;
	private String player1Name;
	private String player2Name;
	private boolean is1Computer;
	private boolean is2Computer;

	private Settings() {
		height = 8;
		width = 8;
		player1Name = "PLAYER1";
		player2Name = "PLAYER2";
		is1Computer = false;
		is2Computer = false;
	}

	public static Settings instance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	public int getBoardHeight() {
		return height;
	}

	public void setBoardHeight(int height) {
		this.height = height;
	}

	public int getBoardWidth() {
		return width;
	}

	public void setBoardWidth(int width) {
		this.width = width;
	}

	public String getPlayer1Name() {
		return player1Name;
	}

	public void setPlayer1Name(String name) {
		this.player1Name = name;
	}

	public String getPlayer2Name() {
		return player2Name;
	}

	public void setPlayer2Name(String name) {
		this.player2Name = name;
	}

	public Boolean get1IsComputer() {
		return is1Computer;
	}

	public void set1IsComputer(boolean bool) {
		is1Computer = bool;
	}

	public Boolean get2IsComputer() {
		return is2Computer;
	}

	public void set2IsComputer(boolean bool) {
		is2Computer = bool;
	}

}
