package game;
/**
 * @author VinceG
 * 
 * Simple list of Colors that can be used and interpreted by the javascript
 *
 */
public enum Color {
	YELLOW("yellow"),
	ORANGE("orange"),
	RED("red"),
	MAGENETA("magenta"),
	VIOLET("violet"),
	BLUE("blue"),
	CYAN("cyan"),
	LIGHTGREEN("light-green"),
	RAINBOW("rainbow"),
	DEFAULT("green");
	
	private final String color;
	
	Color(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public static Color getColor(String color) {
		for(Color c : Color.values()) {
			if(c.getColor().equals(color))
				return c;
		}
		return Color.DEFAULT;
	}
}
