package blocklang.blocks;

/**
 * ConditionBlock
 */
public abstract class ConditionBlock {
    protected Float posX;
    protected Float posY;
    protected Float width;
    protected Float height;

    public ConditionBlock(Float posX, Float posY, Float width, Float height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public abstract Boolean isTrue();

    public abstract void draw();
}
