package game.player;

import java.util.Base64;
import java.util.Date;

import game.Color;
import io.netty.channel.ChannelHandlerContext;
import websocket.WebSocketHandler;

public class PlayerProcess {
	private static int NEXT_PID;
	private int process_id;
	private long process_started;
	private long process_ends;
	private ProcessType type;
	private int creditspertick;
	private ProcessStatus status;
	
	public PlayerProcess(int time_needed, ProcessType type) {
		this.setId(NEXT_PID++);
		this.setStatus(ProcessStatus.PROCESSING);
		this.setType(type);
		this.setEndTime(new Date().getTime() + (time_needed*1000));
		this.setStartTime(new Date().getTime());
	}
	
	public PlayerProcess(int time_needed, ProcessType type, int creditspertick) {
		this.setId(NEXT_PID++);
		this.setStatus(ProcessStatus.PROCESSING);
		this.setType(type);
		this.setCreditsPerTick(creditspertick);
		this.setEndTime(new Date().getTime() + (time_needed*1000));
		this.setStartTime(new Date().getTime());
	}
	
	/**
	 * <h1>Send a process message to the assigned {@link Player}</h1>
	 * <p>Sends the message to the {@link ChannelHandlerContext} in form of a json-string.</p>
	 * <p>{"type":"printprocess","message":?,"processid":?,"color":?}</p>
	 * <p></p>
	 * @param message as {@link String}
	 * @param color as {@link Color}
	 * @param player as {@link Player}
	 */
	public void printProcess(String message, Color color, Player player) {
		WebSocketHandler.channelSendMessage("{\"type\":\"printprocess\",\"message\":\""+Base64.getEncoder().encodeToString(message.getBytes())+"\",\"processid\":\""+this.getId()+"\",\"color\":\""+color.getColor()+"\"}", player.getChannelHandlerContext());
	}
	
	/**
	 * <h1>Sends line-update for the process to the {@link Player} assigned to the process</h1>
	 * <p>Sends the process defined message to the {@link ChannelHandlerContext} in form of a json-string.</p>
	 * <p>{"type":"updateprocess","message":?,"processid":?}</p>
	 * <p></p>
	 * @param message as {@link String}
	 * @param player as {@link Player}
	 */
	public void updateProcess(String message, Player player) {
		WebSocketHandler.channelSendMessage("{\"type\":\"updateprocess\",\"message\":\""+Base64.getEncoder().encodeToString(message.getBytes())+"\",\"processid\":\""+this.getId()+"\"}",  player.getChannelHandlerContext());
	}
	
	/**
	 * <h1>Get the process type</h1>
	 * <p></p>
	 * @return processtype as {@link ProcessTypes}
	 */
	public ProcessType getType() {
		return this.type;
	}
	
	/**
	 * <h1>Set the process type</h1>
	 * <p></p>
	 * @param processtype as {@link ProcessTypes}
	 */
	public void setType(ProcessType processtype) {
		this.type = processtype;
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
	 * <h1>Set the credits per tick</h1>
	 * <p></p>
	 * @param processtype as {@link ProcessTypes}
	 */
	public void setCreditsPerTick(int creditspertick) {
		this.creditspertick = creditspertick;
	}
	
	/**
	 * <h1>Get the status in percent</h1>
	 * <p></p>
	 * @return percentage completed as {@link Double} in %
	 */	
	public double getPercent() {
		float time_difference = this.getEndTime() - this.getStartTime(); 
		float time_passed = new Date().getTime() - this.getStartTime();
		return (100/time_difference*time_passed);
	}
	
	/**
	 * <h1>Set the {@link ProcessStatus}</h1>
	 * <p></p>
	 * @param status as {@link ProcessStatus}
	 */
	public void setStatus(ProcessStatus status) {
		this.status = status;
	}
	
	public ProcessStatus getStatus() {
		return this.status;
	}
	
	/**
	 * <h1>Set the process id</h1>
	 * <p></p>
	 * @param process_id as {@link Integer}
	 */
	public void setId(int process_id) {
		this.process_id = process_id;
	}
	
	/**
	 * <h1>Get the process id</h1>
	 * <p></p>
	 * @return process end time as unix time
	 */
	public int getId() {
		return this.process_id;
	}
	
	/**
	 * <h1>Get process start time</h1>
	 * <p></p>
	 * @return process start time as unix time
	 */
	public long getStartTime() {
		return this.process_started;
	}
	
	/**
	 * <h1>Set the start time in form of a unix time</h1>
	 * <p></p>
	 * @param process_started as {@link Long}
	 */
	public void setStartTime(long process_started) {
		this.process_started = process_started;
	}
	
	/**
	 * <h1>Get process end time</h1>
	 * <p></p>
	 * @return process end time as unix time
	 */
	public long getEndTime() {
		return this.process_ends;
	}
	
	/**
	 * <h1>Set the end time in form of a unix time</h1>
	 * <p></p>
	 * @param process_ends as {@link Long}
	 */
	public void setEndTime(long process_ends) {
		this.process_ends = process_ends;
	}
}
