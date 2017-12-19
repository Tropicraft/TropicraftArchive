package tropicraft.world.biomes;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import tropicraft.entities.EntityEagleRay;
import tropicraft.entities.hostile.land.Creeper;
import tropicraft.entities.hostile.land.EIH;
import tropicraft.entities.hostile.land.Skeleton;
import tropicraft.entities.hostile.land.SpiderAdult;
import tropicraft.entities.hostile.land.TreeFrogBlue;
import tropicraft.entities.hostile.land.TreeFrogGreen;
import tropicraft.entities.hostile.land.TreeFrogRed;
import tropicraft.entities.hostile.land.TreeFrogYellow;
import tropicraft.entities.hostile.land.tribes.ashen.AshenHunter;
import tropicraft.entities.passive.flying.Failgull;
import tropicraft.entities.passive.land.Iguana;
import tropicraft.entities.passive.land.VMonkey;
import tropicraft.entities.passive.water.EntityMarlin;
import tropicraft.entities.passive.water.EntityTropicalFish;

public class BiomeGenTropicraft extends BiomeGenBase {

	//Biome IDs
	public static int bIDtropicsOcean = 60;
	public static int bIDtropics = 61;
	public static int bIDrainforestPlains = 62;
	public static int bIDrainforestHills = 63;
	public static int bIDrainforestMountains = 64;
	public static int bIDtropicsRiver = 65;
	public static int bIDtropicsBeach = 66;
	public static int bIDlake = 67;
	public static int bIDislandMountain = 68;
	
	public static BiomeGenTropicraft tropicsOcean = (new BiomeGenTropicsWater(bIDtropicsOcean)).setMinMaxHeight(-1.0F, 0.4F).setTemperatureRainfall(1.5F, 1.25F).setBiomeName("Tropical Ocean");
	public static BiomeGenTropicraft tropics = (new BiomeGenTropics(bIDtropics)).setMinMaxHeight(0.1F, 0.25F).setTemperatureRainfall(2.0F, 1.5F).setBiomeName("Tropics");
	public static BiomeGenTropicraft rainforestPlains = (new BiomeGenRainforest(bIDrainforestPlains)).setColor(0x11882f).setMinMaxHeight(0.2F, 0.35F).setTemperatureRainfall(1.5F, 2.0F).setBiomeName("Rainforest Plains");
	public static BiomeGenTropicraft rainforestHills = (new BiomeGenRainforest(bIDrainforestHills)).setColor(0x11882f).setMinMaxHeight(0.3F, 0.8F).setTemperatureRainfall(1.5F, 2.0F).setBiomeName("Rainforest Hills");
	public static BiomeGenTropicraft rainforestMountains = (new BiomeGenRainforest(bIDrainforestMountains)).setColor(0x11882f).setMinMaxHeight(0.2F, 1.2F).setTemperatureRainfall(1.5F, 2.0F).setBiomeName("Rainforest Mountains");
	public static BiomeGenTropicraft tropicsRiver = (new BiomeGenTropicsWater(bIDtropicsRiver)).setMinMaxHeight(-0.7F, 0.0F).setTemperatureRainfall(1.5F, 1.25F).setBiomeName("Tropical River");
	public static BiomeGenTropicraft tropicsBeach = (new BiomeGenTropicsBeach(bIDtropicsBeach)).setMinMaxHeight(-0.1F, 0.1F).setTemperatureRainfall(1.5F, 1.25F).setBiomeName("Tropical Beach");
	public static BiomeGenTropicraft lake = (new BiomeGenTropicsWater(bIDlake)).setMinMaxHeight(-0.6F, 0.1F).setTemperatureRainfall(1.5F, 1.25F).setBiomeName("Tropical Lake");
	public static BiomeGenTropicraft islandMountain = (new BiomeGenRainforest(bIDislandMountain)).setColor(0x11882f).setMinMaxHeight(0.1F, 2.5F).setTemperatureRainfall(1.5F, 2.0F).setBiomeName("Island Rainforest Mountains");
	
	public static List<BiomeGenTropicraft> rainforestBiomes = Arrays.asList(rainforestPlains, rainforestHills, rainforestMountains, islandMountain);
	
	public BiomeGenTropicraft(int par1) {
		super(par1);
		
		this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        
        //Not using creature list because its used in chunkgen only, marking all land entities as IMob / hostiles 
        
        if (par1 == bIDrainforestMountains) {
        	this.spawnableMonsterList.add(new SpawnListEntry(TreeFrogBlue.class, 25, 1, 2));
            this.spawnableMonsterList.add(new SpawnListEntry(TreeFrogGreen.class, 25, 1, 2));
            this.spawnableMonsterList.add(new SpawnListEntry(TreeFrogRed.class, 25, 1, 2));
            this.spawnableMonsterList.add(new SpawnListEntry(TreeFrogYellow.class, 25, 1, 2));
        }
        
        this.spawnableMonsterList.add(new SpawnListEntry(VMonkey.class, 20, 1, 3));
        this.spawnableMonsterList.add(new SpawnListEntry(Iguana.class, 20, 1, 1));
        
        this.spawnableMonsterList.add(new SpawnListEntry(Creeper.class, 2, 1, 2));
        this.spawnableMonsterList.add(new SpawnListEntry(EIH.class, 10, 1, 1));
        this.spawnableMonsterList.add(new SpawnListEntry(Skeleton.class, 25, 1, 8));
        this.spawnableMonsterList.add(new SpawnListEntry(AshenHunter.class, 2, 3, 12));
        
        this.spawnableMonsterList.add(new SpawnListEntry(Failgull.class, 30, 5, 20));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntityTropicalFish.class, 10, 1 ,12));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntityMarlin.class, 10, 1 ,16));
        this.spawnableWaterCreatureList.add(new SpawnListEntry(EntityEagleRay.class, 10, 1 ,6));
        
        this.spawnableMonsterList.add(new SpawnListEntry(SpiderAdult.class, 50, 1, 3));
	}
	
	//So the biomes can be of type "BiomeGenTropicraft" without casting
	
	public BiomeGenTropicraft setTemperatureRainfall(float par1, float par2)
    {
        if (par1 > 0.1F && par1 < 0.2F)
        {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }
        else
        {
            this.temperature = par1;
            this.rainfall = par2;
            return this;
        }
    }

    public BiomeGenTropicraft setMinMaxHeight(float par1, float par2)
    {
        this.minHeight = par1;
        this.maxHeight = par2;
        return this;
    }

    public BiomeGenTropicraft setBiomeName(String par1Str)
    {
        this.biomeName = par1Str;
        return this;
    }

    public BiomeGenTropicraft setColor(int par1)
    {
        this.color = par1;
        return this;
    }
    
}
