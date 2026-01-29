package blocklang.blocks.variables;

import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.ValueBlock;

/**
 * NumberBlock
 */
public class NumberBlock extends ValueBlock {
    private Float value;

    public NumberBlock(Float x, Float y) {
        super(x, y, 45.f, 28.f);
        value = 0.f;
    }
    public NumberBlock() {
        this(0.f, 0.f);
    }

    public Float getValue() {
        return value;
    }
    public void setValue(Float value) {
        this.value = value;
    }

	@Override
	public void draw() {
        Rectangle shape = new Rectangle();
        shape.x(posX);
        shape.y(posY);
        shape.width(width);
        shape.height(height);
        Color color = new Color();
        color.r((byte) 255);
        color.g((byte) 255);
        color.b((byte) 255);
        color.a((byte) 255);
        DrawRectangleRounded(shape, 1.f, 5, color);
	}

	@Override
	public Boolean equals(ValueBlock otherValue) {
        if (this == otherValue)
            return true;
        if (otherValue == null)
            return false;
        if (otherValue instanceof NumberBlock number) {
            return this.value.equals(number.getValue());
        }
        // TODO other valueblocks
        return false;
	}
}
