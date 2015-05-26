package reversi;

public class Settings {
	
	private static Settings instance = null;
	
	private int N;
	private String player1Name;
	private String player2Name;
	
	
	private Settings() {
		N = 4;
		player1Name = "PLAYER1";
		player2Name = "PLAYER2";
	}
	
	public static Settings instance() {
		if (instance == null) {
			instance = new Settings();
		}
		
		return instance;
	}
	
	public int getN() {
		return N;
	}
	
	public String getPlayer1Name() {
		return player1Name;
	}
	
	public String getPlayer2Name() {
		return player2Name;
	}
}
