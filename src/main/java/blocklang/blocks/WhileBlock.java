package blocklang.blocks;

import static com.raylib.Colors.BLUE;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Rectangle;

/**
 * WhileBlock
 */
public class WhileBlock extends Block {
    private Block inBlock;
    private Boolean condition;

    public WhileBlock(Float x, Float y) {
        super(BlockType.WHILE, x, y, 100.f, 30.f);
        condition = true;
    }
    public WhileBlock() {
        this(0.f, 0.f);
    }

    public void setInBlock(Block inBlock) {
        inBlock.setPosX(this.posX + INDENTATION);
        inBlock.setPosY(this.posY + this.height);
        this.inBlock = inBlock;
    }
    @Override
    public void setNextBlock(Block nextBlock) throws InvalidBlockException {
        if (!nextBlock.getBlockType().equals(BlockType.CLOSING_BLOCK))
            throw new InvalidBlockException(nextBlock + " should be a closing block");
        nextBlock.setPosX(this.posX);
        nextBlock.setPosY(this.posY + this.height);
        this.nextBlock = nextBlock;
    }
    public Block getInBlock() {
        return inBlock;
    }

    public Boolean hasInBlock() {
        return inBlock != null;
    }

	@Override
	public void draw() {
        Rectangle shape = new Rectangle();
        shape.x(posX);
        shape.y(posY);
        shape.width(width);
        shape.height(height);
        DrawRectangleRounded(shape, 0.6f, 5, BLUE);
	}

    @Override
    public void drawWithChildren() {
        this.draw();
        if (hasInBlock())
            inBlock.drawWithChildren();
        else
            nextBlock.drawWithChildren();
    }

    @Override
    public void runWithChildren() {
        System.out.println(type);
        if (hasInBlock() && condition)
            inBlock.runWithChildren();
        else
            nextBlock.runWithChildren();
    }
}
