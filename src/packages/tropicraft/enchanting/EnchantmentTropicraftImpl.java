package tropicraft.enchanting;

public class EnchantmentTropicraftImpl extends TropicraftEnchantment {

	private int minEnchantability;
	private int maxEnchantability;
	private int maxLevel;
	
	public EnchantmentTropicraftImpl(int effectID, int weight, int minEnchantability,
			int maxEnchantability, int maxLevel, String name) {
		super(effectID, weight);
		this.minEnchantability = minEnchantability;
		this.maxEnchantability = maxEnchantability;
		this.maxLevel = maxLevel;
		this.setName(name);
	}

	
    /**
     * Returns the minimal value of enchantability nedded on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return minEnchantability;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return this.getMinEnchantability(par1) + maxEnchantability;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return maxLevel;
    }
}
