package blocklang.blocks.control;

import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.InstructionBlock;
import blocklang.blocks.BlockType;
import blocklang.blocks.Position;
import blocklang.blocks.BooleanBlock;

/**
 * IfElseBlock
 */
public class IfElseBlock extends InstructionBlock {
    private BooleanBlock condition;
    private InstructionBlock inTrueBlock;
    private InstructionBlock inFalseBlock;
    private Float middleHeight;
    private Float middleY;
    private Float closeHeight;
    private Float closeY;
    private static Float MARGIN_LEFT = 40.f;
    private static Float MARGIN_RIGHT = 60.f;
    private static Float HOLE_HEIGHT = 25.f;

    public IfElseBlock(Float x, Float y) {
        super(BlockType.IF_ELSE, x, y, 100.f, 50.f);
        middleHeight = BASE_HEIGHT * 2.f / 3.f;
        closeHeight = BASE_HEIGHT * 2.f / 3.f;
        condition = new BooleanBlock();
    }
    public IfElseBlock() {
        this(0.f, 0.f);
    }

    public void setCloseY(Float closeY) {
        this.closeY = closeY;
    }
    public void setConditionBlock(BooleanBlock condition) {
        this.condition = condition;
    }
    public void setInTrueBlock(InstructionBlock inTrueBlock) {
        this.inTrueBlock = inTrueBlock;
    }
    public void setInFalseBlock(InstructionBlock inFalseBlock) {
        this.inFalseBlock = inFalseBlock;
    }
    public InstructionBlock getInTrueBlock() {
        return inTrueBlock;
    }
    public InstructionBlock getInFalseBlock() {
        return inFalseBlock;
    }

    private void positionCondition() {
        Float margin = 3.f;
        condition.positionWithChildren(new Position(getPosX() + MARGIN_LEFT + margin, getPosY() + margin));
        this.height = 2.f * margin + condition.getHeight();
        this.width = MARGIN_LEFT + 2.f * margin + condition.getWidth() + MARGIN_RIGHT;
    }
    public Boolean hasInTrueBlock() {
        return inTrueBlock != null;
    }
    public Boolean hasInFalseBlock() {
        return inFalseBlock != null;
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
        Rectangle middleShape = new Rectangle();
        middleShape.x(getPosX());
        middleShape.y(middleY);
        middleShape.width(width);
        middleShape.height(middleHeight);
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
        DrawRectangleRounded(middleShape, CORNER_RADIUS / (closeHeight / 2.f), 5, color);
        DrawRectangleRounded(bottomShape, CORNER_RADIUS / (closeHeight / 2.f), 5, color);
        condition.draw();
	}

    @Override
    public Position positionWithChildren(Position pos) {
        setPos(pos);
        positionCondition();
        Position inTruePos = new Position(this.getPosX() + INDENTATION, this.getPosY() + this.height);
        Position inFalsePos = new Position(inTruePos.getPosX(), inTruePos.getPosY() + HOLE_HEIGHT);
        if (hasInTrueBlock())
            inFalsePos.setPos(inTrueBlock.positionWithChildren(inTruePos));
        middleY = inFalsePos.getPosY();
        inFalsePos.setPos(this.getPosX() + INDENTATION, middleY + middleHeight);
        Position nextPos = new Position(inFalsePos.getPosX(), inFalsePos.getPosY() + HOLE_HEIGHT);
        if (hasInFalseBlock()) {
            nextPos.setPos(inFalseBlock.positionWithChildren(inFalsePos));
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
            if (hasInTrueBlock())
                inTrueBlock.runWithChildren();
        } else {
            if (hasInFalseBlock())
                inFalseBlock.runWithChildren();
        }
        if (hasNextBlock())
            nextBlock.runWithChildren();
    }

    @Override
    public void drawWithChildren() {
        this.draw();
        if (hasInTrueBlock())
            inTrueBlock.drawWithChildren();
        if (hasInFalseBlock())
            inFalseBlock.drawWithChildren();
        if (hasNextBlock())
            nextBlock.drawWithChildren();
    }
}
