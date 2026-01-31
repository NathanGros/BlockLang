package blocklang.blocks;

/**
 * ValueBlock
 */
public abstract class ValueBlock extends PositionnedBlock {
    protected static Float BASE_WIDTH = 45.f;
    protected static Float BASE_HEIGHT = 28.f;

    public ValueBlock(Float posX, Float posY, Float width, Float height) {
        super(posX, posY, width, height);
    }
    public ValueBlock(Float posX, Float posY) {
        this(posX, posY, BASE_WIDTH, BASE_HEIGHT);
    }
    public ValueBlock() {
        this(0.f, 0.f);
    }

    public abstract Boolean equals(ValueBlock value);

    public abstract Float getFloatValue();
}
