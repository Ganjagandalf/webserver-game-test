package websocket;

import java.util.Base64;

import game.Colors;
import game.CommandHandler;
import game.Server;
import game.player.Player;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception  {
    	for(Player player : Server.getOnlinePlayers()) {
    		if(ctx.equals(player.getChannelHandlerContext())) {
    			return;
    		}
    	}
    	Server.addPlayer(new Player(ctx));
    }
	
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { 
    	
    }
	
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { 
    	Server.getPlayer(ctx).saveData();
    	Server.getOnlinePlayers().remove(Server.getPlayer(ctx));
    }
	
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        if(obj instanceof WebSocketFrame) {
            WebSocketFrame frame = (WebSocketFrame) obj;
            CommandHandler.callCommand(new TextWebSocketFrame(frame.content()).text(), ctx);
        }
    }
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    	ctx.flush();
    }
    
    public static void channelSendMessage(String message, ChannelHandlerContext ctx) {
    	ctx.writeAndFlush(new TextWebSocketFrame(message));
    }
    
    public static void sendMessage(String message, Colors color, ChannelHandlerContext ctx) {
    	channelSendMessage("{\"type\":\"println\",\"message\":\""+Base64.getEncoder().encodeToString(message.getBytes())+"\",\"color\":\""+color.getColor()+"\"}", ctx);
    }
}
