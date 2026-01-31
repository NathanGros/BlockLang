package blocklang.blocks;

import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

/**
 * ValueBlock
 */
public abstract class ValueBlock extends PositionnedBlock {
    protected static Float BASE_WIDTH = 45.f;
    protected static Float BASE_HEIGHT = 28.f;

    public ValueBlock(Float posX, Float posY, Float width, Float height) {
        super(posX, posY, width, height);
    }
    public ValueBlock(Float posX, Float posY) {
        this(posX, posY, BASE_WIDTH, BASE_HEIGHT);
    }
    public ValueBlock() {
        this(0.f, 0.f);
    }

    protected void drawOval(Float posX, Float posY, Float width, Float height, Color color) {
        Rectangle shape = new Rectangle();
        shape.x(posX);
        shape.y(posY);
        shape.width(width);
        shape.height(height);
        DrawRectangleRounded(shape, 1.f, 10, color);
    }

    public abstract Float getFloatValue();

	public Boolean equals(ValueBlock otherValue) {
        if (this == otherValue)
            return true;
        if (otherValue == null)
            return false;
        return this.getFloatValue().equals(otherValue.getFloatValue());
	}
}
