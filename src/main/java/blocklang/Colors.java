package blocklang;

import com.raylib.Raylib.Color;

/**
 * Colors
 */
public class Colors {
    private static final Color controlColor = new Color().r((byte) 255).g((byte) 171).b((byte) 25).a((byte) 255);
    private static final Color controlBorderColor = new Color().r((byte) 205).g((byte) 137).b((byte) 20).a((byte) 255);
    private static final Color eventsColor = new Color().r((byte) 255).g((byte) 191).b((byte) 0).a((byte) 255);
    private static final Color eventsBorderColor = new Color().r((byte) 205).g((byte) 153).b((byte) 0).a((byte) 255);
    private static final Color operatorsColor = new Color().r((byte) 89).g((byte) 192).b((byte) 89).a((byte) 255);
    private static final Color operatorsBorderColor = new Color().r((byte) 71).g((byte) 154).b((byte) 71).a((byte) 255);
	private static final Color whiteTextColor = new Color().r((byte) 255).g((byte) 255).b((byte) 255).a((byte) 255);
    private static final Color blackTextColor = new Color().r((byte) 0).g((byte) 0).b((byte) 0).a((byte) 255);

	public static Color getControlColor() {
		return controlColor;
	}
	public static Color getControlBorderColor() {
		return controlBorderColor;
	}
    public static Color getEventsColor() {
		return eventsColor;
	}
	public static Color getEventsBorderColor() {
		return eventsBorderColor;
	}
	public static Color getOperatorsColor() {
		return operatorsColor;
	}
	public static Color getOperatorsBorderColor() {
		return operatorsBorderColor;
	}
	public static Color getBlockTextColor() {
		return whiteTextColor;
	}
}
