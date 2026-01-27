package blocklang.blocks;

/**
 * Block
 */
public abstract class Block {
    protected BlockType type;
    protected Float posX;
    protected Float posY;
    protected Float width;
    protected Float height;
    protected Block nextBlock;
    protected static Float INDENTATION = 15.f;

    public Block(BlockType type, Float posX, Float posY, Float width, Float height) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public BlockType getBlockType() {
        return type;
    }
    public void setPosX(Float posX) {
        this.posX = posX;
    }
    public Float getPosX() {
        return posX;
    }
    public void setPosY(Float posY) {
        this.posY = posY;
    }
    public Float getPosY() {
        return posY;
    }
    public Float getWidth() {
        return width;
    }
    public Float getHeight() {
        return height;
    }
    public void setNextBlock(Block nextBlock) throws InvalidBlockException {
        if (!nextBlock.getBlockType().equals(BlockType.CLOSING_BLOCK))
            nextBlock.setPosX(this.posX);
        nextBlock.setPosY(this.posY + this.height);
        this.nextBlock = nextBlock;
    }
    public Block getNextBlock() {
        return nextBlock;
    }

    public Boolean hasNextBlock() {
        return nextBlock != null;
    }

    public abstract void draw();

    public void drawWithChildren() {
        this.draw();
        if (hasNextBlock())
            nextBlock.drawWithChildren();
    }

    public void runWithChildren() {
        System.out.println(type);
        if (hasNextBlock())
            nextBlock.runWithChildren();
    }
}
