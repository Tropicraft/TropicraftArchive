package tropicraft.blocks.tileentities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import tropicraft.drinks.Ingredient;
import tropicraft.drinks.MixerRecipeRegistry;
import tropicraft.items.ItemCocktail;
import tropicraft.items.TropicraftItems;
import tropicraft.Tropicraft;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityEIHMixer extends TileEntity {

  /**
	 * Number of ticks the mixer has been mixin'
	 */
	public int ticks;

	/**
	 * Number of ticks to mix
	 */
	public static final int TICKS_TO_MIX = 4*20;

	public ItemStack[] ingredients;

	public boolean mixing;
	
	public ItemStack result;

	public TileEntityEIHMixer() {
		mixing = false;
		ingredients = new ItemStack[2];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.ticks = nbt.getInteger("MixTicks");
		this.mixing = nbt.getBoolean("Mixing");

		if (nbt.hasKey("Ingredient1")) {
			this.ingredients[0] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Ingredient1"));
		} else {
			this.ingredients[0] = null;
		}
		
		if (nbt.hasKey("Ingredient2")) {
			this.ingredients[1] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Ingredient2"));
		} else {
			this.ingredients[1] = null;
		}
		
		if (nbt.hasKey("Result")) {
			this.result = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Result"));
		} else {
			this.result = null;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("MixTicks", this.ticks);
		nbt.setBoolean("Mixing", mixing);

		if (this.ingredients[0] != null) {
			NBTTagCompound var4 = new NBTTagCompound();
			this.ingredients[0].writeToNBT(var4);
			nbt.setCompoundTag("Ingredient1", var4);
		}
		
		if (this.ingredients[1] != null) {
			NBTTagCompound var4 = new NBTTagCompound();
			this.ingredients[1].writeToNBT(var4);
			nbt.setCompoundTag("Ingredient2", var4);
		}
		
		if (this.result != null) {
			NBTTagCompound var4 = new NBTTagCompound();
			this.result.writeToNBT(var4);
			nbt.setCompoundTag("Result", var4);
		}
	}

	@Override
	public void updateEntity() {
		if (ticks < TICKS_TO_MIX && mixing) {
			ticks++;
			if (ticks == TICKS_TO_MIX) {
				finishMixing();
			}
		}
	}
	
	public boolean isDoneMixing() {
		return result != null;
	}
	
	public ItemStack[] getIngredients() {
		return this.ingredients;
	}
	
	public Ingredient[] listAllIngredients() {
		List<Ingredient> is = new ArrayList<Ingredient>();
		
		if (ingredients[0] == null || ingredients[1] == null) {
			return null;
		}
		
		// TODO: use Collections.disjoint?
		is.addAll(listIngredients(ingredients[0]));
		is.addAll(listIngredients(ingredients[1]));
		Collections.sort(is);
		
		return is.toArray(new Ingredient[is.size()]);
	}
	
	public static Ingredient findMatchingIngredient(ItemStack stack) {
		for (Ingredient ingredient: Ingredient.ingredientsList) {
			if (ingredient == null) {
				continue;
			}
			if (ItemStack.areItemStacksEqual(ingredient.getIngredient(), stack)) {
				return ingredient;
			}
		}
		
		return null;
	}
	
	public static List<Ingredient> listIngredients(ItemStack stack) {
		List<Ingredient> is = new ArrayList<Ingredient>();
		
		if (stack.itemID == TropicraftItems.cocktail.itemID) {
			for (Ingredient ingredient: ItemCocktail.getIngredients(stack)) {
				is.add(ingredient);
			}
		} else {
			Ingredient i = findMatchingIngredient(stack);
			is.add(i);
		}
		
		return is;
	}
	
	public void startMixing() {
		this.ticks = 0;
		this.mixing = true;
		this.sync();
	}

	public void emptyMixer() {
		if (this.ingredients[0] != null) {
			EntityItem item = new EntityItem(worldObj, xCoord, yCoord, zCoord, ingredients[0]);
			worldObj.spawnEntityInWorld(item);
			this.ingredients[0] = null;
		}
		
		if (this.ingredients[1] != null) {
			EntityItem item = new EntityItem(worldObj, xCoord, yCoord, zCoord, ingredients[1]);
			worldObj.spawnEntityInWorld(item);
			this.ingredients[1] = null;
		}
		
		this.ticks = TICKS_TO_MIX;
		this.mixing = false;
		this.sync();
	}
	
	public void retrieveResult() {
		EntityItem e = new EntityItem(worldObj, xCoord, yCoord, zCoord, result);
		worldObj.spawnEntityInWorld(e);
		
		ItemStack container1 = ingredients[0].getItem().getContainerItemStack(ingredients[0]);
		
		if (container1 != null) {
			e = new EntityItem(worldObj, xCoord, yCoord, zCoord, container1);
			worldObj.spawnEntityInWorld(e);
		}
		
		ItemStack container2 = ingredients[1].getItem().getContainerItemStack(ingredients[1]);
		
		if (container2 != null) {
			e = new EntityItem(worldObj, xCoord, yCoord, zCoord, container2);
			worldObj.spawnEntityInWorld(e);
		}
		
		this.ingredients[0] = null;
		this.ingredients[1] = null;
		this.result = null;
		this.ticks = 0;
		this.mixing = false;
		this.sync();
	}
	
	public void finishMixing() {
		result = getResult(getIngredients());
		this.sync();
	}

	public boolean addToMixer(ItemStack ingredient) {
		if (this.ingredients[0] == null) {
			if (ingredient.itemID != TropicraftItems.cocktail.itemID) {
				Ingredient i = findMatchingIngredient(ingredient);
				if (i == null || !i.isPrimary()) {
					return false;
				}
			}
			this.ingredients[0] = ingredient;
			sync();
			return true;
		} else if (this.ingredients[1] == null) {
			if (ingredient.itemID == TropicraftItems.cocktail.itemID) {
				// prevent mixing multiple primary ingredients
				// all cocktails already contain one
				return false;
			}
			
			List<Ingredient> ingredients0 = listIngredients(this.ingredients[0]);
			Ingredient i = findMatchingIngredient(ingredient);
			
			if (i == null || i.isPrimary() || ingredients0.contains(i)) {
				return false;
			}
			
			this.ingredients[1] = ingredient;
			sync();
			return true;
		} else {
			return false;
		}
	}

	public boolean isMixing() {
		return this.mixing;
	}
	
	private boolean isMixerFull() {
		return ingredients[0] != null && ingredients[1] != null;
	}
	
	public boolean canMix() {
		return !mixing && isMixerFull();
	}
	
	public void sync() {
		PacketDispatcher.sendPacketToAllInDimension(getDescriptionPacket(), worldObj.provider.dimensionId);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound var1 = new NBTTagCompound();
		this.writeToNBT(var1);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, var1);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		this.readFromNBT(pkt.customParam1);
	}

	public ItemStack getResult(ItemStack[] ingredients2) {
		return MixerRecipeRegistry.getInstance().getResult(ingredients2);
	}

}
