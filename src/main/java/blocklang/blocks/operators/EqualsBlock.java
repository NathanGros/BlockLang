package blocklang.blocks.operators;

import static com.raylib.Raylib.CheckCollisionPointRec;
import static com.raylib.Raylib.DrawPoly;
import static com.raylib.Raylib.DrawRectangleRec;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import blocklang.blocks.BooleanBlock;
import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;
import blocklang.blocks.ValueBlock;
import blocklang.blocks.ValueSlot;

/**
 * CompareBlock
 */
public class EqualsBlock extends BooleanBlock {
    private ValueBlock value1;
    private ValueBlock value2;
    private static Float CENTER_WIDTH = 20.f;

    public EqualsBlock(Float posX, Float posY) {
        super(posX, posY, 100.f, 40.f);
        value1 = new ValueSlot();
        value2 = new ValueSlot();
    }
    public EqualsBlock() {
        this(0.f, 0.f);
    }

    @Override
    public void setPosX(Float posX) {
        super.setPosX(posX);
    }
    @Override
    public void setPosY(Float posY) {
        super.setPosY(posY);
        positionValueBlocks();
    }
    public void setValue1(Integer value) {
        ValueSlot valueSlot = new ValueSlot();
        valueSlot.setValue(value.floatValue());
        this.value1 = valueSlot;
    }
    public void setValue2(Integer value) {
        ValueSlot valueSlot = new ValueSlot();
        valueSlot.setValue(value.floatValue());
        this.value2 = valueSlot;
    }

    private void positionValueBlocks() {
        Float margin = 3.f;
        Float valuesHeight = Math.max(value1.getHeight(), value2.getHeight());
        this.height = 2.f * margin + valuesHeight;
        this.width = CENTER_WIDTH + 4.f * margin + height + value1.getWidth() + value2.getWidth();
        value1.setPos(new Position(
            getPosX() + height / 2.f + margin,
            getPosY() + margin + (valuesHeight - value1.getHeight()) / 2.f
        ));
        value2.setPos(new Position(
            value1.getPosX() + value1.getWidth() + CENTER_WIDTH + 2.f * margin,
            getPosY() + margin + (valuesHeight - value2.getHeight()) / 2.f
        ));
    }

	@Override
	public Boolean isTrue() {
        return value1.equals(value2);
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
        positionValueBlocks();
        return new Position(pos);
    }

    @Override
    public void drawWithChildren() {
        draw();
        value1.drawWithChildren();
        value2.drawWithChildren();
    }

    public PositionnedBlock selectWithChildren(Vector2 mousePos) {
        Rectangle shape = new Rectangle();
        shape.x(getPosX());
        shape.y(getPosY());
        shape.width(width);
        shape.height(height);
        if (CheckCollisionPointRec(mousePos, shape)) {
            PositionnedBlock selected1 = value1.selectWithChildren(mousePos);
            if (selected1 != null) {
                if (selected1 == value1)
                    value1 = null;
                return selected1;
            }
            PositionnedBlock selected2 = value2.selectWithChildren(mousePos);
            if (selected2 != null) {
                if (selected2 == value2)
                    value2 = null;
                return selected2;
            }
            return this;
        }
        return null;
    }

    @Override
    public Boolean insertWithChildren(PositionnedBlock selectedBlock, Vector2 mousePos) {
        if (value1.insertWithChildren(selectedBlock, mousePos))
            return true;
        if (value2.insertWithChildren(selectedBlock, mousePos))
            return true;
        return false;
    }
}
