package tropicraft.entities.ai.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tropicraft.blocks.BlockBongoDrum;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import CoroAI.PFQueue;
import CoroAI.c_CoroAIUtil;
import CoroAI.entity.EnumInfo;
import CoroAI.entity.EnumJobState;
import CoroAI.entity.JobBase;
import CoroAI.entity.JobManager;

public class JobMusic extends JobBase {
	
	public int scanRange = 8;
	public int ticksPlaying = 0;
	public int ticksPlayingMax = 100;
	
	public int ticksBetweenAreaScans = 40;
	
	public List<ChunkCoordinates> drumCoords = new ArrayList<ChunkCoordinates>();
	
	public JobMusic(JobManager jm) {
		super(jm);
	}
	
	@Override
	public boolean shouldExecute() {
		return true;
	}
	
	@Override
	public boolean shouldContinue() {
		return true;
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (ent.worldObj.getWorldTime() % ticksBetweenAreaScans == 0) {
			scanAreaForDrums();
		}
		
		if (drumCoords.size() > 0) {
			
			Random rand = new Random();
			
			if (ent.entityToAttack == null && ticksPlaying == 0 && rand.nextInt(150) == 0) {
				ticksPlaying = ticksPlayingMax + rand.nextInt(150);
			} else {
				
			}
			
			if (ent.entityToAttack != null) {
				ticksPlaying = 0;
			}
			
			if (ticksPlaying > 0) {
				
				ticksPlaying--;
				
				int randID = rand.nextInt(drumCoords.size());
				
				int id = ent.worldObj.getBlockId(drumCoords.get(randID).posX, drumCoords.get(randID).posY, drumCoords.get(randID).posZ);
				if (id != 0 && Block.blocksList[id] instanceof BlockBongoDrum) {
					//System.out.println("play bongo!!!");
					int randChoice = 2 + ent.entityId % 10;//2+rand.nextInt(1);
					
					if (rand.nextInt(10) == 0) randChoice /= 2;
					
					if (ent.worldObj.getWorldTime() % randChoice * 2 == 0) {
						Block.blocksList[id].onBlockActivated(ent.worldObj, drumCoords.get(randID).posX, drumCoords.get(randID).posY, drumCoords.get(randID).posZ, null, 1, 0, 0, 0);
						
						//ent.rotationPitch = -45 + rand.nextInt(90);
						ent.rotationYaw += rand.nextInt(90);
						ent.getNavigator().clearPathEntity();
						ent.getMoveHelper().setMoveTo(drumCoords.get(randID).posX, drumCoords.get(randID).posY, drumCoords.get(randID).posZ, 0.2F);
						ent.swingItem();
						if (ent.onGround && rand.nextInt(10) == 0) ent.jump();
					}
				}
			}
		}
	}
	
	public void scanAreaForDrums() {
		drumCoords.clear();
		for (int x = -scanRange/2; x < scanRange/2; x++) {
			for (int z = -scanRange/2; z < scanRange/2; z++) {
				int id = ent.worldObj.getBlockId((int)Math.floor(ent.posX) + x, (int)Math.floor(ent.posY), (int)Math.floor(ent.posZ) + z);
				
				if (id != 0 && Block.blocksList[id] instanceof BlockBongoDrum) {
					drumCoords.add(new ChunkCoordinates((int)Math.floor(ent.posX) + x, (int)Math.floor(ent.posY), (int)Math.floor(ent.posZ) + z));
				}
			}
		}
	}
	
}
