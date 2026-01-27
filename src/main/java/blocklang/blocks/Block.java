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

    public Block(BlockType type, Float posX, Float posY, Float width, Float height) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public Boolean hasNextBlock() {
        return nextBlock != null;
    }

    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
    }

    public Block getNextBlock() {
        return nextBlock;
    }

    public abstract void draw();
}
