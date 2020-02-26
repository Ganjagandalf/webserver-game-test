package game.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import game.core.command.CommandExecutor;
import game.core.command.CommandHandler;
import game.core.player.Player;
import game.core.utils.Color;

public class CommandHelp implements CommandExecutor{
	
	@Override
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
		Map<String, CommandExecutor> commands = new TreeMap<>(CommandHandler.getAllCommands());
		for(String command : commands.keySet()) {
			CommandExecutor executor = CommandHandler.getCommand(command);
			if(!player.isLoggedIn()) {
				if(!CommandHandler.needsLogin(executor)) {
					player.sendMessage(command + ":");
					executor.printDescription(player);
				}
			}else if(CommandHandler.checkPowerlevel(executor, player)){
				player.sendMessage(command + ":");
				executor.printDescription(player);
			}	
		}	
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    Print all commands", Color.CYAN);		
	}
}
