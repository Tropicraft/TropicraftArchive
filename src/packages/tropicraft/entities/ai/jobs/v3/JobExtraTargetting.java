package tropicraft.entities.ai.jobs.v3;

import tropicraft.items.ItemTropicsPickaxe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import CoroAI.componentAI.jobSystem.JobBase;
import CoroAI.componentAI.jobSystem.JobManager;

public class JobExtraTargetting extends JobBase {
	
	//Extra targetting for player, atm range based omnipotence
	public int rangeTarget = 48;
	
	public JobExtraTargetting(JobManager jm) {
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
	public void tick() {
		super.tick();
		if (ai.entityToAttack == null) ai.entityToAttack = ent.worldObj.getClosestVulnerablePlayerToEntity(ent, rangeTarget);
	}

}
