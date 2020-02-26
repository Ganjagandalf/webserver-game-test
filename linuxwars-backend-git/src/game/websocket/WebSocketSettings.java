package game.websocket;

import java.nio.charset.Charset;

public class WebSocketSettings {
	public static final int PORT = 8080;
	public static final boolean KEEPALIVE = true;
	public static final int MAX_LINE_LENGTH = 1024;
	public static final Charset CHARSET = Charset.forName("UTF-8"); // (b)
}
