package tropicraft.entities.hostile.land.tribes.ashen;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import tropicraft.Tropicraft;
import tropicraft.entities.ai.jobs.v3.JobHuntAshen;
import tropicraft.entities.hostile.EntityLand;
import tropicraft.items.TropicraftItems;
import CoroUtil.componentAI.AIAgent;
import CoroUtil.componentAI.IInvUser;
import CoroUtil.componentAI.jobSystem.JobFormation;
import CoroUtil.diplomacy.TeamTypes;

public class Ashen extends EntityLand implements IInvUser {

	//Client side, used for mask bob
	public float bobber;
	public int maskCount;
	
	public Ashen(World par1World) {
		super(par1World);
		setSize(.7F, 1.50F);
		agent.jobMan.clearJobs();
		agent.jobMan.addPrimaryJob(new JobHuntAshen(agent.jobMan));
		agent.jobMan.addJob(new JobFormation(agent.jobMan));
		
		agent.collideResistPathing = 0.7F;
		//agent.setMoveSpeed(0.35F);
		agent.dipl_info = TeamTypes.getType("ashen");
		
		this.experienceValue = 8;
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		maskCount = 12;
		getDataWatcher().addObject(16, (Integer)rand.nextInt(maskCount)); //mask
		getDataWatcher().addObject(17, (Integer)2); //animation state
	}
	
	@Override
	protected String getLivingSound() {
		return Tropicraft.modID + ":" + "ashenLaugh";
	}
	
	@Override
	public void checkNewAgent() {
		if (agent == null) agent = new AIAgent(this, true);
	}

	public int getMaskType() {
		return this.getDataWatcher().getWatchableObjectInt(16);
	}

	public int getActionState() {
		return this.getDataWatcher().getWatchableObjectInt(17);
	}

	public boolean hasMask() {
		return getMaskType() != -1;
	}
	
	@Override
	public int getCooldownRanged() {
		return 40;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.readEntityFromNBT(par1nbtTagCompound);
		this.getDataWatcher().updateObject(16, par1nbtTagCompound.getInteger("maskType"));
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("maskType", getMaskType());
	}

	@Override
	public void postInitFakePlayer() {
		if (agent.entInv.inventory.mainInventory[0] == null) {
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.dagger));
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.blowGun));
			agent.entInv.inventory.addItemStackToInventory(new ItemStack(TropicraftItems.dart, 64));
		}
	}

	@Override
	public String getLocalizedName() {
		return "an Ashen Hunter";
	}
	
	@Override
	public void onDeath(DamageSource d) {
		super.onDeath(d);
		if (!this.worldObj.isRemote) {
			//this.entityDropItem(new ItemStack(TropicraftItems.ashenMasks.itemID, 1, getMaskType()), 0);
		}
	}
	
	@Override
	public void onLivingUpdate() {
		// TODO Auto-generated method stub
		super.onLivingUpdate();
		if (worldObj.isRemote) {
			//maskType = this.getDataWatcher().getWatchableObjectInt(16);
		} else {
			//this.getDataWatcher().updateObject(16, maskType);
		}
	}

}
