package net.tropicraft.dungeons;

import net.minecraft.util.ChunkCoordinates;

public class DungeonChunkCoordinates extends ChunkCoordinates {

	/**
	 * Metadata for this ChunkCoordinates
	 */
	public int metadata;
	
	public DungeonChunkCoordinates() {
	}

	public DungeonChunkCoordinates(int par1, int par2, int par3) {
		super(par1, par2, par3);
	}

	public DungeonChunkCoordinates(ChunkCoordinates par1ChunkCoordinates) {
		super(par1ChunkCoordinates);
	}
	
    public boolean equals(Object par1Obj)
    {
        if (!(par1Obj instanceof ChunkCoordinates))
        {
            return false;
        }
        else
        {
            DungeonChunkCoordinates var2 = (DungeonChunkCoordinates)par1Obj;
            return this.posX == var2.posX && this.posY == var2.posY && this.posZ == var2.posZ && this.metadata == var2.metadata;
        }
    }
    
    
}
