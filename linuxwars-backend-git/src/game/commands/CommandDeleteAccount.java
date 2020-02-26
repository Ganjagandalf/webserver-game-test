package game.commands;

import java.sql.SQLException;
import java.util.ArrayList;

import game.core.Server;
import game.core.command.CommandArgs;
import game.core.command.CommandExecutor;
import game.core.player.Player;
import game.core.player.PlayerType;
import game.core.utils.Color;
import main.LinuxwarsBackend;

public class CommandDeleteAccount implements CommandExecutor{

	@Override
	@CommandArgs(needsLogin = true, playertype = PlayerType.GOD)
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
		String targetplayer = args.get(0);		
		try {
			for(Player target : Server.getOnlinePlayers()) {
				if(target.getUsername().equalsIgnoreCase(targetplayer)) {
					Server.getOnlinePlayers().remove(target);
					target.logout();
				}
			}	
			LinuxwarsBackend.DB_CON.execute("DELETE FROM `browsergame`.`users` WHERE `username`='" + targetplayer + "';");
			player.sendMessage(String.format("Player %s has been deleted! (if he had existed....)", targetplayer), Color.LIGHTGREEN);
		} catch (SQLException e) {}
	}

	@Override
	public void printDescription(Player player) {
		// TODO Auto-generated method stub
		
	}

}
