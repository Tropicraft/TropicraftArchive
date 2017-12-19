package net.tropicraft.questsystem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.tropicraft.questsystem.quests.ActiveQuest;

import java.util.HashMap;

public class PlayerQuestManager {

	public HashMap<String, PlayerQuests> playerQuestManagers;
	public World worldRef;
	
	
	
	public PlayerQuestManager(World world) {
		worldRef = world;
		playerQuestManagers = new HashMap();
	}
	
	public void check(EntityPlayer entP) {
		if (playerQuestManagers.containsKey(entP.username)) {
			
		} else {
			playerQuestManagers.put(entP.username, new PlayerQuests(this, entP));
		}
	}
	
	public void tick() {
		for (int i = 0; i < worldRef.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
			check(entP);
			playerQuestManagers.get(entP.username).tick();
		}
	}
	
	public void giveQuest(int questID, String username, boolean giveToAllPlayers) {
		if (giveToAllPlayers) {
			for (int i = 0; i < worldRef.playerEntities.size(); i++) {
				EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
				check(entP);
				playerQuestManagers.get(entP.username).tick();
			}
		} else {
			
		}
	}
	
	public void markQuestCompleteForAll(ActiveQuest quest) {
		for (int i = 0; i < worldRef.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
			check(entP);
			playerQuestManagers.get(entP.username).removeQuest(quest);
		}
	}
	
	public void onEvent(Event event) {
		for (int i = 0; i < worldRef.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
			check(entP);
			playerQuestManagers.get(entP.username).onEvent(event);
		}
	}
	
	public void saveAndSync() {
		for (int i = 0; i < worldRef.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
			check(entP);
			playerQuestManagers.get(entP.username).saveAndSyncImpl();
		}
	}
	
}
