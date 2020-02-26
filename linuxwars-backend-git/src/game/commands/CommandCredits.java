package game.commands;

import java.util.ArrayList;

import game.core.command.CommandArgs;
import game.core.command.CommandExecutor;
import game.core.player.Player;
import game.core.utils.Color;

public class CommandCredits implements CommandExecutor{

	@Override
	@CommandArgs(needsLogin = true)
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
		if(player.isLoggedIn())
			player.sendMessage("You got " + player.getCredits() + " Credits!", Color.CYAN);		
		else
			player.sendMessage("No Account = No Cash. Login to see your balance!", Color.RED);
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    Shows your current balance!", Color.CYAN);
	}
}
