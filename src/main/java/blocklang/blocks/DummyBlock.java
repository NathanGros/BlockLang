package blocklang.blocks;

import static com.raylib.Colors.YELLOW;
import static com.raylib.Raylib.DrawRectangleRounded;

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
        DrawRectangleRounded(shape, 0.6f, 5, YELLOW);
	}
}
