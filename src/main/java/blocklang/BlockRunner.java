package blocklang;

import java.util.List;

import blocklang.blocks.*;

/**
 * BlockRunner
 */
public class BlockRunner {
    public static void run(List<Block> blocks) {
        for (Block block: blocks) {
            if (!(block instanceof StartBlock))
                continue;
            Block currentBlock = block;
            while (currentBlock.hasNextBlock()) {
                currentBlock = currentBlock.getNextBlock();
                System.out.println(currentBlock);
            }
        }
    }
}
