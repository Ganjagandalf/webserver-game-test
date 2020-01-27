package game.commands;

import java.util.ArrayList;

import game.Colors;
import game.CommandExecutor;
import game.player.Player;
import websocket.WebSocketHandler;

public class CommandMotd extends WebSocketHandler implements CommandExecutor{

	@Override
	public void onCommand(ArrayList<String> args, Player player) {
		player.sendMessage("                                     :::::::");
		player.sendMessage("                                 ::###########::");
		player.sendMessage("                              ::#################::");
		player.sendMessage("                             :#####################:");
		player.sendMessage("                            :#######################:");
		player.sendMessage("                           :#########################:");
		player.sendMessage("                           :#########################:");
		player.sendMessage("                           :######;   ;###;   ;######:");
		player.sendMessage("                           ;####;      :#:       ####;");
		player.sendMessage("                            ###:       :#:       :###");
		player.sendMessage("                            ###:      :###:      :###");
		player.sendMessage("                             ;####::###   ###::####;");
		player.sendMessage("                              ;#######;   ;#######;");
		player.sendMessage("                                :#######:#######:");
		player.sendMessage("                                 :#;#;#;#;#;#;#:");
		player.sendMessage("                                 ##:# # # # #:##");
		player.sendMessage("                                  #####:#:#:###");
		player.sendMessage("                                   ;#########/");
		player.sendMessage("                                     :;;;;;:");
		player.sendMessage(" ", Colors.DEFAULT);
		player.sendMessage("    JOIN THE WAR NOW-!!-JOIN THE WAR NOW-!!-JOIN THE WAR NOW-!!-JOIN THE WAR NOW", Colors.RAINBOW);
		player.sendMessage(" ", Colors.DEFAULT);
		player.sendMessage("                    STEP 1 : type 'register <username> <email>'", Colors.LIGHTGREEN);
		player.sendMessage("                    STEP 2 : PROFIT????", Colors.LIGHTGREEN);
		player.sendMessage("                                       OR", Colors.RED);
		player.sendMessage("                    STEP 2 : type 'login <username> <password>'", Colors.LIGHTGREEN);
		player.sendMessage(" ", Colors.DEFAULT);
		player.sendMessage("    JOIN THE WAR NOW-!!-JOIN THE WAR NOW-!!-JOIN THE WAR NOW-!!-JOIN THE WAR NOW", Colors.RAINBOW);
		player.sendMessage(" ", Colors.DEFAULT);
		
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    Prints the current motd", Colors.CYAN);		
	}
}
