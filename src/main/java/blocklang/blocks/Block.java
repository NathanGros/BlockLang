package blocklang.blocks;

/**
 * Block
 */
public abstract class Block {
    protected BlockType type;
    protected Position pos;
    protected Float width;
    protected Float height;
    protected Block nextBlock;
    protected static Float INDENTATION = 15.f;
    protected static Float BASE_HEIGHT = 30.f;
    protected static Float CORNER_RADIUS = 6.f;

    public Block(BlockType type, Float posX, Float posY, Float width, Float height) {
        this.type = type;
        this.pos = new Position(posX, posY);
        this.width = width;
        this.height = height;
    }

    public BlockType getBlockType() {
        return type;
    }
    public void setPosX(Float posX) {
        this.pos.setPosX(posX);
    }
    public Float getPosX() {
        return pos.getPosX();
    }
    public void setPosY(Float posY) {
        this.pos.setPosY(posY);
    }
    public Float getPosY() {
        return pos.getPosY();
    }
    public void setPos(Float posX, Float posY) {
        pos = new Position(posX, posY);
    }
    public void setPos(Position pos) {
        this.pos = new Position(pos.getPosX(), pos.getPosY());
    }
    public Position getPos() {
        return new Position(pos.getPosX(), pos.getPosY());
    }
    public Float getWidth() {
        return width;
    }
    public Float getHeight() {
        return height;
    }
    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
    }
    public Block getNextBlock() {
        return nextBlock;
    }

    public Boolean hasNextBlock() {
        return nextBlock != null;
    }

    public abstract void draw();

    public Position positionWithChildren(Position pos) {
        setPos(pos);
        Position nextPos = new Position(this.pos.getPosX(), this.pos.getPosY() + this.height);
        Position endPos = new Position(nextPos);
        if (hasNextBlock())
            endPos.setPos(nextBlock.positionWithChildren(nextPos));
        return endPos;
    }

    public void runWithChildren() {
        System.out.println(type);
        if (hasNextBlock())
            nextBlock.runWithChildren();
    }

    public void drawWithChildren() {
        this.draw();
        if (hasNextBlock())
            nextBlock.drawWithChildren();
    }
}
