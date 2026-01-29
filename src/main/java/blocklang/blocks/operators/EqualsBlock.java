package blocklang.blocks.operators;

import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.ConditionBlock;
import blocklang.blocks.ValueBlock;
import blocklang.blocks.variables.NumberBlock;

/**
 * CompareBlock
 */
public class EqualsBlock extends ConditionBlock {
    private ValueBlock value1;
    private ValueBlock value2;

    public EqualsBlock(Float posX, Float posY) {
        super(posX, posY, 100.f, 30.f);
    }
    public EqualsBlock() {
        this(0.f, 0.f);
    }

    public void setValue1(Integer value) {
        NumberBlock number = new NumberBlock();
        number.setValue(value.floatValue());
        this.value1 = number;
    }
    public void setValue2(Integer value) {
        NumberBlock number = new NumberBlock();
        number.setValue(value.floatValue());
        this.value2 = number;
    }

	@Override
	public Boolean isTrue() {
        return value1.equals(value2);
	}

	@Override
	public void draw() {
        Rectangle shape = new Rectangle();
        shape.x(posX);
        shape.y(posY);
        shape.width(width);
        shape.height(height);
        Color color = new Color();
        color.r((byte) 89);
        color.g((byte) 192);
        color.b((byte) 89);
        color.a((byte) 255);
        DrawRectangleRounded(shape, 0.6f, 5, color);
	}
}
