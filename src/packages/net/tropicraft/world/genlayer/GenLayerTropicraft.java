package net.tropicraft.world.genlayer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerTropicraft extends GenLayer {

    public static GenLayer[] getGenLayers(long l)
    {
        GenLayer obj = new GenLayerTropicsIsland(1L);
        obj = new GenLayerFuzzyZoom(2000L, obj);
        obj = new GenLayerTropicsExpand(1L, obj);
        obj = new GenLayerZoom(2001L, obj);
        obj = new GenLayerTropicsExpand(2L, obj);
        obj = new GenLayerZoom(2002L, obj);
        obj = new GenLayerTropicsExpand(3L, obj);
        byte zoomFactor = 2;
        GenLayer obj1 = obj;
        obj1 = GenLayerZoom.func_75915_a(1000L, obj, 0);
        obj1 = GenLayerZoom.func_75915_a(1000L, obj1, zoomFactor + 2);
        obj1 = new GenLayerTropicsRiver(1L, obj1);
        obj1 = new GenLayerSmooth(1000L, obj1);
        
        GenLayer obj2 = obj;
        obj2 = new GenLayerTropicsAddBiomes(200L, obj2);
        obj2 = new GenLayerTropicsAddRainforestBiomes(100L, obj2);
        obj2 = new GenLayerTropicsSmallIslands(100L, obj2);
        obj2 = new GenLayerTropicsLakes(100L, obj2);
        obj2 = GenLayerZoom.func_75915_a(1000L, obj2, 2);
        
        GenLayer obj3 = new GenLayerTemperature(obj2);
        GenLayer obj4 = new GenLayerDownfall(obj2);
        for(int i = 0; i < zoomFactor; i++)
        {
            obj2 = new GenLayerZoom(1000 + i, obj2);
            obj3 = new GenLayerSmoothZoom(1000 + i, obj3);
            obj3 = new GenLayerTemperatureMix(obj3, obj2, i);
            obj4 = new GenLayerSmoothZoom(1000 + i, obj4);
            obj4 = new GenLayerDownfallMix(obj4, obj2, i);
        }

        obj2 = new GenLayerSmooth(1000L, obj2);
        obj2 = new GenLayerTropicsRiverMix(100L, obj2, obj1);
        obj2 = new GenLayerTropicsBeach(1L, obj2);
        obj3 = GenLayerSmoothZoom.func_35517_a(1000L, obj3, 2);
        obj4 = GenLayerSmoothZoom.func_35517_a(1000L, obj4, 2);
        GenLayerVoronoiZoom genlayerzoomvoronoi = new GenLayerVoronoiZoom(10L, obj2);
        obj2.initWorldGenSeed(l);
        obj3.initWorldGenSeed(l);
        obj4.initWorldGenSeed(l);
        genlayerzoomvoronoi.initWorldGenSeed(l);
        return (new GenLayer[] {
            obj2, genlayerzoomvoronoi, obj3, obj4
        });
    }

	public GenLayerTropicraft(long l) {
		super(l);
	}
	
}
