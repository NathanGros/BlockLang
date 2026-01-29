package blocklang.blocks.control;

import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.Block;
import blocklang.blocks.BlockType;
import blocklang.blocks.BooleanBlock;

/**
 * IfElseBlock
 */
public class IfElseBlock extends Block {
    private BooleanBlock condition;
    private Boolean ran;
    private Block inTrueBlock;
    private Block inFalseBlock;
    private Float middleHeight;
    private Float middleY;
    private Float closeHeight;
    private Float closeY;
    private static Float MARGIN_LEFT = 40.f;
    private static Float MARGIN_RIGHT = 60.f;

    public IfElseBlock(Float x, Float y) {
        super(BlockType.IF_ELSE, x, y, 100.f, 50.f);
        ran = false;
        middleHeight = BASE_HEIGHT * 2.f / 3.f;
        middleY = posY + height;
        closeHeight = BASE_HEIGHT * 2.f / 3.f;
        closeY = posY + height + middleHeight;
        condition = new BooleanBlock();
        positionBooleanBlock();
    }
    public IfElseBlock() {
        this(0.f, 0.f);
    }

    public void setCloseY(Float closeY) {
        this.closeY = closeY;
    }
    public void setConditionBlock(BooleanBlock condition) {
        this.condition = condition;
        positionBooleanBlock();
    }
    public void setInTrueBlock(Block inTrueBlock) {
        this.inTrueBlock = inTrueBlock;
        if (inTrueBlock.isPlaced())
            return;
        inTrueBlock.setPosX(this.posX + INDENTATION);
        inTrueBlock.setPosY(this.posY + this.height);
        inTrueBlock.place();
    }
    public void setInFalseBlock(Block inFalseBlock) {
        this.inFalseBlock = inFalseBlock;
        if (inFalseBlock.isPlaced())
            return;
        inFalseBlock.setPosX(this.posX + INDENTATION);
        inFalseBlock.setPosY(this.middleY + this.middleHeight);
        inFalseBlock.place();
    }
    @Override
    public void setNextBlock(Block nextBlock) {
        setNextBlockAtHeight(nextBlock, this.closeY + this.closeHeight);
    }
    public Block getInTrueBlock() {
        return inTrueBlock;
    }
    public Block getInFalseBlock() {
        return inFalseBlock;
    }

    private void positionBooleanBlock() {
        Float margin = 3.f;
        this.height = 2.f * margin + condition.getHeight();
        this.width = MARGIN_LEFT + 2.f * margin + condition.getWidth() + MARGIN_RIGHT;
        condition.setPosX(this.posX + MARGIN_LEFT + margin);
        condition.setPosY(this.posY + margin);
    }
    public Boolean hasInTrueBlock() {
        return inTrueBlock != null;
    }
    public Boolean hasInFalseBlock() {
        return inFalseBlock != null;
    }
    @Override
    public void place() {
        this.isPlaced = true;
        closeY = posY + height;
        positionBooleanBlock();
    }

	@Override
	public void draw() {
        Rectangle topShape = new Rectangle();
        topShape.x(posX);
        topShape.y(posY);
        topShape.width(width);
        topShape.height(height);
        Rectangle sideShape = new Rectangle();
        sideShape.x(posX);
        sideShape.y(posY + height / 2.f);
        sideShape.width(INDENTATION);
        sideShape.height(closeY + closeHeight / 2.f - (posY + height / 2.f));
        Rectangle bottomShape = new Rectangle();
        bottomShape.x(posX);
        bottomShape.y(closeY);
        bottomShape.width(width);
        bottomShape.height(closeHeight);
        Color color = new Color();
        color.r((byte) 255);
        color.g((byte) 171);
        color.b((byte) 25);
        color.a((byte) 255);
        DrawRectangleRounded(topShape, CORNER_RADIUS / (height / 2.f), 5, color);
        DrawRectangleRec(sideShape, color);
        DrawRectangleRounded(bottomShape, CORNER_RADIUS / (closeHeight / 2.f), 5, color);
        condition.draw();
	}

    @Override
    public void drawWithChildren(Boolean drawToggle) {
        if (this.drawToggle != drawToggle)
            return;
        this.draw();
        this.drawToggle = !this.drawToggle;
        if (hasInTrueBlock())
            inTrueBlock.drawWithChildren(drawToggle);
        if (hasInFalseBlock())
            inFalseBlock.drawWithChildren(drawToggle);
        nextBlock.drawWithChildren(drawToggle);
    }

    @Override
    public void runWithChildren() {
        if (!hasInTrueBlock()) {
            nextBlock.runWithChildren();
            return;
        }
        System.out.println(type);
        if (ran) {
            ran = false;
            nextBlock.runWithChildren();
            return;
        }
        ran = true;
        if (hasInTrueBlock() && condition.isTrue()) {
            inTrueBlock.runWithChildren();
        } else if (hasInFalseBlock() && !condition.isTrue()) {
            inFalseBlock.runWithChildren();
        } else {
            nextBlock.runWithChildren();
        }
    }
}
