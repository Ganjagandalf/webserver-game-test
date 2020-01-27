package game;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import game.player.Player;
import io.netty.channel.ChannelHandlerContext;
import websocket.WebSocketHandler;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

/**
 * @author VinceG
 *
 * CommandHandler is for handling all commands. Obviously...
 */
public abstract class CommandHandler extends WebSocketHandler{
    
	// HashMap with all available commands as key and the class for the Command as value
	public static HashMap<String, CommandExecutor> commands = new HashMap<String, CommandExecutor>();
	/**
	 * <h1></h1>
	 * <p>
	 * Register a {@link CommandExecutor} that gets executed when a specific command - given as {@link String} - is called. 
	 * <p>
	 * @param command
	 * @param executor
	 */
	public static void registerCommand(String command, CommandExecutor executor) {
		commands.put(command, executor);
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
			WebSocketHandler.sendMessage("Something went horribly wrong... please reload the page!", Colors.RED, ctx);
		}else {
			try{
				// Parse the String send to the webserver to a json-object.
	            JsonObject command_json = new JsonParser().parse(cmd).getAsJsonObject();
	            String command = new String(Base64.getDecoder().decode(command_json.get("command").getAsString()));
	            ArrayList<String> args = new ArrayList<>();
	            
	            //convert the args to a ArrayList<String> with first arg as index 0 and so on...
	            for(int i = 0; i < new String(Base64.getDecoder().decode(command_json.get("args").getAsString())).split(" ").length; i++){
	            	try {
	            		String arg = new String(Base64.getDecoder().decode(command_json.get("args").getAsString())).split(" ")[i];
	                    args.add(i, arg);
	            	}catch(ArrayIndexOutOfBoundsException ex) {}
	            }
	            
	            // checks if a CommandExecutor with the given command is registered
	            if(commands.keySet().contains(command)) {
	            	//command is registered
	            	commands.get(command).onCommand(args, player);
	            }else {
	            	//command is not registered
		            player.sendMessage(String.format("Command \"%s\" not found!", command), Colors.RED);
	            }
	            
	        }catch(JsonSyntaxException ex){
	        	// Oops! JsonParse error... lets send the player a message :D Why not.
	        	player.sendMessage("Oops! There was an error! :(", Colors.RED);
	            return;
	        }  
		}              
    }
}
