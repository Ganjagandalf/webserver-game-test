package game;

import game.player.Player;
import game.player.PlayerProcess;
import game.player.ProcessStatus;

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
				for(PlayerProcess process : player.getProcesses()) {
					switch(process.getType()) {
						case MINING:
							handleMiningProcess(process, player);
							break;
						default:
							player.sendMessage("Irjendwat is schief jelofen :/", Color.RED);
							break;
					}
					
				}
			}
			
			for(Player player : Server.getOnlinePlayers()) {
				for(PlayerProcess proc : player.getProcesses()) {
					if(proc.getStatus().equals(ProcessStatus.FINISHED)) {
						player.removeProcess(proc);
					}
				}
			}
		}catch(Exception ex) {}
	}
	
	public void handleMiningProcess(PlayerProcess process, Player player) {
		double percent = (double)Math.round(process.getPercent()*10)/10;
		if(percent >= 100) {
			process.updateProcess("Mining finihsed ;) -> [==================================================] 100%", player);
			process.setStatus(ProcessStatus.FINISHED);
			player.setCredits(player.getCredits() + process.getCreditsPerTick());
		}else {
			String bar = "";
			int percent_bar = (int)Math.round(percent/2);
			for(int i = 0; i < 50; i++) {
				if(i <= percent_bar) {
					bar += "#";
				}else {
					bar += "-";
				}
			}
			process.updateProcess("You started Mining -> ["+ bar +"] " + percent + "%", player);
			player.setCredits(player.getCredits() + process.getCreditsPerTick());				
		}
	}
}
