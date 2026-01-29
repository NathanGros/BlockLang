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
    protected Position pos;
    protected Float width;
    protected Float height;

    public BooleanBlock(Float posX, Float posY, Float width, Float height) {
        this.pos = new Position(posX, posY);
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
        this.pos.setPosX(posX);
    }
    public Float getPosX() {
        return pos.getPosX();
    }
    public void setPosY(Float posY) {
        this.pos.setPosY(posY);
    }
    public Float getPosY() {
        return pos.getPosY();
    }
    public void setPos(Float posX, Float posY) {
        pos = new Position(posX, posY);
    }
    public void setPos(Position pos) {
        this.pos = new Position(pos.getPosX(), pos.getPosY());
    }
    public Position getPos() {
        return new Position(pos.getPosX(), pos.getPosY());
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
        center.x(getPosX() + halfHeight);
        center.y(getPosY());
        center.width(width - height);
        center.height(height);

        Vector2 leftTopVertex = new Vector2();
        leftTopVertex.x(getPosX() + halfHeight);
        leftTopVertex.y(getPosY());
        Vector2 leftCenterVertex = new Vector2();
        leftCenterVertex.x(getPosX());
        leftCenterVertex.y(getPosY() + halfHeight);
        Vector2 leftBottomVertex = new Vector2();
        leftBottomVertex.x(getPosX() + halfHeight);
        leftBottomVertex.y(getPosY() + height);

        Vector2 rightTopVertex = new Vector2();
        rightTopVertex.x(getPosX() + width - halfHeight);
        rightTopVertex.y(getPosY());
        Vector2 rightCenterVertex = new Vector2();
        rightCenterVertex.x(getPosX() + width);
        rightCenterVertex.y(getPosY() + halfHeight);
        Vector2 rightBottomVertex = new Vector2();
        rightBottomVertex.x(getPosX() + width - halfHeight);
        rightBottomVertex.y(getPosY() + height);

        Color color = new Color();
        color.r((byte) 0);
        color.g((byte) 0);
        color.b((byte) 0);
        color.a((byte) 50);
        DrawRectangleRec(center, color);
        DrawTriangle(leftTopVertex, leftCenterVertex, leftBottomVertex, color);
        DrawTriangle(rightBottomVertex, rightCenterVertex, rightTopVertex, color);
    }

    public void positionWithChildren(Position pos) {
        setPos(pos);
    }

    public void drawWithChildren() {
        this.draw();
    }
}
