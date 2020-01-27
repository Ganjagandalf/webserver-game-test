package game;

public class GameTick extends Thread{
	@Override
	public void run(){
		while(true) {
			try {
				doStuff();
				GameTick.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	public void doStuff() {
		/*
		 * TODO: Nothing here yet... i also think about removing it because i dont have any good idea for this.. lul
		 */
	}
}
