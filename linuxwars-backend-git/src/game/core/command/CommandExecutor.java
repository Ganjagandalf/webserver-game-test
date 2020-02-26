package game.core.command;

import java.util.ArrayList;

import game.core.player.Player;


public interface CommandExecutor{
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin);
	public void printDescription(Player player);
}
