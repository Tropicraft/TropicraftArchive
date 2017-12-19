package net.tropicraft.world.biomes;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BiomeGenTropicraft extends BiomeGenBase {
	
	public static BiomeGenTropicraft[] tropiBiomeList = new BiomeGenTropicraft[256];
	
	public byte beachTopBlock = (byte)Block.sand.blockID;
	public byte beachFillerBlock = (byte)Block.sandStone.blockID;
	public boolean beachLayerLowered = false;
	
	public byte beachAlternateTopBlock = (byte)Block.sand.blockID;
	public byte beachAlternateFillerBlock = (byte)Block.sandStone.blockID;
	public boolean beachAlternateLayerLowered = false;
	
	public byte underwaterTopBlock = (byte)Block.dirt.blockID;
	public byte underwaterFillerBlock = (byte)Block.dirt.blockID;
	
	public static final BiomeGenTropicraft tropicsOcean = (new BiomeGenTropicsOcean(60)).setBiomeName("Tropics Ocean").setColor(0x8db000).setMinMaxHeight(-1F, 0.1F).setTemperatureRainfall(0.8F, 0.5F);
	public static final BiomeGenTropicraft tropics = (new BiomeGenTropics(61)).setBiomeName("Tropics").setColor(0x8db360).setMinMaxHeight(0.05F, 0.3F).setTemperatureRainfall(0.8F, 0.5F);
	public static final BiomeGenTropicraft rainforestPlains = (new BiomeGenTropicsRainforest(62)).setBiomeName("Rainforest Plains").setColor(0x11882f).setMinMaxHeight(0.4F, 0.5F).setTemperatureRainfall(0.6F, 0.8F);
	public static final BiomeGenTropicraft rainforestHills = (new BiomeGenTropicsRainforest(63)).setBiomeName("Rainforest Hills").setColor(0x11882f).setMinMaxHeight(0.4F, 0.9F).setTemperatureRainfall(0.6F, 0.8F);
	public static final BiomeGenTropicraft rainforestMountains = (new BiomeGenTropicsRainforest(64)).setBiomeName("Rainforest Mountains").setColor(0x11882f).setMinMaxHeight(0.5F, 1.3F).setTemperatureRainfall(0.6F, 0.8F);
	public static final BiomeGenTropicraft tropicsRiver = (new BiomeGenTropicsOcean(65)).setBiomeName("Tropics River").setColor(0x8db000).setMinMaxHeight(-0.7F, 0.0F).setTemperatureRainfall(0.8F, 0.5F);
	public static final BiomeGenTropicraft beach = (new BiomeGenTropicsBeach(66)).setBiomeName("Beach").setColor(0x8db000).setMinMaxHeight(0.1F, -0.1F).setTemperatureRainfall(0.7F, 0.6F); //This seems to have some NPE related to minHeight
	public static final BiomeGenTropicraft lake = (new BiomeGenTropicsOcean(67)).setBiomeName("Lake").setColor(0x8db000).setMinMaxHeight(-0.6F, 0.1F).setTemperatureRainfall(0.8F, 0.5F);
	
	public static List<BiomeGenTropicraft> rainforestBiomes = Arrays.asList(rainforestPlains, rainforestHills, rainforestMountains);
	
	private static ArrayList<String> mobnames;
	private static ArrayList<Class> classnames;
	private static ArrayList<Integer> spawnrates;
	private static ArrayList<EnumCreatureType> creaturetypes;
	private static ArrayList<String> othermobnames;
	private static ArrayList<Class> otherclassnames;	
	private int poisonredfrograrity = 5;
	private int poisonyellowfrograrity = 5;
	private int poisonbluefrograrity = 5;
	private int treefrograrity = 7;
	private int MOWrarity = 20;
	private int EIHrarity = 10;
	private int iggyrarity = 15;
	private int starfishrarity = 30;
	private int marlinrarity = 24;
	private int turtlerarity = 24;
	private int seagullrarity = 10;
	private int vmonkeyrarity = 30;
	private int ashenrarity = 30;
	private int tropfishrarity = 10;//new
	
	protected BiomeGenTropicraft(int i) {	//i got 99 problems, but biomes ain't one :D
		super(i);
		mobnames = new ArrayList<String>();
		classnames = new ArrayList<Class>();
		spawnrates = new ArrayList<Integer>();
		creaturetypes = new ArrayList<EnumCreatureType>();
		othermobnames = new ArrayList<String>();
		otherclassnames = new ArrayList<Class>();  	 
		tropiBiomeList[i] = this;
        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
	}
	
    @Override
    public List getSpawnableList(EnumCreatureType enumcreaturetype)
    {
        if (enumcreaturetype == EnumCreatureType.monster)
        {
            return spawnableMonsterList;
        }
        if (enumcreaturetype == EnumCreatureType.creature)
        {
            return spawnableCreatureList;
        }
        if (enumcreaturetype == EnumCreatureType.waterCreature)
        {
            return spawnableWaterCreatureList;
        }
        else
        {
            return null;
        }
    }

	public BiomeGenTropicraft setTemperatureRainfall(float f, float f1)
    {
        if(f > 0.1F && f < 0.2F)
        {
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        } else
        {
            temperature = f;
            rainfall = f1;
            return this;
        }
    }
	
	
	/** this is cojo trying
	 * (non-Javadoc)
	 * @see net.minecraft.src.BiomeGenBase#getSpawningChance()
	 **/
	@Override
    public float getSpawningChance()
    {
        return 0.1F;
    }

    public BiomeGenTropicraft setMinMaxHeight(float f, float f1)
    {
        minHeight = f;
        maxHeight = f1;
        return this;
    }
    
    @Override
	public BiomeGenTropicraft setBiomeName(String s)
    {
        biomeName = s;
        return this;
    }

    @Override
	public BiomeGenTropicraft func_76733_a(int par1)
    {
        this.field_76754_C = par1;
        return this;
    }

    @Override
	public BiomeGenTropicraft setColor(int i)
    {
        color = i;
        return this;
    }

    @Override
    public int getSkyColorByTemp(float f)
    {
        f /= 3F;
        if(f < -1F)
        {
            f = -1F;
        }
        if(f > 1.0F)
        {
            f = 1.0F;
        }
 //       return java.awt.Color.getHSBColor(0.6222222F - f * 0.05F, 0.5F + f * 0.1F, 1.0F).getRGB();
        return java.awt.Color.getHSBColor(0.6322222F - f * 0.05F, 0.5F + f * 0.1F, 1.0F).getRGB();
    }
    
    protected void addMobWithNaturalSpawn(Class classname, int min, int max, int spawnrarity)	
	{
		spawnableCreatureList.add(new SpawnListEntry(classname,spawnrarity, min, max));
	}
    
    protected void addMonsterWithNaturalSpawn(Class classname, int min, int max, int spawnrarity)	
	{
		spawnableMonsterList.add(new SpawnListEntry(classname,spawnrarity, min, max));
	}
    
    protected void addWaterMobWithNaturalSpawn(Class classname, int min, int max, int spawnrarity)	
	{
		spawnableWaterCreatureList.add(new SpawnListEntry(classname,spawnrarity, min, max));
	}
}
