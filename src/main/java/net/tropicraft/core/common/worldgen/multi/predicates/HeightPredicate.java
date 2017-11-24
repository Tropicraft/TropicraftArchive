package net.tropicraft.core.common.worldgen.multi.predicates;

import com.google.common.base.Predicate;

import net.minecraft.world.chunk.Chunk;

public class HeightPredicate implements Predicate<Chunk> {

    private int minHeight;

    public HeightPredicate(int minHeight) {
        this.minHeight = minHeight;
    }

    @Override
    public boolean apply(Chunk chunk) {
        return chunk.getLowestHeight() >= this.minHeight;
    }

}
