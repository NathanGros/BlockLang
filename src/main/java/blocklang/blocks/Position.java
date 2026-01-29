package blocklang.blocks;

/**
 * Position
 */
public class Position {
    private Float posX;
    private Float posY;

	public Position(Float posX, Float posY) {
        this.posX = posX;
        this.posY = posY;
    }
	public Position(Position pos) {
        this.posX = pos.getPosX();
        this.posY = pos.getPosY();
    }

    public Float getPosX() {
		return posX;
	}
	public void setPosX(Float posX) {
		this.posX = posX;
	}
	public Float getPosY() {
		return posY;
	}
	public void setPosY(Float posY) {
		this.posY = posY;
	}
    public void setPos(Float posX, Float posY) {
        this.posX = posX;
        this.posY = posY;
    }
    public void setPos(Position pos) {
        this.posX = pos.getPosX();
        this.posY = pos.getPosY();
    }
}
