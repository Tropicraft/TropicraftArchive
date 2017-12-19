package net.tropicraft;

public enum EnumToolMaterialTropics
{
    CHUNKOHEAD(0, 40, 2.0F, 2),
    BAMBOO(1, 110, 1.2F, 1),
    EUDIALYTE(3, 1000, 7.4F, 1),
    ZIRCON(2, 225, 3.0F, 2),
    IFORGET(1, 250, 12F, 0);
    /*    IRON(2, 250, 6.0F, 2, 14),
    EMERALD(3, 1561, 8.0F, 3, 10),*/
    
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiencyOnProperMaterial;
    private final int damageVsEntity;
 
    private EnumToolMaterialTropics(int harvestlvl, int maxuses, float efficiency, int damagevsentity)
    {
        harvestLevel = harvestlvl;
        maxUses = maxuses;
        efficiencyOnProperMaterial = efficiency;
        damageVsEntity = damagevsentity;
    }

    public int getMaxUses()
    {
        return maxUses;
    }

    public float getEfficiencyOnProperMaterial()
    {
        return efficiencyOnProperMaterial;
    }

    public int getDamageVsEntity()
    {
        return damageVsEntity;
    }

    public int getHarvestLevel()
    {
        return harvestLevel;
    }

}
