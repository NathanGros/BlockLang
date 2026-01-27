package blocklang.blocks;

import static com.raylib.Colors.RED;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Rectangle;

/**
 * StopBlock
 */
public class StopBlock extends Block {
    public StopBlock(Float x, Float y) {
        super(BlockType.STOP, x, y, 150.f, 30.f);
    }
    public StopBlock() {
        this(0.f, 0.f);
    }

	@Override
	public void draw() {
        Rectangle shape = new Rectangle();
        shape.x(posX);
        shape.y(posY);
        shape.width(width);
        shape.height(height);
        DrawRectangleRounded(shape, 0.6f, 5, RED);
	}
}
