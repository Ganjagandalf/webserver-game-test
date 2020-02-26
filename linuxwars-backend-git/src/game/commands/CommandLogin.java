package game.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import game.core.command.CommandExecutor;
import game.core.player.Player;
import game.core.player.PlayerType;
import game.core.utils.Color;
import game.core.utils.Encode;
import main.LinuxwarsBackend;

public class CommandLogin implements CommandExecutor{

	@Override
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
		if(!player.isLoggedIn()) {
			if(args.size() == 2) {
				String username = args.get(0);
				String password = args.get(1);
				try {
					ArrayList<HashMap<String, String>> result;
					result = LinuxwarsBackend.DB_CON.executeResults("SELECT * FROM `users` WHERE `username`='"+username+"'");
					for(HashMap<String, String> res : result) {
						if(res.get("username").equals(username)) {
							if(Encode.sha512(password).equals(res.get("password"))) {
								try {
									HashMap<String, String> result_credits = LinuxwarsBackend.DB_CON.executeResults("SELECT * FROM `credits` WHERE `id`=" + Integer.valueOf(res.get("id"))).get(0);
									if(result.isEmpty()) {
										LinuxwarsBackend.DB_CON.execute("INSERT INTO `browsergame`.`credits`(`id`,`credits`) VALUES ("+Integer.valueOf(res.get("id"))+",10000);");
										player.setCredits(10000);
									}else {
										player.setCredits(Integer.valueOf(result_credits.get("credits")));
									}
									player.setPlayerid(Integer.valueOf(res.get("id")));
								} catch (SQLException | IndexOutOfBoundsException e) {
									LinuxwarsBackend.DB_CON.execute("INSERT INTO `browsergame`.`credits`(`id`,`credits`) VALUES ("+Integer.valueOf(res.get("id"))+",10000);");
									player.setCredits(10000);
								}
								player.setLoggedIn(true);
								player.setPlayertype(PlayerType.getPlayerType(Integer.parseInt(res.get("powerlevel"))));
								if(player.getPlayertype().getTitle().length() > 0) {
									player.setUsername("[" + player.getPlayertype().getTitle() + "]" + username);
								}else {
									player.setUsername(username);
								}
								
								player.sendMessage(String.format("You are now logged in as %s", username));
								player.updateLabel(username + "@127.0.0.1:~$");
								return;
							}
						}
					}
						
					player.sendMessage("Username or password wrong ;/", Color.RED);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				player.sendMessage("Damn bro... wrong syntax o_o", Color.RED);
			}
		}else {
			player.sendMessage("You are already logged in!", Color.RED);
		}
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    login <username> <password>", Color.CYAN);		
	}
}
