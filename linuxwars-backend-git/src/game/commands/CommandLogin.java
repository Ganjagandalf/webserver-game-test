package game.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import game.Colors;
import game.CommandExecutor;
import game.Encode;
import game.player.Player;
import main.LinuxwarsBackend;

public class CommandLogin implements CommandExecutor{

	@Override
	public void onCommand(ArrayList<String> args, Player player) {
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
								player.setLoggedIn(true);
								player.setUsername(username);
								player.setPlayerid(Integer.valueOf(res.get("id")));
								player.sendMessage(String.format("You are now logged in as %s", username));
								player.updateLabel(username + "@fakeserver:~$");
								return;
							}
						}
					}
						
					player.sendMessage("Username or password wrong ;/", Colors.RED);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				player.sendMessage("Damn bro... wrong syntax o_o", Colors.RED);
			}
		}else {
			player.sendMessage("You are already logged in!", Colors.RED);
		}
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    login <username> <password>", Colors.CYAN);		
	}
}
