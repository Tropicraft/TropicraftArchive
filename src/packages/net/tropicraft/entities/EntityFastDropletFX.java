package net.tropicraft.entities;

import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFastDropletFX extends EntityFX {	

	private int field_40104_aw;

	public EntityFastDropletFX(World world, double d, double d1, double d2)
	{
		super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);

		particleRed = 1.0F;
		particleGreen = 0.0F;
		particleBlue = 0.0F;

		setParticleTextureIndex(113);
		setSize(0.01F, 0.01F);
		particleGravity = 0.06F;
		particleScale = 1 + rand.nextFloat()*rand.nextInt(5);
		field_40104_aw = 5;
		particleMaxAge = (int)(32D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D));
		motionX = motionY = motionZ = 0.0D;
	}

	public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
	}

	public int getEntityBrightnessForRender(float f)
	{
		return 257;

	}

	public float getEntityBrightness(float f)
	{

		return 1.0F;

	}

	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;	       
		particleRed = 1.0F;
		particleRed = 1.0F;
		particleGreen = 16F / (float)((5 - field_40104_aw) + 16);
		particleBlue = 0F; //4F / (float)((5 - field_40104_aw) + 8);

		motionY -= particleGravity;
		if (field_40104_aw-- > 0)
		{
			motionX *= 0.02D;
			motionY *= 0.02D;
			motionZ *= 0.02D;
			setParticleTextureIndex(113);
		}
		else
		{
			setParticleTextureIndex(112);
		}
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.98000001907348633D;
		motionY *= 0.98000001907348633D;
		motionZ *= 0.98000001907348633D;
		if (particleMaxAge-- <= 0)
		{
			setDead();
		}
		if (onGround)
		{
			setParticleTextureIndex(114);	           
			motionX *= 0.69999998807907104D;
			motionZ *= 0.69999998807907104D;
		}
		Material material = worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
		if (material.isLiquid() || material.isSolid())
		{
			double d = (float)(MathHelper.floor_double(posY) + 1) - BlockFluid.getFluidHeightPercent(worldObj.getBlockMetadata(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)));
			if (posY < d)
			{
				setDead();
			}
		}
	}
}



