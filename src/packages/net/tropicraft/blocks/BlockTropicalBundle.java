package net.tropicraft.blocks;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockTropicalBundle extends BlockTropicalWood {

	public BlockTropicalBundle(int i) {
		super(i);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) {
		if (j == 0) {
			if (i == 1|| i == 0) {
				return this.blockIndexInTexture - 16;
			}
			return this.blockIndexInTexture;            
		}
		if (j == 8){        	
			if (i == 3 || i == 2) {
				return this.blockIndexInTexture - 16;
			}            
			return this.blockIndexInTexture;
		}
		if(j == 4){
			if(i == 4 || i == 5){
				return this.blockIndexInTexture - 16;
			}
			else{
				return this.blockIndexInTexture;
			}
		}
		else return this.blockIndexInTexture;
	}

	@Override
	public int getRenderType()
	{
		return 31;
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4) & 3;
		int var7 = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, (EntityPlayer)par5EntityLiving);
		byte var8 = 0;

		switch (var7)
		{
		case 0:
		case 1:
			var8 = 0;
			break;
		case 2:
		case 3:
			var8 = 8;
			break;
		case 4:
		case 5:
			var8 = 4;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | var8);
	}



}
