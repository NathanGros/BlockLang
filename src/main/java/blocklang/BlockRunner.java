package blocklang;

import java.util.List;

import blocklang.blocks.*;

/**
 * BlockRunner
 */
public class BlockRunner {
    public static void run(List<Block> roots) {
        System.out.println("Running...");
        for (Block root: roots) {
            if (!root.getBlockType().equals(BlockType.START))
                continue;
            root.runWithChildren();
        }
    }
}
