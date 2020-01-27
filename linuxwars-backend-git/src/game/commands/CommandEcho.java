package game.commands;

import java.util.ArrayList;

import game.Colors;
import game.CommandExecutor;
import game.player.Player;
import websocket.WebSocketHandler;

public class CommandEcho extends WebSocketHandler implements CommandExecutor{
	@Override
	public void onCommand(ArrayList<String> args, Player player) {
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
	            player.sendMessage(String.join(" ", args), Colors.DEFAULT.getColor(color));
	        }else{
	            player.sendMessage("Wrong syntax. Usage:'echo <message>'", Colors.RED);
	            player.sendMessage("    --color <color> => Text gets printed out in the given color", Colors.RED);
	            player.sendMessage(" ", Colors.RED);
	            player.sendMessage("    Paramteres can be placed in the middle of the text and dont have to be directly after the command.", Colors.RED);
	        }
		}else {
			player.sendMessage("You have to be logged in to use this command!", Colors.RED);
		}
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    Echo prints the given arguments to the \"console\".", Colors.CYAN);
		player.sendMessage("        --color <color> prints the message in the given color", Colors.CYAN);
	}
}