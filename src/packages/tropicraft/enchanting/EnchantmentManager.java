package tropicraft.enchanting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tropicraft.ModIds;

public class EnchantmentManager {
	
	private static List<Integer> enchantableIds = new ArrayList<Integer>();

	public static final TropicraftEnchantment baneOfReptiles = new EnchantmentBaneOfReptiles(ModIds.ENCHANT_BANE, 5);
	
	public static final TropicraftEnchantment beachcombing = new EnchantmentShells(ModIds.ENCHANT_COMBING, 5);
	
	public static final TropicraftEnchantment fruitNinja = new EnchantmentFruit(ModIds.ENCHANT_NINJA, 5);
	
	public static final TropicraftEnchantment scubaSteve = new EnchantmentScubaSteve(ModIds.ENCHANT_SCUBA, 5);
	
	public static final TropicraftEnchantment midasTouch = new EnchantmentTropicraftImpl(ModIds.ENCHANT_MIDAS, 5, 1, 40, 5, "Midas Touch");
	
	public static void init() {
		
	}
	
	public static boolean canApply(ItemStack stack) {
		return enchantableIds.contains(stack.itemID);
	}
	
	public static EnchantmentData enchant(EntityPlayer player, ItemStack stack) {
		int lvl = player.experienceLevel;
		
		if (lvl > 30) {
			Random rand = new Random();
			
			if (rand.nextBoolean()) {
				int eLvl = 1;
			}
		}
		
		return null;
	}
}
