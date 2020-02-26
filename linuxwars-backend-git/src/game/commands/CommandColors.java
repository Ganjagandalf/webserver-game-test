package game.commands;

import java.util.ArrayList;

import game.Color;
import game.CommandExecutor;
import game.player.Player;

public class CommandColors implements CommandExecutor{

	@Override
	public void onCommand(ArrayList<String> args, Player player) {
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
