package tropicraft.questsystem.quests;

import CoroUtil.componentAI.ICoroAI;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import tropicraft.questsystem.EnumQuestState;
import tropicraft.questsystem.PlayerQuests;

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
			
		}
	}
	
	public void pickupEvent(EntityItemPickupEvent event) {
		if (event.entityPlayer.equals(playerQuests.playerInstance) && event.item.getEntityItem().getItem().itemID == neededItemID) {
			curItemCount++;
			saveAndSync();
			System.out.println("quest item inc");
		}
	}
	
	@Override
	public void eventComplete(ICoroAI ent) {
		// TODO Auto-generated method stub
		super.eventComplete(ent);
	}
}
