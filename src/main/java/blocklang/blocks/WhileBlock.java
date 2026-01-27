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

    public void setInBlock(Block inBlock) {
        this.inBlock = inBlock;
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
        if (condition)
            inBlock.runWithChildren();
        else
            nextBlock.runWithChildren();
    }
}
