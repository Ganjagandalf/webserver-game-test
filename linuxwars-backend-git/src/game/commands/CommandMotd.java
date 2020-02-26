package game.commands;

import java.util.ArrayList;

import game.core.command.CommandExecutor;
import game.core.player.Player;
import game.core.utils.Color;

public class CommandMotd implements CommandExecutor{

	@Override
	public void onCommand(ArrayList<String> args, Player player, boolean needsLogin) {
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
		player.sendMessage(" ", Color.DEFAULT);
		player.sendMessage("    JOIN THE WAR NOW-!!-JOIN THE WAR NOW-!!-JOIN THE WAR NOW-!!-JOIN THE WAR NOW", Color.RAINBOW);
		player.sendMessage(" ", Color.DEFAULT);
		player.sendMessage("                    STEP 1 : type 'register <username> <email>'", Color.LIGHTGREEN);
		player.sendMessage("                    STEP 2 : PROFIT????", Color.LIGHTGREEN);
		player.sendMessage("                                       OR", Color.RED);
		player.sendMessage("                    STEP 2 : type 'login <username> <password>'", Color.LIGHTGREEN);
		player.sendMessage(" ", Color.DEFAULT);
		player.sendMessage("    JOIN THE WAR NOW-!!-JOIN THE WAR NOW-!!-JOIN THE WAR NOW-!!-JOIN THE WAR NOW", Color.RAINBOW);
		player.sendMessage(" ", Color.DEFAULT);
		
	}

	@Override
	public void printDescription(Player player) {
		player.sendMessage("    Prints the current motd", Color.CYAN);		
	}
}
