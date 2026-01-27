package blocklang.blocks.events;

import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.Block;
import blocklang.blocks.BlockType;

/**
 * StartBlock
 */
public class StartBlock extends Block {
    public StartBlock(Float x, Float y) {
        super(BlockType.START, x, y, 180.f, 30.f);
    }
    public StartBlock() {
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
        color.g((byte) 191);
        color.b((byte) 0);
        color.a((byte) 255);
        DrawRectangleRounded(shape, 0.6f, 5, color);
	}
}
