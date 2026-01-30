package blocklang.blocks;

/**
 * InstructionBlock
 */
public abstract class InstructionBlock extends PositionnedBlock {
    protected BlockType type;
    protected InstructionBlock nextBlock;
    protected static Float INDENTATION = 15.f;
    protected static Float BASE_HEIGHT = 30.f;
    protected static Float CORNER_RADIUS = 6.f;

    public InstructionBlock(BlockType type, Float posX, Float posY, Float width, Float height) {
        super(posX, posY, width, height);
        this.type = type;
    }

    public BlockType getBlockType() {
        return type;
    }
    public void setNextBlock(InstructionBlock nextBlock) {
        this.nextBlock = nextBlock;
    }
    public InstructionBlock getNextBlock() {
        return nextBlock;
    }

    public Boolean hasNextBlock() {
        return nextBlock != null;
    }

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
