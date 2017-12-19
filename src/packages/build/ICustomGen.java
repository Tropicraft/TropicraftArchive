package build;

import net.minecraft.world.World;
import build.world.BuildJob;

public interface ICustomGen {
	/* Method used for injecting custom generation after any pass, first pass being setting air */
	public void genPassPre(World world, BuildJob parBuildJob, int parPass);
}
