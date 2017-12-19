package net.tropicraft.blocks.liquids;

import net.minecraft.block.Block;

public class LAPILiquidPair {

	int block1ID;
	int block2ID;
	
	public LAPILiquidPair(Block block, Block pairedBlock) throws Exception {
		block1ID = block.blockID;
		block2ID = pairedBlock.blockID;
		
		if(block1ID == block2ID)
			throw new Exception("[LAPI] Paired block ids are the same, this is illegal and will crash the game");
		
	}
	
	public int getPairedId(int id) {
		if(id == block1ID) return block2ID;
		else
			if(id == block2ID) return block1ID;
			else
				return -1;
	//	return id == block1ID ? block2ID : id == block2ID ? block1ID : illegalPairedId();
	}

	private int illegalPairedId() {
		System.out.println("[LAPI] ILLEGAL PAIRED ID");
		return -1;
	}

}
