package net.tropicraft.entity.hostile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.tropicraft.entity.EntityLand;
import net.tropicraft.registry.BlockRegistry;

public class EntityEIH extends EntityLand implements IMob {

	/**
	 * TODO: mob needs spider like leap attack AI task
	 */
	
    /** Constant value used for fetching/setting the anger state of the EIH in the datawatcher */
    public static final int DATAWATCHER_ANGER_STATE = 16;

    public EntityEIH(World world) {
        super(world);
        this.isImmuneToFire = true;
        setSize(1.4F, 4.0F);
        this.experienceValue = 10;
        
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1D, false));
    }

    @Override
    public void entityInit() {
        super.entityInit();
        getDataWatcher().addObject(DATAWATCHER_ANGER_STATE, Byte.valueOf((byte)0)); //anger state
    }

    @Override
    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Sitting", isImmobile());
        nbttagcompound.setBoolean("Angry", isAngry());
        nbttagcompound.setBoolean("Awake", isAwake());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setImmobile(nbttagcompound.getBoolean("Sitting"));
        setAngry(nbttagcompound.getBoolean("Angry"));
        setAwake(nbttagcompound.getBoolean("Awake"));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        // System.out.println("Entity to attack = " + this.entityToAttack);
        if (!isImmobile()) {
            prevRotationYaw = rotationYaw;
            prevRotationPitch = rotationPitch;
        }
        getPlayerToHurt();

        if (getAttackTarget() != null && !hasPath() && !isAngry()) {
            Entity entity = getAttackTarget();
            if (entity instanceof EntityPlayer) {
                if (!((EntityPlayer)entity).capabilities.isCreativeMode) {

                    EntityPlayer entityplayer = (EntityPlayer) entity;

                    if (getDistanceToEntity(entityplayer) < 10F) {
                        setAwake(true);
                        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

                        if (itemstack != null) {
                            if (isAwake() && itemstack.getItem() == Item.getItemFromBlock(BlockRegistry.chunk)) {
                                setAngry(true);
                                setImmobile(false);
                            }

                        }
                    }
                    if (getDistanceToEntity(entityplayer) < 3F 
                            && worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
                        setAwake(false);
                        setImmobile(false);
                        setAngry(true);
                    }

                }

                else {
                    setImmobile(true);
                    setAwake(false);
                    setAngry(false);

                    moveEntity(0D, -.1D, 0D);
                    setRotation(prevRotationYaw, prevRotationPitch);

                }
            }
        }

        if (isImmobile()) {
            setRotation(prevRotationYaw, prevRotationPitch);
        } else {
            setAwake(false);
        }
    }

    private void getPlayerToHurt() {
        if (getAttackTarget() == null) {
            setAttackTarget(worldObj.getClosestPlayerToEntity(this, 10));
        } else if (getDistanceToEntity(getAttackTarget()) > 16) {
        	setAttackTarget(null);

        }
    }

    /*@Override
    protected Entity findPlayerToAttack() {
        if (isAngry()) {
            return worldObj.getClosestPlayerToEntity(this, 16D);
        } else {
            return null;
        }
    }*/

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if (damagesource.getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) damagesource.getSourceOfDamage();

            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if (itemstack != null) {
                if (itemstack.getItem().canHarvestBlock(Blocks.iron_ore, itemstack)) {

                    super.attackEntityFrom(damagesource, f);
                } else {
                    int b = rand.nextInt(1);
                    if (b == 0) {
                        worldObj.playSoundAtEntity(this, tcSound("headlaughing"), 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                    }
                    if (b == 1) {
                        worldObj.playSoundAtEntity(this, tcSound("headlaughing2"), 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
                    }
                }
            }
        }

        setImmobile(false);
        setAngry(true);
        if (damagesource.getSourceOfDamage() instanceof EntityLivingBase) {
        	setAttackTarget((EntityLivingBase) damagesource.getSourceOfDamage());
        }

        return true;
    }/*

    @Override
    protected void attackEntity(Entity entity, float f) {
        if (isAngry()) {

            if (f > 2.0F && f < 6F && rand.nextInt(10) == 0) {
                if (onGround) {
                    double d = entity.posX - posX;
                    double d1 = entity.posZ - posZ;
                    float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                    motionX = (d / (double) f1) * 0.75D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                    motionZ = (d1 / (double) f1) * 0.75D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                    motionY = 0.40000000596046448D;
                }
            } else
                if ((double) f < 1.5D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY) {
                    attackTime = 120;
                    byte damage = 7;
                    entity.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
                }

            if (getDistanceToEntity(entity) > 10D) {
                setImmobile(true);
                setAngry(false);
                isJumping = false;
                this.motionX = 0D;
                this.motionY = 0D;
                this.motionZ = 0D;
                entityToAttack = null;
            }
        }
    }*/

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        // can use this for right click reactions
        return false;
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int looting) {
        int j = 2 + this.rand.nextInt(1 + looting);
        int k;

        for (k = 0; k < j; ++k) {
            this.dropItem(Item.getItemFromBlock(BlockRegistry.chunk), 1);
        }       
    }

    @Override
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        return !worldObj.isAirBlock(new BlockPos(i, j - 1, k)) && this.worldObj.getLightFromNeighbors(new BlockPos(i, j, k)) > 8 && super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected String getHurtSound() {
        return tcSound("headpain");
    }

    @Override
    protected String getDeathSound() {
        return tcSound("headdeath");
    }

    @Override
    protected String getLivingSound() {
        if (isAngry()) {
            if (rand.nextInt(10) == 0) {
                return tcSound("headmed");
            } else {
                return null;
            }
        }
        if (isAwake()) {
            if (rand.nextInt(10) == 0) {
                return tcSound("headshort");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public float getSoundVolume() {
        return 0.4F;
    }/*

    @Override
    protected boolean isMovementCeased() {
        return isImmobile();
    }*/

    public boolean isImmobile() {
        return (dataWatcher.getWatchableObjectByte(DATAWATCHER_ANGER_STATE) & 1) != 0;
    }

    public void setImmobile(boolean flag) {
        byte byte0 = dataWatcher.getWatchableObjectByte(DATAWATCHER_ANGER_STATE);
        if (flag) {
            dataWatcher.updateObject(DATAWATCHER_ANGER_STATE, Byte.valueOf((byte) (byte0 | 1)));
        } else {
            dataWatcher.updateObject(DATAWATCHER_ANGER_STATE, Byte.valueOf((byte) (byte0 & -2)));
        }
    }

    public boolean isAngry() {
        return (dataWatcher.getWatchableObjectByte(DATAWATCHER_ANGER_STATE) & 2) != 0;
    }

    public void setAngry(boolean flag) {
        byte byte0 = dataWatcher.getWatchableObjectByte(DATAWATCHER_ANGER_STATE);
        if (flag) {
            dataWatcher.updateObject(DATAWATCHER_ANGER_STATE, Byte.valueOf((byte) (byte0 | 2)));
        } else {
            dataWatcher.updateObject(DATAWATCHER_ANGER_STATE, Byte.valueOf((byte) (byte0 & -3)));
        }
    }

    public boolean isAwake() {
        return (dataWatcher.getWatchableObjectByte(DATAWATCHER_ANGER_STATE) & 4) != 0;
    }

    public void setAwake(boolean flag) {
        byte byte0 = dataWatcher.getWatchableObjectByte(DATAWATCHER_ANGER_STATE);
        if (flag) {
            dataWatcher.updateObject(DATAWATCHER_ANGER_STATE, Byte.valueOf((byte) (byte0 | 4)));
        } else {
            dataWatcher.updateObject(DATAWATCHER_ANGER_STATE, Byte.valueOf((byte) (byte0 & -5)));
        }
    }
}
