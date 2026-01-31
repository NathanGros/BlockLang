package blocklang.blocks;

/**
 * BooleanBlock
 */
public abstract class BooleanBlock extends PositionnedBlock {
    protected static Float BASE_WIDTH = 45.f;
    protected static Float BASE_HEIGHT = 28.f;

    public BooleanBlock(Float posX, Float posY, Float width, Float height) {
        super(posX, posY, width, height);
    }
    public BooleanBlock(Float posX, Float posY) {
        this(posX, posY, BASE_WIDTH, BASE_HEIGHT);
    }
    public BooleanBlock() {
        this(0.f, 0.f);
    }

    public abstract Boolean isTrue();
}
