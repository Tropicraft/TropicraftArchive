package extendedrenderer.particle.behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extendedrenderer.particle.entity.EntityIconWindFX;
import extendedrenderer.particle.entity.EntityRotFX;

@SideOnly(Side.CLIENT)
public class ParticleBehaviors {

	public List<EntityRotFX> particles = new ArrayList<EntityRotFX>();
	public Vec3 coordSource;
	public Random rand = new Random();
	
	//Visual tweaks
	public float rateDarken = 0.025F;
	public float rateBrighten = 0.010F;
	public float rateBrightenSlower = 0.003F;
	public float rateAlpha = 0.002F;
	public float rateScale = 0.1F;
	public int tickSmokifyTrigger = 40;
	
	public ParticleBehaviors(Vec3 source) {
		coordSource = source;
	}
	
	public void tickUpdateList() {
		for (int i = 0; i < particles.size(); i++) {
			EntityRotFX particle = particles.get(i);
			
			if (particle.isDead) {
				particles.remove(particle);
			} else {
				tickUpdate(particle);
			}
		}
	}
	
	public void tickUpdate(EntityRotFX particle) {
		tickUpdateSmoke(particle);
	}
	
	public void tickUpdateSmoke(EntityRotFX particle) {
		
			
		double centerX = particle.posX;
		double centerY = particle.posY;
		double centerZ = particle.posZ;
		
		if (coordSource != null) {
			centerX = coordSource.xCoord + 0.5D;
			centerY = coordSource.yCoord + 0.5D;
			centerZ = coordSource.zCoord + 0.5D;
		}
		
		double vecX = centerX - particle.posX;
		double vecZ = centerZ - particle.posZ;
		double distToCenter = Math.sqrt(vecX * vecX + vecZ * vecZ);
		double rotYaw = (float)(Math.atan2(vecZ, vecX) * 180.0D / Math.PI);
		double adjYaw = Math.min(360, 45+particle.getAge());
		
		rotYaw -= adjYaw;
		//rotYaw -= 90D;
		//rotYaw += 20D;
		double speed = 0.1D;
		if (particle.getAge() < 25 && distToCenter > 0.05D) {
			particle.motionX = Math.cos(rotYaw * 0.017453D) * speed;
			particle.motionZ = Math.sin(rotYaw * 0.017453D) * speed;
		} else {
			double speed2 = 0.008D;
			
			double pSpeed = Math.sqrt(particle.motionX * particle.motionX + particle.motionZ * particle.motionZ);
			
			//cheap air search code
			if (pSpeed < 0.2 && particle.motionY < 0.01) {
				speed2 = 0.08D;
			}
			
			if (pSpeed < 0.002 && Math.abs(particle.motionY) < 0.02) {
				particle.motionY -= 0.15D;
			}
			
			particle.motionX += (rand.nextDouble() - rand.nextDouble()) * speed2;
			particle.motionZ += (rand.nextDouble() - rand.nextDouble()) * speed2;
			
		}
		int cycle = 40;
		
		float brightnessShiftRate = rateDarken;
		
		int stateChangeTick = tickSmokifyTrigger;
		
		if (particle.getAge() < stateChangeTick) {
			particle.setGravity(-0.2F);
			particle.setRBGColorF(particle.getRedColorF() - brightnessShiftRate, particle.getGreenColorF() - brightnessShiftRate, particle.getBlueColorF() - brightnessShiftRate);
		} else if (particle.getAge() == stateChangeTick) {
			particle.setRBGColorF(0,0,0);
		} else {
			brightnessShiftRate = rateBrighten;
			particle.setGravity(-0.05F);
			//particle.motionY *= 0.99F;
			if (particle.getRedColorF() < 0.3F) {
				
			} else {
				brightnessShiftRate = rateBrightenSlower;
			}
			
			particle.setRBGColorF(particle.getRedColorF() + brightnessShiftRate, particle.getGreenColorF() + brightnessShiftRate, particle.getBlueColorF() + brightnessShiftRate);
			
			if (particle.getAlphaF() > 0) {
				particle.setAlphaF(particle.getAlphaF() - rateAlpha);
			} else {
				particle.setDead();
			}
		}
		
		if (particle.particleScale < 8F) particle.particleScale += rateScale;
		
		/*if (particle.getAge() % cycle < cycle/2) {
			particle.setGravity(-0.02F);
		} else {*/
			
		//}
			
		
	}
	
	public EntityRotFX spawnNewParticle(World world, Icon icon, double x, double y, double z, double vecX, double vecY, double vecZ) {
		EntityRotFX entityfx = new EntityIconWindFX(world, x, y, z, vecX, vecY, vecZ, icon, Minecraft.getMinecraft().renderEngine);
		entityfx.pb = this;
		return entityfx;
	}
	
	public EntityRotFX setParticleRandoms(EntityRotFX particle, boolean yaw, boolean pitch) {
		if (yaw) particle.rotationYaw = rand.nextInt(360);
		if (pitch) particle.rotationPitch = rand.nextInt(360);
		return particle;
	}
	
	public EntityRotFX setParticleFire(EntityRotFX particle) {
		particle.setRBGColorF(0.6F + (rand.nextFloat() * 0.4F), 0.2F + (rand.nextFloat() * 0.2F), 0);
		particle.particleScale = 0.25F + 0.2F * rand.nextFloat();
		particle.brightness = 1F;
		particle.setSize(0.1F, 0.1F);
		particle.setAlphaF(0.6F);
		return particle;
	}
	
	public void cleanup() {
		
	}
	
}
