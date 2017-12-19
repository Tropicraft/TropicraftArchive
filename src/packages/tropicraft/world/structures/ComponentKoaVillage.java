package tropicraft.world.structures;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;

import tropicraft.Tropicraft;
import tropicraft.blocks.TropicraftBlocks;

public class ComponentKoaVillage extends StructureComponent {

	private static final int SEA_LEVEL = 63;
	
    private boolean hasGenerated = false;
    private int xSize = 256;
    private int ySize = 48;
    private int zSize = 256;
    private KoaVillageGenerator village;
    private List<Integer> waterBlocks = new LinkedList<Integer>();
    private List<Integer> sandBlocks = new LinkedList<Integer>();
    
    public ComponentKoaVillage() {
        waterBlocks.add(Block.waterMoving.blockID);
        waterBlocks.add(Block.waterStill.blockID);
        waterBlocks.add(TropicraftBlocks.tropicsWaterFlowing.blockID); 
        waterBlocks.add(TropicraftBlocks.tropicsWaterStationary.blockID);

        sandBlocks.add(Block.sand.blockID);
        sandBlocks.add(TropicraftBlocks.purifiedSand.blockID);
    }
    
    public ComponentKoaVillage(WorldChunkManager worldchunkmanager, Random random, int x, int z, World world) {
        super(0);
        
        waterBlocks.add(Block.waterMoving.blockID);
        waterBlocks.add(Block.waterStill.blockID);
        waterBlocks.add(TropicraftBlocks.tropicsWaterFlowing.blockID); 
        waterBlocks.add(TropicraftBlocks.tropicsWaterStationary.blockID);

        sandBlocks.add(Block.sand.blockID);
        sandBlocks.add(TropicraftBlocks.purifiedSand.blockID);

        village = new KoaVillageGenerator(world);
        
        // Set this component's orientation
        coordBaseMode = 0;

        // Get bounding box based on orientation
        boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(x, 0, z, 0, 0, 0, xSize, ySize, zSize, coordBaseMode);

    }

    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox structureboundingbox) {
        
        if (hasBeenGenerated()) {
            return false;
        } else {
        	village = new KoaVillageGenerator(world);
        }

        boundingBox.offset(0, SEA_LEVEL - boundingBox.minY - 1, 0);

        for (int scanU = 16; scanU < 48 && !hasBeenGenerated(); scanU++) {
            for (int scanV = 64; scanV < xSize - 64 && !hasBeenGenerated(); scanV++) {

                int x1 = scanU;
                int z1 = scanV;
                int x2 = scanV;
                int z2 = scanU;
                int x3 = xSize - scanU - 1;
                int z3 = zSize - scanV - 1;
                int x4 = xSize - scanV - 1;
                int z4 = zSize - scanU - 1;

                if (sandBlocks.contains(getBlockIdAtCurrentPosition(world, x1, 0, z1, boundingBox))
                        && waterBlocks.contains(getBlockIdAtCurrentPosition(world, x1 + 1, 0, z1, boundingBox))
                        && getBlockIdAtCurrentPosition(world, x1 - 1, 1, z1, boundingBox) == 0) {
               //     System.out.println(String.format("Generating village at %d, %d - %d", x1 + boundingBox.minX, z1 + boundingBox.minZ, 0));
                    setHasGenerated();
                    village.generate(world, random, x1 + boundingBox.minX, boundingBox.minY + 1, z1 + boundingBox.minZ, 0);
                }
                
                if (sandBlocks.contains(getBlockIdAtCurrentPosition(world, x2, 0, z2, boundingBox))
                    && waterBlocks.contains(getBlockIdAtCurrentPosition(world, x2, 0, z2 + 1, boundingBox))
                    && getBlockIdAtCurrentPosition(world, x2, 1, z2 - 1, boundingBox) == 0) {
               //     System.out.println(String.format("Generating village at %d, %d - %d", x2 + boundingBox.minX, z2 + boundingBox.minZ, 2));
                    setHasGenerated();
                    village.generate(world, random, x2 + boundingBox.minX, boundingBox.minY + 1, z2 + boundingBox.minZ, 2);
                }
                
                if (sandBlocks.contains(getBlockIdAtCurrentPosition(world, x3, 0, z3, boundingBox))
                        && waterBlocks.contains(getBlockIdAtCurrentPosition(world, x3 - 1, 0, z3, boundingBox))
                        && getBlockIdAtCurrentPosition(world, x3 + 1, 1, z3, boundingBox) == 0) {
                //    System.out.println(String.format("Generating village at %d, %d - %d", x3 + boundingBox.minX, z3 + boundingBox.minZ, 1));
                    setHasGenerated();
                    village.generate(world, random, x3 + boundingBox.minX, boundingBox.minY + 1, z3 + boundingBox.minZ, 1);
                }
                
                if (sandBlocks.contains(getBlockIdAtCurrentPosition(world, x4, 0, z4, boundingBox))
                        && waterBlocks.contains(getBlockIdAtCurrentPosition(world, x4, 0, z4 - 1, boundingBox))
                        && getBlockIdAtCurrentPosition(world, x4, 0, z4 + 1, boundingBox) == 0) {
              //      System.out.println(String.format("Generating village at %d, %d - %d", x4 + boundingBox.minX, z4 + boundingBox.minZ, 3));
                    setHasGenerated();
                    village.generate(world, random, x4 + boundingBox.minX, boundingBox.minY + 1, z4 + boundingBox.minZ, 3);
                }
            }
        }

        return true;
    }

    private void setHasGenerated() {
        hasGenerated = true;
    }

    public boolean hasBeenGenerated() {
        return hasGenerated;
    }

    /**
     * For loading
     */
	@Override
	protected void func_143012_a(NBTTagCompound nbt) {
		this.hasGenerated = nbt.getBoolean("hasGenerated");
		
	}

	/**
	 * For setting
	 */
	@Override
	protected void func_143011_b(NBTTagCompound nbt) {
		nbt.setBoolean("hasGenerated", hasGenerated);
	}
}