package blocklang.blocks.control;

import static com.raylib.Raylib.CheckCollisionPointRec;
import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import blocklang.blocks.InstructionBlock;
import blocklang.blocks.BlockType;
import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;
import blocklang.blocks.BooleanBlock;

/**
 * IfBlock
 */
public class IfBlock extends InstructionBlock {
    private BooleanBlock condition;
    private InstructionBlock inBlock;
    private Float closeHeight;
    private Float closeY;
    private static Float MARGIN_LEFT = 40.f;
    private static Float MARGIN_RIGHT = 60.f;
    private static Float HOLE_HEIGHT = 25.f;

    public IfBlock(Float x, Float y) {
        super(BlockType.IF, x, y, 100.f, 50.f);
        closeHeight = BASE_HEIGHT * 2.f / 3.f;
        condition = new BooleanBlock();
    }
    public IfBlock() {
        this(0.f, 0.f);
    }

    public void setConditionBlock(BooleanBlock condition) {
        this.condition = condition;
    }
    public void setInBlock(InstructionBlock inBlock) {
        this.inBlock = inBlock;
    }
    public InstructionBlock getInBlock() {
        return inBlock;
    }

    private void positionCondition() {
        Float margin = 3.f;
        condition.positionWithChildren(new Position(getPosX() + MARGIN_LEFT + margin, getPosY() + margin));
        this.height = 2.f * margin + condition.getHeight();
        this.width = MARGIN_LEFT + 2.f * margin + condition.getWidth() + MARGIN_RIGHT;
    }
    public Boolean hasInBlock() {
        return inBlock != null;
    }

	@Override
	public void draw() {
        Rectangle topShape = new Rectangle();
        topShape.x(getPosX());
        topShape.y(getPosY());
        topShape.width(width);
        topShape.height(height);
        Rectangle sideShape = new Rectangle();
        sideShape.x(getPosX());
        sideShape.y(getPosY() + height / 2.f);
        sideShape.width(INDENTATION);
        sideShape.height(closeY + closeHeight / 2.f - (getPosY() + height / 2.f));
        Rectangle bottomShape = new Rectangle();
        bottomShape.x(getPosX());
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
	}

    @Override
    public Position positionWithChildren(Position pos) {
        setPos(pos);
        positionCondition();
        Position inPos = new Position(this.getPosX() + INDENTATION, this.getPosY() + this.height);
        Position nextPos = new Position(inPos.getPosX(), inPos.getPosY() + HOLE_HEIGHT);
        if (hasInBlock()) {
            nextPos.setPos(inBlock.positionWithChildren(inPos));
        }
        closeY = nextPos.getPosY();
        nextPos.setPos(this.getPosX(), closeY + closeHeight);
        Position endPos = new Position(nextPos);
        if (hasNextBlock())
            endPos.setPos(nextBlock.positionWithChildren(nextPos));
        return endPos;
    }

    @Override
    public void runWithChildren() {
        System.out.println(type);
        if (condition.isTrue()) {
            if (hasInBlock())
                inBlock.runWithChildren();
        }
        if (hasNextBlock())
            nextBlock.runWithChildren();
    }

    @Override
    public void drawWithChildren() {
        this.draw();
        condition.drawWithChildren();
        if (hasInBlock())
            inBlock.drawWithChildren();
        if (hasNextBlock())
            nextBlock.drawWithChildren();
    }

    @Override
    public PositionnedBlock selectWithChildren(Vector2 mousePos) {
        Rectangle shape = new Rectangle();
        shape.x(getPosX());
        shape.y(getPosY());
        shape.width(width);
        shape.height(height);
        if (CheckCollisionPointRec(mousePos, shape)) {
            PositionnedBlock selected = condition.selectWithChildren(mousePos);
            if (selected != null) {
                if (selected == condition)
                    condition = new BooleanBlock();
                return selected;
            }
            return this;
        }
        if (hasInBlock()) {
            PositionnedBlock selected = inBlock.selectWithChildren(mousePos);
            if (selected != null) {
                if (selected == inBlock)
                    inBlock = null;
                return selected;
            }
        }
        if (hasNextBlock()) {
            PositionnedBlock selected = nextBlock.selectWithChildren(mousePos);
            if (selected != null) {
                if (selected == nextBlock)
                    nextBlock = null;
                return selected;
            }
        }
        return null;
    }

    @Override
    public Boolean insertWithChildren(PositionnedBlock selectedBlock, Vector2 mousePos) {
        if (condition.insertWithChildren(selectedBlock, mousePos))
            return true;
        if (hasNextBlock()) {
            if (nextBlock.insertWithChildren(selectedBlock, mousePos))
                return true;
        }
        if (hasInBlock()) {
            if (inBlock.insertWithChildren(selectedBlock, mousePos))
                return true;
        }
        if (selectedBlock instanceof InstructionBlock selectedInstructionBlock) {
            // Try to insert in
            Rectangle insertInShape = new Rectangle();
            insertInShape.x(getPosX());
            insertInShape.y(getPosY());
            insertInShape.width(width);
            insertInShape.height(height);
            if (CheckCollisionPointRec(mousePos, insertInShape)) {
                InstructionBlock oldInBlock = inBlock;
                inBlock = selectedInstructionBlock;
                selectedInstructionBlock.appendLastWithChildren(oldInBlock);
                return true;
            }
            // Try to insert next
            Rectangle insertNextShape = new Rectangle();
            insertNextShape.x(getPosX());
            insertNextShape.y(closeY);
            insertNextShape.width(width);
            insertNextShape.height(closeHeight);
            if (CheckCollisionPointRec(mousePos, insertNextShape)) {
                InstructionBlock oldNextBlock = nextBlock;
                nextBlock = selectedInstructionBlock;
                selectedInstructionBlock.appendLastWithChildren(oldNextBlock);
                return true;
            }
        }
        return false;
    }
}
