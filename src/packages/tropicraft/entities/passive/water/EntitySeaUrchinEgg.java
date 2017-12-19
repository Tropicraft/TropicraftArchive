package tropicraft.entities.passive.water;

import tropicraft.ModInfo;
import tropicraft.entities.EntityEchinoderm;
import tropicraft.entities.EntityEchinodermEgg;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySeaUrchinEgg extends EntityEchinodermEgg {
	public EntitySeaUrchinEgg(World world) {
		super(world);
		texture = "/mods/"+ModInfo.MODID+"/textures/entities/seaurchinegg.png";
	}

	@Override
	public EntityEchinoderm createBaby() {
		return new EntitySeaUrchin(worldObj, true);
	}
}
