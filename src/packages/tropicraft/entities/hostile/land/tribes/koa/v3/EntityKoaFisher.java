package tropicraft.entities.hostile.land.tribes.koa.v3;

import cpw.mods.fml.common.registry.LanguageRegistry;
import CoroUtil.componentAI.jobSystem.JobHunt;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.entities.ai.jobs.v3.JobFish;
import tropicraft.entities.ai.jobs.v3.JobQuestGiver;
import tropicraft.entities.ai.jobs.v3.JobTrade;
import tropicraft.items.TropicraftItems;

public class EntityKoaFisher extends EntityKoaBase {

	public EntityKoaFisher(World par1World) {
		super(par1World);
		//texture = "/mods/TropicraftMod/textures/entities/koa/KoaMan3.png";
		agent.jobMan.clearJobs();
		agent.jobMan.addPrimaryJob(new JobFish(agent.jobMan));
		agent.jobMan.addJob(new JobHunt(agent.jobMan));
		
		this.setCurrentItemOrArmor(0, new ItemStack(Item.swordIron));
		this.setCurrentItemOrArmor(4, new ItemStack(Item.helmetIron));
	}

	@Override
	public int getCooldownRanged() {
		return 40;
	}

	@Override
	public void postInitFakePlayer() {
		super.postInitFakePlayer();
		if (agent.entInv.inventory.mainInventory[0] == null) {
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.dagger));
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.leafBall));
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.fishingRodTropical));
		}
	}
	
	@Override
	public String getLocalizedName() {
		return koaName + ", a Koa Fisher";
	}

}
