package blocklang;

import java.util.ArrayList;
import java.util.List;

import blocklang.blocks.*;
import blocklang.blocks.control.*;
import blocklang.blocks.events.*;
import blocklang.blocks.operators.*;

/**
 * BlockRunner
 */
public class BlockView {
    private List<Block> roots;

    public BlockView() {
        roots = new ArrayList<>();
        StartBlock startBlock = new StartBlock(0.f, 0.f);
        DummyBlock dummyBlock1 = new DummyBlock();
        ForBlock forBlock = new ForBlock();
        DummyBlock dummyBlock2 = new DummyBlock();
        IfBlock ifBlock1 = new IfBlock();
        DummyBlock dummyBlock3 = new DummyBlock();
        EqualsBlock equalsBlock1 = new EqualsBlock();
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
        equalsBlock1.setValue1(1);
        equalsBlock1.setValue2(1);
        ifBlock1.setConditionBlock(equalsBlock1);
        ifBlock1.setInBlock(dummyBlock3);
        forBlock.setNextBlock(whileBlock1);
        whileBlock1.setInBlock(dummyBlock4);
        whileBlock1.setNextBlock(ifElseBlock);
        ifElseBlock.setInTrueBlock(dummyBlock5);
        ifElseBlock.setNextBlock(stopBlock);
        roots.add(startBlock);
    }

    public List<Block> getRoots() {
        return roots;
    }

    public void positionAll() {
        for (Block root: roots) {
            if (!root.getBlockType().equals(BlockType.START))
                continue;
            root.positionWithChildren(root.getPos());
        }
    }

    public void runAll() {
        System.out.println("Running...");
        for (Block root: roots) {
            if (!root.getBlockType().equals(BlockType.START))
                continue;
            root.runWithChildren();
        }
    }

    public void drawAll() {
        for (Block block: roots) {
            block.drawWithChildren();
        }
    }
}
