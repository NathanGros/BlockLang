package blocklang.blocks;

import com.raylib.Raylib.Color;

/**
 * DummyBlock
 */
public class DummyBlock extends InstructionBlock {
    public DummyBlock(Float x, Float y) {
        super(BlockType.DUMMY, x, y, 200.f, 30.f);
    }
    public DummyBlock() {
        this(0.f, 0.f);
    }

	@Override
	public void draw() {
        Color borderColor = new Color();
        borderColor.r((byte) 61);
        borderColor.g((byte) 121);
        borderColor.b((byte) 205);
        borderColor.a((byte) 255);
        Color color = new Color();
        color.r((byte) 76);
        color.g((byte) 151);
        color.b((byte) 255);
        color.a((byte) 255);
        Float border = 1.f;
        Float roundness = CORNER_RADIUS / (height / 2.f);
        drawRectangle(getPosX(), getPosY(), width, height, roundness, borderColor);
        drawRectangle(getPosX() + border, getPosY() + border, width - 2.f * border, height - 2.f * border, roundness, color);
	}
}
