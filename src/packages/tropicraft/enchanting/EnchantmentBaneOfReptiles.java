package tropicraft.enchanting;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class EnchantmentBaneOfReptiles extends TropicraftEnchantment {

	public EnchantmentBaneOfReptiles(int effectID, int weight) {
		super(effectID, weight);
		setName("Bane of Reptiles");
	}
	
	@Override
	public float calcModifierLiving(int par1, EntityLivingBase entity) {
		return MathHelper.floor_float((float)par1 * 4.5F);
	}

}
