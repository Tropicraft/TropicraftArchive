package tropicraft.enchanting;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class EnchantmentBaneOfReptiles extends TropicraftEnchantment {

	public EnchantmentBaneOfReptiles(int effectID, int weight) {
		super(effectID, weight);
		setName("Bane of Reptiles");
	}
	
	@Override
	public int calcModifierLiving(int par1, EntityLiving entity) {
		return MathHelper.floor_float((float)par1 * 4.5F);
	}

}
