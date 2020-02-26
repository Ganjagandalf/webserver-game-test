package game.commands;

import java.util.ArrayList;

import game.core.command.CommandExecutor;
import game.core.player.Player;
import game.core.utils.Color;

public class CommandEcho implements CommandExecutor{

	@Override
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
		String color = "green";
		if(player.isLoggedIn()) {
			for(int i = 0; i < args.size(); i++){
	            if(args.get(i).equals("--color")){
	                color = args.get(i+1);
	                args.remove(i+1);
	                args.remove(i);
	            }
	        }   
	        if(args.size() > 0){
	            player.sendMessage(String.join(" ", args), Color.getColor(color));
	            player.setCredits(player.getCredits() + 1);
	        }else{
	            player.sendMessage("Wrong syntax. Usage:'echo <message>'", Color.RED);
	            player.sendMessage("    --color <color> => Text gets printed out in the given color", Color.RED);
	            player.sendMessage(" ", Color.RED);
	            player.sendMessage("    Paramteres can be placed in the middle of the text and dont have to be directly after the command.", Color.RED);
	        }
		}else {
			player.sendMessage("You have to be logged in to use this command!", Color.RED);
		}
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    Echo prints the given arguments to the \"console\".", Color.CYAN);
		player.sendMessage("        --color <color> prints the message in the given color", Color.CYAN);
	}
}
