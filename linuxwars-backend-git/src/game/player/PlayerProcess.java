package game.player;

import java.util.Base64;
import java.util.Date;

import game.Colors;
import io.netty.channel.ChannelHandlerContext;
import websocket.WebSocketHandler;

public class PlayerProcess {
	private static int next_pid;
	private int process_id;
	private long process_started;
	private long process_till_done;
	private Player player;
	private ProcessTypes type;
	private int creditspertick;
	
	public PlayerProcess(Player player, int time_needed, ProcessTypes type) {
		this.process_id = next_pid;
		this.type = type;
		next_pid += 1;
		this.player = player;
		this.process_till_done = new Date().getTime() + (time_needed*1000);
		this.process_started = new Date().getTime();
	}
	
	public PlayerProcess(Player player, int time_needed, ProcessTypes type, int creditspertick) {
		this.process_id = next_pid;
		this.type = type;
		this.creditspertick = creditspertick;
		next_pid += 1;
		this.player = player;
		this.process_till_done = new Date().getTime() + (time_needed*1000);
		this.process_started = new Date().getTime();
	}
	
	/**
	 * <h1>Send a process message to the assigned {@link Player}</h1>
	 * <p>Sends the message to the {@link ChannelHandlerContext} in form of a json-string.</p>
	 * <p>{"type":"printprocess","message":?,"processid":?,"color":?}</p>
	 * <p></p>
	 * @param message as {@link String}
	 * @param color as {@link Colors}
	 */
	public void printProcess(String message, Colors color) {
		WebSocketHandler.channelSendMessage("{\"type\":\"printprocess\",\"message\":\""+Base64.getEncoder().encodeToString(message.getBytes())+"\",\"processid\":\""+process_id+"\",\"color\":\""+color.getColor()+"\"}", player.getChannelHandlerContext());
	}
	
	/**
	 * <h1>Sends line-update for the process to the {@link Player} assigned to the process</h1>
	 * <p>Sends the process defined message to the {@link ChannelHandlerContext} in form of a json-string.</p>
	 * <p>{"type":"updateprocess","message":?,"processid":?}</p>
	 * <p></p>
	 * @param message as {@link String}
	 */
	public void updateProcess(String message) {
		WebSocketHandler.channelSendMessage("{\"type\":\"updateprocess\",\"message\":\""+Base64.getEncoder().encodeToString(message.getBytes())+"\",\"processid\":\""+process_id+"\"}",  player.getChannelHandlerContext());
	}
	
	/**
	 * <h1>Get the process type</h1>
	 * <p></p>
	 * @return processtype as {@link ProcessTypes}
	 */
	public ProcessTypes getType() {
		return this.type;
	}
	
	/**
	 * <h1>Get the credits per tick</h1>
	 * <p></p>
	 * @return credits per tick as {@link Integer}
	 */
	public int getCreditsPerTick() {
		return this.creditspertick;
	}
	
	/**
	 * <h1>Get the finished status in percent</h1>
	 * <p>Returns the status in form of %</p>
	 * @return percentage completed {@link Double}
	 */	
	public double getPercent() {
		float time_difference = process_till_done - process_started; 
		float time_passed = new Date().getTime() - process_started;
		return (100/time_difference*time_passed);
	}
}
