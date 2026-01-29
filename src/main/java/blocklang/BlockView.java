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
        WhileBlock whileBlock1 = new WhileBlock();
        EqualsBlock equalsBlock1 = new EqualsBlock();
        WhileBlock whileBlock2 = new WhileBlock();
        DummyBlock dummyBlock2 = new DummyBlock();
        DummyBlock dummyBlock3 = new DummyBlock();
        StopBlock stopBlock = new StopBlock();
        startBlock.setNextBlock(dummyBlock1);
        dummyBlock1.setNextBlock(forBlock);
        forBlock.setNbRepetitions(3);
        forBlock.setInBlock(dummyBlock2);
        dummyBlock2.setNextBlock(whileBlock1);
        equalsBlock1.setValue1(3);
        equalsBlock1.setValue2(4);
        whileBlock1.setConditionBlock(equalsBlock1);
        whileBlock1.setNextBlock(forBlock);
        forBlock.setNextBlock(whileBlock2);
        whileBlock2.setInBlock(dummyBlock3);
        dummyBlock3.setNextBlock(whileBlock2);
        whileBlock2.setNextBlock(stopBlock);
        roots.add(startBlock);
    }

    public List<Block> getRoots() {
        return roots;
    }

    public void drawAll() {
        for (Block block: roots) {
            block.drawWithChildren(block.getDrawToggle());
        }
    }
}
