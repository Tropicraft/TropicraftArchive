package net.tropicraft.creative;

import net.minecraft.creativetab.CreativeTabs;
import net.tropicraft.mods.TropicraftMod;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TropiCreativeTabs {

	public static class CreativeTabDecoTC extends CreativeTabs {

		public CreativeTabDecoTC(String name) {
			super(name);
		}

		@SideOnly(Side.CLIENT)
		/**
		 * the itemID for the item to be displayed on the tab
		 */
		public int getTabIconItemIndex()
		{
			return TropicraftMod.umbrella.shiftedIndex;
		}
	}

	public static class CreativeTabMiscTC extends CreativeTabs {

		public CreativeTabMiscTC(String name) {
			super(name);
		}

		@SideOnly(Side.CLIENT)
		/**
		 * the itemID for the item to be displayed on the tab
		 */
		public int getTabIconItemIndex()
		{
			return TropicraftMod.fishBucket.shiftedIndex;
		}
	}

	public static class CreativeTabToolsTC extends CreativeTabs {

		public CreativeTabToolsTC(String name) {
			super(name);
		}

		@SideOnly(Side.CLIENT)
		/**
		 * the itemID for the item to be displayed on the tab
		 */
		public int getTabIconItemIndex()
		{
			return TropicraftMod.pickaxeEudialyte.shiftedIndex;
		}
	}

	public static class CreativeTabCombatTC extends CreativeTabs {

		public CreativeTabCombatTC(String name) {
			super(name);
		}

		@SideOnly(Side.CLIENT)
		/**
		 * the itemID for the item to be displayed on the tab
		 */
		public int getTabIconItemIndex()
		{
			return TropicraftMod.swordZircon.shiftedIndex;
		}
	}

	public static class CreativeTabMaterialsTC extends CreativeTabs {

		public CreativeTabMaterialsTC(String name) {
			super(name);
		}

		@SideOnly(Side.CLIENT)
		/**
		 * the itemID for the item to be displayed on the tab
		 */
		public int getTabIconItemIndex()
		{
			return TropicraftMod.fertilizer.shiftedIndex;
		}
	}

	public static class CreativeTabBlockTC extends CreativeTabs {

		public CreativeTabBlockTC(String name) {
			super(name);
		}

		@SideOnly(Side.CLIENT)
		/**
		 * the itemID for the item to be displayed on the tab
		 */
		public int getTabIconItemIndex()
		{
			return TropicraftMod.bambooBlock.blockID;
		}
	}

	public static class CreativeTabFoodTC extends CreativeTabs
	{
		public CreativeTabFoodTC(String par2Str)
		{
			super(par2Str);
		}

		@SideOnly(Side.CLIENT)
		/**
		 * the itemID for the item to be displayed on the tab
		 */
		public int getTabIconItemIndex()
		{
			return TropicraftMod.bambooMugFull.shiftedIndex;
		}
	}
	
	public static class CreativeTabFroxTC extends CreativeTabs
	{
		public CreativeTabFroxTC(String par2Str)
		{
			super(par2Str);
			LanguageRegistry.instance().addStringLocalization("itemGroup.frox", "Froxlab");
		}

		@SideOnly(Side.CLIENT)
		/**
		 * the itemID for the item to be displayed on the tab
		 */
		public int getTabIconItemIndex()
		{
			return TropicraftMod.froxEasternIsles.shiftedIndex;
		}
	}

}
