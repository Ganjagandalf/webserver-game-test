package game;

import java.util.ArrayList;

import game.player.Player;

public interface CommandExecutor {
	public void onCommand(ArrayList<String> args, Player player);
	public void printDescription(Player player);
}
