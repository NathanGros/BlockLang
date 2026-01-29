package blocklang.blocks;

/**
 * ValueBlock
 */
public abstract class ValueBlock {
    protected Float posX;
    protected Float posY;
    protected Float width;
    protected Float height;

    public ValueBlock(Float posX, Float posY, Float width, Float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public abstract void draw();

    public abstract Boolean equals(ValueBlock value);
}
