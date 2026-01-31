package blocklang.blocks.operators;

import static com.raylib.Raylib.CheckCollisionPointRec;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;
import blocklang.blocks.ValueBlock;
import blocklang.blocks.ValueSlot;

/**
 * AddBlock
 */
public class AddBlock extends ValueBlock {
    private ValueBlock value1;
    private ValueBlock value2;
    private static Float CENTER_WIDTH = 20.f;

    public AddBlock(Float posX, Float posY) {
        super(posX, posY, BASE_WIDTH, BASE_HEIGHT);
        value1 = new ValueSlot();
        value2 = new ValueSlot();
    }
    public AddBlock() {
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
        value1.positionWithChildren(new Position(
            getPosX(),
            getPosY()
        ));
        value2.positionWithChildren(new Position(
            getPosX(),
            getPosY()
        ));
        Float valuesHeight = Math.max(value1.getHeight(), value2.getHeight());
        Float margin = 3.f;
        this.height = 2.f * margin + valuesHeight;
        value1.positionWithChildren(new Position(
            getPosX() + margin,
            getPosY() + margin + (valuesHeight - value1.getHeight()) / 2.f
        ));
        value2.positionWithChildren(new Position(
            value1.getPosX() + value1.getWidth() + CENTER_WIDTH + 2.f * margin,
            getPosY() + margin + (valuesHeight - value2.getHeight()) / 2.f
        ));
        this.width = CENTER_WIDTH + 4.f * margin + value1.getWidth() + value2.getWidth();
    }

	@Override
	public Float getFloatValue() {
        return value1.getFloatValue() + value2.getFloatValue();
	}

	@Override
	public void draw() {
        Color borderColor = new Color();
        borderColor.r((byte) 71);
        borderColor.g((byte) 154);
        borderColor.b((byte) 71);
        borderColor.a((byte) 255);
        Color color = new Color();
        color.r((byte) 89);
        color.g((byte) 192);
        color.b((byte) 89);
        color.a((byte) 255);
        Float borderY = 1.f;
        Float borderX = borderY * (float) Math.sqrt(2);
        drawOval(getPosX(), getPosY(), width, height, borderColor);
        drawOval(getPosX() + borderX, getPosY() + borderY, width - 2.f * borderX, height - 2.f * borderY, color);
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
