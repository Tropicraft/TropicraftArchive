package tropicraft.volleyball;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.blocks.BlockTropicraftImpl;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCourtPost extends BlockTropicraftImpl {

	public BlockCourtPost(int id, String imageName) {
		super(id, Material.rock, imageName);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.blockIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + "bambooBundle_side");
	}
	
    /**
     * Called when the block is placed in the world.
     */
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemstack) {
	//	if (!world.isRemote) {
			
	    	//check if court bounds
	    	ChunkCoordinates[] coords = CourtHelper.checkCourtForValidity(world, x, y, z);
	    	
	    	if (coords != null && coords.length == 4) {
	    		Court court = CourtHelper.buildCourt(world, coords);
	    		
	    		if (court != null) {
	    			int xCoord = (int) (court.minX + MathHelper.floor_double(court.xLength / 2));
	    			int yCoord = (int) court.y;
	    			int zCoord = (int) (court.minZ + MathHelper.floor_double(court.zLength / 2));
	    			
	    			world.setBlock(xCoord, yCoord, zCoord, Volleyball.courtMaster.blockID);
	    			((TileEntityCourtMaster)world.getBlockTileEntity(xCoord, yCoord, zCoord)).onFirstPlacement(court);
	    		}
	    	}
		//}
    }

}
