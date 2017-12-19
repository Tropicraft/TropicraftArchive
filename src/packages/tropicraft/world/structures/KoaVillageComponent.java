package tropicraft.world.structures;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import java.util.Arrays;
import java.util.List;

import tropicraft.blocks.TropicraftBlocks;

public abstract class KoaVillageComponent {

    //private final Block[] waterBlocks = new Block[]{Block.waterMoving, Block.waterStill, mod_palmLeaves.waterMovingTropics, mod_palmLeaves.waterStillTropics};
    //private List waterBlockList = Arrays.asList(waterBlocks);
    protected static final List<Integer> waterBlockIDs = Arrays.asList(Block.waterMoving.blockID, Block.waterStill.blockID, TropicraftBlocks.tropicsWaterFlowing.blockID, TropicraftBlocks.tropicsWaterStationary.blockID);
    protected int homeX, homeY, homeZ;
    protected int genX, genY, genZ;
    protected int genDirection = -1;
    protected KoaVillage village;
    protected StructureBoundingBox boundingBox;

    public KoaVillageComponent() {
    }
    
    public abstract boolean canFitInWorld(World world);
    
    public abstract void generate(World world);
    
    public abstract void spawn(World world);
    
    public StructureBoundingBox getBoundingBox() {
        return boundingBox;
    }

    protected void place(World world, int x, int y, int z, Block b) {
        world.setBlock(x, y, z, b.blockID);
    }

    protected void place(World world, int i, int j, int k, Block block, int meta) {
        placeWithMetadata(world, i, j, k, block, meta);
    }

    protected void placeDownTilGround(World world, int x, int y, int z, Block block1) {
        while (waterBlockIDs.contains(world.getBlockId(x, y, z)) || (world.getBlockId(x, y, z) == 0 && y > 0)) {
            place(world, x, y, z, block1);
            y--;
        }
    }

    protected void placeWithMetadata(World world, int i, int j, int k, Block block, int meta) {
        world.setBlock(i, j, k, block.blockID, meta, 3);
    }

    protected void place(World world, int i, int j, int k, int l) {
        world.setBlock(i, j, k, l);
    }

    protected void placeBlockInAdjacentAirWithRadius(World world, int i2, int j2,
            int k2, int r1, int r2, Block blockgold) {

        for (int a = r1; a < r2; a++) {
            int[] asdf = new int[3];

            if (world.getBlockId(i2 + a, j2, k2) == 0) {
                asdf[0] = i2 + a;
                asdf[1] = j2;
                asdf[2] = k2;
                place(world, asdf[0], asdf[1], asdf[2], blockgold);
            }
            if (world.getBlockId(i2 - a, j2, k2) == 0) {
                asdf[0] = i2 - a;
                asdf[1] = j2;
                asdf[2] = k2;
                place(world, asdf[0], asdf[1], asdf[2], blockgold);
            }
            if (world.getBlockId(i2 + a, j2, k2 + a) == 0) {
                asdf[0] = i2 + a;
                asdf[1] = j2;
                asdf[2] = k2 + a;
                place(world, asdf[0], asdf[1], asdf[2], blockgold);
            }
            if (world.getBlockId(i2 - a, j2, k2 - a) == 0) {
                asdf[0] = i2 - a;
                asdf[1] = j2;
                asdf[2] = k2 - a;
                place(world, asdf[0], asdf[1], asdf[2], blockgold);
            }
            if (world.getBlockId(i2, j2, k2 + a) == 0) {
                asdf[0] = i2;
                asdf[1] = j2;
                asdf[2] = k2 + a;
                place(world, asdf[0], asdf[1], asdf[2], blockgold);
            }
            if (world.getBlockId(i2, j2, k2 - a) == 0) {
                asdf[0] = i2;
                asdf[1] = j2;
                asdf[2] = k2 - a;
                place(world, asdf[0], asdf[1], asdf[2], blockgold);
            }
            if (world.getBlockId(i2 - a, j2, k2 + a) == 0) {
                asdf[0] = i2 - a;
                asdf[1] = j2;
                asdf[2] = k2 + a;
                place(world, asdf[0], asdf[1], asdf[2], blockgold);
            }
            if (world.getBlockId(i2 + a, j2, k2 - a) == 0) {
                asdf[0] = i2 + a;
                asdf[1] = j2;
                asdf[2] = k2 - a;
                place(world, asdf[0], asdf[1], asdf[2], blockgold);
            }
        }
    }
}
