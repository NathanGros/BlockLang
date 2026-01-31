package blocklang.blocks;

import static com.raylib.Raylib.DrawPoly;
import static com.raylib.Raylib.DrawRectangleRec;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

/**
 * BooleanBlock
 */
public abstract class BooleanBlock extends PositionnedBlock {
    protected static Float BASE_WIDTH = 45.f;
    protected static Float BASE_HEIGHT = 28.f;

    public BooleanBlock(Float posX, Float posY, Float width, Float height) {
        super(posX, posY, width, height);
    }
    public BooleanBlock(Float posX, Float posY) {
        this(posX, posY, BASE_WIDTH, BASE_HEIGHT);
    }
    public BooleanBlock() {
        this(0.f, 0.f);
    }

    protected void drawHexagon(Float posX, Float posY, Float width, Float height, Color color) {
        Float halfHeight = height / 2.f;
        Rectangle center = new Rectangle();
        center.x(posX + halfHeight);
        center.y(posY);
        center.width(width - height);
        center.height(height);
        Vector2 leftCenter = new Vector2();
        leftCenter.x(posX + halfHeight);
        leftCenter.y(posY + halfHeight);
        Vector2 rightCenter = new Vector2();
        rightCenter.x(posX + width - halfHeight);
        rightCenter.y(posY + halfHeight);
        Float radius = halfHeight;
        
        DrawRectangleRec(center, color);
        DrawPoly(leftCenter, 4, radius, 0.f, color);
        DrawPoly(rightCenter, 4, radius, 0.f, color);
    }

    public abstract Boolean isTrue();
}
