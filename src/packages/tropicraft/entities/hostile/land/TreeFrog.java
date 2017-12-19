package tropicraft.entities.hostile.land;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.entities.hostile.EntityLand;
import tropicraft.items.TropicraftItems;
import CoroAI.componentAI.jobSystem.JobHunt;
import CoroAI.diplomacy.TeamTypes;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class TreeFrog extends EntityLand implements IEntityAdditionalSpawnData {

	//0 = peacefull green, 1 = red, 2 = blue, 3 = yellow
	public int type = 0;
	public int jumpDelay = 0;
	public int jumpDelayMax = 10;
	public boolean wasOnGround = false;
	
	public double leapVecX;
	public double leapVecZ;
	
	public TreeFrog(World par1World) {
		super(par1World);
		setSize(0.8F, 0.8F);
		updateTexture();
		agent.jobMan.clearJobs();
		agent.jobMan.addPrimaryJob(new JobHunt(agent.jobMan) { public void onTickCloseCombat() { } });
		agent.shouldAvoid = false;
		
		entityCollisionReduction = 0.8F;
	}
	
	public TreeFrog(World par1World, int parType) {
		this(par1World);
		type = parType;
	}
	
	@Override
	public void attackMelee(Entity ent, float dist) {
		ent.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
		if (ent.worldObj.rand.nextInt(3) == 0) {
			((EntityLiving)ent).addPotionEffect(new PotionEffect(Potion.poison.id, 60));
			worldObj.playSoundAtEntity(ent, "frogspit", 1.0f, 1.0f);
		}
	}
	
	public void updateTexture() {
		if (type == 0) {
			texture = "/mods/TropicraftMod/textures/entities/treefroggreen.png";
		} else if (type == 1) {
			texture = "/mods/TropicraftMod/textures/entities/treefrogredlight.png";
		} else if (type == 2) {
			texture = "/mods/TropicraftMod/textures/entities/treefrogblue.png";
		} else if (type == 3) {
			texture = "/mods/TropicraftMod/textures/entities/treefrogyellow.png";
		}
	}
	
	@Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int j = this.rand.nextInt(2) + this.rand.nextInt(1 + par2);
        int k;

        for (k = 0; k < j; ++k)
        {
            //this.dropItem(TropicraftItems.fr, 1);
        }
        
        this.dropItem(TropicraftItems.frogLeg.itemID, 1);

    }
	
	@Override
	public void updateAITasks() {
		// TODO Auto-generated method stub
		super.updateAITasks();
		
		getAIAgent().shouldPathfollow = false;
		getAIAgent().shouldFixBadYPathing = true;
		getAIAgent().checkPathfindLock();
		
		double curSpeed = Math.sqrt(motionX * motionX + motionZ * motionZ);
		
		if (onGround) {
			motionX = 0D;
			motionZ = 0D;
		}
		
		Random rand = new Random();
		
		//if (worldObj.getClosestPlayerToEntity(this, 5D) == null) setDead();
		
		
		if (!getNavigator().noPath() || getAIAgent().entityToAttack != null) {
			if (onGround || isInWater()) {
				if (jumpDelay > 0) jumpDelay--;
				if (jumpDelay <= 0) {
					jumpDelay = rand.nextInt(4);
					
					getNavigator().onUpdateNavigation();
	        //TODO		getAIAgent().tickMovementHelp();
					
					this.jump();
					this.motionY += -0.01D + rand.nextDouble() * 0.1D;
					//double vecX = 0;
					//double vecY = 0;
					//double vecZ = 0;
					double speed = 0.4D;
					if (getNavigator().getPath() != null) {
						try {
							if (getNavigator().getPath().getCurrentPathIndex() > getNavigator().getPath().getCurrentPathLength()) {
								//System.out.println("frog hopathing: path index: " + getNavigator().getPath().getCurrentPathIndex() + " > path size: " + getNavigator().getPath().getCurrentPathLength() + ", resetting path");
								getNavigator().clearPathEntity();
								return;
								
							}
							Vec3 pos = getNavigator().getPath().getPosition(this);
							leapVecX = pos.xCoord - this.posX;
							leapVecZ = pos.zCoord - this.posZ;
							speed = 0.2D;
							//leapVecX = 0;
							//leapVecZ = 0;
						} catch (Exception ex) {
							//Why the hell does it crash when i touch the path data this way? is the cur path index invalid? error happens no matter the placing of above path code
							//System.out.println("TreeFrog getPosition crash");
							//ex.printStackTrace();
							return;
						}
					} else if (getAIAgent().entityToAttack != null) {
						leapVecX = getAIAgent().entityToAttack.posX - this.posX;
						leapVecZ = getAIAgent().entityToAttack.posZ - this.posZ;
						//jumpDelay = 1;
					}

					if (leapVecX != 0) {
						
						double dist2 = (double)Math.sqrt(leapVecX * leapVecX + leapVecZ * leapVecZ);
						motionX += leapVecX / dist2 * speed;
						//motionY += vecY / dist2 * speed;
						motionZ += leapVecZ / dist2 * speed;
					}
					
				}
			} else {
				if (leapVecX != 0 && curSpeed < 0.02) {
					double speed = 0.1D;
					if (isInWater()) speed = 0.2D;
					double dist2 = (double)Math.sqrt(leapVecX * leapVecX + leapVecZ * leapVecZ);
					motionX += leapVecX / dist2 * speed;
					//motionY += vecY / dist2 * speed;
					motionZ += leapVecZ / dist2 * speed;
				}
			}
			
			if (isInWater()) {
				motionY += 0.07D;
			}
		}
		
		wasOnGround = onGround;
	}
	
	@Override
	public void onLivingUpdate()
    {
		fallDistance = 0;
		this.setAir(400);
		super.onLivingUpdate();
		if (!worldObj.isRemote) {
			
		} else {
			//onGround = false;
		}
    }
	
	public void moveTowards(Entity ent, Entity targ, float speed) {
		double vecX = targ.posX - ent.posX;
		double vecY = targ.posY - ent.posY;
		double vecZ = targ.posZ - ent.posZ;

		double dist2 = (double)Math.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
		ent.motionX += vecX / dist2 * speed;
		ent.motionY += vecY / dist2 * speed;
		ent.motionZ += vecZ / dist2 * speed;
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		data.writeInt(type);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		type = data.readInt();
		updateTexture();
	}
	
	

}
