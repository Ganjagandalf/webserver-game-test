package game.core;

import game.core.player.Player;
import game.core.process.GameProcess;
import game.core.process.GameProcessStatus;

public class GameTick extends Thread{

	/*
	 * A gamtick happens every ~500ms
	 * (server runs a ~2 tps)
	 */
	public static final int TPS = 2;
	@Override
	public void run(){
		while(true) {
			try {
				doStuff();
				GameTick.sleep(1000/TPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	public static int getTps() {
		return TPS;
	}
	
	public void doStuff() {
		try {			
			for(Player player : Server.getOnlinePlayers()) {
				for(GameProcess process : player.getProcesses()) {
					process.doStuff(player);	
				}
			}
			
			for(Player player : Server.getOnlinePlayers()) {
				for(GameProcess proc : player.getProcesses()) {
					if(proc.getStatus().equals(GameProcessStatus.FINISHED)) {
						player.removeProcess(proc);
					}
				}
			}
		}catch(Exception ex) {}
	}
}
