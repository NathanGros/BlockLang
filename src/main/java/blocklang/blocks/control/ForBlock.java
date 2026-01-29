package blocklang.blocks.control;

import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.Block;
import blocklang.blocks.BlockType;

/**
 * ForBlock
 */
public class ForBlock extends Block {
    private Integer nbRepetitions;
    private Integer iterator;
    private Block inBlock;
    private Float closeHeight;
    private Float closeY;

    public ForBlock(Float x, Float y) {
        super(BlockType.FOR, x, y, 100.f, 30.f);
        closeHeight = height * 2.f / 3.f;
        closeY = posY + height;
        nbRepetitions = 0;
        iterator = 0;
    }
    public ForBlock() {
        this(0.f, 0.f);
    }

    public void setCloseY(Float closeY) {
        this.closeY = closeY;
    }
    public void setNbRepetitions(Integer nbRepetitions) {
        this.nbRepetitions = nbRepetitions;
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
        this.nextBlock = nextBlock;
        if (nextBlock.getBlockType().equals(BlockType.WHILE) && nextBlock.isPlaced()) {
            WhileBlock whileBlock = (WhileBlock) nextBlock;
            whileBlock.setCloseY(this.closeY + this.closeHeight);
            return;
        }
        if (nextBlock.getBlockType().equals(BlockType.FOR) && nextBlock.isPlaced()) {
            ForBlock forBlock = (ForBlock) nextBlock;
            forBlock.setCloseY(this.closeY + this.closeHeight);
            return;
        }
        if (nextBlock.isPlaced())
            return;
        nextBlock.setPosX(this.posX);
        nextBlock.setPosY(this.closeY + this.closeHeight);
        nextBlock.place();
    }
    public Block getInBlock() {
        return inBlock;
    }

    public Boolean hasInBlock() {
        return inBlock != null;
    }
    @Override
    public void place() {
        this.isPlaced = true;
        closeY = posY + height;
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
        DrawRectangleRounded(topShape, 0.6f, 5, color);
        DrawRectangleRec(sideShape, color);
        DrawRectangleRounded(bottomShape, 0.9f, 5, color);
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
        if (hasInBlock() && iterator < nbRepetitions) {
            iterator++;
            inBlock.runWithChildren();
        } else {
            iterator = 0;
            nextBlock.runWithChildren();
        }
    }
}
