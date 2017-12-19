package tropicraft.entities.hostile.land.tribes.koa.v3;

import CoroAI.componentAI.jobSystem.JobHunt;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.entities.ai.jobs.v3.JobSleep;
import tropicraft.entities.ai.jobs.v3.JobTrade;
import tropicraft.items.TropicraftItems;

public class EntityKoaTrader extends EntityKoaBase {

	public EntityKoaTrader(World par1World) {
		super(par1World);
		texture = "/mods/TropicraftMod/textures/entities/koa/KoaManTrader.png";
		
		agent.jobMan.clearJobs();
		agent.jobMan.addPrimaryJob(new JobTrade(agent.jobMan));
		//agent.jobMan.addJob(new JobHunt(agent.jobMan));
		agent.scanForHomeChest = false;
		agent.setMoveSpeed(0.25F);
	}

	@Override
	public int getMaxHealth() {
		return 80;
	}

	@Override
	public int getCooldownRanged() {
		return 5;
	}

	@Override
	public void postInitFakePlayer() {
		super.postInitFakePlayer();
		if (agent.entInv.inventory.mainInventory[0] == null) {
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.swordEudialyte));
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.blowGun));
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.dart, 64));
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.dart, 64));
		}
	}
	
	@Override
	public String getLocalizedName() {
		return "a Koa Trader";
	}

}
