package tropicraft.questsystem;

import tropicraft.entities.hostile.land.Skeleton;
import tropicraft.items.TropicraftItems;
import tropicraft.questsystem.quests.ActiveQuest;
import tropicraft.questsystem.quests.ItemQuest;
import tropicraft.questsystem.quests.KillEntityQuest;

public class QuestCreator {
	
	public static int maxQuestCount = 30;
	public static int curQuestCount = 2; //for randoms

	public static ActiveQuest getNewQuestInstance(PlayerQuests plQuests, int questID) {
		
		switch (questID) {
			case 0:
				return new KillEntityQuest(plQuests, questID, Skeleton.class, 15, true);
			case 1:
				return new ItemQuest(plQuests, questID, TropicraftItems.freshMarlin.itemID, 5, true);
		}
		
		return null;
				
	}
	
}
