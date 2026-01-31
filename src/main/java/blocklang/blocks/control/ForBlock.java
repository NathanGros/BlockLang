package blocklang.blocks.control;

import static com.raylib.Raylib.CheckCollisionPointRec;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import blocklang.blocks.BlockType;
import blocklang.blocks.InstructionBlock;
import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;
import blocklang.blocks.ValueBlock;
import blocklang.blocks.ValueSlot;

/**
 * ForBlock
 */
public class ForBlock extends InstructionBlock {
    private ValueBlock nbRepetitions;
    private InstructionBlock inBlock;
    private Float closeHeight;
    private Float closeY;
    private static Float MARGIN_LEFT = 100.f;
    private static Float MARGIN_RIGHT = 10.f;
    private static Float HOLE_HEIGHT = 25.f;

    public ForBlock(Float x, Float y) {
        super(BlockType.FOR, x, y, 100.f, 40.f);
        closeHeight = BASE_HEIGHT * 2.f / 3.f;
        nbRepetitions = new ValueSlot();
    }
    public ForBlock() {
        this(0.f, 0.f);
    }

    public void setNbRepetitions(Integer nbRepetitions) {
        ValueSlot n = new ValueSlot();
        n.setValue(nbRepetitions.floatValue());
        this.nbRepetitions = n;
    }
    public void setInBlock(InstructionBlock inBlock) {
        this.inBlock = inBlock;
    }
    public InstructionBlock getInBlock() {
        return inBlock;
    }

    private void positionNbRepetitionsBlock() {
        Float margin = 3.f;
        nbRepetitions.positionWithChildren(new Position(getPosX() + MARGIN_LEFT + margin, getPosY() + margin));
        this.height = 2.f * margin + nbRepetitions.getHeight();
        this.width = MARGIN_LEFT + 2.f * margin + nbRepetitions.getWidth() + MARGIN_RIGHT;
    }
    public Boolean hasInBlock() {
        return inBlock != null;
    }

	@Override
	public void draw() {
        Color borderColor = new Color();
        borderColor.r((byte) 205);
        borderColor.g((byte) 137);
        borderColor.b((byte) 20);
        borderColor.a((byte) 255);
        Color color = new Color();
        color.r((byte) 255);
        color.g((byte) 171);
        color.b((byte) 25);
        color.a((byte) 255);
        Float border = 1.f;
        Float roundness = CORNER_RADIUS / (height / 2.f);
        Float closeRoundness = CORNER_RADIUS / (closeHeight / 2.f);
        drawRectangle(getPosX(), getPosY(), width, height, roundness, borderColor);
        drawRectangle(
            getPosX(),
            getPosY() + height / 2.f,
            INDENTATION,
            closeY + closeHeight / 2.f - (getPosY() + height / 2.f),
            roundness,
            borderColor
        );
        drawRectangle(getPosX(), closeY, width, closeHeight, closeRoundness, borderColor);
        drawRectangle(getPosX() + border, getPosY() + border, width - 2.f * border, height - 2.f * border, roundness, color);
        drawRectangle(
            getPosX() + border,
            getPosY() + height / 2.f + border,
            INDENTATION - 2.f * border,
            closeY + closeHeight / 2.f - (getPosY() + height / 2.f),
            roundness,
            color
        );
        drawRectangle(getPosX() + border, closeY + border, width - 2.f * border, closeHeight - 2.f * border, closeRoundness, color);
	}

    @Override
    public Position positionWithChildren(Position pos) {
        setPos(pos);
        positionNbRepetitionsBlock();
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
        if (hasInBlock()) {
            for (int i = 0; i < nbRepetitions.getFloatValue(); i++)
                inBlock.runWithChildren();
        }
        if (hasNextBlock())
            nextBlock.runWithChildren();
    }

    @Override
    public void drawWithChildren() {
        this.draw();
        nbRepetitions.draw();
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
            PositionnedBlock selected = nbRepetitions.selectWithChildren(mousePos);
            if (selected != null) {
                if (selected == nbRepetitions)
                    nbRepetitions = null;
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
        if (nbRepetitions.insertWithChildren(selectedBlock, mousePos))
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
