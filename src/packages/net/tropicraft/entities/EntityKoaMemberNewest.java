package net.tropicraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

import CoroAI.componentAI.AIAgent;
import CoroAI.componentAI.ICoroAI;
import CoroAI.componentAI.jobSystem.JobHunt;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityKoaMemberNewest extends EntityLiving implements IEntityAdditionalSpawnData, ICoroAI
{
	
	public boolean gender;	//true for male, false for female
    public int nameIndex;
    
    public final String[] tribalMaleNames = new String[]{"Akamu", "Ekewaka", "Ikaika", "Iukini", "Kai", "Kaimana", "Kaimi", "Kanoa", "Kapena", "Keahi", "Keaweaheulu","Kekipi",
    		"Kekoa", "Konani", "Makani", "Mano", "Nahele"};
    
    public final String[] nameMaleMeanings = new String[]{"Earth, or Red", "Guardian of Prosperity", "Well Born", "Strong", "Sea", "Diamond", "The Seeker",
    		"The Free One", "The Captain", "Flames", "Cool Breeze Over the Mountains","Rebel", "The Brave One", "Bright", "Wind", "Shark", "Forest"};
    
    public final String[] tribalFemaleNames = new String[]{"Alamea", "Alani", "Alaula", "Ewa", "Kai", "Kaiolohia", "Kalama", "Keala", "Kiele", "Mahina",
    		"Makala", "Maylea", "Noe"};
    
    public final String[] nameFemaleMeanings = new String[]{"Precious", "Orange Tree", "Dawn", "Life", "Sea", "Calm Sea", "Flaming Torch", "The Pathway", "Fragant Blossom",
    		"Moon, or Moonlight", "Myrtle", "Wildflower", "Mist or Misty Rain"};
    
    
    public AIAgent agent;
    
	public EntityKoaMemberNewest(World world) {
		super(world);
		
		agent = new AIAgent(this, true);
		agent.jobMan.addPrimaryJob(new JobHunt(agent.jobMan));
		
		agent.setMoveSpeed(0.28F);
		agent.maxReach_Melee = 3F;
		agent.maxReach_Ranged = 15F;
		texture = "/tropicalmod/mobs/KoaMan3.png";
	}
	
	protected boolean isAIEnabled()
    {
        return true;
    }
	
	@Override
	public void updateAITasks() {
		agent.updateAITasks();

		//this.setDead();
	}
	
	@Override
	public boolean interact(EntityPlayer entityplayer)
    {
		
		
		return false;
    }

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		//data.writeUTF(name);
		data.writeUTF(texture);
		
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		//String temp = data.readUTF();
		//if (temp.length() > 0) {
			//name = temp;
			texture = data.readUTF();
		//}
		
	}
	
	@Override
	public boolean canDespawn() {
		return false;
	}

	@Override
	public AIAgent getAIAgent() {
		// TODO Auto-generated method stub
		return agent;
	}

	@Override
	public void setPathToEntity(PathEntity pathentity) {
		agent.setPathToEntity(pathentity);
	}

	@Override
	public boolean isBreaking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnemy(Entity ent) {
		if (ent instanceof EntityMob) return true;
		return false;
	}

	@Override
	public int getCooldownMelee() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getCooldownRanged() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public void attackMelee(Entity ent, float dist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attackRanged(Entity ent, float dist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 20;
	}
}
