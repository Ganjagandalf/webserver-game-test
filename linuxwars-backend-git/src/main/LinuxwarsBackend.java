package main;

import java.sql.SQLException;

import database.DBConnection;
import game.CommandHandler;
import game.commands.CommandColors;
import game.commands.CommandCredits;
import game.commands.CommandEcho;
import game.commands.CommandHelp;
import game.commands.CommandLogin;
import game.commands.CommandLogout;
import game.commands.CommandMining;
import game.commands.CommandMotd;
import game.commands.CommandRegister;
import websocket.WebSocketServer;

public class LinuxwarsBackend {
	
	public static DBConnection DB_CON;
	
	public static void main(String[] args) {  
    	
		/*
    	 * Establish database connection
    	 */
    	try {
			DB_CON = new DBConnection("localhost", "3306", "vincent", "3MHUPao9!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    	/*
    	 * Register Commands
    	 */
    	CommandHandler.registerCommand("colors", new CommandColors());
        CommandHandler.registerCommand("echo", new CommandEcho());
        CommandHandler.registerCommand("help", new CommandHelp());
        CommandHandler.registerCommand("login", new CommandLogin());
        CommandHandler.registerCommand("logout", new CommandLogout());
        CommandHandler.registerCommand("motd", new CommandMotd());
        CommandHandler.registerCommand("register", new CommandRegister());
        CommandHandler.registerCommand("credits", new CommandCredits());
        CommandHandler.registerCommand("mining", new CommandMining());
    	
        /*
    	 * Start Webserver
    	 */
        WebSocketServer server = new WebSocketServer();
        server.start();
    }
}
