package game.player;

import java.util.Base64;
import java.util.HashMap;

import game.Colors;
import game.Server;
import io.netty.channel.ChannelHandlerContext;
import websocket.WebSocketHandler;

public class Player {
	private int player_id;
	private String session_key;
	private String username;
	@SuppressWarnings("unused")
	private HashMap<Integer, PlayerProcess> processes = new HashMap<>();
	private ChannelHandlerContext client;
	private boolean loggedin = false;
	
	public Player(ChannelHandlerContext client){
		this.client = client;
	}
	
	public void login(String username, String password) {
		this.setUsername(username);
	}
	
	public void logout() {
		this.loggedin = false;
		this.username = null;
		this.session_key = null;
		this.player_id = 0;
	}
	
	public void sendMessage(String message, Colors color) {
		WebSocketHandler.channelSendMessage("{\"type\":\"println\",\"message\":\""+Base64.getEncoder().encodeToString(message.getBytes())+"\",\"color\":\""+color.getColor()+"\"}", this.client);
		
	}
	
	public void sendMessage(String message) {
		WebSocketHandler.channelSendMessage("{\"type\":\"println\",\"message\":\""+Base64.getEncoder().encodeToString(message.getBytes())+"\",\"color\":\""+Colors.DEFAULT.getColor()+"\"}", this.client);
	}
		
	public void updateLabel(String label) {
		WebSocketHandler.channelSendMessage("{\"type\":\"updatelabel\",\"message\":\""+Base64.getEncoder().encodeToString(label.getBytes())+"\"}", this.client);
	}
	
	public ChannelHandlerContext getChannelHandlerContext() {
		return this.client;
	}
	
	public boolean isLoggedIn() {
		return loggedin;
	}
	
	public void setLoggedIn(boolean loggedin) {
		this.loggedin = loggedin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSessionkey() {
		return session_key;
	}

	public void setSessionkey(String session_key) {
		this.session_key = session_key;
	}

	public int getPlayerid() {
		return player_id;
	}

	public void setPlayerid(int player_id) {
		this.player_id = player_id;
	}
}
