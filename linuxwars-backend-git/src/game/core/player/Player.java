package game.core.player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import game.core.process.GameProcess;
import game.core.utils.Color;
import game.websocket.WebSocketHandler;
import io.netty.channel.ChannelHandlerContext;
import main.LinuxwarsBackend;


public class Player {
	private int player_id;
	private String session_key;
	private String username;
	private List<GameProcess> processes = new ArrayList<GameProcess>();
	private ChannelHandlerContext client;
	private boolean loggedin = false;
	private int credits;
	private PlayerType playertype;
	
	public Player(ChannelHandlerContext client){
		this.client = client;
		this.setLoggedIn(false);
		this.setUsername(null);
		this.setSessionkey(null);
		this.setPlayerid(0);
		this.setProcesses(null);
		this.setPlayertype(PlayerType.USER);
	}
	
	public void logout() {
		this.saveData();
		this.setLoggedIn(false);
		this.setUsername(null);
		this.setSessionkey(null);
		this.setPlayerid(0);
		this.setProcesses(null);
		this.setPlayertype(null);
	}
	
	/**
	 * <h1>Send a message to a {@link Player}</h1>
	 * <p>Sends the given message to the {@link ChannelHandlerContext} in form of a json-string.</p>
	 * <p>{"type":"println","message":?,"color":?}</p>
	 * <p></p>
	 * @param message as {@link String}
	 * @param color as {@link Color}
	 */
	public void sendMessage(String message, Color color) {
		WebSocketHandler.channelSendMessage("{\"type\":\"println\",\"message\":\""+Base64.getEncoder().encodeToString(message.getBytes())+"\",\"color\":\""+color.getColor()+"\"}", this.client);
	}
	
	/**
	 * <h1>Send a message to a {@link Player}</h1>
	 * <p>Sends the given message to the {@link ChannelHandlerContext} in form of a json-string.</p>
	 * <p>{"type":"println","message":?,"color":"green"}</p>
	 * <p></p>
	 * @param message as {@link String}
	 */
	public void sendMessage(String message) {
		WebSocketHandler.channelSendMessage("{\"type\":\"println\",\"message\":\""+Base64.getEncoder().encodeToString(message.getBytes())+"\",\"color\":\""+Color.DEFAULT.getColor()+"\"}", this.client);
	}
		
	/**
	 * <h1>Send a new "label" to a {@link Player}</h1>
	 * <p>Sends the given {@link String} to the {@link ChannelHandlerContext} in form of a json-string.</p>
	 * <p>{"type":"updatelabel","message":?}</p>
	 * <p></p>
	 * @param message as {@link String}
	 */
	public void updateLabel(String label) {
		WebSocketHandler.channelSendMessage("{\"type\":\"updatelabel\",\"message\":\""+Base64.getEncoder().encodeToString(label.getBytes())+"\"}", this.client);
	}
	
	/**
	 * <h1>Get the {@link ChannelHandlerContext} from the player</h1>
	 * <p></p>
	 * @return {@link ChannelHandlerContext}
	 */
	public ChannelHandlerContext getChannelHandlerContext() {
		return this.client;
	}
	
	/**
	 * <h1>Gets the login-status</h1>
	 * <p></p>
	 * @return {@link True} if the player is logged in
	 */
	public boolean isLoggedIn() {
		return loggedin;
	}
	
	/**
	 * <h1>Sets the login-status</h1>
	 * <p></p>
	 * param loggedin as {@link True}
	 */
	public void setLoggedIn(boolean loggedin) {
		this.loggedin = loggedin;
	}

	/**
	 * <h1>Gets the username</h1>
	 * <p></p>
	 * @return username of the player as {@link String} 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * <h1>Sets the username</h1>
	 * <p></p>
	 * @param username as {@link String}
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * <h1>Gets the sessionkey</h1>
	 * <p></p>
	 * @return the sessionkey as a {@link String}
	 */
	public String getSessionkey() {
		return session_key;
	}

	/**
	 * <h1>Sets the sessionkey</h1>
	 * <p></p>
	 * @param session_key as {@link String}
	 */
	public void setSessionkey(String session_key) {
		this.session_key = session_key;
	}

	/**
	 * <h1>Gets the player id</h1>
	 * <p></p>
	 * @return the player is as a {@link Integer}
	 */
	public int getPlayerid() {
		return player_id;
	}

	/**
	 * <h1>Sets the player id</h1>
	 * <p></p>
	 * @param player_id as {@link Integer}
	 */
	public void setPlayerid(int player_id) {
		this.player_id = player_id;
	}
	
	/**
	 * <h1>Saves the data in the database</h1>
	 */
	public void saveData() {
		try {
			LinuxwarsBackend.DB_CON.execute("UPDATE `credits` SET `credits`="+this.getCredits()+" WHERE `id`="+this.getPlayerid()+";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <h1>Sets the amount credits of the player</h1>
	 * <p></p>
	 * @param new amount of credits as {@link Integer}
	 */
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	/**
	 * <h1>Gets the amount credits of the player</h1>
	 * <p></p>
	 * @return the amount of credits ass {@link Integer}
	 */
	public int getCredits() {
		return this.credits;
	}
	
	/**
	 * <h1>Adds a {@link PlayerProcess} to the players processes</h1>
	 * <p></p>
	 * @param process as {@link PlayerProcess}
	 */
	public void addProcess(GameProcess process) {
		this.processes.add(process);
	}
	
	/**
	 * <h1>Gets all running {@link PlayerProcess}'s</h1>
	 * <p></p>
	 * @return list of running processes as {@link List<PlayerProcess>}
	 */
	public List<GameProcess> getProcesses(){
		return this.processes;
	}
	
	/**
	 * <h1>Sets all running {@link PlayerProcess}'s</h1>
	 * <p></p>
	 * @param processes as {@link List<PlayerProcess>}
	 */
	public void setProcesses(List<GameProcess> processes){
		this.processes = processes;
	}
	
	/**
	 * <h1>Removes a {@link PlayerProcess} from the players processes</h1>
	 * <p></p>
	 * @param process as {@link PlayerProcess}
	 */
	public void removeProcess(GameProcess process) {
		this.processes.remove(process);
	}

	public PlayerType getPlayertype() {
		return playertype;
	}

	public void setPlayertype(PlayerType playertype) {
		this.playertype = playertype;
	}
}
