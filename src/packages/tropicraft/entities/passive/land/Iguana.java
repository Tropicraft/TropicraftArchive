package tropicraft.entities.passive.land;

import CoroAI.componentAI.jobSystem.JobFormation;
import CoroAI.diplomacy.TeamTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.entities.EnumTropiMobType;
import tropicraft.entities.ISpecialTropiMob;
import tropicraft.entities.hostile.EntityLand;
import tropicraft.items.TropicraftItems;

public class Iguana extends EntityLand implements ISpecialTropiMob {
	
	public Iguana(World par1World) {
		super(par1World);
		setSize(0.8F, 0.8F);
		texture = "/mods/TropicraftMod/textures/entities/iggytexture.png";
		agent.dipl_info = TeamTypes.getType("animal");
		agent.jobMan.addJob(new JobFormation(agent.jobMan));
		this.isImmuneToFire = true;
	}
	
	public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockId(i, j - 1, k) != 0 && this.worldObj.getFullBlockLightValue(i, j, k) > 8 && super.getCanSpawnHere();
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
		worldObj.playSoundAtEntity(ent, "iggyattack", 1.0f, 1.0f);
	}
	
	@Override
	protected int getDropItemId() {
		return TropicraftItems.scale.itemID;
	};
	
	@Override
	protected String getLivingSound() {
		return "iggyliving";
	}
	
	@Override
	protected String getHurtSound() {
		return super.getHurtSound();
	}
	
	@Override
	protected String getDeathSound() {
		return "iggydeath";
	}

}
