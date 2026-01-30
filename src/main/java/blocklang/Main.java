package blocklang;

import static com.raylib.Raylib.*;

import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;

import static com.raylib.Colors.*;

public class Main {
    public static void main(String[] args) {
        // Initialization
        int screenWidth = 800;
        int screenHeight = 450;

        BlockView blockView = new BlockView();
        blockView.positionAll();
        blockView.runAll();

        Boolean dragScreenMode = false;
        Boolean moveSelectedBlockMode = false;
        PositionnedBlock selectedBlock = null;
        Vector2 mousePosition = GetMousePosition();

        SetConfigFlags(FLAG_MSAA_4X_HINT);
        SetConfigFlags(FLAG_WINDOW_RESIZABLE);
        InitWindow(screenWidth, screenHeight, "BlockLang");

        Camera2D camera = new Camera2D()
            .offset(new Vector2().x(screenWidth / 2).y(screenHeight / 2))
            .target(Vector2Zero())
            .rotation(0.0f)
            .zoom(1.0f);


        SetTargetFPS(60);

        while (!WindowShouldClose()) {
            if (IsWindowResized()) {
                screenWidth = GetScreenWidth();
                screenHeight = GetScreenHeight();
                camera.offset(new Vector2().x(screenWidth / 2).y(screenHeight / 2));
            }

            // Update
            float dt = GetFrameTime();
            Vector2 cameraPos = camera.offset();
            Float mouseWheelMovementY = GetMouseWheelMoveV().y();
            if (mouseWheelMovementY != 0.f) {
                if (mouseWheelMovementY > 0)
                    camera.zoom(camera.zoom() * 1.3f);
                else
                    camera.zoom(camera.zoom() / 1.3f);
            }
            if (camera.zoom() < 0.3f)
                camera.zoom(0.3f);
            if (camera.zoom() > 3.f)
                camera.zoom(3.f);
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                selectedBlock = blockView.selectBlock(GetScreenToWorld2D(GetMousePosition(), camera));
                if (selectedBlock == null) {
                    dragScreenMode = true;
                    mousePosition = GetMousePosition();
                } else {
                    moveSelectedBlockMode = true;
                    mousePosition = GetMousePosition();
                }
            }
            if (IsMouseButtonReleased(MOUSE_BUTTON_LEFT)) {
                if (dragScreenMode) {
                    dragScreenMode = false;
                }
                if (moveSelectedBlockMode) {
                    moveSelectedBlockMode = false;
                    selectedBlock = null;
                }
            }
            if (dragScreenMode) {
                Vector2 newMousePosition = GetMousePosition();
                Vector2 oldMouseWorldPosition = GetScreenToWorld2D(mousePosition, camera);
                Vector2 newMouseWorldPosition = GetScreenToWorld2D(newMousePosition, camera);
                camera.target(Vector2Add(Vector2Subtract(camera.target(), newMouseWorldPosition), oldMouseWorldPosition));
                mousePosition = newMousePosition;
            }
            if (moveSelectedBlockMode) {
                Vector2 newMousePosition = GetMousePosition();
                Vector2 oldMouseWorldPosition = GetScreenToWorld2D(mousePosition, camera);
                Vector2 newMouseWorldPosition = GetScreenToWorld2D(newMousePosition, camera);
                Vector2 moveVector = Vector2Subtract(newMouseWorldPosition, oldMouseWorldPosition);
                Vector2 oldBlockPosition = new Vector2();
                oldBlockPosition.x(selectedBlock.getPosX());
                oldBlockPosition.y(selectedBlock.getPosY());
                Vector2 newBlockPosition = Vector2Add(oldBlockPosition, moveVector);
                selectedBlock.positionWithChildren(new Position(newBlockPosition.x(), newBlockPosition.y()));
                mousePosition = newMousePosition;
            }

            // Draw
            BeginDrawing();
                ClearBackground(RAYWHITE);
                BeginMode2D(camera);
                blockView.drawAll();
                if (selectedBlock != null) {
                    selectedBlock.drawWithChildren();
                }
                EndMode2D();
            EndDrawing();
        }
        // De-Initialization
        CloseWindow();
    }
}
