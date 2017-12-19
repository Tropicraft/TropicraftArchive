package net.tropicraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import net.tropicraft.mods.TropicraftMod;

public class BlockCoffeePlant extends TropicraftBlock {
	public static final int MAX_HEIGHT = 3;
	public static final int GROWTH_RATE_FERTILE = 10;
	public static final int GROWTH_RATE_INFERTILE = 20;
	private static final int RIPENING_RATE_FERTILE = 12;
	private static final int RIPENING_RATE_INFERTILE = 25;
	
	public BlockCoffeePlant(int i) {
		super(i, Material.plants);
		setTickRandomly(true);
        setRequiresSelfNotify();
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		meta = meta&7; // last three bits
		
		return blockIndexInTexture + meta;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		// top
		// x,y,z is not a bug - RenderBlocks passes in the coords of the neighboring block
		if (side == 1 && world.getBlockId(x,y,z) == blockID) {
			return false;
		}
		
		// bottom
		if (side == 0 && world.getBlockId(x,y,z) == blockID) {
			return false;
		}
		
		return super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	public boolean isOpaqueCube() {
        return false;
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }
	
	private void tryToGrowUpwards(World world, int x, int y, int z, Random random) {
		if (world.isAirBlock(x,y+1,z)) {
			int height;
			for (height = 1; world.getBlockId(x,y-height,z) == blockID; ++height);
			
			if (height < MAX_HEIGHT && random.nextInt(isFertile(world, x, y-height, z) ? GROWTH_RATE_FERTILE : GROWTH_RATE_INFERTILE) == 0) {
                world.setBlockAndMetadataWithNotify(x, y+1, z, blockID, 0);
			}
		}
	}
	
	private void tryToRipen(World world, int x, int y, int z, Random random) {
		int meta = world.getBlockMetadata(x, y, z)&7;
		
		// sanity check, meta shifter might do this to us
		if (meta == 7) {
			world.setBlockMetadataWithNotify(x, y, z, 6);
			return;
		}
		
		// already fully ripe?
		if (meta == 6) {
			return;
		}
		
		// don't grow in darkness
		if (world.getBlockLightValue(x, y + 1, z) < 9) {
			return;
		}
		
		// random chance of ripening
		if (random.nextInt(isFertile(world, x, y - 1, z) ? RIPENING_RATE_FERTILE : RIPENING_RATE_INFERTILE) != 0) {
			return;
		}
		
		// ripen
		world.setBlockMetadataWithNotify(x, y, z, meta+1);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.isRemote) {
			return;
		}
		
		tryToGrowUpwards(world, x, y, z, random);
		tryToRipen(world, x, y, z, random);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float offsetX, float offsetY, float offsetZ) {
		if ((world.getBlockMetadata(x, y, z)&7) != 6) {
			return false;
		}
		
		// no world.isRemote check needed - dropBlockAsItem_do contains one already
		world.setBlockMetadataWithNotify(x, y, z, 0);
		ItemStack stack = new ItemStack(TropicraftMod.coffeeBean, 1, 2);
		dropBlockAsItem_do(world, x, y, z, stack);
		return true;
	}

	@Override
	public int idDropped(int par1, Random random, int par3) {
		return TropicraftMod.coffeeBean.shiftedIndex;
	}

	@Override
	public int damageDropped(int meta) {
		return 2;
	}

	@Override
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return TropicraftMod.coffeeBean.shiftedIndex;
	}

	@Override
	public int getDamageValue(World par1World, int par2, int par3, int par4) {
		return 0;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return meta == 6 ? 1 : 0;
	}
	
    @Override
    public void onNeighborBlockChange (World world, int x, int y, int z, int neighborId) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockWithNotify(x, y, z, 0);
        }
    }
    
    @Override
    public boolean canBlockStay (World world, int x, int y, int z) {
        Block soil = blocksList[world.getBlockId(x, y - 1, z)];
        return (world.getFullBlockLightValue(x, y, z) >= 8 ||
        		world.canBlockSeeTheSky(x, y, z))
                && (soil != null && (soil.blockID == this.blockID || soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, TropicraftMod.coffeeBean)));
    }
    
    @Override
    public int getRenderType() {
    	return TropicraftMod.coffeePlantRenderId;
    }
}
