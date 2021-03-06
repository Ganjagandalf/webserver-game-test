package game.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.core.player.Player;
import io.netty.channel.ChannelHandlerContext;

public class Server {
	private static List<Player> PLAYERS = Collections.synchronizedList(new ArrayList<>());
	// gives all Players wich are registered atm
	public static List<Player> getOnlinePlayers() {
		return PLAYERS;
	}
	
	// adds a player to the ArrayList
	public static void addPlayer(Player player) {
		PLAYERS.add(player);
	}
	
	// get the player for the given connection (ChannelHandlerContext)
	public static Player getPlayer(ChannelHandlerContext ctx) {
		for(Player p : PLAYERS) {
			if(p.getChannelHandlerContext().equals(ctx))
				return p;
		}
		return null;
	}
	
	// remove the player wich is connected to the given connection (ChannelHandlerContext)
	public static void removePlayer(ChannelHandlerContext ctx) {
		for(Player p : PLAYERS) {
			if(p.getChannelHandlerContext().equals(ctx))
				PLAYERS.remove(p);
		}
	}
}
