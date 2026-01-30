package blocklang.blocks;

/**
 * ValueBlock
 */
public abstract class ValueBlock extends PositionnedBlock {
    public ValueBlock(Float posX, Float posY, Float width, Float height) {
        super(posX, posY, width, height);
    }

    public abstract Boolean equals(ValueBlock value);
}
