package blocklang.blocks;

import static com.raylib.Raylib.CheckCollisionPointRec;
import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawTriangle;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

/**
 * BooleanSlot
 */
public class BooleanSlot extends BooleanBlock {
    private BooleanBlock child;

    public BooleanSlot(Float x, Float y) {
        super(x, y, BASE_WIDTH, BASE_HEIGHT);
        child = null;
    }
    public BooleanSlot() {
        this(0.f, 0.f);
    }

    public void setChild(BooleanBlock child) {
        this.child = child;
    }

    public Boolean hasChild() {
        return child != null;
    }

    private void positionChild() {
        child.positionWithChildren(new Position(getPosX(), getPosY()));
        this.height = child.getHeight();
        this.width = child.getWidth();
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

    @Override
    public Position positionWithChildren(Position pos) {
        setPos(pos);
        if (hasChild())
            positionChild();
        return new Position(pos);
    }

    @Override
    public void drawWithChildren() {
        this.draw();
        if (hasChild())
            child.drawWithChildren();
    }

    @Override
    public PositionnedBlock selectWithChildren(Vector2 mousePos) {
        if (!hasChild())
            return null;
        Rectangle shape = new Rectangle();
        shape.x(getPosX());
        shape.y(getPosY());
        shape.width(width);
        shape.height(height);
        if (CheckCollisionPointRec(mousePos, shape)) {
            PositionnedBlock selected = child.selectWithChildren(mousePos);
            if (selected != null) {
                if (selected == child) {
                    child = null;
                    width = BASE_WIDTH;
                    height = BASE_HEIGHT;
                }
                return selected;
            }
            return null;
        }
        return null;
    }

	@Override
	public Boolean insertWithChildren(PositionnedBlock selectedBlock, Vector2 mousePos) {
        if (hasChild())
            return child.insertWithChildren(selectedBlock, mousePos);
        if (selectedBlock instanceof BooleanBlock selectedBooleanBlock) {
            Rectangle shape = new Rectangle();
            shape.x(getPosX());
            shape.y(getPosY());
            shape.width(width);
            shape.height(height);
            if (CheckCollisionPointRec(mousePos, shape)) {
                child = selectedBooleanBlock;
                return true;
            }
            return false;
        }
        return false;
	}

    @Override
    public Boolean isTrue() {
        if (hasChild())
            return child.isTrue();
        return false;
    }
}
