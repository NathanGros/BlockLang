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
        WhileBlock whileBlock1 = new WhileBlock();
        EqualsBlock equalsBlock1 = new EqualsBlock();
        WhileBlock whileBlock2 = new WhileBlock();
        EqualsBlock equalsBlock2 = new EqualsBlock();
        WhileBlock whileBlock3 = new WhileBlock();
        EqualsBlock equalsBlock3 = new EqualsBlock();
        DummyBlock dummyBlock2 = new DummyBlock();
        StopBlock stopBlock = new StopBlock();
        startBlock.setNextBlock(dummyBlock1);
        dummyBlock1.setNextBlock(whileBlock1);
        equalsBlock1.setValue1(1);
        equalsBlock1.setValue2(2);
        whileBlock1.setConditionBlock(equalsBlock1);
        whileBlock1.setInBlock(dummyBlock2);
        dummyBlock2.setNextBlock(whileBlock2);
        equalsBlock2.setValue1(3);
        equalsBlock2.setValue2(4);
        whileBlock2.setConditionBlock(equalsBlock2);
        whileBlock2.setNextBlock(whileBlock1);
        whileBlock1.setNextBlock(whileBlock3);
        equalsBlock3.setValue1(5);
        equalsBlock3.setValue2(6);
        whileBlock3.setConditionBlock(equalsBlock3);
        whileBlock3.setNextBlock(stopBlock);
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
