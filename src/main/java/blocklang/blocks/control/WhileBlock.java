package blocklang.blocks.control;

import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.Block;
import blocklang.blocks.BlockType;
import blocklang.blocks.BooleanBlock;

/**
 * WhileBlock
 */
public class WhileBlock extends Block {
    private BooleanBlock condition;
    private Block inBlock;
    private Float closeHeight;
    private Float closeY;
    private static Float MARGIN_LEFT = 100.f;
    private static Float MARGIN_RIGHT = 10.f;

    public WhileBlock(Float x, Float y) {
        super(BlockType.WHILE, x, y, 100.f, 50.f);
        closeHeight = BASE_HEIGHT * 2.f / 3.f;
        closeY = posY + height;
        condition = new BooleanBlock();
        positionBooleanBlock();
    }
    public WhileBlock() {
        this(0.f, 0.f);
    }

    public void setCloseY(Float closeY) {
        this.closeY = closeY;
    }
    public void setConditionBlock(BooleanBlock condition) {
        this.condition = condition;
        positionBooleanBlock();
    }
    public void setInBlock(Block inBlock) {
        this.inBlock = inBlock;
        if (inBlock.isPlaced())
            return;
        inBlock.setPosX(this.posX + INDENTATION);
        inBlock.setPosY(this.posY + this.height);
        inBlock.place();
    }
    @Override
    public void setNextBlock(Block nextBlock) {
        setNextBlockAtHeight(nextBlock, this.closeY + this.closeHeight);
    }
    public Block getInBlock() {
        return inBlock;
    }

    private void positionBooleanBlock() {
        Float margin = 3.f;
        this.height = 2.f * margin + condition.getHeight();
        this.width = MARGIN_LEFT + 2.f * margin + condition.getWidth() + MARGIN_RIGHT;
        condition.setPosX(this.posX + MARGIN_LEFT + margin);
        condition.setPosY(this.posY + margin);
    }
    public Boolean hasInBlock() {
        return inBlock != null;
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
        if (hasInBlock())
            inBlock.drawWithChildren(drawToggle);
        nextBlock.drawWithChildren(drawToggle);
    }

    @Override
    public void runWithChildren() {
        System.out.println(type);
        if (hasInBlock() && condition.isTrue())
            inBlock.runWithChildren();
        else
            nextBlock.runWithChildren();
    }
}
