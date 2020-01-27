package game;
/**
 * @author VinceG
 * 
 * Simple list of Colors that can be used and interpreted by the javascript
 *
 */
public enum Colors {
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
	
	Colors(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public Colors getColor(String color) {
		for(Colors c : Colors.values()) {
			if(c.getColor().equals(color))
				return c;
		}
		return Colors.DEFAULT;
	}
}
