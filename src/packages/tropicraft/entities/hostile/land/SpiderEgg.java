package tropicraft.entities.hostile.land;

import java.util.Random;

import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tropicraft.entities.ai.jobs.v3.JobEggHatch;
import tropicraft.entities.hostile.EntityLand;

public class SpiderEgg extends EntityLand implements IMob {

	public int motherID = -1;
	public JobEggHatch job;
	
	public SpiderEgg(World par1World) {
		super(par1World);
		
		//texture = "/mods/TropicraftMod/textures/entities/spideregg.png";
		agent.jobMan.clearJobs();
		agent.jobMan.addPrimaryJob(job = new JobEggHatch(agent.jobMan));
		
		agent.shouldAvoid = false;
	}
	
	@Override
	protected boolean canDespawn()
    {
        return false;
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.readEntityFromNBT(par1nbtTagCompound);
		motherID = par1nbtTagCompound.getInteger("motherID");
		job.motherID = motherID;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("motherID", motherID);
	}
	
	@Override
	protected String getLivingSound()
    {
        return "mob.spider.say";
    }

	@Override
    protected String getHurtSound()
    {
        return "mob.spider.say";
    }

	@Override
    protected String getDeathSound()
    {
        return "mob.spider.death";
    }

}
