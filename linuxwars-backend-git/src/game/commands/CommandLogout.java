package game.commands;

import java.util.ArrayList;

import game.Colors;
import game.CommandExecutor;
import game.player.Player;

public class CommandLogout implements CommandExecutor{

	@Override
	public void onCommand(ArrayList<String> args, Player player) {
		if(player.isLoggedIn()) {
			player.sendMessage("You are now logged out!", Colors.RED);
			player.logout();
			player.updateLabel("nouser@loginserver:/ssh/login$");
		}else {
			player.sendMessage("Damn bro.. you're not logged in... o_O", Colors.RED);
		}		
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    logout... its simple like that bro.", Colors.CYAN);		
	}
}