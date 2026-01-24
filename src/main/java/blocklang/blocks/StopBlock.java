package blocklang.blocks;

import static com.raylib.Colors.RED;
import static com.raylib.Raylib.DrawRectangle;

/**
 * StopBlock
 */
public class StopBlock extends Block {
    private Block previousBlock;

    public StopBlock(Float x, Float y) {
        super(BlockType.STOP, x, y, 200.f, 20.f);
    }

	@Override
	public void draw() {
        DrawRectangle(posX.intValue(), posY.intValue(), width.intValue(), height.intValue(), RED);
	}
}
