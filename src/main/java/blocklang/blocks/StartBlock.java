package blocklang.blocks;

import static com.raylib.Colors.GREEN;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Rectangle;

/**
 * StartBlock
 */
public class StartBlock extends Block {
    public StartBlock(Float x, Float y) {
        super(BlockType.START, x, y, 180.f, 30.f);
    }

	@Override
	public void draw() {
        Rectangle shape = new Rectangle();
        shape.x(posX);
        shape.y(posY);
        shape.width(width);
        shape.height(height);
        DrawRectangleRounded(shape, 0.6f, 5, GREEN);
	}
}
