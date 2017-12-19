package tropicraft.entities.ai.jobs.v3;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import tropicraft.ai.WorldDirector;
import tropicraft.packets.TropicraftPacketHandler;
import tropicraft.questsystem.EnumQuestState;
import tropicraft.questsystem.PlayerQuestManager;
import tropicraft.questsystem.QuestCreator;
import tropicraft.questsystem.quests.ActiveQuest;
import CoroUtil.componentAI.jobSystem.JobBase;
import CoroUtil.componentAI.jobSystem.JobManager;

public class JobQuestGiver extends JobBase {

	public JobQuestGiver(JobManager jm) {
		super(jm);
	}
	
	@Override
	public boolean shouldExecute() {
		return true;
	}
	
	@Override
	public boolean shouldContinue() {
		return true;
	}
	
	@Override
	public boolean hookInteract(EntityPlayer par1EntityPlayer) {
		if (!ent.worldObj.isRemote) {
			ActiveQuest quest = PlayerQuestManager.i.getPlayerQuests(par1EntityPlayer).getFirstQuestByStatus(EnumQuestState.CONCLUDING);
			if (quest == null) {
				int randID = 0;//par1EntityPlayer.worldObj.rand.nextInt(QuestCreator.curQuestCount);
				PlayerQuestManager.i.giveQuest(randID, par1EntityPlayer.username, false);
			} else {
				quest.eventComplete(entInt);
				questComplete(par1EntityPlayer);
			}
			return true;
		}
		return super.hookInteract(par1EntityPlayer);
	}
	
	@Override
	public void tick() {
		super.tick();
	}
	
	public void questComplete(EntityPlayer par1EntityPlayer) {
		//givePage(par1EntityPlayer);
	}
	
	
}
