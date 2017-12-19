package net.tropicraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.FoodStats;

import java.lang.reflect.Field;

public class TCUtil {
	
	public static String refl_mcp_Item_maxStackSize = "maxStackSize";
	public static String refl_c_Item_maxStackSize = "bR";
	public static String refl_s_Item_maxStackSize = "bQ";
	public static String refl_mcp_Item_navigator = "navigator";
	public static String refl_c_Item_navigator = "ar";
	public static String refl_s_Item_navigator = "k";
	
	public static String refl_mcp_EntityPlayer_itemInUse = "itemInUse";
	public static String refl_c_EntityPlayer_itemInUse = "d";
	public static String refl_s_EntityPlayer_itemInUse = "d";
	public static String refl_mcp_EntityPlayer_itemInUseCount = "itemInUseCount";
	public static String refl_c_EntityPlayer_itemInUseCount = "e";
	public static String refl_s_EntityPlayer_itemInUseCount = "e";
	public static String refl_mcp_FoodStats_foodLevel = "foodLevel";
	public static String refl_c_FoodStats_foodLevel = "a";
	public static String refl_s_FoodStats_foodLevel = "a";
	
	
	
	public TCUtil() {
		
	}
	
	public static Field s_getItemInUse() {
		return tryGetField(EntityPlayer.class, refl_s_EntityPlayer_itemInUse, refl_mcp_EntityPlayer_itemInUse);
	}
	
	public static Field s_getItemInUseCount() {
		return tryGetField(EntityPlayer.class, refl_s_EntityPlayer_itemInUseCount, refl_mcp_EntityPlayer_itemInUseCount);
	}
	
	public static Field s_getFoodLevel() {
		return tryGetField(FoodStats.class, refl_s_FoodStats_foodLevel, refl_mcp_FoodStats_foodLevel);
	}
	
	public static Field tryGetField(Class theClass, String obf, String mcp) {
		Field field = null;
		try {
			field = theClass.getDeclaredField(obf);
			field.setAccessible(true);
		} catch (Exception ex) {
			try {
				field = theClass.getDeclaredField(mcp);
				field.setAccessible(true);
			} catch (Exception ex2) { ex2.printStackTrace(); }
		}
		return field;
	}
	
	//apparently the tick updates are weird, this function is now unused....
	//this will only really be needed for when the user drops a gun while holding it, as item onUpdate calls if item isnt selected too
	public static boolean lastTickGunInHand = false;
	public static void handleGunUseBinds(boolean gunInHand) {
		lastTickGunInHand = gunInHand;
	}
	
	
	
	/*public static int getClipCount(EntityPlayer var0, int var1)
    {
		int ammo = getAmmoData(var0, var1);
		return ammo;
    }*/
	
	public static int getInventorySlotContainItem(InventoryPlayer var0, int var1)
    {
        for (int var2 = 0; var2 < var0.mainInventory.length; ++var2)
        {
            if (var0.mainInventory[var2] != null && var0.mainInventory[var2].itemID == var1)
            {
                return var2;
            }
        }

        return -1;
    }
	
	

	/*public void setBlocksExplodable(boolean var1)
    {
        try
        {
            int var2;

            if (!var1)
            {
                blockResistance = new float[Block.blocksList.length];

                for (var2 = 0; var2 < Block.blocksList.length; ++var2)
                {
                    if (Block.blocksList[var2] != null)
                    {
                        blockResistance[var2] = Block.blocksList[var2].blockResistance;

                        if (var2 != Block.glass.blockID || !mod_SdkGuns.bulletsDestroyGlass)
                        {
                            Block.blocksList[var2].blockResistance = 6000000.0F;
                        }
                    }
                }
            }
            else if (blockResistance != null)
            {
                for (var2 = 0; var2 < Block.blocksList.length; ++var2)
                {
                    if (Block.blocksList[var2] != null)
                    {
                        Block.blocksList[var2].blockResistance = blockResistance[var2];
                    }
                }
            }

            areBlocksExplodable = var1;
        }
        catch (Exception var3)
        {
            ModLoader.getLogger().throwing("mod_SdkFps", "setBlocksExplodable", var3);
            SdkTools.ThrowException(String.format("Error setting blocks explodable: %b.", new Object[] {Boolean.valueOf(var1)}), var3);
        }
    }*/
    
    
	
}
