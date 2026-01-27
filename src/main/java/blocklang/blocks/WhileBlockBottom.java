package blocklang.blocks;

import static com.raylib.Colors.BLUE;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Rectangle;

/**
 * WhileBlockBottom
 */
public class WhileBlockBottom extends Block {
    public WhileBlockBottom(Float x, Float y) {
        super(BlockType.WHILE, x, y, 100.f, 30.f);
    }

	@Override
	public void draw() {
        Rectangle shape = new Rectangle();
        shape.x(posX);
        shape.y(posY);
        shape.width(width);
        shape.height(height);
        DrawRectangleRounded(shape, 0.6f, 5, BLUE);
	}
}
