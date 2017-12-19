package net.tropicraft.questsystem;

import net.tropicraft.entities.EntityTropiSkeleton;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.questsystem.quests.ActiveQuest;
import net.tropicraft.questsystem.quests.ItemQuest;
import net.tropicraft.questsystem.quests.KillEntityQuest;

public class QuestCreator {
	
	public static int maxQuestCount = 30;

	public static ActiveQuest getNewQuestInstance(PlayerQuests plQuests, int questID) {
		
		switch (questID) {
			case 0:
				return new KillEntityQuest(plQuests, questID, EntityTropiSkeleton.class, 50, false);
			case 1:
				return new ItemQuest(plQuests, questID, TropicraftMod.ashenMask.shiftedIndex, 5, true);
		}
		
		return null;
				
	}
	
}
