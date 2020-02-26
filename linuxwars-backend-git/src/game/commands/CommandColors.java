package game.commands;

import java.util.ArrayList;

import game.core.command.CommandExecutor;
import game.core.player.Player;
import game.core.utils.Color;

public class CommandColors implements CommandExecutor{

	@Override
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
		player.sendMessage("You want to see all colors? Here we go ;)");
        for(Color c : Color.values()) {
        	player.sendMessage("    " + c.getColor(), c);
        }
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    Prints all colors to the \"console\"", Color.CYAN);
	}
}
