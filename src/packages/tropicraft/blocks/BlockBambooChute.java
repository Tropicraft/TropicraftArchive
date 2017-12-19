package tropicraft.blocks;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import tropicraft.ModInfo;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBambooChute extends BlockTropicraft implements IPlantable {

	/**
	 * Number of random ticks this block should go through before growing
	 */
	private static final int NUM_TICKS_GROWTH = 6;
	
	/**
	 * How tall, in blocks, this plant should grow
	 */
	private int plantHeight;
	
	public BlockBambooChute(int id, Material material) {
		super(id, material);
		float f = 0.375F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
		setTickRandomly(true);
	}

	/**
	 * Called every random tick of this block, only on the server (YEAH OK THANKS NEWT)
	 * @param world World instance
	 * @param x xCoord in the world
	 * @param y yCoord in the world
	 * @param z zCoord in the world
	 * @param random java.util.Random instance
	 */
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.isAirBlock(x, y + 1, z)) {
			int plantHeight;	//number of blocks tall
			for (plantHeight = 1; world.getBlockId(x, y - plantHeight, z) == blockID; plantHeight++) {
			}
			if (plantHeight < 12) {
				int meta = world.getBlockMetadata(x, y, z);
				if (meta == 8) {
					world.setBlock(x, y + 1, z, blockID);
					world.setBlockMetadataWithNotify(x, y, z, 0, 3);
				} else {
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
				}
			}			
		}		
	}


	/**
	 * Called when the block is placed, determines if bamboo belongs here
	 * @param World world object
	 * @param i xCoord
	 * @param j yCoord
	 * @param k zCoord
	 * @return whether this block can be placed at the coords sent in
	 */
	
	@Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
		int idBelow = world.getBlockId(i, j - 1, k);
		int idAdjacentX1 = world.getBlockId(i - 1, j - 1, k);
		int idAdjacentX2 = world.getBlockId(i + 1, j - 1, k);
		int idAdjacentZ1 = world.getBlockId(i, j - 1, k - 1);
		int idAdjacentZ2 = world.getBlockId(i, j - 1, k + 1);
		
		if (idBelow == blockID) {
			return true;
		}
		if (idBelow != Block.grass.blockID && idBelow != Block.dirt.blockID) {
			return false;
		}
		if (idAdjacentX1 == Block.dirt.blockID || idAdjacentX1 == Block.grass.blockID) {
			return true;
		}
		if (idAdjacentX2 == Block.dirt.blockID || idAdjacentX2 == Block.grass.blockID) {
			return true;
		}
		if (idAdjacentZ1 == Block.dirt.blockID || idAdjacentZ1 == Block.grass.blockID) {
			return true;
		} else {
			return idAdjacentZ2 == Block.grass.blockID;
		}
    }
	
    /**
     * only called by clickMiddleMouseButton, and passed to inventory.setCurrentItem (along with isCreative)
     */
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return this.blockID;
    }
    
    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 1;
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return TropicraftItems.bambooChute.itemID;
    }
    
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }
    
    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return this.canPlaceBlockAt(par1World, par2, par3, par4);
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID)
    {
        this.checkBlockCoordValid(world, x, y, z);
    }
    
    /**
     * Checks if current block pos is valid, if not, breaks the block as dropable item. Used for reed and cactus.
     */
    protected final void checkBlockCoordValid(World world, int x, int y, int z)
    {
        if (!this.canBlockStay(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }
    
    /**
     * Determines if this block can support the passed in plant, allowing it to be planted and grow.
     * Some examples:
     *   Reeds check if its a reed, or if its sand/dirt/grass and adjacent to water
     *   Cacti checks if its a cacti, or if its sand
     *   Nether types check for soul sand
     *   Crops check for tilled soil
     *   Caves check if it's a colid surface
     *   Plains check if its grass or dirt
     *   Water check if its still water
     *
     * @param world The current world
     * @param x X Position
     * @param y Y Position
     * @param z Z position
     * @param direction The direction relative to the given position the plant wants to be, typically its UP
     * @param plant The plant that wants to check
     * @return True to allow the plant to be planted/stay.
     */
    @Override
    public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant)
    {
        int plantID = plant.getPlantID(world, x, y + 1, z);
        EnumPlantType plantType = plant.getPlantType(world, x, y + 1, z);

        if (plantID == blockID)
        {
            return true;
        }

        switch (plantType)
        {
            case Desert: return blockID == sand.blockID;
            case Nether: return blockID == slowSand.blockID;
            case Crop:   return blockID == tilledField.blockID;
            case Cave:   return isBlockSolidOnSide(world, x, y, z, UP);
            case Plains: return blockID == grass.blockID || blockID == dirt.blockID;
            case Water:  return world.getBlockMaterial(x, y, z) == Material.water && world.getBlockMetadata(x, y, z) == 0;
            case Beach:
                boolean isBeach = (blockID == Block.grass.blockID || blockID == Block.dirt.blockID || blockID == Block.sand.blockID);
                boolean hasWater = (world.getBlockMaterial(x - 1, y, z    ) == Material.water ||
                                    world.getBlockMaterial(x + 1, y, z    ) == Material.water ||
                                    world.getBlockMaterial(x,     y, z - 1) == Material.water ||
                                    world.getBlockMaterial(x,     y, z + 1) == Material.water);
                return isBeach && hasWater;
        }

        return false;
    }
    
	/**
	 * @return Get the image name for this block
	 */
	@Override
	public String getImageName() {
		return "bambooChute";
	}

	/**
	 * Type of plant
	 */
    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z)
    {
        return EnumPlantType.Plains;
    }

    /**
     * Plant id, used for the IPlantable interface
     */
    @Override
    public int getPlantID(World world, int x, int y, int z)
    {
        return blockID;
    }

    /**
     * Metadata of plant
     */
    @Override
    public int getPlantMetadata(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }

}
