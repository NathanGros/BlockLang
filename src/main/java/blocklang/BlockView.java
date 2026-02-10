package blocklang;

import static com.raylib.Raylib.*;

import static com.raylib.Raylib.GetMouseWheelMoveV;
import static com.raylib.Raylib.GetScreenHeight;
import static com.raylib.Raylib.GetScreenWidth;
import static com.raylib.Raylib.Vector2Zero;

import java.util.ArrayList;
import java.util.List;

import com.raylib.Raylib.Camera2D;
import com.raylib.Raylib.Vector2;

import blocklang.blocks.DummyBlock;
import blocklang.blocks.Position;
import blocklang.blocks.PositionnedBlock;
import blocklang.blocks.control.ForBlock;
import blocklang.blocks.control.IfBlock;
import blocklang.blocks.control.IfElseBlock;
import blocklang.blocks.control.WhileBlock;
import blocklang.blocks.events.StartBlock;
import blocklang.blocks.events.StopBlock;
import blocklang.blocks.operators.AddBlock;
import blocklang.blocks.operators.AndBlock;
import blocklang.blocks.operators.EqualsBlock;
import blocklang.blocks.operators.NotBlock;

/**
 * BlockView
 */
public class BlockView {
    private int viewX;
    private int viewY;
    private Camera2D camera;
    private boolean shouldUpdateFont;
    private float timerFontReload;
    private Boolean dragScreenMode;
    private Boolean moveSelectedBlockMode;
    private PositionnedBlock selectedBlock;
    private Vector2 mousePosition;
    private List<PositionnedBlock> roots;

    public BlockView() {
        viewX = 0;
        viewY = 0;
        camera = new Camera2D();
        camera.offset(Vector2Zero());
        camera.target(Vector2Zero());
        camera.rotation(0.0f);
        camera.zoom(2.0f);
        shouldUpdateFont = true;
        timerFontReload = 10.f;
        dragScreenMode = false;
        moveSelectedBlockMode = false;
        selectedBlock = null;
        mousePosition = GetMousePosition();

        // Font
        FontUtil.setWantedWorldFontSize(15.f);
        FontUtil.reloadFont(camera);

        roots = new ArrayList<>();

        DummyBlock dummyBlock0 = new DummyBlock(-300.f, 200.f);
        StopBlock stopBlock0 = new StopBlock();
        dummyBlock0.setNextBlock(stopBlock0);
        roots.add(dummyBlock0);

        AndBlock andBlock = new AndBlock(-300.f, 0.f);
        roots.add(andBlock);

        AddBlock addBlock = new AddBlock(-300.f, 100.f);
        roots.add(addBlock);

        StartBlock startBlock = new StartBlock(0.f, 0.f);
        DummyBlock dummyBlock1 = new DummyBlock();
        ForBlock forBlock = new ForBlock();
        DummyBlock dummyBlock2 = new DummyBlock();
        IfBlock ifBlock1 = new IfBlock();
        DummyBlock dummyBlock3 = new DummyBlock();
        EqualsBlock equalsBlock = new EqualsBlock();
        NotBlock notBlock = new NotBlock();
        WhileBlock whileBlock1 = new WhileBlock();
        DummyBlock dummyBlock4 = new DummyBlock();
        IfElseBlock ifElseBlock = new IfElseBlock();
        DummyBlock dummyBlock5 = new DummyBlock();
        StopBlock stopBlock = new StopBlock();

        startBlock.setNextBlock(dummyBlock1);
        dummyBlock1.setNextBlock(forBlock);
        forBlock.setNbRepetitions(3);
        forBlock.setInBlock(dummyBlock2);
        dummyBlock2.setNextBlock(ifBlock1);
        equalsBlock.setValue1(1);
        equalsBlock.setValue2(1);
        ifBlock1.setConditionBlock(equalsBlock);
        ifBlock1.setInBlock(dummyBlock3);
        forBlock.setNextBlock(whileBlock1);
        whileBlock1.setInBlock(dummyBlock4);
        whileBlock1.setNextBlock(ifElseBlock);
        ifElseBlock.setConditionBlock(notBlock);
        ifElseBlock.setInTrueBlock(dummyBlock5);
        ifElseBlock.setNextBlock(stopBlock);
        roots.add(startBlock);

        positionAll();
    }

    public List<PositionnedBlock> getRoots() {
        return roots;
    }

    public void positionAll() {
        for (PositionnedBlock root: roots) {
            root.positionWithChildren(root.getPos());
        }
    }

    public void runAll() {
        System.out.println("Running...");
        for (PositionnedBlock root: roots) {
            if (root instanceof StartBlock start)
                start.runWithChildren();
        }
    }

    public void drawAll() {
        DrawRectangle(viewX, viewY, GetScreenWidth() - viewX, GetScreenHeight(), Colors.getBlockViewBackgroundColor());
        BeginMode2D(camera);
        for (PositionnedBlock root: roots) {
            root.drawWithChildren();
        }
        if (selectedBlock != null) {
            selectedBlock.drawWithChildren();
        }
        EndMode2D();
    }

    public PositionnedBlock selectBlock(Vector2 mouseWorldPosition) {
        List<PositionnedBlock> newRoots = new ArrayList<>();
        for (PositionnedBlock root: roots) {
            newRoots.add(root);
        }
        List<PositionnedBlock> reversedRoots = roots.reversed(); // Search in reverse order to select blocks on "top" first
        for (PositionnedBlock root: reversedRoots) {
            PositionnedBlock selectedBlock = root.selectWithChildren(mouseWorldPosition);
            if (selectedBlock == null)
                continue;
            if (selectedBlock == root) // Don't add twice
                newRoots.remove(root);
            newRoots.add(selectedBlock); // Put the selected block last (on "top")
            roots = newRoots;
            positionAll();
            return selectedBlock;
        }
        return null;
    }

    public void handleLeftClick() {
        selectedBlock = selectBlock(GetScreenToWorld2D(GetMousePosition(), camera));
        if (selectedBlock == null) {
            dragScreenMode = true;
            mousePosition = GetMousePosition();
        } else {
            moveSelectedBlockMode = true;
            mousePosition = GetMousePosition();
        }
    }

    public void insertBlockAtPos(PositionnedBlock selectedBlock, Vector2 mouseWorldPosition) {
        List<PositionnedBlock> reversedRoots = roots.reversed();
        for (PositionnedBlock root: reversedRoots) {
            if (selectedBlock == root) { // Dont insert in self
                continue;
            }
            Boolean inserted = root.insertWithChildren(selectedBlock, mouseWorldPosition);
            if (inserted) {
                roots.remove(selectedBlock);
                root.positionWithChildren(root.getPos());
                return;
            }
        }
    }

    public void handleLeftRelease() {
        if (dragScreenMode) {
            dragScreenMode = false;
        }
        if (moveSelectedBlockMode) {
            moveSelectedBlockMode = false;
            insertBlockAtPos(selectedBlock, GetScreenToWorld2D(mousePosition, camera));
            selectedBlock = null;
        }
    }

    public void refreshSize() {
        viewX = GetScreenWidth() / 5;
        viewY = 0;
        Vector2 newOffset = new Vector2();
        newOffset.x((GetScreenWidth() + viewX) / 2.f);
        newOffset.y(GetScreenHeight() / 2);
        camera.offset(newOffset);
    }

    public void updateZoom() {
        Float mouseWheelMovementY = GetMouseWheelMoveV().y();
        if (mouseWheelMovementY != 0.f) {
            if (mouseWheelMovementY > 0) {
                camera.zoom(camera.zoom() * 1.3f);
            } else {
                camera.zoom(camera.zoom() / 1.3f);
            }
            timerFontReload = 0.f;
            shouldUpdateFont = true;
        }
        if (camera.zoom() < 0.3f)
        camera.zoom(0.3f);
        if (camera.zoom() > 3.f)
        camera.zoom(3.f);
    }

    public void updateFontTimer() {
        timerFontReload += GetFrameTime();
    }

    public void reloadFontIfNeeded() {
        if (shouldUpdateFont && timerFontReload > 2.f) {
            FontUtil.reloadFont(camera);
            timerFontReload = 0.f;
            shouldUpdateFont = false;
        }
    }

    public void updateWhileDragScreenMode() {
        if (dragScreenMode) {
            Vector2 newMousePosition = GetMousePosition();
            Vector2 oldMouseWorldPosition = GetScreenToWorld2D(mousePosition, camera);
            Vector2 newMouseWorldPosition = GetScreenToWorld2D(newMousePosition, camera);
            camera.target(Vector2Add(Vector2Subtract(camera.target(), newMouseWorldPosition), oldMouseWorldPosition));
            mousePosition = newMousePosition;
        }
    }

    public void updateWhileDragBlockMode() {
        if (moveSelectedBlockMode) {
            Vector2 newMousePosition = GetMousePosition();
            Vector2 oldMouseWorldPosition = GetScreenToWorld2D(mousePosition, camera);
            Vector2 newMouseWorldPosition = GetScreenToWorld2D(newMousePosition, camera);
            Vector2 moveVector = Vector2Subtract(newMouseWorldPosition, oldMouseWorldPosition);
            Vector2 oldBlockPosition = new Vector2();
            oldBlockPosition.x(selectedBlock.getPosX());
            oldBlockPosition.y(selectedBlock.getPosY());
            Vector2 newBlockPosition = Vector2Add(oldBlockPosition, moveVector);
            selectedBlock.positionWithChildren(new Position(newBlockPosition.x(), newBlockPosition.y()));
            mousePosition = newMousePosition;
        }
    }
}
