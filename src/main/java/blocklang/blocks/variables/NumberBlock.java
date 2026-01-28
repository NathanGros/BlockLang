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

	public NumberBlock(Float posX, Float posY, Float width, Float height) {
		super(posX, posY, width, height);
        value = 0.f;
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
        color.r((byte) 76);
        color.g((byte) 151);
        color.b((byte) 255);
        color.a((byte) 255);
        DrawRectangleRounded(shape, 1.f, 5, color);
	}
}
