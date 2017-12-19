package net.tropicraft.entities.avian;

import net.minecraft.entity.EntityFlying;
import net.minecraft.world.World;

public abstract class EntityFlyingTC extends EntityFlying {

	public EntityFlyingTC(World par1World) {
		super(par1World);
	}
	
	@Override
	public void moveEntityWithHeading(float p1, float p2) {
		super.moveEntityWithHeading(p1, p2);
	}

}
