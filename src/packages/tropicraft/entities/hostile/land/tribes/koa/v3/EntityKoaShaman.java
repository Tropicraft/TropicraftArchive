package tropicraft.entities.hostile.land.tribes.koa.v3;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.entities.ai.jobs.v3.JobQuestGiver;
import tropicraft.items.TropicraftItems;

public class EntityKoaShaman extends EntityKoaBase {

	public EntityKoaShaman(World par1World) {
		super(par1World);
		texture = "/mods/TropicraftMod/textures/entities/koa/KoaShaman.png";
		agent.jobMan.addJob(new JobQuestGiver(agent.jobMan));
	}

	@Override
	public int getMaxHealth() {
		return 60;
	}

	@Override
	public int getCooldownRanged() {
		return 10;
	}

	@Override
	public void postInitFakePlayer() {
		super.postInitFakePlayer();
		if (agent.entInv.inventory.mainInventory[0] == null) {
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.spearBamboo));
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.staffFire));
		}
	}
	
	@Override
	public String getLocalizedName() {
		return "a Koa Shaman";
	}

}
