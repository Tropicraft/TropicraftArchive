package net.tropicraft.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.world.chunkprovider.ChunkProviderTropics;

public class WorldProviderTropics extends WorldProvider {
    
    // The world time offset from regular sunrise to tropics sunrise
//    public static final int timeOffset = -12000;
	public static final int timeOffset = 0;
	
    /** Array for sunrise/sunset colors (RGBA) */
    private float[] colorsSunriseSunset = new float[4];

    public WorldProviderTropics() {
    }

    @Override
    public void registerWorldChunkManager() {
        worldChunkMgr = new WorldChunkManagerTropics(worldObj);
        dimensionId = TropicraftMod.tropicsDimensionID;
        isHellWorld = false;
        hasNoSky = false;
    }

    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderTropics(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled());
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }

	@Override
	public String getSaveFolder() {
		return "TROPICS";
	}

	@Override
	public String getWelcomeMessage() {
		return "Drifting into the tropics!";
	}

	@Override
	public String getDepartMessage() {
		return "Leaving the tropics!";
	}
    
    @Override
    public int getAverageGroundLevel()
    {
        return 63;
    }
    
    @Override
    public float calculateCelestialAngle(long par1, float par3) {
    	return super.calculateCelestialAngle(par1 + timeOffset, par3);
    }

	@Override
	public String getDimensionName() {
		return "Tropics";
	}
	
    @SideOnly(Side.CLIENT)
    @Override

    /**
     * Returns array with sunrise/sunset colors
     */
    public float[] calcSunriseSunsetColors(float par1, float par2)
    {
        float var3 = 0.4F;
        float var4 = MathHelper.cos(par1 * (float)Math.PI * 2.0F) - 0.0F;
        float var5 = -0.0F;

        if (var4 >= var5 - var3 && var4 <= var5 + var3)
        {
            float var6 = (var4 - var5) / var3 * 0.5F + 0.5F;
            float var7 = 1.0F - (1.0F - MathHelper.sin(var6 * (float)Math.PI)) * 0.99F;
            var7 *= var7;
            this.colorsSunriseSunset[0] = var6 * 0.3F + 0.7F;
            this.colorsSunriseSunset[1] = var6 * var6 * 0.7F + 0.2F;
            this.colorsSunriseSunset[2] = var6 * var6 * 0.0F + 0.2F;
            this.colorsSunriseSunset[3] = var7;
            return this.colorsSunriseSunset;
        }
        else
        {
            return null;
        }
    }
    
}
