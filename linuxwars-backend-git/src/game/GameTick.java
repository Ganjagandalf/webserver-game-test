package game;

import java.util.ArrayList;
import java.util.Iterator;

import game.player.Player;
import game.player.PlayerProcess;
import game.player.ProcessTypes;

public class GameTick extends Thread{
	@Override
	public void run(){
		while(true) {
			try {
				doStuff();
				GameTick.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	public void doStuff() {
		try {
			ArrayList<PlayerProcess> finished_processes = new ArrayList<>();
			synchronized(Server.getOnlinePlayers()) {
				Iterator<Player> iterator_players = Server.getOnlinePlayers().iterator();
				while(iterator_players.hasNext()) {
					Player player = iterator_players.next();
					synchronized(player.processes) {
						Iterator<PlayerProcess> iterator_processes = player.processes.iterator();
						while(iterator_processes.hasNext()) {
							PlayerProcess process = iterator_processes.next();
							double percent = (double)Math.round(process.getPercent()*10)/10;
							if(percent >= 100) {
								if(process.getType().equals(ProcessTypes.MINING)) {
									process.updateProcess("You started Mining -> [==================================================] 100%");
									player.credits += process.getCreditsPerTick();
								}
								finished_processes.add(process);
							}else {
								if(process.getType().equals(ProcessTypes.MINING)) {
									String bar = "";
									int percent_bar = (int)Math.round(percent/2);
									for(int i = 0; i < 50; i++) {
										if(i <= percent_bar) {
											bar += "#";
										}else {
											bar += "-";
										}
									}
									process.updateProcess("You started Mining -> ["+bar+"] " + percent + "%");
									player.credits += process.getCreditsPerTick();
								}					
							}
						}
					}
				}
			}
			for(Player player : Server.getOnlinePlayers()) {
				for(PlayerProcess proc : finished_processes) {
					player.processes.remove(proc);
				}
			}
		}catch(Exception ex) {}
	}
}
