package game.processes;

import game.core.player.Player;
import game.core.process.GameProcess;
import game.core.process.GameProcessStatus;

public class ProcessMining extends GameProcess{

	public ProcessMining(int time_needed) {
		super(time_needed);
	}
	
	public ProcessMining(int time_needed, int creditspertick) {
		super(time_needed, creditspertick);
	}
	
	@Override
	public void doStuff(Player player) {
		double percent = (double)Math.round(this.getPercent()*10)/10;
		if(percent >= 100) {
			this.updateProcess("Mining finihsed ;) -> [==================================================] 100%", player);
			this.setStatus(GameProcessStatus.FINISHED);
			player.setCredits(player.getCredits() + this.getCreditsPerTick());
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
			this.updateProcess("You started Mining -> ["+ bar +"] " + percent + "%", player);
			player.setCredits(player.getCredits() + this.getCreditsPerTick());				
		}
	}
}
