package game.core.player;

public enum PlayerType {
	USER(0, ""),
	MODERATOR(10, "Mod"),
	ADMIN(20, "Admin"),
	GOD(100, "God");
	
	int powerlevel;
	String title;
	
	PlayerType(int powerlevel, String title) {
		this.powerlevel = powerlevel;
		this.title = title;
	}
	
	public int getPowerlevel() {
		return this.powerlevel;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public static PlayerType getPlayerType(int powerlevel) {
		PlayerType tmp_playerType = PlayerType.USER;
		for(PlayerType playerType : PlayerType.values()) {
			if(playerType.getPowerlevel() > tmp_playerType.getPowerlevel() && playerType.getPowerlevel() <= powerlevel)
				tmp_playerType = playerType;
		}
		return tmp_playerType;
	}
}
