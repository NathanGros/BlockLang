package blocklang.blocks.control;

import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.Block;
import blocklang.blocks.BlockType;

/**
 * WhileBlockBottom
 */
public class WhileBlockBottom extends Block {
    public WhileBlockBottom(Float x, Float y) {
        super(BlockType.CLOSING_BLOCK, x, y, 100.f, 20.f);
    }
    public WhileBlockBottom() {
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
        color.r((byte) 255);
        color.g((byte) 171);
        color.b((byte) 25);
        color.a((byte) 255);
        DrawRectangleRounded(shape, 0.9f, 5, color);
	}
}
