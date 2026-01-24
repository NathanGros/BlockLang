package blocklang.blocks;

import static com.raylib.Colors.GREEN;
import static com.raylib.Raylib.DrawRectangle;

/**
 * StartBlock
 */
public class StartBlock extends Block {
    private Block nextBlock;

    public StartBlock(Float x, Float y) {
        super(BlockType.START, x, y, 200.f, 20.f);
    }

	@Override
	public void draw() {
        DrawRectangle(posX.intValue(), posY.intValue(), width.intValue(), height.intValue(), GREEN);
	}
}
