package tropicraft.enchanting;

public class EnchantmentShells extends TropicraftEnchantment {

	public EnchantmentShells(int effectID, int weight) {
		super(effectID, weight);
		setName("Beachcombing");
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
        return this.getMinEnchantability(par1) + 40;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 5;
    }

}
