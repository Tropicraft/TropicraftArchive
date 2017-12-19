package tropicraft.entities.ai.jobs.v3;

import tropicraft.items.ItemTropicsPickaxe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import CoroAI.componentAI.jobSystem.JobBase;
import CoroAI.componentAI.jobSystem.JobManager;

public class JobSleep extends JobBase {
	
	public boolean sleeping = true;

	public JobSleep(JobManager jm) {
		super(jm);
	}
	
	@Override
	public boolean shouldExecute() {
		return true;
	}
	
	@Override
	public boolean shouldContinue() {
		return !sleeping;
	}
	
	@Override
	public boolean hookHit(DamageSource ds, int damage) {
		
		//System.out.println(ds.toString());
		if (ds.getEntity() instanceof EntityPlayer) {
			sleeping = false;
			ItemStack is = ((EntityPlayer)ds.getEntity()).getCurrentEquippedItem();
			if (is != null && (is.getItem() instanceof ItemPickaxe || is.getItem() instanceof ItemTropicsPickaxe)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	@Override
	public void onIdleTick() {
		if (!sleeping) super.onIdleTick();
	}

}
