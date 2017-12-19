package tropicraft.client.entities.particles;

import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import extendedrenderer.particle.entity.EntityIconFX;

public class EntityFireballFX extends EntityIconFX {

	public Vec3 spawnPos;
	
	public EntityFireballFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12,
			Icon par14Item) {
		super(par1World, par2, par4, par6, par8, par10, par12, par14Item);
		spawnPos = Vec3.createVectorHelper(par2, par4, par6);
		particleGravity = 0;
		brightness = 1F;
		this.particleRed = this.particleGreen = this.particleBlue = 1F;//this.rand.nextFloat() * 0.3F;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		/*if (particleAge < 10) {
	        particleScale = particleAge * 1F;
        } else {*/
    	if (particleMaxAge - particleAge < 200) {
        	int count = (particleMaxAge - particleAge);
        	particleScale = (float)count * 0.1F;
    	}
    	
    	this.rotationPitch+=10;
    	this.rotationYaw+=10;
        	//particleScale = (particleMaxAge - particleAge) * 5F;
        //}
		
		float maxScale = 2F;
        if (particleScale > maxScale) {
        	this.particleScale = maxScale;
        }
		
		if (particleAge < 40 && spawnPos != null) {
	        double speed = 0.03D * ((getMaxAge() - particleAge) / (double)getMaxAge());
	        
	        if (this.posX > spawnPos.xCoord) {
	        	this.motionX -= speed;
	        } else {
	        	this.motionX += speed;
	        }
	        if (this.posY > spawnPos.yCoord) {
	        	this.motionY -= speed;
	        } else {
	        	this.motionY += speed;
	        }
	        if (this.posZ > spawnPos.zCoord) {
	        	this.motionZ -= speed;
	        } else {
	        	this.motionZ += speed;
	        }
        }
	}

}
