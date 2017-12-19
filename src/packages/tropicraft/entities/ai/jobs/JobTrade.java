package tropicraft.entities.ai.jobs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.blocks.tileentities.TileEntityPurchasePlate;
import tropicraft.economy.ItemEntry;
import tropicraft.economy.ItemValueEntries;
import tropicraft.economy.ItemValues;
import tropicraft.items.TropicraftItems;
import CoroAI.c_CoroAIUtil;
import CoroAI.entity.EnumJobState;
import CoroAI.entity.JobBase;
import CoroAI.entity.JobManager;

public class JobTrade extends JobBase {
	
	public float tradeDistTrigger;
	
	public int tradeLastItemOffer;
	
	public ChunkCoordinates tradeBlockPos;
	public ChunkCoordinates frameBlockPos;
	
	public TileEntityPurchasePlate tradePlate;
	
	public EntityPlayer activeTrader;
	
	public ArrayList<ItemStack> offeredItems = new ArrayList();
	
	public int idTradeBlock;
	
	public JobTrade(JobManager jm) {
		super(jm);
	}
	
	public boolean interact(EntityPlayer ent) {
		if (offeredItems == null) offeredItems = new ArrayList();
		ItemStack is = ent.getCurrentEquippedItem();
		if (is != null) {
			//System.out.println(is);
			if (ItemValues.getItemEntry(is) != null) {
				ent.inventory.mainInventory[ent.inventory.currentItem] = null;
				offeredItems.add(is);
				
				if (tradePlate != null) {
					tradePlate.credit = getOfferedItemsValue();
					MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(tradePlate.getDescriptionPacket());
				}
			}
			
			//System.out.println();
		}
		
		return false;
	}

	public void convertOfferingsToCurrency(int newCredit) {
		
		offeredItems.clear();
		
		int leftToConvert = newCredit;
		
		while (leftToConvert > ItemValueEntries.currency.getMaxStackSize()) {
			offeredItems.add(new ItemStack(ItemValueEntries.currency.getItem(), ItemValueEntries.currency.getMaxStackSize()));
			//activeTrader.inventory.addItemStackToInventory();
			leftToConvert -= ItemValueEntries.currency.getMaxStackSize();
		}
		
		if (leftToConvert > 0) {
			offeredItems.add(new ItemStack(ItemValueEntries.currency.getItem(), leftToConvert));
		}
	}
	
	public int getOfferedItemsValue() {
		int value = 0;
		try {
			for (int i = 0; i < offeredItems.size(); i++) {
				//value += (float)ValuedItems.getItemCost(offeredItems.get(i).getItem()) / (float)ValuedItems.getBuyItemCount(offeredItems.get(i).getItem()) * offeredItems.get(i).stackSize;
				value += ItemValues.getItemEntry(offeredItems.get(i)).getTotalValue(offeredItems.get(i));//(float)ValuedItems.getItemCost(offeredItems.get(i).getItem()) / (float)ValuedItems.getBuyItemCount(offeredItems.get(i).getItem()) * offeredItems.get(i).stackSize;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return value;
	}
	
	public int returnCredit() {
		int value = 0;
		try {
			for (int i = 0; i < offeredItems.size(); i++) {
				activeTrader.inventory.addItemStackToInventory(offeredItems.get(i));
			}
			offeredItems.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return value;
	}
	
	@Override
	public void tick() {
		
		//setJobState(EnumJobState.IDLE);
		
		if (ent != null && ent.getHealth() == 20) {
			ent.setHealth(80);
		}
		
		//TEMP!!!!!!!!
		idTradeBlock = TropicraftBlocks.tradeBlock.blockID;
		
		
		ent.maxDistanceFromHome = 0.5F;
		tradeDistTrigger = 4F;
		
		if (tradeBlockPos == null) {
			tradeBlockPos = tickFind(idTradeBlock, 7);
			
			if (tradeBlockPos != null) {
				TileEntity tile = ent.worldObj.getBlockTileEntity(tradeBlockPos.posX, tradeBlockPos.posY, tradeBlockPos.posZ);
				
				if (tile != null) {
					//((TileEntityPurchasePlate)tile).tradeKoa = ent;
					tradePlate = (TileEntityPurchasePlate)tile;
				}
			}
		} else if (frameBlockPos == null) {
			List list = ent.worldObj.getEntitiesWithinAABBExcludingEntity(ent, ent.boundingBox.expand(6, 3, 6));
			
			for(int j = 0; j < list.size(); j++)
	        {
	            Entity entity1 = (Entity)list.get(j);
	            if(entity1 instanceof EntityItemFrame) {
	            	if (((EntityItemFrame)entity1).getDisplayedItem() == null) {
	            		((EntityItemFrame)entity1).setDisplayedItem(ItemValues.itemsBuyable.get(ent.worldObj.rand.nextInt(ItemValues.itemsBuyable.size())).item);//new ItemStack(ValuedItems.getItemBuyable(ent.worldObj.rand.nextInt(ValuedItems.buyables.size()))));
	            	}
	            }
	        }
		}
		
		if (activeTrader == null) {
			activeTrader = ent.worldObj.getClosestPlayerToEntity(ent, tradeDistTrigger);
			if (activeTrader != null) {
				tradeStart();
			}
		} else {
			if (ent.getDistanceToEntity(activeTrader) > tradeDistTrigger * 1.5) {
				//walked away, reset trade (give back anything?)
				tradeReset();
			} else {
				tradeTick();
			}
		}
	}
	
	public void tradeStart() {
		//System.out.println("trade start");
		if (tradePlate != null) {
			tradePlate.tradeState = 1;
			tradePlate.credit = getOfferedItemsValue();
		}
	}
	
	@Override
	public void tradeConfirmCallback() {
		
		int credit = getOfferedItemsValue();
		ItemEntry ie = ItemValues.itemsBuyable.get(tradePlate.itemIndex);
		int cost = ie.value;
		
		if (credit >= cost) {
			credit -= cost;
			
			convertOfferingsToCurrency(credit);
			
			ItemStack is = ie.item.copy();
			
			activeTrader.inventory.addItemStackToInventory(is);
			
		} else {
			//not enough credit!
		}
		
		tradeSuccess();
	}
	
	public void tradeSuccess() {
		//dont use reset here, reset is for when player leaves
		//System.out.println("trade success");
		if (tradePlate != null) {
			tradePlate.tradeState = 1;
			tradePlate.credit = getOfferedItemsValue();
		}
	}
	
	public void tradeReset() {
		if (offeredItems != null) returnCredit();
		activeTrader = null;
		if (tradePlate != null) {
			tradePlate.tradeState = 0;
			tradePlate.credit = 0;
			tradePlate.activeTrader = null;
		}
		System.out.println("trade reset");
	}
	
	public void tradeTick() {
		TileEntity tEnt = null;//
		if (tradeBlockPos != null) tEnt = ent.worldObj.getBlockTileEntity(tradeBlockPos.posX, tradeBlockPos.posY, tradeBlockPos.posZ);
		if (tradeBlockPos != null && tEnt == null) {
			tradeBlockPos = null;
		} else {
			if (tEnt instanceof TileEntityPurchasePlate) {
				((TileEntityPurchasePlate) tEnt).activeTrader = this.activeTrader;
			}
		}
	}
	
	@Override
	public void setJobItems() {
		c_CoroAIUtil.setItems_JobTrade(ent);
	}
	
	public ChunkCoordinates tickFind(int id, int range) {
		
		int randX = (int) ent.posX+ent.rand.nextInt(range) - (range/2);
		int randY = (int) ent.posY+ent.rand.nextInt(range) - (range/2);
		int randZ = (int) ent.posZ+ent.rand.nextInt(range) - (range/2);
		
		int foundID = ent.worldObj.getBlockId(randX, randY, randZ);
		
		if (foundID == id) {
			System.out.println("found trade block");
			return new ChunkCoordinates(randX, randY, randZ);
		}
		
		return null;
	}
	
	@Override
	public boolean shouldExecute() {
		//execute if no threat
		return isAreaSecure();
	}
	
	@Override
	public boolean shouldContinue() {
		//continue if there is a threat
		return !isAreaSecure();
	}
	
	public boolean isAreaSecure() {
		if (tradeBlockPos != null) { 
			List list = ent.worldObj.getEntitiesWithinAABBExcludingEntity(ent, AxisAlignedBB.getBoundingBox(tradeBlockPos.posX, tradeBlockPos.posY, tradeBlockPos.posZ, tradeBlockPos.posX, tradeBlockPos.posY, tradeBlockPos.posZ).expand(6, 3, 6));
			
			for(int j = 0; j < list.size(); j++)
	        {
	            Entity entity1 = (Entity)list.get(j);
	            if(ent.isEnemy(entity1)) {
	            	return false;
	            }
	        }
		}
		return true;
	}
	
	@Override
	public void onIdleTick() {
		
		//ent.setHealth(20);
		//System.out.println(ent.currentAction);////getFoodStats().getFoodLevel());
		//System.out.println(ent.job.getPrimaryJobClass().state);
		
		if (ent.getNavigator().noPath()) {
			
			//EntityPlayer clPl = ent.worldObj.getClosestPlayerToEntity(ent, 5F);
			
			if (activeTrader != null) {
				ent.faceEntity(activeTrader, 15, 15);
			} else if (tradeBlockPos != null) {
				ent.faceCoord(tradeBlockPos.posX, tradeBlockPos.posY, tradeBlockPos.posZ, 15, 15);
			}
			
			if (ent.getDistance(ent.homeX, ent.homeY, ent.homeZ) > ent.maxDistanceFromHome) {
	    		
	    		int randsize = 4;
	    		//ent.walkTo(ent, ent.homeX+ent.rand.nextInt(randsize) - (randsize/2), ent.homeY+0, ent.homeZ+ent.rand.nextInt(randsize) - (randsize/2),ent.maxPFRange, 600);
	    		ent.walkTo(ent, ent.homeX, ent.homeY, ent.homeZ,ent.maxPFRange, 600);
	    		
			} else {
				if (ent.worldObj.rand.nextInt(10) == 0) {
					int randsize = 2;
		    		ent.walkTo(ent, ent.homeX+ent.rand.nextInt(randsize) - (randsize/2), ent.homeY+0, ent.homeZ+ent.rand.nextInt(randsize) - (randsize/2),ent.maxPFRange, 600);
				}
				
			}
			
			
		}
	}

	protected void jobTrading() {
		
		//consider this function broken after the refactoring!!!!!!!!!!!!!!!!!! FIX ME EVENTUALLY!
		
		EntityPlayer entP = ent.worldObj.getClosestPlayerToEntity(ent, 16F);
		int pX = (int)(entP.posX-0.5F);
		int pY = (int)entP.posY;
		int pZ = (int)(entP.posZ-0.5F);
		
		//Stop the trade
		if (ent.getDistanceToEntity(entP) > tradeDistTrigger/* || !mod_tropicraft.getIsEngaged(ent.entityId)*/) {
			/*if (mod_tropicraft.getIsEngaged(ent.entityId)) {
				mod_tropicraft.setEngaged(false, -1);
			}
			ent.conversationHandler.hasSaidFirstSentence = false;*/
			tradeTimeout = 600;
			ent.swapJob(jm.priJob);
		}
		
		if (state == EnumJobState.IDLE) {
			ent.walkTo(ent, pX, pY, pZ, ent.maxPFRange, 600);
			setJobState(EnumJobState.W1);
		} else if (state == EnumJobState.W1) {
			if (ent.getDistanceToEntity(entP)/*ent.getDistance(pX, pY, pZ)*/ <= 2F) {
				ent.setPathExToEntity(null);
				ent.faceEntity(entP, 30F, 30F);
				//ent.faceCoord((int)(homeX-0.5F), (int)homeY, (int)(homeZ-0.5F), 180, 180);
			
				//talk to player
				//System.out.println("talk trigger");
				
				//get trade item
				int itemSlot = getRandomTradeItemSlot();
				ent.setCurrentSlot(itemSlot);
				
				setJobState(EnumJobState.W2);
			} else if (walkingTimeout <= 0 || !ent.hasPath()) {
				//ent.setPathExToEntity(null);
				//walkTo(ent, pX, pY, pZ, maxPFRange, 600);
			}
		//Active trading
		} else if (state == EnumJobState.W2) {
			
			//bug fix
			if (ent.inventory.currentItem == 0) {
				ent.setCurrentSlot(getRandomTradeItemSlot());
			}
			
			if (entP.getCurrentEquippedItem() != null && tradeLastItemOffer != entP.getCurrentEquippedItem().itemID) {
				Item tryItem = entP.getCurrentEquippedItem().getItem();
				tradeLastItemOffer = entP.getCurrentEquippedItem().itemID;
				/*if (ent instanceof EntityKoaMemberNew && ((EntityKoaMemberNew)ent).trade_PlayerOfferings.contains(tryItem)) {
					
					ModLoader.getMinecraftInstance().thePlayer.addChatMessage(" <" + '\247' + "a" + ent.name + '\247' + "f" + "> " + ent.conversationHandler.getContentPhrase());
					
				} else {
					ModLoader.getMinecraftInstance().thePlayer.addChatMessage(" <" + '\247' + "a" + ent.name + '\247' + "f" + "> " + ent.conversationHandler.getUpsetPhrase());
				}*/
			}
			
			
			ent.faceEntity(entP, 10F, 10F);
			 
			//dont forget, when transaction occurs, duplicate the item, dont remove from koa (have him switch to main item though)
			
		}
		
		
	}
	
	protected int getRandomTradeItemSlot() {
		ent.activeTradeItemSlot = ent.slot_Trade - ent.rand.nextInt(3);
		
		return ent.activeTradeItemSlot;
	}
	
	public void koaTrade(EntityPlayer ep) {
		ItemStack itemPlayer = ep.getCurrentEquippedItem();
		ItemStack itemKoa = ent.getCurrentEquippedItem();
		
		if (itemKoa == null || itemPlayer == null) return;
		
		//Remove items into buffer, then swap
		
		//Do player item remove
		if (itemPlayer.isStackable() && itemPlayer.stackSize > 0) itemPlayer.stackSize--;
		if (itemPlayer.stackSize <= 0 || !itemPlayer.isStackable()) {
			ep.inventory.mainInventory[ep.inventory.currentItem] = null;
		}
		
		//Koa item remove, wait.... no!
		//itemPlayer.stackSize--;
		/*if (itemPlayer.stackSize == 0) {
			ep.inventory.mainInventory[inventory.currentItem] = null;
		}*/
		ep.inventory.addItemStackToInventory(itemKoa);
		ent.inventory.addItemStackToInventory(itemPlayer);
		
		/*if (mod_tropicraft.getIsEngaged(ent.entityId)) {
			mod_tropicraft.setEngaged(false, -1);
		}
		ent.conversationHandler.hasSaidFirstSentence = false;*/
		//ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Woo");
		ent.swapJob(ent.job.priJob);
		
		//10 minutes
		tradeTimeout = 12000;
		
	}
	
	
	
}
