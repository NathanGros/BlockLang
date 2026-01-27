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
        WhileBlock whileBlock = new WhileBlock();
        WhileBlockBottom whileBlockBottom = new WhileBlockBottom();
        EqualsBlock equalsBlock = new EqualsBlock();
        DummyBlock dummyBlock2 = new DummyBlock();
        StopBlock stopBlock = new StopBlock();
        try {
            startBlock.setNextBlock(dummyBlock1);
            dummyBlock1.setNextBlock(whileBlock);
            equalsBlock.setValue1(1);
            equalsBlock.setValue2(1);
            whileBlock.setConditionBlock(equalsBlock);
            whileBlock.setInBlock(dummyBlock2);
            whileBlock.setNextBlock(whileBlockBottom);
            dummyBlock2.setNextBlock(whileBlockBottom);
            whileBlockBottom.setNextBlock(stopBlock);
            roots.add(startBlock);
        } catch (InvalidBlockException e) {
            System.out.println("Error: " + e);
        }
    }

    public List<Block> getRoots() {
        return roots;
    }

    public void drawAll() {
        for (Block block: roots) {
            block.drawWithChildren();
        }
    }
}
