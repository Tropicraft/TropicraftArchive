package net.tropicraft.core.common.entity.passive;

import java.util.List;
import java.util.Random;

import javax.vecmath.Vector3f;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityFailgull extends EntityFlying {

	public int courseChangeCooldown = 0;
//	public double waypointX;
//	public double waypointY;
//	public double waypointZ;

	public boolean inFlock;
	public EntityFailgull leader;

	public int flockCount = 0;

	public int flockPosition = 0;

	public EntityFailgull(World world) {
		super(world);
		setSize(0.4F, 0.6F);
		this.experienceValue = 1;
		this.moveHelper = new EntityFailgull.FailgullMoveHelper(this);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(5, new EntityFailgull.AIRandomFly(this));
		this.tasks.addTask(7, new EntityFailgull.AILookAround(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
	}

	@Override
	public void entityInit() {
		super.entityInit();
	}

	private void poop() {
		if (!world.isRemote && world.rand.nextInt(20) == 0) {
			EntitySnowball s = new EntitySnowball(world, posX, posY, posZ);
			s.shoot(0, 0, 0, 0, 0);
			world.spawnEntity(s);
		}
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	static class AILookAround extends EntityAIBase {
		private EntityFailgull parentEntity;

		public AILookAround(EntityFailgull ghast) {
			this.parentEntity = ghast;
			this.setMutexBits(2);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			return true;
		}

		/**
		 * Updates the task
		 */
		@Override
		public void updateTask() {
			this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float)Math.PI);
		}
	}

	static class AIRandomFly extends EntityAIBase {
		private EntityFailgull parentEntity;

		public AIRandomFly(EntityFailgull gull) {
			this.parentEntity = gull;
			this.setMutexBits(1);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

			if (!entitymovehelper.isUpdating()) {
				return true;
			} else {
				double d0 = entitymovehelper.getX() - this.parentEntity.posX;
				double d1 = entitymovehelper.getY() - this.parentEntity.posY;
				double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean shouldContinueExecuting() {
			return false;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			Random random = this.parentEntity.getRNG();
			double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
		}
	}

	static class FailgullMoveHelper extends EntityMoveHelper {
		private EntityFailgull failgull;
		private int courseChangeCooldown;
		private static double NEIGHBOR_RADIUS = 30D;
		private static float MAX_VELOCITY = 0.01f;
		private static float MAX_SPEED = 0.01f;
		private static float SEPARATION_WEIGHT = 1f;
		private static float ALIGNMENT_WEIGHT = 1f;
		private static float COHESION_WEIGHT = 1f;
		private static float MAX_FORCE = 0.15f;
		private static float DESIRED_SEPARATION = 3f;

		public FailgullMoveHelper(EntityFailgull gull) {
			super(gull);
			this.failgull = gull;
		}
		
		public void step() {
			List<EntityFailgull> neighbors = failgull.world.getEntitiesWithinAABB(EntityFailgull.class, failgull.getEntityBoundingBox().grow(NEIGHBOR_RADIUS, NEIGHBOR_RADIUS, NEIGHBOR_RADIUS));
			Vector3f acceleration = this.flock(neighbors);
		//	System.out.println(acceleration);
		//	System.out.println(MAX_FORCE);
			failgull.motionX += acceleration.x;
			failgull.motionY += acceleration.y;
			failgull.motionZ += acceleration.z;
			limitVelocity(MAX_SPEED);
			failgull.posX += failgull.motionX;
			failgull.posY += failgull.motionY;
			failgull.posZ += failgull.motionZ;
		}
		
		public void limitVelocity(float limitVal) {
			if (failgull.motionX > limitVal) {
				failgull.motionX = limitVal;
			}
			
			if (failgull.motionY > limitVal) {
				failgull.motionY = limitVal;
			}
			
			if (failgull.motionZ > limitVal) {
				failgull.motionZ = limitVal;
			}
		}
		
		public void limit(Vector3f vec, float val) {
			if (vec.x > val) vec.x = val;
			if (vec.y > val) vec.y = val;
			if (vec.z > val) vec.z = val;
		}
		
		public Vector3f flock(List<EntityFailgull> boids) {
			Vector3f separation = this.separate(boids);
			separation.scale(SEPARATION_WEIGHT);
			Vector3f alignment = this.align(boids);
			alignment.scale(ALIGNMENT_WEIGHT);
			Vector3f cohesion = this.cohere(boids);
			cohesion.scale(COHESION_WEIGHT);
			separation.add(alignment);
			separation.add(cohesion);
			return cohesion;
		}
		
		public Vector3f separate(List<EntityFailgull> boids) {
			Vector3f mean = new Vector3f();
			int count = 0;
			
			for (EntityFailgull boid : boids) {
				Vector3f curr = getCurrentPos(this.failgull);
				float d = distance(curr, getCurrentPos(boid));
				if (d > 0 && d < DESIRED_SEPARATION) {
					curr.sub(getCurrentPos(boid));
					curr.normalize();
					curr.scale(1 / d);
					mean.add(curr);
					count++;
				}
			}
			
			if (count > 0) mean.scale(1 / count);
			
			return mean;
		}

		public Vector3f align(List<EntityFailgull> boids) {
			Vector3f mean = new Vector3f();
			int count = 0;
			for (EntityFailgull boid : boids) {
//				float d = distance(getCurrentPos(this.failgull), getCurrentPos(boid));
//				if ()
				mean.add(getCurrentVelocity(boid));
				count++;
			}
			
			if (count > 0) {
				mean.scale(1 / count);
			}
			
			limit(mean, MAX_FORCE);
			
			return mean;
		}
		
		public float distance(Vector3f a, Vector3f b) {
			return (float) Math.sqrt(((a.x + b.x) * (a.x + b.x)) + ((a.y + b.y) * (a.y + b.y)) + ((a.z + b.z) * (a.z + b.z)));
		}

		public Vector3f cohere(List<EntityFailgull> boids) {
			Vector3f sum = new Vector3f(0, 0, 0);
			int count = 0;
			for (EntityFailgull boid : boids) {
				// exclude distance check here
				sum.add(getCurrentPos(boid));
				count++;
			}
			
			sum.scale(1 / count);
			
			if (count > 0) return steerTo(sum);
			else return sum;
		}
		
		public Vector3f getCurrentPos(EntityFailgull boid) {
			return new Vector3f((float)boid.posX, (float)boid.posY, (float)boid.posZ);
		}
		
		public Vector3f getCurrentVelocity(EntityFailgull boid) {
			return new Vector3f((float)boid.motionX, (float)boid.motionY, (float)boid.motionZ);
		}
		
		public Vector3f steerTo(Vector3f target) {
			Vector3f pos = new Vector3f();
			Vector3f targetClone = new Vector3f(target);
			targetClone.sub(getCurrentPos(this.failgull));
			Vector3f desired = new Vector3f(targetClone);
			// magnitude of vector
			float d = desired.length();
			
			// account for double and float differences
			// if dist > 0, calc steering
			// otherwise, return 0 vector
			if (d > 0) {
				desired.normalize();
				
				if (d < 100) {
					desired.scale((float)(0.3 * (d/100.0)));
				} else {
					desired.scale(0.3f);
				}
				
				Vector3f steer = new Vector3f();
				steer.sub(desired, getCurrentVelocity(this.failgull));
				limit(steer, MAX_FORCE);

				return steer;
			} else {
				return new Vector3f(0, 0, 0);
			}
		}

		@Override
		public void onUpdateMoveHelper() {
            double d0 = this.posX - this.failgull.posX;
            double d1 = this.posY - this.failgull.posY;
            double d2 = this.posZ - this.failgull.posZ;
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;

			if (this.courseChangeCooldown-- <= 0) {
				this.courseChangeCooldown += failgull.rand.nextInt(5) + 2;
				d3 = (double)MathHelper.sqrt(d3);

				if (this.isNotColliding(this.posX, this.posY, this.posZ, d3)) {
//					failgull.motionX += d0 / d3 * 0.1D;
//					failgull.motionY += d1 / d3 * 0.1D;
//					failgull.motionZ += d2 / d3 * 0.1D;
					this.step();
				} else {
					this.action = EntityMoveHelper.Action.WAIT;
				}
			}

//			if (failgull.leader != null) {
//				if (failgull.flockPosition % 2 == 0) {
//					this.waypointX = failgull.leader.waypointX;
//					this.waypointY = failgull.leader.waypointY;
//					this.waypointZ = failgull.leader.waypointZ;
//				} else {
//					this.waypointX = failgull.leader.waypointX;
//					this.waypointY = failgull.leader.waypointY;
//					this.waypointZ = failgull.leader.waypointZ;
//				}
//			}

//			if (!failgull.inFlock) {
//				List list = failgull.world.getEntitiesWithinAABB(EntityFailgull.class, failgull.getEntityBoundingBox().grow(10D, 10D, 10D));
//
//				int lowest = failgull.getEntityId();
//				EntityFailgull f = null;
//
//				for (Object o : list) {
//					f = (EntityFailgull) o;
//
//					if (f.leader != null) {
//						failgull.flockPosition = ++f.leader.flockCount;
//						f.inFlock = true;
//						failgull.leader = f.leader;
//						break;
//					}
//				}
//			}
//
//			if (!failgull.inFlock && failgull.leader == null) {
//				failgull.leader = failgull;
//				failgull.inFlock = true;
//			}
		}

		/**
		 * Checks if entity bounding box is not colliding with terrain
		 */
		private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
			double d0 = (x - this.failgull.posX) / p_179926_7_;
			double d1 = (y - this.failgull.posY) / p_179926_7_;
			double d2 = (z - this.failgull.posZ) / p_179926_7_;
			AxisAlignedBB axisalignedbb = this.failgull.getEntityBoundingBox();

			for (int i = 1; (double)i < p_179926_7_; ++i) {
				axisalignedbb = axisalignedbb.offset(d0, d1, d2);

				if (!this.failgull.world.getCollisionBoxes(this.failgull, axisalignedbb).isEmpty()) {
					return false;
				}
			}

			return true;
		}
	}

	@Override
	public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
		return type == EnumCreatureType.MONSTER;
	}
}
