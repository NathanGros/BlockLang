package blocklang.blocks;

/**
 * BooleanBlock
 */
public abstract class BooleanBlock extends PositionnedBlock {
    public BooleanBlock(Float posX, Float posY, Float width, Float height) {
        super(posX, posY, width, height);
    }
    public BooleanBlock(Float posX, Float posY) {
        this(posX, posY, 45.f, 28.f);
    }
    public BooleanBlock() {
        this(0.f, 0.f);
    }

    public abstract Boolean isTrue();
}
