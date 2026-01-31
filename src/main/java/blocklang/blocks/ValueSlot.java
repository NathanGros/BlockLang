package blocklang.blocks;

import static com.raylib.Raylib.CheckCollisionPointRec;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

/**
 * ValueSlot
 */
public class ValueSlot extends ValueBlock {
    private Float value;
    private ValueBlock child;

    public ValueSlot(Float x, Float y) {
        super(x, y, BASE_WIDTH, BASE_HEIGHT);
        value = 0.f;
    }
    public ValueSlot() {
        this(0.f, 0.f);
    }

    public void setChild(ValueBlock child) {
        this.child = child;
    }

    public Boolean hasChild() {
        return child != null;
    }

    @Override
    public Float getFloatValue() {
        if (hasChild())
            return child.getFloatValue();
        return value;
    }
    public void setValue(Float value) {
        this.value = value;
    }

    private void positionChild() {
        child.positionWithChildren(new Position(getPosX(), getPosY()));
        this.height = child.getHeight();
        this.width = child.getWidth();
    }

	@Override
	public void draw() {
        Rectangle borderShape = new Rectangle();
        borderShape.x(getPosX());
        borderShape.y(getPosY());
        borderShape.width(width);
        borderShape.height(height);
        Color borderColor = new Color();
        borderColor.r((byte) 205);
        borderColor.g((byte) 205);
        borderColor.b((byte) 205);
        borderColor.a((byte) 255);

        Rectangle shape = new Rectangle();
        shape.x(getPosX() + 1.f);
        shape.y(getPosY() + 1.f);
        shape.width(width - 2.f);
        shape.height(height - 2.f);
        Color color = new Color();
        color.r((byte) 255);
        color.g((byte) 255);
        color.b((byte) 255);
        color.a((byte) 255);

        DrawRectangleRounded(borderShape, 1.f, 10, borderColor);
        DrawRectangleRounded(shape, 1.f, 10, color);
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
                    return selected;
                }
            }
            return null;
        }
        return null;
    }

	@Override
	public Boolean insertWithChildren(PositionnedBlock selectedBlock, Vector2 mousePos) {
        if (hasChild())
            return child.insertWithChildren(selectedBlock, mousePos);
        if (selectedBlock instanceof ValueBlock selectedValueBlock) {
            Rectangle shape = new Rectangle();
            shape.x(getPosX());
            shape.y(getPosY());
            shape.width(width);
            shape.height(height);
            if (CheckCollisionPointRec(mousePos, shape)) {
                child = selectedValueBlock;
                return true;
            }
            return false;
        }
        return false;
	}

	@Override
	public Boolean equals(ValueBlock otherValue) {
        if (this == otherValue)
            return true;
        if (otherValue == null)
            return false;
        if (otherValue instanceof ValueSlot valueSlot) {
            return this.value.equals(valueSlot.getFloatValue());
        }
        // TODO other valueblocks
        return false;
	}
}
