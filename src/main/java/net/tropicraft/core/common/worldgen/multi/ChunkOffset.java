package net.tropicraft.core.common.worldgen.multi;

import net.minecraft.util.math.ChunkPos;

public class ChunkOffset {

    public int x;
    public int z;
    
    public ChunkOffset(int x, int z) {
        this.x = x;
        this.z = z;
    }
    
    public ChunkPos getPosOffset(ChunkPos pos) {
        return new ChunkPos(pos.x + this.x, pos.z + this.z);
    }
}
