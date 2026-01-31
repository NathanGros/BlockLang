package blocklang.blocks;

import static com.raylib.Raylib.CheckCollisionPointRec;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

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

    protected void drawRectangle(Float posX, Float posY, Float width, Float height, Float roundness, Color color) {
        Rectangle shape = new Rectangle();
        shape.x(posX);
        shape.y(posY);
        shape.width(width);
        shape.height(height);
        DrawRectangleRounded(shape, roundness, 5, color);
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

    public PositionnedBlock selectWithChildren(Vector2 mousePos) {
        Rectangle shape = new Rectangle();
        shape.x(getPosX());
        shape.y(getPosY());
        shape.width(width);
        shape.height(height);
        if (CheckCollisionPointRec(mousePos, shape))
            return this;
        if (hasNextBlock()) {
            PositionnedBlock selected = nextBlock.selectWithChildren(mousePos);
            if (selected == nextBlock)
                nextBlock = null;
            return selected;
        }
        return null;
    }

    public void appendLastWithChildren(InstructionBlock oldNextBlock) {
        if (!hasNextBlock()) {
            nextBlock = oldNextBlock;
            return;
        }
        nextBlock.appendLastWithChildren(oldNextBlock);
    }

    @Override
    public Boolean insertWithChildren(PositionnedBlock selectedBlock, Vector2 mousePos) {
        if (hasNextBlock()) {
            if (nextBlock.insertWithChildren(selectedBlock, mousePos))
                return true;
        }
        if (selectedBlock instanceof InstructionBlock selectedInstructionBlock) {
            Rectangle insertShape = new Rectangle();
            insertShape.x(getPosX());
            insertShape.y(getPosY());
            insertShape.width(width);
            insertShape.height(height);
            if (CheckCollisionPointRec(mousePos, insertShape)) {
                InstructionBlock oldNextBlock = nextBlock;
                nextBlock = selectedInstructionBlock;
                selectedInstructionBlock.appendLastWithChildren(oldNextBlock);
                return true;
            }
        }
        return false;
    }
}
