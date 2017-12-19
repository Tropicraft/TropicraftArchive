package tropicraft.creative;

import net.minecraft.creativetab.CreativeTabs;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.items.TropicraftItems;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TropiCreativeTabs {

	public static final CreativeTabs tabBlock = new CreativeTabBlockTC("buildingBlocks");
	public static final CreativeTabs tabFood = new CreativeTabFoodTC("food");
	public static final CreativeTabs tabTools = new CreativeTabToolsTC("tools");
	public static final CreativeTabs tabCombat = new CreativeTabCombatTC("combat");
	public static final CreativeTabs tabDecorations = new CreativeTabDecoTC("decorations");
	public static final CreativeTabs tabMaterials = new CreativeTabMaterialsTC("materials");
	public static final CreativeTabs tabMusic = new CreativeTabMusicTC("music");
	public static final CreativeTabs tabMisc = new CreativeTabMiscTC("misc");

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
			//TODO return TropicraftItems.umbrella.itemID;
			return TropicraftItems.pearl.itemID;
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
			return TropicraftItems.fishBucket.itemID;
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
			return TropicraftItems.pickaxeEudialyte.itemID;
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
			return TropicraftItems.swordZircon.itemID;
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
			return TropicraftItems.fertilizer.itemID;
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
			return TropicraftBlocks.bambooBundle.blockID;
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
			return TropicraftItems.bambooMugEmpty.itemID;
		}
	}

	public static class CreativeTabMusicTC extends CreativeTabs
	{
		public CreativeTabMusicTC(String par2Str)
		{
			super(par2Str);
			LanguageRegistry.instance().addStringLocalization("itemGroup.music", "Music");
		}

		@SideOnly(Side.CLIENT)
		/**
		 * the itemID for the item to be displayed on the tab
		 */
		public int getTabIconItemIndex()
		{
			return TropicraftItems.recordEasternIsles.itemID;
		}
	}
}
