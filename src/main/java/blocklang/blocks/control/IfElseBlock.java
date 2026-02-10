package blocklang.blocks.control;

import static com.raylib.Raylib.CheckCollisionPointRec;
import static com.raylib.Raylib.DrawTextEx;
import static com.raylib.Raylib.MeasureTextEx;
import static com.raylib.Raylib.Vector2Zero;

import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import blocklang.Colors;
import blocklang.FontUtil;
import blocklang.blocks.BlockType;
import blocklang.blocks.BooleanBlock;
import blocklang.blocks.BooleanSlot;
import blocklang.blocks.InstructionBlock;
import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;

/**
 * IfElseBlock
 */
public class IfElseBlock extends InstructionBlock {
    private BooleanSlot conditionSlot;
    private InstructionBlock inTrueBlock;
    private InstructionBlock inFalseBlock;
    private Float middleHeight;
    private Float middleY;
    private Float closeHeight;
    private Float closeY;
    private static final String text1 = "If";
    private static final String text2 = "Else";
    private static Float MARGIN_LEFT = 40.f;
    private static Float MARGIN_RIGHT = 10.f;
    private static Float HOLE_HEIGHT = 25.f;

    public IfElseBlock(Float x, Float y) {
        super(BlockType.IF_ELSE, x, y, 100.f, 50.f);
        middleHeight = BASE_HEIGHT;
        closeHeight = BASE_HEIGHT * 2.f / 3.f;
        conditionSlot = new BooleanSlot();
    }
    public IfElseBlock() {
        this(0.f, 0.f);
    }

    public void setCloseY(Float closeY) {
        this.closeY = closeY;
    }
    public void setConditionBlock(BooleanBlock condition) {
        this.conditionSlot.setChild(condition);
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
        conditionSlot.positionWithChildren(new Position(getPosX() + MARGIN_LEFT + margin, getPosY() + margin));
        this.height = 2.f * margin + conditionSlot.getHeight();
        this.width = MARGIN_LEFT + 2.f * margin + conditionSlot.getWidth() + MARGIN_RIGHT;
    }
    public Boolean hasInTrueBlock() {
        return inTrueBlock != null;
    }
    public Boolean hasInFalseBlock() {
        return inFalseBlock != null;
    }

    private void drawText() {
        Vector2 text1Size = MeasureTextEx(FontUtil.getFont(), text1, FontUtil.getWorldFontSize(), 0);
        text1Size.y(text1Size.y() * 3.f / 4.f);
        float text1MarginX = BASE_HEIGHT / 2.f;
        float text1MarginY = (getHeight() - text1Size.y()) / 2.f;
        Vector2 text1Pos = Vector2Zero().x(getPosX() + text1MarginX).y(getPosY() + text1MarginY);
        DrawTextEx(FontUtil.getFont(), text1, text1Pos, FontUtil.getWorldFontSize(), 0, Colors.getBlockTextColor());

        Vector2 text2Size = MeasureTextEx(FontUtil.getFont(), text1, FontUtil.getWorldFontSize(), 0);
        text2Size.y(text2Size.y() * 3.f / 4.f);
        float text2MarginX = BASE_HEIGHT / 2.f;
        float text2MarginY = (middleHeight - text2Size.y()) / 2.f;
        Vector2 text2Pos = Vector2Zero().x(getPosX() + text2MarginX).y(middleY + text2MarginY);
        DrawTextEx(FontUtil.getFont(), text2, text2Pos, FontUtil.getWorldFontSize(), 0, Colors.getBlockTextColor());
    }

	@Override
	public void draw() {
        Float border = 1.f;
        Float roundness = CORNER_RADIUS / (height / 2.f);
        Float middleRoundness = CORNER_RADIUS / (middleHeight / 2.f);
        Float closeRoundness = CORNER_RADIUS / (closeHeight / 2.f);
        drawRectangle(getPosX(), getPosY(), width, height, roundness, Colors.getControlBorderColor());
        drawRectangle(
            getPosX(),
            getPosY() + height / 2.f,
            INDENTATION,
            closeY + closeHeight / 2.f - (getPosY() + height / 2.f),
            roundness,
            Colors.getControlBorderColor()
        );
        drawRectangle(getPosX(), middleY, width, middleHeight, middleRoundness, Colors.getControlBorderColor());
        drawRectangle(getPosX(), closeY, width, closeHeight, closeRoundness, Colors.getControlBorderColor());
        drawRectangle(getPosX() + border, getPosY() + border, width - 2.f * border, height - 2.f * border, roundness, Colors.getControlColor());
        drawRectangle(
            getPosX() + border,
            getPosY() + height / 2.f + border,
            INDENTATION - 2.f * border,
            closeY + closeHeight / 2.f - (getPosY() + height / 2.f),
            roundness,
            Colors.getControlColor()
        );
        drawRectangle(getPosX() + border, middleY + border, width - 2.f * border, middleHeight - 2.f * border, middleRoundness, Colors.getControlColor());
        drawRectangle(getPosX() + border, closeY + border, width - 2.f * border, closeHeight - 2.f * border, closeRoundness, Colors.getControlColor());
        drawText();
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
        if (conditionSlot.isTrue()) {
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
        conditionSlot.drawWithChildren();
        if (hasInTrueBlock())
            inTrueBlock.drawWithChildren();
        if (hasInFalseBlock())
            inFalseBlock.drawWithChildren();
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
            PositionnedBlock selected = conditionSlot.selectWithChildren(mousePos);
            if (selected != null) {
                if (selected == conditionSlot)
                    conditionSlot = new BooleanSlot();
                return selected;
            }
            return this;
        }
        if (hasInTrueBlock()) {
            PositionnedBlock selected = inTrueBlock.selectWithChildren(mousePos);
            if (selected != null) {
                if (selected == inTrueBlock)
                    inTrueBlock = null;
                return selected;
            }
        }
        if (hasInFalseBlock()) {
            PositionnedBlock selected = inFalseBlock.selectWithChildren(mousePos);
            if (selected != null) {
                if (selected == inFalseBlock)
                    inFalseBlock = null;
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
        if (conditionSlot.insertWithChildren(selectedBlock, mousePos))
            return true;
        if (hasNextBlock()) {
            if (nextBlock.insertWithChildren(selectedBlock, mousePos))
                return true;
        }
        if (hasInTrueBlock()) {
            if (inTrueBlock.insertWithChildren(selectedBlock, mousePos))
                return true;
        }
        if (hasInFalseBlock()) {
            if (inFalseBlock.insertWithChildren(selectedBlock, mousePos))
                return true;
        }
        if (selectedBlock instanceof InstructionBlock selectedInstructionBlock) {
            // Try to insert in true
            Rectangle insertInTrueShape = new Rectangle();
            insertInTrueShape.x(getPosX());
            insertInTrueShape.y(getPosY());
            insertInTrueShape.width(width);
            insertInTrueShape.height(height);
            if (CheckCollisionPointRec(mousePos, insertInTrueShape)) {
                InstructionBlock oldInTrueBlock = inTrueBlock;
                inTrueBlock = selectedInstructionBlock;
                selectedInstructionBlock.appendLastWithChildren(oldInTrueBlock);
                return true;
            }
            // Try to insert in false
            Rectangle insertInFalseShape = new Rectangle();
            insertInFalseShape.x(getPosX());
            insertInFalseShape.y(middleY);
            insertInFalseShape.width(width);
            insertInFalseShape.height(middleHeight);
            if (CheckCollisionPointRec(mousePos, insertInFalseShape)) {
                InstructionBlock oldInFalseBlock = inFalseBlock;
                inFalseBlock = selectedInstructionBlock;
                selectedInstructionBlock.appendLastWithChildren(oldInFalseBlock);
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
