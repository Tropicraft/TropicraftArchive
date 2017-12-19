package net.tropicraft;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.tropicraft.entities.EntityAshen;
import net.tropicraft.entities.EntityAshenHunter;
import net.tropicraft.entities.EntityBeachFloat;
import net.tropicraft.entities.EntityChair;
import net.tropicraft.entities.EntityCoconutGrenade;
import net.tropicraft.entities.EntityDart;
import net.tropicraft.entities.EntityEIH;
import net.tropicraft.entities.EntityFallingPurifiedSand;
import net.tropicraft.entities.EntityHangingTropicraft;
import net.tropicraft.entities.EntityIguana;
import net.tropicraft.entities.EntityKoaManly;
import net.tropicraft.entities.EntityKoaMemberNewest;
import net.tropicraft.entities.EntityLostMask;
import net.tropicraft.entities.EntityManOWar;
import net.tropicraft.entities.EntityMarlin;
import net.tropicraft.entities.EntityParticleWave;
import net.tropicraft.entities.EntityPoisonousTreeFrogBlue;
import net.tropicraft.entities.EntityPoisonousTreeFrogRed;
import net.tropicraft.entities.EntityPoisonousTreeFrogYellow;
import net.tropicraft.entities.EntitySeaTurtle;
import net.tropicraft.entities.EntitySnareTrap;
import net.tropicraft.entities.EntityStarfish;
import net.tropicraft.entities.EntityTreeFrog;
import net.tropicraft.entities.EntityTropiCreeper;
import net.tropicraft.entities.EntityTropiSkeleton;
import net.tropicraft.entities.EntityTropicalFish;
import net.tropicraft.entities.EntityTropicraftLeafball;
import net.tropicraft.entities.EntityTurtleEgg;
import net.tropicraft.entities.EntityUmbrella;
import net.tropicraft.entities.EntityVMonkey;
import net.tropicraft.entities.EntityWallShell;
import net.tropicraft.entities.models.ModelAshen;
import net.tropicraft.entities.models.ModelEIH;
import net.tropicraft.entities.models.ModelFish;
import net.tropicraft.entities.models.ModelIguana;
import net.tropicraft.entities.models.ModelKoaMan;
import net.tropicraft.entities.models.ModelManOWar;
import net.tropicraft.entities.models.ModelMarlin;
import net.tropicraft.entities.models.ModelStarfish;
import net.tropicraft.entities.models.ModelTreeFrog;
import net.tropicraft.entities.models.ModelTurtle;
import net.tropicraft.entities.models.ModelTurtleEgg;
import net.tropicraft.entities.models.ModelVMonkey;
import net.tropicraft.entities.renderers.RenderAshen;
import net.tropicraft.entities.renderers.RenderBeachFloat;
import net.tropicraft.entities.renderers.RenderBlueTreeFrog;
import net.tropicraft.entities.renderers.RenderChair;
import net.tropicraft.entities.renderers.RenderCoconut;
import net.tropicraft.entities.renderers.RenderDart;
import net.tropicraft.entities.renderers.RenderEIH;
import net.tropicraft.entities.renderers.RenderFallingPurifiedSand;
import net.tropicraft.entities.renderers.RenderFishHook;
import net.tropicraft.entities.renderers.RenderIguana;
import net.tropicraft.entities.renderers.RenderKoaMan;
import net.tropicraft.entities.renderers.RenderLavaBall;
import net.tropicraft.entities.renderers.RenderLostMask;
import net.tropicraft.entities.renderers.RenderManOWar;
import net.tropicraft.entities.renderers.RenderMarlin;
import net.tropicraft.entities.renderers.RenderRedTreeFrog;
import net.tropicraft.entities.renderers.RenderSmoke;
import net.tropicraft.entities.renderers.RenderSnareTrap;
import net.tropicraft.entities.renderers.RenderStarfish;
import net.tropicraft.entities.renderers.RenderTreeFrog;
import net.tropicraft.entities.renderers.RenderTropiCreeper;
import net.tropicraft.entities.renderers.RenderTropiFrame;
import net.tropicraft.entities.renderers.RenderTropiSkeleton;
import net.tropicraft.entities.renderers.RenderTropicalFish;
import net.tropicraft.entities.renderers.RenderTropicraftLeafball;
import net.tropicraft.entities.renderers.RenderTurtle;
import net.tropicraft.entities.renderers.RenderTurtleEgg;
import net.tropicraft.entities.renderers.RenderUmbrella;
import net.tropicraft.entities.renderers.RenderVMonkey;
import net.tropicraft.entities.renderers.RenderWallShell;
import net.tropicraft.entities.renderers.RenderYellowTreeFrog;
import net.tropicraft.mods.TropicraftMod;

import CoroAI.entity.EntityTropicalFishHook;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class TCRenderRegistry {

	private static void addMappings() {
		addMapping(EntityChair.class, new RenderChair());
		addMapping(EntityUmbrella.class, new RenderUmbrella());
		addMapping(EntityCoconutGrenade.class, new RenderCoconut(TropicraftMod.coconutItem.getIconFromDamage(0)));
		addMapping(EntityEIH.class, new RenderEIH(new ModelEIH(), 0.5F));
		addMapping(EntityIguana.class, new RenderIguana(new ModelIguana(), 0.5F));
		addMapping(EntitySeaTurtle.class, new RenderTurtle(new ModelTurtle(), 0.5F));
		addMapping(EntityTurtleEgg.class, new RenderTurtleEgg(new ModelTurtleEgg(), .025F));
		addMapping(EntityWallShell.class, new RenderWallShell());
		addMapping(EntityBeachFloat.class, new RenderBeachFloat());
		addMapping(EntityFallingPurifiedSand.class, new RenderFallingPurifiedSand());
		addMapping(EntityTropiCreeper.class, new RenderTropiCreeper());
		addMapping(EntityMarlin.class, new RenderMarlin(new ModelMarlin(), 0.5F));
		addMapping(EntityManOWar.class, new RenderManOWar(new ModelManOWar(32, 20, true), new ModelManOWar(0, 20, false), .35F));
		addMapping(EntityStarfish.class, new RenderStarfish(new ModelStarfish(), 0.5F));
		addMapping(EntityVMonkey.class, new RenderVMonkey(new ModelVMonkey(), 0.5F));
		addMapping(EntityTropiSkeleton.class, new RenderTropiSkeleton(0.5F));
		addMapping(EntityTreeFrog.class, new RenderTreeFrog(new ModelTreeFrog(), 0.5F));
		addMapping(EntityPoisonousTreeFrogRed.class, new RenderRedTreeFrog(new ModelTreeFrog(), 0.5F));
		addMapping(EntityPoisonousTreeFrogBlue.class, new RenderBlueTreeFrog(new ModelTreeFrog(), 0.5F));
		addMapping(EntityPoisonousTreeFrogYellow.class, new RenderYellowTreeFrog(new ModelTreeFrog(), 0.5F));
		addMapping(EntityLostMask.class, new RenderLostMask());
		addMapping(EntityDart.class, new RenderDart());
		addMapping(EntityAshen.class, new RenderAshen(new ModelAshen(), 0.25F));
		addMapping(EntityAshenHunter.class, new RenderAshen(new ModelAshen(), 0.25F));
		addMapping(EntityTropicalFish.class, new RenderTropicalFish(new ModelFish(), 0.25F));
		addMapping(EntityTropicraftLeafball.class, new RenderTropicraftLeafball(TropicraftMod.leafBall.getIconFromDamage(0)));
		addMapping(EntityKoaManly.class, new RenderKoaMan(new ModelKoaMan(), 0.5F));
		addMapping(EntityTropicalFishHook.class, new RenderFishHook());
		addMapping(net.tropicraft.entities.EntitySmoke.class, new RenderSmoke());
	    addMapping(net.tropicraft.entities.EntityLavaBall.class, new RenderLavaBall());
	    addMapping(EntitySnareTrap.class, new RenderSnareTrap());
		addMapping(EntityKoaMemberNewest.class, new RenderKoaMan(new ModelKoaMan(), 0.5F));
		//addMapping(EntityParticleWave.class, new RenderStorm());
	 //   addMapping(EntitySeaweed.class, new RenderSeaweed());
		addMapping(EntityHangingTropicraft.class, new RenderTropiFrame());
		
	}
	
	private static void addMapping(Class<? extends Entity> entityClass, Render render) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, render);
	}

	
	public static void init() {
		addMappings();
	}
}
