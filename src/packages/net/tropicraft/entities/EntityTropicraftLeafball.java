package net.tropicraft.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import CoroAI.componentAI.ICoroAI;
import CoroAI.entity.c_EnhAI;

//backup

//public static Item leafball = (new ItemTropicraftLeafball(15022)).setItemName("Leafball");
//map.put(net.minecraft.src.EntityTropicraftLeafball.class, new RenderTropicraftLeafball(leafball.getIconFromDamage(0)));
//addMobWithoutNaturalSpawn("EntityTropicraftLeafball", net.minecraft.src.EntityTropicraftLeafball.class);

public class EntityTropicraftLeafball extends EntityThrowable
{
	public EntityTropicraftLeafball(World world)
	{
		super(world);
	}

	public EntityTropicraftLeafball(World world, EntityLiving entityliving)
	{
		super(world, entityliving);
	}

	public EntityTropicraftLeafball(World world, double d, double d1, double d2)
	{
		super(world, d, d1, d2);
	}

	protected void onImpact(MovingObjectPosition movingobjectposition)
	{
		if (movingobjectposition.entityHit != null)
		{
			if (!worldObj.isRemote)
			{
				byte byte0 = 2;
				if (movingobjectposition.entityHit instanceof c_EnhAI && getThrower() instanceof c_EnhAI) {
					if (((c_EnhAI) getThrower()).dipl_team != ((c_EnhAI) movingobjectposition.entityHit).dipl_team) {
						movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), byte0);
					} else {

					}
				} else if (movingobjectposition.entityHit instanceof ICoroAI && getThrower() instanceof ICoroAI) {
					if (((ICoroAI) getThrower()).getAIAgent().dipl_team != ((ICoroAI) movingobjectposition.entityHit).getAIAgent().dipl_team) {
						movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), byte0);
					} else {

					}
				} else {
					movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), byte0);
				}

				/*if (movingobjectposition.entityHit instanceof EntityBlaze)
            {
                byte0 = 3;
            }*/
            /*     if (movingobjectposition.entityHit instanceof EntityKoaMember && thrower instanceof EntityKoaMember) {
    			if (((EntityKoaMember) thrower).dipl_team != ((EntityKoaMember) movingobjectposition.entityHit).dipl_team) {
    				movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), byte0);
    			} else {

    			}
    		}
            else if (!(movingobjectposition.entityHit instanceof EntityKoaMemberNew)) { 
            	if (!movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), byte0));
            	if (thrower instanceof EntityPlayer) {
            		int what = 0;
            	}
            } else if (movingobjectposition.entityHit instanceof EntityKoaMemberNew && thrower instanceof EntityKoaMemberNew) {
    			if (((EntityKoaMemberNew) thrower).dipl_team != ((EntityKoaMemberNew) movingobjectposition.entityHit).dipl_team) {
    				movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), byte0);
    			} else {

    			}
    		}
        } 
        for (int i = 0; i < 30; i++)
        {
            //worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        	double speed = 0.01D;
        	EntityTexFX var31 = new EntityTexFX(worldObj, posX, posY, posZ, rand.nextGaussian()*rand.nextGaussian()*speed, rand.nextGaussian()*speed, rand.nextGaussian()*rand.nextGaussian()*speed, (rand.nextInt(80)/10), 0, mod_EntMover.effLeafID);
            var31.setGravity(0.3F);
            Random rand = new Random();
            var31.rotationYaw = rand.nextInt(360);
            mod_ExtendedRenderer.rotEffRenderer.addEffect(var31);
        }
             */

				setDead();
			}
		}
	}
}
