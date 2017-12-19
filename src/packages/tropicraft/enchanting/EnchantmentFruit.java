package tropicraft.enchanting;

public class EnchantmentFruit extends TropicraftEnchantment {

	public EnchantmentFruit(int effectID, int weight) {
		super(effectID, weight);
		setName("Fruit Ninja");
	}
	
    /**
     * Returns the minimal value of enchantability nedded on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return 1;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return this.getMinEnchantability(par1) + 9;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }

}
