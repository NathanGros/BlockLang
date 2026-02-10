package blocklang;

import static com.raylib.Raylib.GetScreenToWorld2D;
import static com.raylib.Raylib.GetWorldToScreen2D;
import static com.raylib.Raylib.LoadFontEx;
import static com.raylib.Raylib.UnloadFont;
import static com.raylib.Raylib.Vector2Zero;

import org.bytedeco.javacpp.IntPointer;

import com.raylib.Raylib.Camera2D;
import com.raylib.Raylib.Font;
import com.raylib.Raylib.Vector2;

public class FontUtil {
    private static String fontPath = "src/main/resources/caliban-font/Caliban-m132.ttf";
	private static Font font;
    private static float wantedWorldFontSize = 30.f;
	private static float worldFontSize;

    public static String getFontPath() {
		return fontPath;
	}

	public static void setFontPath(String fontPath) {
		FontUtil.fontPath = fontPath;
	}

    public static void setFont(Font newFont) {
        font = newFont;
    }

    public static Font getFont() {
        return font;
    }

    public static float getWantedWorldFontSize() {
		return wantedWorldFontSize;
	}

	public static void setWantedWorldFontSize(float wantedWorldFontSize) {
		FontUtil.wantedWorldFontSize = wantedWorldFontSize;
	}

    public static void setWorldFontSize(float newWorldFontSize) {
        worldFontSize = newWorldFontSize;
    }

    public static float getWorldFontSize() {
        return worldFontSize;
    }

    public static void reloadFont(Camera2D camera) {
        if (font != null)
            UnloadFont(font);
        Vector2 screenPos1 = GetWorldToScreen2D(Vector2Zero(), camera);
        Vector2 screenPos2 = GetWorldToScreen2D(Vector2Zero().y(wantedWorldFontSize), camera);
        int screenFontSize = (int) (screenPos2.y() - screenPos1.y());
        Vector2 worldPos1 = GetScreenToWorld2D(Vector2Zero(), camera);
        Vector2 worldPos2 = GetScreenToWorld2D(Vector2Zero().y(screenFontSize), camera);
        setWorldFontSize(worldPos2.y() - worldPos1.y());
        setFont(LoadFontEx(fontPath, screenFontSize, (IntPointer) null, 0));
    }
}
