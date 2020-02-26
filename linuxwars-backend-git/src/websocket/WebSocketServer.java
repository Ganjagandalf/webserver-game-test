package websocket;

import game.GameTick;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import websocket.WebSocketHandler;
import websocket.WebSocketSettings;

public class WebSocketServer extends Thread{
	GameTick tick;
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(
                        new HttpServerCodec(),
                        new HttpObjectAggregator(65536),
                        new WebSocketServerCompressionHandler(),
                        new WebSocketServerProtocolHandler("/websocket", null, true),
                        new WebSocketHandler());
                    }
                });
                bootstrap.option(ChannelOption.SO_BACKLOG, 50);
                bootstrap.childOption(ChannelOption.SO_KEEPALIVE, WebSocketSettings.KEEPALIVE);
                ChannelFuture future = bootstrap.bind(WebSocketSettings.PORT).sync();
                System.out.println("Server gestartet!");
                tick = new GameTick();
                tick.start();
                future.channel().closeFuture().sync();
        } catch (Exception ex) {
        	bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}
}
