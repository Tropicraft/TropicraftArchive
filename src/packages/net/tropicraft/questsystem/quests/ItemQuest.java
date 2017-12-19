package net.tropicraft.questsystem.quests;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.tropicraft.questsystem.EnumQuestState;
import net.tropicraft.questsystem.PlayerQuests;

public class ItemQuest extends ActiveQuest {
	
	public int neededItemID;
	public int neededItemCount;
	public boolean returnToQuestGiver;
	
	public int curItemCount;

	public ItemQuest(PlayerQuests parPlQuests, int parID, int itemID, int count, boolean parReturnToQuestGiver) {
		super(parPlQuests, parID);
		neededItemID = itemID;
		neededItemCount = count;
		returnToQuestGiver = parReturnToQuestGiver;
	}

	@Override
	public void tick() {
		super.tick();
		
		if (curState == EnumQuestState.ASSIGNED) {
			//check inv for item
			if (curItemCount >= neededItemCount) {
				if (returnToQuestGiver) {
					setState(EnumQuestState.CONCLUDING);
				} else {
					setState(EnumQuestState.COMPLETE);
				}
			}
		} else if (curState == EnumQuestState.CONCLUDING) {
			//logic that determines they have talked to the quest giver to complete the quest, should this be here or in the koa?
		}
	}
	
	public void pickupEvent(EntityItemPickupEvent event) {
		if (event.entityPlayer.equals(playerQuests.playerInstance) && event.item.func_92014_d().getItem().shiftedIndex == neededItemID) {
			curItemCount++;
			saveAndSync();
			System.out.println("quest item inc");
		}
	}
}
