package tropicraft.entities.projectiles;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityCreeper;

public class EntityDartHelper {
    
    public static void setEntityAITasks(EntityLiving entity, EntityAITasks newTasks) {
        if (entity != null) {
            //entity.tasks = null;
        	try {
        		setFinalStatic(EntityLiving.class.getDeclaredField("tasks"), (Object)newTasks);
        	} catch (Exception ex) {
        		
        	}
        }
    } 
    
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
    
    public static EntityAITasks getEntityAITasks(EntityLiving entity) {
        if (entity != null) {
            return entity.tasks;
        }
        return null;
    }
    
    public static void setEntityMoveSpeed(EntityLiving entity, float speed) {
        if (entity != null) {
            entity.setAIMoveSpeed(speed);
        }
    }
    
    public static float getEntityMoveSpeed(EntityLiving entity) {
        if (entity != null) {
            return entity.getAIMoveSpeed();
        }
        return 0.0F;
    } 
    
    public static void setIsEntityJumping(EntityLiving entity, boolean isJumping) {
        if (entity != null) {
            entity.isJumping = isJumping;
        }
    }
    
    public static void setEntityToAttack(EntityCreature entity, Entity target) {
        if (entity != null && target != null) {
            entity.attackEntityAsMob(target);
        }
    }
    
    public static void setCreeperIgnitionTime(EntityCreeper entity, int ignitionTime) {
        if (entity != null) {
            entity.setCreeperState(-1);
        }
    }
    
    public static void setEntityAttackTime(EntityLiving entity, int attackTime) {
        if (entity != null) {
            entity.attackTime = attackTime;
        }
    }

    
}
