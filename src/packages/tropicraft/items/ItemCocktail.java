package tropicraft.items;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.blocks.tileentities.TileEntityBambooMug;
import tropicraft.drinks.ColorMixer;
import tropicraft.drinks.Drink;
import tropicraft.drinks.Ingredient;
import tropicraft.drinks.MixerRecipe;
import tropicraft.drinks.MixerRecipeRegistry;
import tropicraft.ModInfo;
import tropicraft.Tropicraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCocktail extends ItemTropicraftImpl {
	private static final int DEFAULT_COLOR = 0xf3be36;
	private Icon contentsIcon;

	// nbt layout:
	// - byte DrinkID: 0 if no known drink, else the Drink.drinkList index
	// - int Color: alpha blended mix of colors based on ingredients
	// - NBTTagList Ingredients
	//   - byte IngredientID: Ingredient.ingredientList index
	//   - short Count: count of this ingredient in the mixture, typically 1
	
	public ItemCocktail(int i, String imgName, CreativeTabs tabs) {
		super(i, imgName, tabs);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
		setContainerItem(TropicraftItems.bambooMugEmpty);
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if (par1ItemStack.stackTagCompound == null) {
			return;
		}
		
		NBTTagList ingredients = par1ItemStack.stackTagCompound.getTagList("Ingredients");

		for (int i = 0; i < ingredients.tagCount(); ++i) {
			NBTTagCompound ingredient = (NBTTagCompound) ingredients.tagAt(i);
			//int count = ingredient.getShort("Count");
			int id = ingredient.getByte("IngredientID");
			String ingredientName = Ingredient.ingredientsList[id].getIngredient().getDisplayName();
			//String lvl = StatCollector.translateToLocal("enchantment.level." + count);
			//par3List.add(ingredientName + " " + lvl);
			par3List.add(ingredientName);
		}
		
		Drink drink = Drink.drinkList[par1ItemStack.stackTagCompound.getByte("DrinkID")];
		
		if (drink != null) {
			par3List.add("\247o"+drink.displayName);
		}
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (MixerRecipe recipe: MixerRecipeRegistry.getInstance().getRecipes()) {
			par3List.add(makeCocktail(recipe));
		}
	}
	
	@Override
	public boolean getShareTag() {
		return true;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamageForRenderPass(int par1, int par2) {
    	return par2 == 0 ? this.itemIcon : this.contentsIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
    	if (par2 == 0) {
    		return 0xffffff;
    	} else {
    		return getCocktailColor(par1ItemStack);
    	}
    }
    
    public static int getCocktailColor(ItemStack stack) {
    	if (stack.stackTagCompound != null) {
    		if (stack.stackTagCompound.hasKey("Color")) {
    			return stack.stackTagCompound.getInteger("Color"); 
    		} else {
    			return DEFAULT_COLOR;
    		}
    	} else {
    		return DEFAULT_COLOR;
    	}
    }
    
    public static ItemStack makeCocktail(MixerRecipe recipe) {
		ItemStack stack = new ItemStack(TropicraftItems.cocktail);
		NBTTagCompound nbt = new NBTTagCompound();
		Drink drink = recipe.getCraftingResult();
		nbt.setByte("DrinkID", (byte)drink.drinkId);
		NBTTagList tagList = new NBTTagList();
		
		Ingredient primary = null;
		List<Ingredient> additives = new LinkedList<Ingredient>();
		
		for (Ingredient ingredient: recipe.getIngredients()) {
			NBTTagCompound ingredientNbt = new NBTTagCompound();
			ingredientNbt.setByte("IngredientID", (byte)ingredient.ingredientId);
			tagList.appendTag(ingredientNbt);
			
			if (ingredient.isPrimary()) {
				primary = ingredient;
			} else {
				additives.add(ingredient);
			}
		}
		
		nbt.setTag("Ingredients", tagList);
		
		int color = primary == null ? DEFAULT_COLOR : primary.getColor();
		
		for (Ingredient additive: additives) {
			color = ColorMixer.getInstance().alphaBlendRGBA(color, additive.getColor(), additive.getAlpha());
		}
		
		nbt.setInteger("Color", color);

		stack.stackTagCompound = nbt;
		return stack;
    }
    
    public static ItemStack makeCocktail(Ingredient[] ingredients) {
		ItemStack stack = new ItemStack(TropicraftItems.cocktail);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("DrinkID", (byte)0);
		NBTTagList tagList = new NBTTagList();
		
		Ingredient primary = null;
		List<Ingredient> additives = new LinkedList<Ingredient>();
		
		for (Ingredient ingredient: ingredients) {
			NBTTagCompound ingredientNbt = new NBTTagCompound();
			ingredientNbt.setByte("IngredientID", (byte)ingredient.ingredientId);
			tagList.appendTag(ingredientNbt);
			
			if (ingredient.isPrimary()) {
				primary = ingredient;
			} else {
				additives.add(ingredient);
			}
		}
		
		nbt.setTag("Ingredients", tagList);
		
		int color = primary == null ? DEFAULT_COLOR : primary.getColor();
		
		for (Ingredient additive: additives) {
			color = ColorMixer.getInstance().alphaBlendRGBA(color, additive.getColor(), additive.getAlpha());
		}
		
		nbt.setInteger("Color", color);
		
		stack.stackTagCompound = nbt;
		return stack;
    	
    }
    
    public static Ingredient[] getIngredients(ItemStack stack) {
    	if (stack.itemID != TropicraftItems.cocktail.itemID || !stack.hasTagCompound()) {
    		return new Ingredient[0];
    	}
    	
    	NBTTagCompound nbt = stack.getTagCompound();
    	NBTTagList tagList = nbt.getTagList("Ingredients");
    	Ingredient[] ingredients = new Ingredient[tagList.tagCount()];
    	
    	for (int i = 0; i < tagList.tagCount(); ++i) {
    		int id = ((NBTTagCompound)tagList.tagAt(i)).getByte("IngredientID");
    		ingredients[i] = Ingredient.ingredientsList[id];
    	}
    	
    	return ingredients;
    }
    
    public static Drink getDrink(ItemStack stack) {
    	if (stack.itemID != TropicraftItems.cocktail.itemID || !stack.hasTagCompound()) {
    		return null;
    	}
    	NBTTagCompound nbt = stack.getTagCompound();
    	return Drink.drinkList[nbt.getByte("DrinkID")];
    }
    
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		int var11 = par3World.getBlockId(par4, par5, par6);

		if (var11 == Block.snow.blockID) {
			par7 = 1;
		} else if (var11 != Block.vine.blockID && var11 != Block.tallGrass.blockID && var11 != Block.deadBush.blockID && (Block.blocksList[var11] == null || !Block.blocksList[var11] .isBlockReplaceable(par3World, par4, par5, par6))) {
			if (par7 == 0) {
				--par5;
			} else if (par7 == 1) {
				++par5;
			} else if (par7 == 2) {
				--par6;
			} else if (par7 == 3) {
				++par6;
			} else if (par7 == 4) {
				--par4;
			} else if (par7 == 5) {
				++par4;
			}
		}
		
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
			return false;
		} else if (par3World.canPlaceEntityOnSide(TropicraftBlocks.bambooMug.blockID, par4, par5, par6, false, par7, par2EntityPlayer, null)) {
			Block var12 = TropicraftBlocks.bambooMug;
			int var13 = this.getMetadata(par1ItemStack.getItemDamage());
			int var14 = var12.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, var13);

			if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, var14)) {
				par3World.playSoundEffect((double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F), (double) ((float) par6 + 0.5F), var12.stepSound.getPlaceSound(), (var12.stepSound.getVolume() + 1.0F) / 2.0F, var12.stepSound.getPitch() * 0.8F);
				--par1ItemStack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if (!world.setBlock(x, y, z, TropicraftBlocks.bambooMug.blockID, metadata, 2)) {
			return false;
		}

		if (world.getBlockId(x, y, z) == TropicraftBlocks.bambooMug.blockID) {
			TropicraftBlocks.bambooMug.onBlockPlacedBy(world, x, y, z, player, null);
			TropicraftBlocks.bambooMug.onPostBlockPlaced(world, x, y, z, metadata);
			
			TileEntityBambooMug mug = (TileEntityBambooMug) world.getBlockTileEntity(x, y, z);
			mug.setCocktail(stack.copy());
			
	        int var6 = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

	        int meta = 2;
	        
	        if (var6 == 0) {
	        	meta = 2;
	        } else if (var6 == 1) {
	        	meta = 5;
	        } else if (var6 == 2) {
	        	meta = 3;
	        } else if (var6 == 3) {
	        	meta = 4;
	        }

	        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}

		return true;
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.drink;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
    	Drink drink = getDrink(par1ItemStack);
    	
    	if (drink != null) {
    		if (!par3EntityPlayer.canEat(drink.alwaysEdible)) {
    			return par1ItemStack;
    		}
    	} else if (!par3EntityPlayer.canEat(false)) {
    		return par1ItemStack;
    	}
    	
    	par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

        return par1ItemStack;
    }
    
    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        
    	for (Ingredient ingredient: getIngredients(par1ItemStack)) {
    		ingredient.onDrink(par3EntityPlayer);
    	}
    	
    	Drink drink = getDrink(par1ItemStack);
    	
    	if (drink != null) {
    		drink.onDrink(par3EntityPlayer);
    	}
    	
        return new ItemStack(TropicraftItems.bambooMugEmpty);
    }

	@Override
	public void registerIcons(IconRegister iconRegistry) {
		super.registerIcons(iconRegistry);
		this.contentsIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName() + "contents");
	}
}
