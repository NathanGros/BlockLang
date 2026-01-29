package blocklang.blocks;

import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

/**
 * DummyBlock
 */
public class DummyBlock extends Block {
    public DummyBlock(Float x, Float y) {
        super(BlockType.DUMMY, x, y, 200.f, 30.f);
    }
    public DummyBlock() {
        this(0.f, 0.f);
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
        DrawRectangleRounded(shape, CORNER_RADIUS / (height / 2.f), 5, color);
	}
}
