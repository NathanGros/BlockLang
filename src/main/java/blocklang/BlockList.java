package blocklang;

import static com.raylib.Raylib.DrawRectangle;
import static com.raylib.Raylib.GetScreenHeight;
import static com.raylib.Raylib.GetScreenWidth;

/**
 * BlockList
 */
public class BlockList {
    private int listX;
    private int listY;
    private int listWidth;
    private int listHeight;

    public BlockList() {
        listX = 0;
        listY = 0;
        listWidth = 0;
        listHeight = 0;
    }

    public void refreshSize() {
        listX = 0;
        listY = 0;
        listWidth = GetScreenWidth() / 5;
        listHeight = GetScreenHeight();
    }

    public void drawAll() {
        DrawRectangle(listX, listY, listWidth, listHeight, Colors.getBlockListBackgroundColor());
    }
}
