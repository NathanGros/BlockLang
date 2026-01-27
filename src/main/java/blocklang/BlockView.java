package blocklang;

import java.util.ArrayList;
import java.util.List;

import blocklang.blocks.*;

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
        DummyBlock dummyBlock2 = new DummyBlock();
        StopBlock stopBlock = new StopBlock();
        try {
            startBlock.setNextBlock(dummyBlock1);
            dummyBlock1.setNextBlock(whileBlock);
            // whileBlock.setInBlock(dummyBlock2);
            whileBlock.setNextBlock(whileBlockBottom);
            // dummyBlock2.setNextBlock(whileBlockBottom);
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
