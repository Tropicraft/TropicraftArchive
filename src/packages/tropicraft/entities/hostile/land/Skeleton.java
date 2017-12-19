package tropicraft.entities.hostile.land;

import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.entities.hostile.EntityLand;
import tropicraft.items.TropicraftItems;
import CoroUtil.componentAI.jobSystem.JobFormation;

public class Skeleton extends EntityLand implements IMob {

	public Skeleton(World par1World) {
		super(par1World);
		
		this.setCurrentItemOrArmor(0, new ItemStack(TropicraftItems.spearBamboo));
		
		for (int i = 0; i < this.equipmentDropChances.length; ++i)
        {
            this.equipmentDropChances[i] = 0;
        }
		

		agent.jobMan.addJob(new JobFormation(agent.jobMan));
		agent.shouldAvoid = false;
		
		this.experienceValue = 6;
	}
	
	@Override
    public boolean getCanSpawnHere()
    {
        return this.isValidLightLevel() && super.getCanSpawnHere();
    }
	
	@Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int j = this.rand.nextInt(2) + this.rand.nextInt(1 + par2);
        int k;

        for (k = 0; k < j; ++k)
        {
            this.dropItem(Item.bone.itemID, 1);
        }
        
        if (this.rand.nextInt(10) == 0) this.dropItem(TropicraftItems.spearBamboo.itemID, 1);
        
    }
	
	@Override
	protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

	@Override
    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

	@Override
    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

}
