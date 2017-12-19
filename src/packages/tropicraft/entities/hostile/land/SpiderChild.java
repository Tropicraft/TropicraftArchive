package tropicraft.entities.hostile.land;

import tropicraft.entities.ai.jobs.v3.JobExtraTargetting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

public class SpiderChild extends SpiderBase {

	public int age = 0;
	public int ageMax = 20*60*10; //10 irl minutes to get to adult, at then i guess transform to adult entity?
	
	public SpiderChild(World par1World) {
		super(par1World);

		agent.jobMan.addJob(new JobExtraTargetting(agent.jobMan));
		
		texture = "/mods/TropicraftMod/textures/entities/spiderchild.png";
		this.setSize(0.5F, 0.35F);
		agent.setMoveSpeed(0.33F);
		//this.setSize(0.35F, 0.25F);
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	@SideOnly(Side.CLIENT)

    /**
     * How large the spider should be scaled.
     */
    public float spiderScaleAmount()
    {
        return 0.5F;
    }

}
