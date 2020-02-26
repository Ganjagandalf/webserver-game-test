package game.commands;

import java.util.ArrayList;

import game.core.GameTick;
import game.core.command.CommandArgs;
import game.core.command.CommandExecutor;
import game.core.player.Player;
import game.core.process.GameProcess;
import game.core.utils.Color;
import game.processes.ProcessMining;

public class CommandMining implements CommandExecutor{

	@Override
	@CommandArgs(needsLogin = true)
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
		if(player.isLoggedIn()) {
			GameProcess process = new ProcessMining(10, 200);
			player.addProcess(process);
			player.sendMessage(String.format("Let's mine some Coins. This will take 120 seconds and gives you %d Credits each second!", 
					process.getCreditsPerTick()*GameTick.getTps()));
			process.printProcess("You started Mining -> [--------------------------------------------------] 0,00%", Color.CYAN, player);
		}else {
			player.sendMessage("You have to be logged in to mine some coins bruh!", Color.RED);
		}
	}

	@Override
	public void printDescription(Player player) {
		// TODO Auto-generated method stub
		
	}
}
