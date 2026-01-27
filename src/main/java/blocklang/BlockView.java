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
        StartBlock startBlock = new StartBlock(100.f, 100.f);
        DummyBlock dummyBlock1 = new DummyBlock(100.f, 130.f);
        WhileBlock whileBlock = new WhileBlock(100.f, 160.f);
        WhileBlockBottom whileBlockBottom = new WhileBlockBottom(100.f, 220.f);
        DummyBlock dummyBlock2 = new DummyBlock(100.f, 190.f);
        StopBlock stopBlock = new StopBlock(100.f, 250.f);
        startBlock.setNextBlock(dummyBlock1);
        dummyBlock1.setNextBlock(whileBlock);
        whileBlock.setInBlock(dummyBlock2);
        dummyBlock2.setNextBlock(whileBlockBottom);
        whileBlock.setNextBlock(whileBlockBottom);
        whileBlockBottom.setNextBlock(stopBlock);
        roots.add(startBlock);
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
