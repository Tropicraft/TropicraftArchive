package net.tropicraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.tropicraft.blocks.tileentities.TileEntityLemonSqueezer;
import net.tropicraft.mods.TropicraftMod;

public class BlockLemonSqueezer extends BlockContainer {
	// Touched classes: BlockLemonSqueezer, TileEntityLemonSqueezer, TileEntityLemonSqueezerRenderer,
	// ClientProxy, TropicraftMod, /tropicalmod/lemonsqueezer.png, /tropicalmod/tropiitems.png,
	// LemonSqueezerRenderHandler, ItemFruitJuice, CraftingTropicraft
	
	public enum FruitType {
		LEMON(TropicraftMod.lemon, new ItemStack(TropicraftMod.lemonJuice)),
		LIME(TropicraftMod.lime, new ItemStack(TropicraftMod.limeJuice)),
		ORANGE(TropicraftMod.orange, new ItemStack(TropicraftMod.orangeJuice)),
		GRAPEFRUIT(TropicraftMod.grapefruit, new ItemStack(TropicraftMod.grapefruitJuice));
		
		public final Item fruit;
		public final ItemStack drink;
		
		private FruitType(Item fruit, ItemStack drink) {
			this.fruit = fruit;
			this.drink = drink;
		}
	}

	public BlockLemonSqueezer(int par1) {
		super(par1, Material.rock);
        this.setCreativeTab(TropicraftMod.tabBlock);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		ItemStack stack = par5EntityPlayer.getCurrentEquippedItem();
		
		if (stack == null) {
			return false;
		}
		
		TileEntityLemonSqueezer squeezer = (TileEntityLemonSqueezer)par1World.getBlockTileEntity(par2, par3, par4);
		
		if (stack.itemID == TropicraftMod.bambooMugEmpty.shiftedIndex && squeezer.isDoneSqueezing()) {
			if (!par1World.isRemote) {
				par5EntityPlayer.inventory.decrStackSize(par5EntityPlayer.inventory.currentItem, 1);
				EntityItem e = new EntityItem(par1World, par2, par3, par4, squeezer.fruitType.drink.copy());
				par1World.spawnEntityInWorld(e);
				squeezer.emptySqueezer();
			}
			return true;
		}
		
		if (!squeezer.isSqueezing()) {
			for (FruitType fruitType: FruitType.values()) {
				if (stack.itemID == fruitType.fruit.shiftedIndex) {
					if (!par1World.isRemote) {
						par5EntityPlayer.inventory.decrStackSize(par5EntityPlayer.inventory.currentItem, 1);
						squeezer.startSqueezing(fruitType);
					}
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityLemonSqueezer();
	}
	
	@Override
    public int getRenderType() {
        return TropicraftMod.lemonSqueezerRenderId;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }
    
    @Override
    public int getRenderBlockPass() {
        return 0;
    }

}
