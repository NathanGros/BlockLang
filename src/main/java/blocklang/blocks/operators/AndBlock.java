package blocklang.blocks.operators;

import static com.raylib.Raylib.CheckCollisionPointRec;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import blocklang.blocks.BooleanBlock;
import blocklang.blocks.BooleanSlot;
import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;

/**
 * AndBlock
 */
public class AndBlock extends BooleanBlock {
    private BooleanSlot booleanSlot1;
    private BooleanSlot booleanSlot2;
    private static Float CENTER_WIDTH = 40.f;

    public AndBlock(Float posX, Float posY) {
        super(posX, posY, BASE_WIDTH, BASE_HEIGHT);
        booleanSlot1 = new BooleanSlot();
        booleanSlot2 = new BooleanSlot();
    }
    public AndBlock() {
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
    public void setBooleanBlock1(BooleanBlock booleanBlock) {
        this.booleanSlot1.setChild(booleanBlock);
    }
    public void setBooleanBlock2(BooleanBlock booleanBlock) {
        this.booleanSlot2.setChild(booleanBlock);
    }

    private void positionBooleanSlots() {
        booleanSlot1.positionWithChildren(new Position(
            getPosX(),
            getPosY()
        ));
        booleanSlot2.positionWithChildren(new Position(
            getPosX(),
            getPosY()
        ));
        Float booleanSlotsHeight = Math.max(booleanSlot1.getHeight(), booleanSlot2.getHeight());
        Float marginY = 3.f;
        Float marginX = marginY * (float) Math.sqrt(2);
        booleanSlot1.positionWithChildren(new Position(
            getPosX() + marginX,
            getPosY() + marginY + (booleanSlotsHeight - booleanSlot1.getHeight()) / 2.f
        ));
        booleanSlot2.positionWithChildren(new Position(
            booleanSlot1.getPosX() + booleanSlot1.getWidth() + 2.f * marginX + CENTER_WIDTH,
            getPosY() + marginY + (booleanSlotsHeight - booleanSlot2.getHeight()) / 2.f
        ));
        this.height = 2.f * marginY + booleanSlotsHeight;
        this.width = CENTER_WIDTH + 4.f * marginX + booleanSlot1.getWidth() + booleanSlot2.getWidth();
    }

	@Override
	public Boolean isTrue() {
        return booleanSlot1.isTrue() && booleanSlot2.isTrue();
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
	}

    @Override
    public Position positionWithChildren(Position pos) {
        setPos(pos);
        positionBooleanSlots();
        return new Position(pos);
    }

    @Override
    public void drawWithChildren() {
        draw();
        booleanSlot1.drawWithChildren();
        booleanSlot2.drawWithChildren();
    }

    public PositionnedBlock selectWithChildren(Vector2 mousePos) {
        Rectangle shape = new Rectangle();
        shape.x(getPosX());
        shape.y(getPosY());
        shape.width(width);
        shape.height(height);
        if (CheckCollisionPointRec(mousePos, shape)) {
            PositionnedBlock selected1 = booleanSlot1.selectWithChildren(mousePos);
            if (selected1 != null) {
                return selected1;
            }
            PositionnedBlock selected2 = booleanSlot2.selectWithChildren(mousePos);
            if (selected2 != null) {
                return selected2;
            }
            return this;
        }
        return null;
    }

    @Override
    public Boolean insertWithChildren(PositionnedBlock selectedBlock, Vector2 mousePos) {
        if (booleanSlot1.insertWithChildren(selectedBlock, mousePos))
            return true;
        if (booleanSlot2.insertWithChildren(selectedBlock, mousePos))
            return true;
        return false;
    }
}
