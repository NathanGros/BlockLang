package blocklang.blocks.operators;

import static com.raylib.Raylib.DrawPoly;
import static com.raylib.Raylib.DrawRectangleRec;

import com.raylib.Raylib.Color;
import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

import blocklang.blocks.BooleanBlock;
import blocklang.blocks.ValueBlock;
import blocklang.blocks.variables.NumberBlock;

/**
 * CompareBlock
 */
public class EqualsBlock extends BooleanBlock {
    private ValueBlock value1;
    private ValueBlock value2;
    static Float CENTER_WIDTH = 20.f;

    public EqualsBlock(Float posX, Float posY) {
        super(posX, posY, 100.f, 40.f);
        value1 = new NumberBlock();
        value2 = new NumberBlock();
        positionValueBlocks();
    }
    public EqualsBlock() {
        this(0.f, 0.f);
    }

    @Override
    public void setPosX(Float posX) {
        super.setPosX(posX);
        positionValueBlocks();
    }
    @Override
    public void setPosY(Float posY) {
        super.setPosY(posY);
        positionValueBlocks();
    }
    public void setValue1(Integer value) {
        NumberBlock number = new NumberBlock();
        number.setValue(value.floatValue());
        this.value1 = number;
        positionValueBlocks();
    }
    public void setValue2(Integer value) {
        NumberBlock number = new NumberBlock();
        number.setValue(value.floatValue());
        this.value2 = number;
        positionValueBlocks();
    }

    private void positionValueBlocks() {
        Float margin = 3.f;
        Float valuesHeight = Math.max(value1.getHeight(), value2.getHeight());
        this.height = 2.f * margin + valuesHeight;
        this.width = CENTER_WIDTH + 4.f * margin + height + value1.getWidth() + value2.getWidth();
        value1.setPosX(this.posX + height / 2.f + margin);
        value1.setPosY(this.posY + margin + (valuesHeight - value1.getHeight()) / 2.f);
        value2.setPosX(value1.getPosX() + value1.getWidth() + CENTER_WIDTH + 2.f * margin);
        value2.setPosY(this.posY + margin + (valuesHeight - value2.getHeight()) / 2.f);
    }
	@Override
	public Boolean isTrue() {
        return value1.equals(value2);
	}

	@Override
	public void draw() {
        Rectangle center = new Rectangle();
        Float halfHeight = height / 2.f;
        center.x(posX + halfHeight);
        center.y(posY);
        center.width(width - height);
        center.height(height);
        Vector2 leftCenter = new Vector2();
        leftCenter.x(posX + halfHeight);
        leftCenter.y(posY + halfHeight);
        Vector2 rightCenter = new Vector2();
        rightCenter.x(posX + width - halfHeight);
        rightCenter.y(posY + halfHeight);
        Float radius = halfHeight;
        Color color = new Color();
        color.r((byte) 89);
        color.g((byte) 192);
        color.b((byte) 89);
        color.a((byte) 255);
        DrawRectangleRec(center, color);
        DrawPoly(leftCenter, 4, radius, 0.f, color);
        DrawPoly(rightCenter, 4, radius, 0.f, color);
        value1.draw();
        value2.draw();
	}
}
