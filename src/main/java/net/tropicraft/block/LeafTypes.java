package net.tropicraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum LeafTypes implements IStringSerializable
{
    PALM(0, "palm", MapColor.woodColor),
    KAPOK(1, "kapok", MapColor.obsidianColor)/*,
    MAHOGANY(2, "mahogany", MapColor.sandColor),
    FRUIT(3, "fruit", MapColor.dirtColor),*/
    /*ACACIA(4, "acacia", MapColor.adobeColor),
    DARK_OAK(5, "dark_oak", "big_oak", MapColor.brownColor)*/;

    private static final LeafTypes[] META_LOOKUP = new LeafTypes[values().length];
    private final int meta;
    private final String name;
    private final String unlocalizedName;
    /** The color that represents this entry on a map. */
    private final MapColor mapColor;

    private LeafTypes(int p_i46388_3_, String p_i46388_4_, MapColor p_i46388_5_)
    {
        this(p_i46388_3_, p_i46388_4_, p_i46388_4_, p_i46388_5_);
    }

    private LeafTypes(int p_i46389_3_, String p_i46389_4_, String p_i46389_5_, MapColor p_i46389_6_)
    {
        this.meta = p_i46389_3_;
        this.name = p_i46389_4_;
        this.unlocalizedName = p_i46389_5_;
        this.mapColor = p_i46389_6_;
    }

    public int getMetadata()
    {
        return this.meta;
    }

    /**
     * The color which represents this entry on a map.
     */
    public MapColor getMapColor()
    {
        return this.mapColor;
    }

    public String toString()
    {
        return this.name;
    }

    public static LeafTypes byMetadata(int meta)
    {
        if (meta < 0 || meta >= META_LOOKUP.length)
        {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public String getName()
    {
        return this.name;
    }

    public String getUnlocalizedName()
    {
        return this.unlocalizedName;
    }

    static
    {
        for (LeafTypes blockplanks$enumtype : values())
        {
            META_LOOKUP[blockplanks$enumtype.getMetadata()] = blockplanks$enumtype;
        }
    }
}
