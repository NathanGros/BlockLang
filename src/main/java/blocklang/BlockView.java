package blocklang;

import java.util.ArrayList;
import java.util.List;

import com.raylib.Raylib.Vector2;

import blocklang.blocks.*;
import blocklang.blocks.control.*;
import blocklang.blocks.events.*;
import blocklang.blocks.operators.*;

/**
 * BlockView
 */
public class BlockView {
    private List<PositionnedBlock> roots;

    public BlockView() {
        roots = new ArrayList<>();

        DummyBlock dummyBlock0 = new DummyBlock(-300.f, 200.f);
        StopBlock stopBlock0 = new StopBlock();
        dummyBlock0.setNextBlock(stopBlock0);
        roots.add(dummyBlock0);

        AndBlock andBlock = new AndBlock(-300.f, 0.f);
        roots.add(andBlock);

        OrBlock orBlock = new OrBlock(-300.f, 100.f);
        roots.add(orBlock);

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
        for (PositionnedBlock root: roots) {
            root.drawWithChildren();
        }
    }

    public PositionnedBlock selectBlock(Vector2 mouseWorldPosition) {
        List<PositionnedBlock> newRoots = new ArrayList<>();
        for (PositionnedBlock root: roots) {
            newRoots.add(root);
        }
        List<PositionnedBlock> reversedRoots = roots.reversed();
        for (PositionnedBlock root: reversedRoots) {
            PositionnedBlock selectedBlock = root.selectWithChildren(mouseWorldPosition);
            if (selectedBlock != null) {
                if (selectedBlock == root) {
                    newRoots.remove(root);
                    newRoots.add(root);
                } else {
                    newRoots.add(selectedBlock);
                }
                roots = newRoots;
                positionAll();
                return selectedBlock;
            }
        }
        return null;
    }

    public void insertBlockAtPos(PositionnedBlock selectedBlock, Vector2 mouseWorldPosition) {
        List<PositionnedBlock> reversedRoots = roots.reversed();
        for (PositionnedBlock root: reversedRoots) {
            if (selectedBlock == root) {
                continue;
            }
            Boolean inserted = root.insertWithChildren(selectedBlock, mouseWorldPosition);
            if (inserted) {
                roots.remove(selectedBlock);
                positionAll();
                return;
            }
        }
    }
}
