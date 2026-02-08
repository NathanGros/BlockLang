package blocklang.blocks.events;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.DrawTextEx;
import static com.raylib.Raylib.Vector2Zero;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Vector2;

import blocklang.FontUtil;
import blocklang.blocks.BlockType;
import blocklang.blocks.InstructionBlock;

/**
 * StopBlock
 */
public class StopBlock extends InstructionBlock {
    public StopBlock(Float x, Float y) {
        super(BlockType.STOP, x, y, 150.f, 30.f);
    }
    public StopBlock() {
        this(0.f, 0.f);
    }

	@Override
	public void draw() {
        Color borderColor = new Color();
        borderColor.r((byte) 205);
        borderColor.g((byte) 153);
        borderColor.b((byte) 0);
        borderColor.a((byte) 255);
        Color color = new Color();
        color.r((byte) 255);
        color.g((byte) 191);
        color.b((byte) 0);
        color.a((byte) 255);
        Float border = 1.f;
        Float roundness = CORNER_RADIUS / (height / 2.f);
        drawRectangle(getPosX(), getPosY(), width, height, roundness, borderColor);
        drawRectangle(getPosX() + border, getPosY() + border, width - 2.f * border, height - 2.f * border, roundness, color);
        float fontMarginX = (getHeight() - FontUtil.getWorldFontSize()) / 2.f;
        float fontMarginY = (getHeight() - FontUtil.getWorldFontSize()) / 2.f;
        Vector2 textPos = Vector2Zero().x(getPosX() + fontMarginX).y(getPosY() + fontMarginY);
        DrawTextEx(FontUtil.getFont(), "Stop", textPos, FontUtil.getWorldFontSize(), 0, WHITE);
	}
}
