package tropicraft.entities.hostile.land;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import tropicraft.entities.hostile.EntityLand;

public class TreeFrogGreen extends TreeFrog {

	public TreeFrogGreen(World par1World) {
		super(par1World);
	}
	
	@Override
	public boolean isEnemy(Entity ent) {
		return false;
	}

}
