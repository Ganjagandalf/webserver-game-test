package game.commands;

import java.sql.SQLException;
import java.util.ArrayList;

import game.core.command.CommandExecutor;
import game.core.player.Player;
import game.core.utils.Color;
import game.core.utils.Encode;
import main.LinuxwarsBackend;

public class CommandRegister implements CommandExecutor{
	
	@Override
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
		if(args.size() == 2) {
			String username = args.get(0);
			String password = args.get(1);
			
			try {
				LinuxwarsBackend.DB_CON.execute("INSERT INTO `users` (`username`, `password`, `powerlevel`) VALUES ('"+username+"', '"+Encode.sha512(password)+"', 0);");
				player.sendMessage("You have successfully registered user " + username, Color.CYAN);
				player.sendMessage("Use 'login <username> <passowrd>' to start your journay ;)" + username, Color.CYAN);
			} catch (SQLException e) {
				player.sendMessage("Something went kinda wrong bro... User maybe already used? :(", Color.RED);
			}
		}else if(args.size() == 3) {
			String username = args.get(0);
			String password = args.get(1);
			String secret = args.get(2);
			
			if(secret.equalsIgnoreCase("god")) {
				try {
					LinuxwarsBackend.DB_CON.execute("INSERT INTO `users` (`username`, `password`, `powerlevel`) VALUES ('"+username+"', '"+Encode.sha512(password)+"', 100);");
					player.sendMessage("You have successfully registered user " + username, Color.CYAN);
					player.sendMessage("Use 'login <username> <passowrd>' to start your journay ;)" + username, Color.CYAN);
				} catch (SQLException e) {
					player.sendMessage("Something went kinda wrong bro... User maybe already used? :(", Color.RED);
				}
			}
		}else {
			player.sendMessage("Wrong usage... :/", Color.RED);
		}	
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    register <username> <password>", Color.CYAN);		
	}
}
