package tropicraft.items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.EnumToolMaterialTropics;
import tropicraft.enchanting.EnchantmentManager;

public class ItemTropicsSpade extends ItemToolTropics {

    /** an array of the blocks this spade is effective against */
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand};
   // 	Block.mycelium, TropicraftMod.purifiedSand};


    public ItemTropicsSpade(int i, String imageName, EnumToolMaterialTropics enumtoolmaterial) {
        super(i, 1, enumtoolmaterial, blocksEffectiveAgainst);
        this.imageName = imageName;
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        if (block == Block.snow) {
            return true;
        }
        return block == Block.blockSnow;
    }
    
	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLiving)
	{		
		int l = EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.beachcombing.effectId, par1ItemStack);
		
		if (l > 0 && !par2World.isRemote) {
			Random rand = new Random();
			if (par2World.rand.nextInt(15) < l) {
				int shellID = TropicraftItems.shells.itemID;
				int damage = 0;	// 2 and 3 are rare i think
				
				if (rand.nextInt(50) == 0) {
					if (rand.nextBoolean())
						damage = 2;
					else
						damage = 3;
				} else {
					if (rand.nextBoolean())
						damage = 1;
					else
						damage = 2;
				}
				
				float var10 = par2World.rand.nextFloat() * 0.8F + 0.1F;
				float var11 = par2World.rand.nextFloat() * 0.8F + 0.1F;
				EntityItem item = new EntityItem(par2World, par4 + var10, par5 + var10, par6 + var11, new ItemStack(shellID, 1, damage));
				
			//	item.setLocationAndAngles(par3, par4, par5, 0, 0);
				
				par2World.spawnEntityInWorld(item);
			}
		}
		
		super.onBlockDestroyed(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLiving);
		
		return true;
	}

	@Override
	public String getImageName() {
		return "shovel_" + imageName;
	}

}
