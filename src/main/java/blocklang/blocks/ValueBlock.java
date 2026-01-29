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

    public void setPosX(Float posX) {
        this.posX = posX;
    }
    public Float getPosX() {
        return posX;
    }
    public void setPosY(Float posY) {
        this.posY = posY;
    }
    public Float getPosY() {
        return posY;
    }
    public Float getWidth() {
        return width;
    }
    public Float getHeight() {
        return height;
    }

    public abstract void draw();

    public abstract Boolean equals(ValueBlock value);
}
