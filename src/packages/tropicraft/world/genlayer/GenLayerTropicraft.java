package tropicraft.world.genlayer;

import tropicraft.world.biomes.BiomeGenTropicraft;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerTropicraft extends GenLayer {

	public GenLayerTropicraft(long par1) {
		super(par1);
	}
	
	public static GenLayer[] initializeAllBiomeGenerators(long par0, WorldType par2WorldType)
    {
        GenLayerTropicraftIsland layerIsland = new GenLayerTropicraftIsland(1L);
        GenLayerFuzzyZoom layerFuzzyZoom = new GenLayerFuzzyZoom(2000L, layerIsland);
        GenLayerTropicraftExpandIsland layerExpand = new GenLayerTropicraftExpandIsland(2L, layerFuzzyZoom);
        GenLayerTropicraftAddIsland layerAddIsland = new GenLayerTropicraftAddIsland(3L, layerExpand, 10, BiomeGenTropicraft.tropics.biomeID);
        GenLayerZoom layerZoom = new GenLayerZoom(2000L, layerAddIsland);
        layerExpand = new GenLayerTropicraftExpandIsland(6L, layerZoom);
        GenLayerTropicraftLake layerLake = new GenLayerTropicraftLake(9L, layerExpand, 20);
        layerAddIsland = new GenLayerTropicraftAddIsland(5L, layerLake, 8, BiomeGenTropicraft.tropics.biomeID);
        layerAddIsland = new GenLayerTropicraftAddIsland(6L, layerLake, 13, BiomeGenTropicraft.islandMountain.biomeID);
        layerZoom = new GenLayerZoom(2001L, layerAddIsland);
        layerExpand = new GenLayerTropicraftExpandIsland(7L, layerZoom);
        layerAddIsland = new GenLayerTropicraftAddIsland(8L, layerExpand, 9, BiomeGenTropicraft.tropics.biomeID);
        layerExpand = new GenLayerTropicraftExpandIsland(10L, layerAddIsland);
        GenLayerTropicraftBiomes genLayerBiomes = new GenLayerTropicraftBiomes(15L, layerExpand);
        GenLayerTropicraftAddHills genLayerHills = new GenLayerTropicraftAddHills(16L, genLayerBiomes);
        layerZoom = new GenLayerZoom(2002L, genLayerHills);
    	layerExpand = new GenLayerTropicraftExpandIsland(10L, layerZoom);
        
        for(int i = 0; i < 4; i++)
        {
        	layerExpand = new GenLayerTropicraftExpandIsland(10L, layerExpand);
        }
        
        GenLayerTropicraftRiverInit layerRiverInit = new GenLayerTropicraftRiverInit(12L);
        GenLayer layerRiverMag = GenLayerZoom.magnify(2007L, layerRiverInit, 5);
        GenLayerTropicraftRiver layerRiver = new GenLayerTropicraftRiver(13L, layerRiverMag);
        GenLayerSmooth layerRiverSmooth = new GenLayerSmooth(2008L, layerRiver);
        
        GenLayer layerMagnify = GenLayerZoom.magnify(2007L, layerExpand, 3);
        GenLayer layerBeach = new GenLayerTropicraftBeach(20L, layerMagnify);
        GenLayerSmooth layerBiomesSmooth = new GenLayerSmooth(17L, layerBeach);
        GenLayerTropicraftRiverMix layerRiverMix = new GenLayerTropicraftRiverMix(5L, layerBiomesSmooth, layerRiverSmooth);
        
        GenLayerVoronoiZoom layerVoronoi = new GenLayerVoronoiZoom(10L, layerRiverMix);
        layerRiverMix.initWorldGenSeed(par0);
        layerVoronoi.initWorldGenSeed(par0);
        return new GenLayer[] {layerRiverMix, layerVoronoi};
    }
	
}
