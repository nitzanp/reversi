package reversi;

public class Settings {
	
	private static Settings instance = null;
	
	private int height;
	private int width;
	private String player1Name;
	private String player2Name;
	
	
	private Settings() {
		height = 8;
		width = 8;
		player1Name = "PLAYER1";
		player2Name = "PLAYER2";
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

}
