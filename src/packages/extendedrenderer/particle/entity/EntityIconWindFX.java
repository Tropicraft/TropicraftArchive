package extendedrenderer.particle.entity;

import weather.wind.WindHandler;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class EntityIconWindFX extends EntityIconFX implements WindHandler {

	public EntityIconWindFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12,
			Icon par14Item, RenderEngine par15RenderEngine) {
		super(par1World, par2, par4, par6, par8, par10, par12, par14Item,
				par15RenderEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getWindWeight() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getParticleDecayExtra() {
		// TODO Auto-generated method stub
		return 0;
	}

}
