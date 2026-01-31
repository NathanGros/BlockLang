package blocklang.blocks.operators;

import static com.raylib.Raylib.CheckCollisionPointRec;
import static com.raylib.Raylib.DrawPoly;
import static com.raylib.Raylib.DrawRectangleRec;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import blocklang.blocks.BooleanBlock;
import blocklang.blocks.BooleanSlot;
import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;

/**
 * NotBlock
 */
public class NotBlock extends BooleanBlock {
    private BooleanSlot booleanSlot;
    private static Float LEFT_MARGIN = 40.f;

    public NotBlock(Float posX, Float posY) {
        super(posX, posY, BASE_WIDTH, BASE_HEIGHT);
        booleanSlot = new BooleanSlot();
    }
    public NotBlock() {
        this(0.f, 0.f);
    }

    @Override
    public void setPosX(Float posX) {
        super.setPosX(posX);
    }
    @Override
    public void setPosY(Float posY) {
        super.setPosY(posY);
    }
    public void setBooleanBlock(BooleanBlock booleanBlock) {
        this.booleanSlot.setChild(booleanBlock);
    }

    private void positionBooleanSlot() {
        Float marginY = 3.f;
        Float marginX = marginY * (float) Math.sqrt(2);
        booleanSlot.positionWithChildren(new Position(getPosX() + marginX + LEFT_MARGIN, getPosY() + marginY));
        this.height = 2.f * marginY + booleanSlot.getHeight();
        this.width = LEFT_MARGIN + 2.f * marginX + booleanSlot.getWidth();
    }

	@Override
	public Boolean isTrue() {
        return booleanSlot.isTrue();
	}

	@Override
	public void draw() {
        Rectangle center = new Rectangle();
        Float halfHeight = height / 2.f;
        center.x(getPosX() + halfHeight);
        center.y(getPosY());
        center.width(width - height);
        center.height(height);
        Vector2 leftCenter = new Vector2();
        leftCenter.x(getPosX() + halfHeight);
        leftCenter.y(getPosY() + halfHeight);
        Vector2 rightCenter = new Vector2();
        rightCenter.x(getPosX() + width - halfHeight);
        rightCenter.y(getPosY() + halfHeight);
        Float radius = halfHeight;
        Color color = new Color();
        color.r((byte) 89);
        color.g((byte) 192);
        color.b((byte) 89);
        color.a((byte) 255);
        DrawRectangleRec(center, color);
        DrawPoly(leftCenter, 4, radius, 0.f, color);
        DrawPoly(rightCenter, 4, radius, 0.f, color);
	}

    @Override
    public Position positionWithChildren(Position pos) {
        setPos(pos);
        positionBooleanSlot();
        return new Position(pos);
    }

    @Override
    public void drawWithChildren() {
        draw();
        booleanSlot.drawWithChildren();
    }

    public PositionnedBlock selectWithChildren(Vector2 mousePos) {
        Rectangle shape = new Rectangle();
        shape.x(getPosX());
        shape.y(getPosY());
        shape.width(width);
        shape.height(height);
        if (CheckCollisionPointRec(mousePos, shape)) {
            PositionnedBlock selected = booleanSlot.selectWithChildren(mousePos);
            if (selected != null) {
                return selected;
            }
            return this;
        }
        return null;
    }

    @Override
    public Boolean insertWithChildren(PositionnedBlock selectedBlock, Vector2 mousePos) {
        if (booleanSlot.insertWithChildren(selectedBlock, mousePos))
            return true;
        return false;
    }
}
