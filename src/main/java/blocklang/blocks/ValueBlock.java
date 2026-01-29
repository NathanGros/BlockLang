package blocklang.blocks;

/**
 * ValueBlock
 */
public abstract class ValueBlock {
    protected Position pos;
    protected Float width;
    protected Float height;

    public ValueBlock(Float posX, Float posY, Float width, Float height) {
        this.pos = new Position(posX, posY);
        this.width = width;
        this.height = height;
    }

    public void setPosX(Float posX) {
        this.pos.setPosX(posX);
    }
    public Float getPosX() {
        return pos.getPosX();
    }
    public void setPosY(Float posY) {
        this.pos.setPosY(posY);
    }
    public Float getPosY() {
        return pos.getPosY();
    }
    public void setPos(Float posX, Float posY) {
        pos = new Position(posX, posY);
    }
    public void setPos(Position pos) {
        this.pos = new Position(pos.getPosX(), pos.getPosY());
    }
    public Position getPos() {
        return new Position(pos.getPosX(), pos.getPosY());
    }
    public Float getWidth() {
        return width;
    }
    public Float getHeight() {
        return height;
    }

    public abstract void draw();

    public abstract Boolean equals(ValueBlock value);

    public void positionWithChildren(Position pos) {
        setPos(pos);
    }

    public void drawWithChildren() {
        this.draw();
    }
}
