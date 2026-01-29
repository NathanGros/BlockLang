package blocklang.blocks;

import blocklang.blocks.control.ForBlock;
import blocklang.blocks.control.IfBlock;
import blocklang.blocks.control.WhileBlock;

/**
 * Block
 */
public abstract class Block {
    protected BlockType type;
    protected Float posX;
    protected Float posY;
    protected Float width;
    protected Float height;
    protected Block nextBlock;
    protected static Float INDENTATION = 15.f;
    protected Boolean isPlaced;
    protected Boolean drawToggle;

    public Block(BlockType type, Float posX, Float posY, Float width, Float height) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.isPlaced = false;
        this.drawToggle = false;
    }

    public BlockType getBlockType() {
        return type;
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
    protected void setNextBlockAtHeight(Block nextBlock, Float nextPosY) {
        this.nextBlock = nextBlock;
        if (nextBlock.isPlaced() && nextBlock.getBlockType().equals(BlockType.WHILE)) {
            WhileBlock whileBlock = (WhileBlock) nextBlock;
            whileBlock.setCloseY(nextPosY);
            return;
        }
        if (nextBlock.isPlaced() && nextBlock.getBlockType().equals(BlockType.FOR)) {
            ForBlock forBlock = (ForBlock) nextBlock;
            forBlock.setCloseY(nextPosY);
            return;
        }
        if (nextBlock.isPlaced() && nextBlock.getBlockType().equals(BlockType.IF)) {
            IfBlock ifBlock = (IfBlock) nextBlock;
            ifBlock.setCloseY(nextPosY);
            return;
        }
        if (nextBlock.isPlaced())
            return;
        nextBlock.setPosX(this.posX);
        nextBlock.setPosY(nextPosY);
        nextBlock.place();
    }
    public void setNextBlock(Block nextBlock) {
        setNextBlockAtHeight(nextBlock, this.posY + this.height);
    }
    public Block getNextBlock() {
        return nextBlock;
    }
    public Boolean isPlaced() {
        return isPlaced;
    }
    public void place() {
        this.isPlaced = true;
    }
    public Boolean getDrawToggle() {
        return drawToggle;
    }

    public Boolean hasNextBlock() {
        return nextBlock != null;
    }

    public abstract void draw();

    public void drawWithChildren(Boolean drawToggle) {
        if (this.drawToggle != drawToggle)
            return;
        this.draw();
        this.drawToggle = !this.drawToggle;
        if (hasNextBlock())
            nextBlock.drawWithChildren(drawToggle);
    }

    public void runWithChildren() {
        System.out.println(type);
        if (hasNextBlock())
            nextBlock.runWithChildren();
    }
}
