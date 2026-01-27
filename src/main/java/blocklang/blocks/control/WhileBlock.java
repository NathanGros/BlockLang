package blocklang.blocks.control;

import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.Block;
import blocklang.blocks.BlockType;
import blocklang.blocks.InvalidBlockException;

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
        Rectangle topShape = new Rectangle();
        topShape.x(posX);
        topShape.y(posY);
        topShape.width(width);
        topShape.height(height);
        Rectangle sideShape = new Rectangle();
        sideShape.x(posX);
        sideShape.y(posY + height / 2);
        sideShape.width(INDENTATION);
        sideShape.height(nextBlock.getPosY() + nextBlock.getHeight() / 2.f - (posY + height / 2.f));
        Color color = new Color();
        color.r((byte) 255);
        color.g((byte) 171);
        color.b((byte) 25);
        color.a((byte) 255);
        DrawRectangleRounded(topShape, 0.6f, 5, color);
        DrawRectangleRec(sideShape, color);
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
