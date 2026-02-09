package blocklang.blocks.operators;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.CheckCollisionPointRec;
import static com.raylib.Raylib.DrawTextEx;
import static com.raylib.Raylib.MeasureTextEx;
import static com.raylib.Raylib.Vector2Zero;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import blocklang.FontUtil;
import blocklang.blocks.BooleanBlock;
import blocklang.blocks.BooleanSlot;
import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;

/**
 * NotBlock
 */
public class NotBlock extends BooleanBlock {
    private BooleanSlot booleanSlot;
    private static final String text = "not";
    private static Float LEFT_MARGIN = 40.f;

    public NotBlock(Float posX, Float posY) {
        super(posX, posY, BASE_WIDTH, BASE_HEIGHT);
        booleanSlot = new BooleanSlot();
    }
    public NotBlock() {
        this(0.f, 0.f);
    }

    @Override
    public void setPosX(Float posX) {
        super.setPosX(posX);
    }
    @Override
    public void setPosY(Float posY) {
        super.setPosY(posY);
    }
    public void setBooleanBlock(BooleanBlock booleanBlock) {
        this.booleanSlot.setChild(booleanBlock);
    }

    private void positionBooleanSlot() {
        Float marginY = 3.f;
        Float marginX = marginY * (float) Math.sqrt(2);
        booleanSlot.positionWithChildren(new Position(getPosX() + marginX + LEFT_MARGIN, getPosY() + marginY));
        this.height = 2.f * marginY + booleanSlot.getHeight();
        this.width = LEFT_MARGIN + 2.f * marginX + booleanSlot.getWidth();
    }

	@Override
	public Boolean isTrue() {
        return booleanSlot.isTrue();
	}

    private void drawText() {
        Vector2 textSize = MeasureTextEx(FontUtil.getFont(), text, FontUtil.getWorldFontSize(), 0);
        textSize.y(textSize.y() * 3.f / 4.f);
        float textMarginX = BASE_HEIGHT / 2.f;
        float textMarginY = (getHeight() - textSize.y()) / 2.f;
        Vector2 textPos = Vector2Zero().x(getPosX() + textMarginX).y(getPosY() + textMarginY);
        DrawTextEx(FontUtil.getFont(), text, textPos, FontUtil.getWorldFontSize(), 0, WHITE);
    }

	@Override
	public void draw() {
        Color borderColor = new Color();
        borderColor.r((byte) 71);
        borderColor.g((byte) 154);
        borderColor.b((byte) 71);
        borderColor.a((byte) 255);
        Color color = new Color();
        color.r((byte) 89);
        color.g((byte) 192);
        color.b((byte) 89);
        color.a((byte) 255);
        Float borderY = 1.f;
        Float borderX = borderY * (float) Math.sqrt(2);
        drawHexagon(getPosX(), getPosY(), width, height, borderColor);
        drawHexagon(getPosX() + borderX, getPosY() + borderY, width - 2.f * borderX, height - 2.f * borderY, color);
        drawText();
	}

    @Override
    public Position positionWithChildren(Position pos) {
        setPos(pos);
        positionBooleanSlot();
        return new Position(pos);
    }

    @Override
    public void drawWithChildren() {
        draw();
        booleanSlot.drawWithChildren();
    }

    public PositionnedBlock selectWithChildren(Vector2 mousePos) {
        Rectangle shape = new Rectangle();
        shape.x(getPosX());
        shape.y(getPosY());
        shape.width(width);
        shape.height(height);
        if (CheckCollisionPointRec(mousePos, shape)) {
            PositionnedBlock selected = booleanSlot.selectWithChildren(mousePos);
            if (selected != null) {
                return selected;
            }
            return this;
        }
        return null;
    }

    @Override
    public Boolean insertWithChildren(PositionnedBlock selectedBlock, Vector2 mousePos) {
        if (booleanSlot.insertWithChildren(selectedBlock, mousePos))
            return true;
        return false;
    }
}
