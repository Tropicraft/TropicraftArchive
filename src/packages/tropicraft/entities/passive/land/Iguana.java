package tropicraft.entities.passive.land;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.Tropicraft;
import tropicraft.entities.EnumTropiMobType;
import tropicraft.entities.ISpecialTropiMob;
import tropicraft.entities.hostile.EntityLand;
import tropicraft.items.TropicraftItems;
import CoroUtil.componentAI.jobSystem.JobFormation;
import CoroUtil.diplomacy.TeamTypes;

public class Iguana extends EntityLand implements ISpecialTropiMob {
	
	public Iguana(World par1World) {
		super(par1World);
		setSize(0.8F, 0.8F);
		agent.dipl_info = TeamTypes.getType("animal");
		agent.jobMan.addJob(new JobFormation(agent.jobMan));
		this.isImmuneToFire = true;
		this.experienceValue = 6;
	}
	
	public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockId(i, j - 1, k) != 0 && this.worldObj.getFullBlockLightValue(i, j, k) > 8 && super.getCanSpawnHere();
    }
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(10.0D);
	}
	
	@Override
	public boolean isEnemy(Entity ent) {
		return false;
	}

	@Override
	public EnumTropiMobType getMobType() {
		return EnumTropiMobType.REPTILE;
	}
	
	@Override
	public void attackMelee(Entity ent, float dist) {
		super.attackMelee(ent, dist);
		worldObj.playSoundAtEntity(ent, Tropicraft.modID + ":" + "iggyattack", 1.0f, 1.0f);
	}
	
	@Override
	protected int getDropItemId() {
		return TropicraftItems.scale.itemID;
	};
	
	@Override
	protected String getLivingSound() {
		return Tropicraft.modID + ":" + "iggyliving";
	}
	
	@Override
	protected String getHurtSound() {
		return super.getHurtSound();
	}
	
	@Override
	protected String getDeathSound() {
		return Tropicraft.modID + ":" + "iggydeath";
	}

}
