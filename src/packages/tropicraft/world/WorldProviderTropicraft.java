package tropicraft.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkProvider;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.world.biomes.WorldChunkManagerTropicraft;
import tropicraft.world.chunk.ChunkProviderTropicraft;

public class WorldProviderTropicraft extends WorldProviderSurface {
	
	@Override
	protected void registerWorldChunkManager()
    {
        worldChunkMgr = new WorldChunkManagerTropicraft(worldObj);
    }

	@Override
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderTropicraft(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled());
    }
    
    @Override
	public String getDepartMessage() {
		return "Fading out of the Tropics";
	}

	@Override
	public String getDimensionName() {
		return "Tropics";
	}
	
	@Override
	public String getWelcomeMessage() {
		return "Drifting into the Tropics";
	}    
	
	@Override
	public String getSaveFolder() {
		return "TROPICS";
	}
	
	@Override
    public boolean canMineBlock(EntityPlayer player, int x, int y, int z)
    {
        return worldObj.getBlockId(x, y, z) == TropicraftBlocks.portal.blockID ? false : worldObj.canMineBlockBody(player, x, y, z);
    }
}
