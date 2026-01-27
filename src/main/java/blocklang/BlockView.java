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
        StopBlock stopBlock = new StopBlock(100.f, 190.f);
        DummyBlock dummyBlock1 = new DummyBlock(100.f, 130.f);
        DummyBlock dummyBlock2 = new DummyBlock(100.f, 160.f);
        startBlock.setNextBlock(dummyBlock1);
        dummyBlock1.setNextBlock(dummyBlock2);
        dummyBlock2.setNextBlock(stopBlock);
        roots.add(startBlock);
    }

    public List<Block> getRoots() {
        return roots;
    }

    public void drawAll() {
        for (Block block: roots) {
            Block currentBlock = block;
            currentBlock.draw();
            while (currentBlock.hasNextBlock()) {
                currentBlock = currentBlock.getNextBlock();
                currentBlock.draw();
            }
        }
    }
}
