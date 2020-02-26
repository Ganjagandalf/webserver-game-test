package game.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import game.Color;
import game.CommandExecutor;
import game.Encode;
import game.player.Player;
import main.LinuxwarsBackend;

public class CommandRegister implements CommandExecutor{
	@Override
	public void onCommand(ArrayList<String> args, Player player) {
		if(args.size() == 2) {
			String username = args.get(0);
			String password = args.get(1);
			
			try {
				LinuxwarsBackend.DB_CON.execute("INSERT INTO `users` (`username`, `password`) VALUES ('"+username+"', '"+Encode.sha512(password)+"');");
				player.sendMessage("You have successfully registered user " + username, Color.CYAN);
				player.sendMessage("Use 'login <username> <passowrd>' to start your journay ;)" + username, Color.CYAN);
			} catch (SQLException e) {
				player.sendMessage("Something went kinda wrong bro... User maybe already used? :(", Color.RED);
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
