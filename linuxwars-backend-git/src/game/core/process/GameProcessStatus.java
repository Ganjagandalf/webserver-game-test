package game.core.process;

public enum GameProcessStatus {
	PROCESSING(1),
	FINISHED(0),
	ERROR(-1);
	
	private int status;
	
	GameProcessStatus(int i){
		this.status = i;
	}
	
	public int getStatus() {
		return this.status;
	}
}
