package net.tropicraft.questsystem.quests;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.tropicraft.questsystem.EnumQuestState;
import net.tropicraft.questsystem.PlayerQuests;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ActiveQuest {

	public int questID;
	public EnumQuestState curState = EnumQuestState.ASSIGNED;
	
	public PlayerQuests playerQuests;
	
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
	
	public boolean hasFinalRequirement() {
		if (curState == EnumQuestState.CONCLUDING) {
			if (true) { //blank quest base class
				return true;
			}
		}
		return false;
	}
	
	public boolean isComplete() {
		if (curState == EnumQuestState.COMPLETE) return true;
		return false;
	}
}
