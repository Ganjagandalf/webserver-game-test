package game.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import game.Colors;
import game.CommandExecutor;
import game.CommandHandler;
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
				player.sendMessage("You have successfully registered user " + username, Colors.CYAN);
				player.sendMessage("Use 'login <username> <passowrd>' to start your journay ;)" + username, Colors.CYAN);
			} catch (SQLException e) {
				player.sendMessage("Something went kinda wrong bro... User maybe already used? :(", Colors.RED);
			}
		}else {
			player.sendMessage("Wrong usage... :/", Colors.RED);
		}	
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    register <username> <password>", Colors.CYAN);		
	}
}
