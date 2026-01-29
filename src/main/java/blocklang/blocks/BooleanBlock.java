package blocklang.blocks;

import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawTriangle;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

/**
 * BooleanBlock
 */
public class BooleanBlock {
    protected Float posX;
    protected Float posY;
    protected Float width;
    protected Float height;

    public BooleanBlock(Float posX, Float posY, Float width, Float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
    public BooleanBlock(Float posX, Float posY) {
        this(posX, posY, 45.f, 28.f);
    }
    public BooleanBlock() {
        this(0.f, 0.f);
    }

    public void setPosX(Float posX) {
        this.posX = posX;
    }
    public Float getPosX() {
        return posX;
    }
    public void setPosY(Float posY) {
        this.posY = posY;
    }
    public Float getPosY() {
        return posY;
    }
    public Float getWidth() {
        return width;
    }
    public Float getHeight() {
        return height;
    }

    public Boolean isTrue() {
        return false;
    }

    public void draw() {
        Float halfHeight = height / 2.f;
        Rectangle center = new Rectangle();
        center.x(posX + halfHeight);
        center.y(posY);
        center.width(width - height);
        center.height(height);

        Vector2 leftTopVertex = new Vector2();
        leftTopVertex.x(posX + halfHeight);
        leftTopVertex.y(posY);
        Vector2 leftCenterVertex = new Vector2();
        leftCenterVertex.x(posX);
        leftCenterVertex.y(posY + halfHeight);
        Vector2 leftBottomVertex = new Vector2();
        leftBottomVertex.x(posX + halfHeight);
        leftBottomVertex.y(posY + height);

        Vector2 rightTopVertex = new Vector2();
        rightTopVertex.x(posX + width - halfHeight);
        rightTopVertex.y(posY);
        Vector2 rightCenterVertex = new Vector2();
        rightCenterVertex.x(posX + width);
        rightCenterVertex.y(posY + halfHeight);
        Vector2 rightBottomVertex = new Vector2();
        rightBottomVertex.x(posX + width - halfHeight);
        rightBottomVertex.y(posY + height);

        Color color = new Color();
        color.r((byte) 0);
        color.g((byte) 0);
        color.b((byte) 0);
        color.a((byte) 50);
        DrawRectangleRec(center, color);
        DrawTriangle(leftTopVertex, leftCenterVertex, leftBottomVertex, color);
        DrawTriangle(rightBottomVertex, rightCenterVertex, rightTopVertex, color);
    }
}
