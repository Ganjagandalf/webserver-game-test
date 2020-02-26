package game.commands;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import game.Color;
import game.CommandExecutor;
import game.CommandHandler;
import game.player.Player;

public class CommandHelp implements CommandExecutor{

	@Override
	public void onCommand(ArrayList<String> args, Player player) {
		Map<String, CommandExecutor> commands = new TreeMap<>(CommandHandler.commands);
		for(String command : commands.keySet()) {
			player.sendMessage(command + ":");
			CommandHandler.commands.get(command).printDescription(player);
		}		
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    Print all commands", Color.CYAN);		
	}
}
