package tropicraft.questsystem.quests;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tropicraft.ai.WorldDirector;
import tropicraft.items.TropicraftItems;
import tropicraft.packets.TropicraftPacketHandler;
import tropicraft.questsystem.EnumQuestState;
import tropicraft.questsystem.PlayerQuests;
import CoroAI.componentAI.ICoroAI;

public class ActiveQuest {

	public int questID;
	public EnumQuestState curState = EnumQuestState.ASSIGNED;
	
	public PlayerQuests playerQuests;
	
	public boolean givesPage = false;
	
	public ActiveQuest(PlayerQuests parPlQuests, int parID) {
		playerQuests = parPlQuests;
		questID = parID;
	}
	
	public void tick() {
		
	}
	
	public void saveAndSync() {
		playerQuests.saveAndSync();
	}
	
	public void load(NBTTagCompound parNBT) {
		curState = EnumQuestState.get(parNBT.getInteger("curState"));
	}
	
	public void save(NBTTagCompound parNBT) {
		parNBT.setInteger("curState", curState.ordinal());
	}
	
	public void setState(EnumQuestState state) {
		curState = state;
		saveAndSync();
	}
	
	public boolean isComplete() {
		return curState == EnumQuestState.COMPLETE;
	}
	
	public void eventComplete(ICoroAI ent) {
		setState(EnumQuestState.COMPLETE);
		givePage(playerQuests.playerInstance, true);
	}
	
	public void givePage(EntityPlayer par1EntityPlayer, boolean itemForm) {
		
		int maxPages = 16;
		NBTTagCompound pNBT = WorldDirector.getPlayerNBT(par1EntityPlayer.username);
		NBTTagCompound nbt = pNBT.getCompoundTag("pagesData");
		if (nbt == null) nbt = new NBTTagCompound();
		
		for (int i = 0; i < maxPages; i++) {
			if (!nbt.hasKey("p" + i)) {
				if (itemForm) {
			//TODO		par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.journalPage, i));
				} else {
					nbt.setBoolean("p" + i, true);
					pNBT.setCompoundTag("pagesData", nbt);
					TropicraftPacketHandler.syncPagesData(par1EntityPlayer.username);
				}
				break;
			}
		}
	}
}
