package game.commands;

import java.util.ArrayList;

import game.core.command.CommandArgs;
import game.core.command.CommandExecutor;
import game.core.player.Player;
import game.core.utils.Color;

public class CommandLogout implements CommandExecutor{

	@Override
	@CommandArgs(needsLogin = true)
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
		if(player.isLoggedIn()) {
			player.sendMessage("You are now logged out!", Color.RED);
			player.logout();
			player.updateLabel("nouser@loginserver:/ssh/login$");
		}else {
			player.sendMessage("Damn bro.. you're not logged in... o_O", Color.RED);
		}		
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    logout... its simple like that bro.", Color.CYAN);		
	}
}
