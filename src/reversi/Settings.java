package reversi;

public class Settings {
	
	private static Settings instance = null;
	
	private int size;
	private String player1Name;
	private String player2Name;
	
	
	private Settings() {
		size = 8;
		player1Name = "PLAYER1";
		player2Name = "PLAYER2";
	}
	
	public static Settings instance() {
		if (instance == null) {
			instance = new Settings();
		}
		
		return instance;
	}
	
	public int getBoardSize() {
		return size;
	}
	
	public void setBoardSize(int size) {
		this.size = size;
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

}
