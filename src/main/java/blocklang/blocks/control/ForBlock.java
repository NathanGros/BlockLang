package blocklang.blocks.control;

import static com.raylib.Raylib.DrawRectangleRec;
import static com.raylib.Raylib.DrawRectangleRounded;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;

import blocklang.blocks.InstructionBlock;
import blocklang.blocks.BlockType;
import blocklang.blocks.Position;
import blocklang.blocks.variables.NumberBlock;

/**
 * ForBlock
 */
public class ForBlock extends InstructionBlock {
    private NumberBlock nbRepetitions;
    private InstructionBlock inBlock;
    private Float closeHeight;
    private Float closeY;
    private static Float MARGIN_LEFT = 100.f;
    private static Float MARGIN_RIGHT = 10.f;
    private static Float HOLE_HEIGHT = 25.f;

    public ForBlock(Float x, Float y) {
        super(BlockType.FOR, x, y, 100.f, 40.f);
        closeHeight = BASE_HEIGHT * 2.f / 3.f;
        nbRepetitions = new NumberBlock();
    }
    public ForBlock() {
        this(0.f, 0.f);
    }

    public void setNbRepetitions(Integer nbRepetitions) {
        NumberBlock n = new NumberBlock();
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
        nbRepetitions.draw();
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
            for (int i = 0; i < nbRepetitions.getValue(); i++)
                inBlock.runWithChildren();
        }
        if (hasNextBlock())
            nextBlock.runWithChildren();
    }

    @Override
    public void drawWithChildren() {
        this.draw();
        if (hasInBlock())
            inBlock.drawWithChildren();
        if (hasNextBlock())
            nextBlock.drawWithChildren();
    }
}
