package blocklang;

import static com.raylib.Raylib.BeginDrawing;
import static com.raylib.Raylib.ClearBackground;
import static com.raylib.Raylib.CloseWindow;
import static com.raylib.Raylib.EndDrawing;
import static com.raylib.Raylib.FLAG_MSAA_4X_HINT;
import static com.raylib.Raylib.FLAG_WINDOW_RESIZABLE;
import static com.raylib.Raylib.InitWindow;
import static com.raylib.Raylib.IsMouseButtonPressed;
import static com.raylib.Raylib.IsMouseButtonReleased;
import static com.raylib.Raylib.IsWindowResized;
import static com.raylib.Raylib.MOUSE_BUTTON_LEFT;
import static com.raylib.Raylib.SetConfigFlags;
import static com.raylib.Raylib.SetTargetFPS;
import static com.raylib.Raylib.UnloadFont;
import static com.raylib.Raylib.WindowShouldClose;

public class Main {
    public static void main(String[] args) {
        int screenWidth = 800;
        int screenHeight = 450;

        BlockView blockView = new BlockView();
        blockView.runAll();

        BlockList blockList = new BlockList();

        SetConfigFlags(FLAG_MSAA_4X_HINT);
        SetConfigFlags(FLAG_WINDOW_RESIZABLE);
        InitWindow(screenWidth, screenHeight, "BlockLang");
        SetTargetFPS(60);

        blockView.refreshSize();
        blockList.refreshSize();

        while (!WindowShouldClose()) {
            blockView.updateFontTimer();
            blockView.reloadFontIfNeeded();

            if (IsWindowResized()) {
                blockView.refreshSize();
                blockList.refreshSize();
            }

            blockView.updateZoom();
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                blockView.handleLeftClick();
            }
            if (IsMouseButtonReleased(MOUSE_BUTTON_LEFT)) {
                blockView.handleLeftRelease();
            }
            blockView.updateWhileDragScreenMode();
            blockView.updateWhileDragBlockMode();

            // Draw
            BeginDrawing();
                ClearBackground(Colors.getBackgroundColor());
                blockView.drawAll();
                blockList.drawAll();
            EndDrawing();
        }
        // De-Initialization
        UnloadFont(FontUtil.getFont());
        CloseWindow();
    }
}
