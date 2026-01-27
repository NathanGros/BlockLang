package blocklang;

import static com.raylib.Raylib.*;

import static com.raylib.Colors.*;

public class Main {
    public static void main(String[] args) {
        // Initialization
        int screenWidth = 800;
        int screenHeight = 450;

        BlockView blockView = new BlockView();
        BlockRunner.run(blockView.getRoots());

        SetConfigFlags(FLAG_MSAA_4X_HINT);
        InitWindow(screenWidth, screenHeight, "BlockLang");

        Camera2D camera = new Camera2D()
            .offset(new Vector2().x(screenWidth / 2).y(screenHeight / 2))
            .target(Vector2Zero())
            .rotation(0.0f)
            .zoom(1.0f);

        SetTargetFPS(60);

        while (!WindowShouldClose()) {
            // Update
            float dt = GetFrameTime();
            Vector2 cameraPos = camera.offset();
            float cameraSpeed = 5f;
            if (IsKeyDown(KEY_LEFT)) {
                camera.target(Vector2Add(camera.target(), new Vector2().x(-1.f * cameraSpeed * 60.0f * dt).y(0.0f)));
            }
            if (IsKeyDown(KEY_RIGHT)) {
                camera.target(Vector2Add(camera.target(), new Vector2().x(cameraSpeed * 60.0f * dt).y(0.0f)));
            }
            if (IsKeyDown(KEY_UP)) {
                camera.target(Vector2Add(camera.target(), new Vector2().x(0.0f).y(-1.f * cameraSpeed * 60.0f * dt)));
            }
            if (IsKeyDown(KEY_DOWN)) {
                camera.target(Vector2Add(camera.target(), new Vector2().x(0.0f).y(cameraSpeed * 60.0f * dt)));
            }

            // Draw
            BeginDrawing();
                ClearBackground(RAYWHITE);
                BeginMode2D(camera);
                blockView.drawAll();
                EndMode2D();
            EndDrawing();
        }
        // De-Initialization
        CloseWindow();
    }
}
