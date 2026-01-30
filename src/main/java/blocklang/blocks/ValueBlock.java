package blocklang.blocks;

import static com.raylib.Raylib.CheckCollisionPointRec;

import com.raylib.Raylib.Rectangle;
import com.raylib.Raylib.Vector2;

/**
 * ValueBlock
 */
public abstract class ValueBlock extends PositionnedBlock {
    public ValueBlock(Float posX, Float posY, Float width, Float height) {
        super(posX, posY, width, height);
    }

    public abstract Boolean equals(ValueBlock value);

    public PositionnedBlock selectWithChildren(Vector2 mousePos) {
        Rectangle shape = new Rectangle();
        shape.x(getPosX());
        shape.y(getPosY());
        shape.width(width);
        shape.height(height);
        if (CheckCollisionPointRec(mousePos, shape))
            return this;
        return null;
    }
}
