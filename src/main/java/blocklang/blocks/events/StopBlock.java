package blocklang.blocks.events;

import static com.raylib.Raylib.DrawTextEx;
import static com.raylib.Raylib.MeasureTextEx;
import static com.raylib.Raylib.Vector2Zero;

import com.raylib.Raylib.Vector2;

import blocklang.Colors;
import blocklang.FontUtil;
import blocklang.blocks.BlockType;
import blocklang.blocks.InstructionBlock;

/**
 * StopBlock
 */
public class StopBlock extends InstructionBlock {
    private static final String text = "Stop";

    public StopBlock(Float x, Float y) {
        super(BlockType.STOP, x, y, 150.f, 30.f);
    }
    public StopBlock() {
        this(0.f, 0.f);
    }

    private void drawText() {
        Vector2 textSize = MeasureTextEx(FontUtil.getFont(), text, FontUtil.getWorldFontSize(), 0);
        textSize.y(textSize.y() * 3.f / 4.f);
        float textMarginX = BASE_HEIGHT / 2.f;
        float textMarginY = (getHeight() - textSize.y()) / 2.f;
        Vector2 textPos = Vector2Zero().x(getPosX() + textMarginX).y(getPosY() + textMarginY);
        DrawTextEx(FontUtil.getFont(), text, textPos, FontUtil.getWorldFontSize(), 0, Colors.getBlockTextColor());
    }

	@Override
	public void draw() {
        Float border = 1.f;
        Float roundness = CORNER_RADIUS / (height / 2.f);
        drawRectangle(getPosX(), getPosY(), width, height, roundness, Colors.getEventsBorderColor());
        drawRectangle(getPosX() + border, getPosY() + border, width - 2.f * border, height - 2.f * border, roundness, Colors.getEventsColor());
        drawText();
	}
}
