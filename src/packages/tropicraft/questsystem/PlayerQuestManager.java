package tropicraft.questsystem;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;
import tropicraft.Tropicraft;
import tropicraft.questsystem.quests.ActiveQuest;

public class PlayerQuestManager {

	public static PlayerQuestManager i;
	public HashMap<String, PlayerQuests> playerQuests;
	public World worldRef;
	
	
	
	public PlayerQuestManager(World world) {
		worldRef = world;
		playerQuests = new HashMap();
		i = this;
	}
	
	public void check(EntityPlayer entP) {
		if (playerQuests.containsKey(entP.username)) {
			
		} else {
			playerQuests.put(entP.username, new PlayerQuests(this, entP));
		}
	}
	
	public PlayerQuests getPlayerQuests(EntityPlayer entP) {
		check(entP);
		return playerQuests.get(entP.username);
	}
	
	public void tick() {
		for (int i = 0; i < worldRef.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
			check(entP);
			playerQuests.get(entP.username).tick();
		}
	}
	
	public void giveQuest(int questID, String username, boolean giveToAllPlayers) {
		
		if (giveToAllPlayers) {
			for (int i = 0; i < worldRef.playerEntities.size(); i++) {
				EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
				check(entP);
				playerQuests.get(entP.username).giveQuest(questID);
			}
		} else {
			EntityPlayer entP = worldRef.getPlayerEntityByName(username);
			if (entP != null) {
				check(entP);
				playerQuests.get(entP.username).giveQuest(questID);
			}
		}
		
		saveAndSync();
	}
	
	public void clearQuests(String username, boolean allPlayers, boolean save) {
		if (allPlayers) {
			for (int i = 0; i < worldRef.playerEntities.size(); i++) {
				EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
				check(entP);
				playerQuests.get(entP.username).clearQuests();
			}
		} else {
			EntityPlayer entP = worldRef.getPlayerEntityByName(username);
			if (entP != null) {
				check(entP);
				playerQuests.get(entP.username).clearQuests();
			}
		}
		
		if (save) saveAndSync();
	}
	
	public void markQuestCompleteForAll(ActiveQuest quest) {
		for (int i = 0; i < worldRef.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
			check(entP);
			playerQuests.get(entP.username).removeQuest(quest);
		}
	}
	
	public void onEvent(Event event) {
		for (int i = 0; i < worldRef.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
			check(entP);
			playerQuests.get(entP.username).onEvent(event);
		}
	}
	
	public void saveAndSync() {
		for (int i = 0; i < worldRef.playerEntities.size(); i++) {
			EntityPlayer entP = (EntityPlayer)worldRef.playerEntities.get(i);
			check(entP);
			playerQuests.get(entP.username).saveAndSyncImpl();
		}
	}
	
	public void saveAndUnload() {
		Tropicraft.dbg("Unloading quest data");
		saveAndSync();
		clearQuests("", true, false);
	}
	
}
