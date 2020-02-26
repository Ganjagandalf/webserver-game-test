package game.core.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import game.core.Server;
import game.core.player.Player;
import game.core.utils.Color;
import game.websocket.WebSocketHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author VinceG
 *
 * CommandHandler is for handling all commands. Obviously...
 */
public abstract class CommandHandler extends WebSocketHandler{
    
	// HashMap with all available commands as key and the class for the Command as value
	private static HashMap<String, CommandExecutor> COMMANDS = new HashMap<String, CommandExecutor>();
	/**
	 * <h1></h1>
	 * <p>
	 * Register a {@link CommandExecutor} that gets executed when a specific command - given as {@link String} - is called. 
	 * <p>
	 * @param command
	 * @param executor
	 */
	public static void registerCommand(String command, CommandExecutor executor) {
		COMMANDS.put(command, executor);
	}
	
	/**
	 * <h1>Get {@link CommandExecutor} for given command<h1>
	 * <p></p>
	 * @param command given as {@link String}
	 * @return command as {@link CommandExecutor}
	 */
	public static CommandExecutor getCommand(String command) {
		return COMMANDS.get(command);
	}
	
	/**
	 * <h1>Get all commands</h1>
	 * <p></p>
	 * @return all commands as {@link HashMap<String, CommandExecutor>}
	 */
	public static HashMap<String, CommandExecutor> getAllCommands(){
		return COMMANDS;
	}
	
	/**
	 * <h1>Call Command</h1>
	 * <p>
	 * This Method is for calling the correct CommandExecutor and parsing the json-String
	 * to a command as {@link String} and arguments as {@link ArrayList<String>}.
	 * Also parses the {@link ChannelHandlerContext} to a {@link Player}
	 * <p>
	 * @author VinceG
	 * @param cmd
	 * @param ctx
	 */
    @SuppressWarnings("deprecation")
	public static void callCommand(String cmd, ChannelHandlerContext ctx){
    
		Player player = Server.getPlayer(ctx);
		if(player.equals(null)) {
			WebSocketHandler.sendMessage("Something went horribly wrong... please reload the page!", Color.RED, ctx);
		}else {
			try{
				// Parse the String send to the webserver to a json-object.
	            JsonObject command_json = new JsonParser().parse(cmd).getAsJsonObject();
	            String command = new String(Base64.getDecoder().decode(command_json.get("command").getAsString()));
	            ArrayList<String> args = new ArrayList<>();
	            
	            //convert the args to a ArrayList<String>
	            for(int i = 0; i < new String(Base64.getDecoder().decode(command_json.get("args").getAsString())).split(" ").length; i++){
	            	try {
	            		String arg = new String(Base64.getDecoder().decode(command_json.get("args").getAsString())).split(" ")[i];
	                    args.add(i, arg);
	            	}catch(ArrayIndexOutOfBoundsException ex) {}
	            }
	            // checks if a CommandExecutor with the given command is registered
	            if(getAllCommands().keySet().contains(command)) {
	            	//command is registered
	            	if(checkPowerlevel(getCommand(command), player)) {
	            		getCommand(command).onCommand(args, player, needsLogin(getCommand(command)));
	            	}else {
	            		// player doesnt have the powerlevel to execute the command
	            		player.sendMessage(String.format("Command \"%s\" not found!", command), Color.RED);
	            	}
	            }else {
	            	//command is not registered
		            player.sendMessage(String.format("Command \"%s\" not found!", command), Color.RED);
	            }
	            
	            if(player.isLoggedIn()) {
	            	System.out.println(String.format("Player %s executed \"%s\"", player.getUsername(), command));
	            }else {
	            	System.out.println(String.format("Connection %s executed \"%s\"", player.getChannelHandlerContext().channel().remoteAddress(), command));
	            }
	            
	        }catch(JsonSyntaxException ex){
	        	// Oops! JsonParse error... lets send the player a message :D Why not.
	        	player.sendMessage("Da is jetztala aber wat schief jelofen. Isch meine... der Befehl is jetz nischt nischt da, aber auch nischt da... irgendwie... :(", Color.RED);
	            return;
	        }  
		}              
    }
    
    public static boolean needsLogin(CommandExecutor executor) {
    	Class<?> object = executor.getClass();
    	for(Method method : object.getMethods()) {
    		if(method.isAnnotationPresent(CommandArgs.class) && method.getName().equalsIgnoreCase("onCommand")) {
    			try {
    				return method.getAnnotation(CommandArgs.class).needsLogin();   
    			} catch (NullPointerException e) {}	
    		}
    	}
    	return false;
    }
    
    public static boolean checkPowerlevel(CommandExecutor executor, Player player) {
    	Class<?> object = executor.getClass();
    	for(Method method : object.getMethods()) {
    		if(method.isAnnotationPresent(CommandArgs.class) && method.getName().equalsIgnoreCase("onCommand")) {
    			try {
    				return method.getAnnotation(CommandArgs.class).playertype().getPowerlevel() <= player.getPlayertype().getPowerlevel(); 
				} catch (NullPointerException e) {
					return true;
				}	
    		}
    	}
    	return true;
    }
}
