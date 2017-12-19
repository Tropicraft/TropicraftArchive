package tropicraft.entities.passive.water;

import tropicraft.ModInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityTurtleEgg extends EntityLiving {

    public EntityTurtleEgg(World world) {
        super(world);
        //texture = ModInfo.TEXTURE_ENTITY_LOC + "eggText.png";
        setSize(.1F, .1F);
        hatchingTime = 0;
        rotationRand = 10;
        this.ignoreFrustumCheck = true;
    }
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(2.0D);
	}

    public void onUpdate() {
        super.onUpdate();
        rotationYaw = 0;
        if (ticksExisted % 400 == 0) {
            hatchingTime = 360;
        }
        if (hatchingTime != 0) {

            rotationRand += 1.5707F * worldObj.rand.nextFloat();
            hatchingTime--;
            if (hatchingTime == 1) {
                if (!worldObj.isRemote) {
                    EntitySeaTurtle entitydart = new EntitySeaTurtle(worldObj, .2F);
                    double d3 = this.posX;
                    double d4 = this.posY;
                    double d5 = this.posZ;
                    entitydart.setLocationAndAngles(d3, d4, d5, 0.0F, 0.0F);
                    worldObj.spawnEntityInWorld(entitydart);
                    this.setDead();
                }

                for (int i = 0; i < 8; i++) {
                    worldObj.spawnParticle("snowballpoof", posX, posY, posZ,
                            0.0D, 0.0D, 0.0D);
                }
            }

            if (hatchingTime == 0) {
                rotationRand = 0;
            }
        }
    }

    public int hatchingTime;
    public double rotationRand;

}