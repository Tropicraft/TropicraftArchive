package net.tropicraft.world.structures;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.tropicraft.entities.EntityKoaManly;
import net.tropicraft.entities.EntityKoaMemberNew;

import java.util.LinkedList;
import java.util.Random;

import CoroAI.entity.EnumJob;

/*import CoroAI.entity.EnumJob;

import net.tropicraft.entities.EntityKoaMan;
import net.tropicraft.entities.EntityKoaMember;
import net.tropicraft.entities.EntityKoaMemberNew;*/

public class KoaVillage 
{
	public int id;
	private static int nextVillageId = 0;
	public boolean allPiecesPlaced;
	private Random rand;
	
	/*private ArrayList<EnumKoaOccupation> maleOccupations = new ArrayList<EnumKoaOccupation>( Arrays.asList(new EnumKoaOccupation[]{EnumKoaOccupation.mCHIEF, 
			EnumKoaOccupation.mFISHERMAN, EnumKoaOccupation.mFISHERMAN, EnumKoaOccupation.mFISHERMAN, EnumKoaOccupation.mFISHERMAN,
			EnumKoaOccupation.mGATHERER, EnumKoaOccupation.mGATHERER, EnumKoaOccupation.mGATHERER,
			EnumKoaOccupation.mHUNTER, EnumKoaOccupation.mHUNTER, EnumKoaOccupation.mHUNTER, EnumKoaOccupation.mHUNTER, 
			EnumKoaOccupation.mSHAMAN, 
			EnumKoaOccupation.mWEATHERGURU}));
	
	private ArrayList<EnumKoaOccupation> femaleOccupations = new ArrayList<EnumKoaOccupation>( Arrays.asList(new EnumKoaOccupation[]{
			EnumKoaOccupation.fFISHERMAN, EnumKoaOccupation.fFISHERMAN, EnumKoaOccupation.fFISHERMAN,
			EnumKoaOccupation.fGATHERER, EnumKoaOccupation.fGATHERER, EnumKoaOccupation.fGATHERER, EnumKoaOccupation.fGATHERER, 
			EnumKoaOccupation.fHUNTER, EnumKoaOccupation.fHUNTER, EnumKoaOccupation.fHUNTER, EnumKoaOccupation.fHUNTER, 
			EnumKoaOccupation.fCOOK, EnumKoaOccupation.fCOOK}));*/
	
	public LinkedList<StructureComponent> components = new LinkedList<StructureComponent>();
	public LinkedList<ChunkCoordinates> spawnPoints = new LinkedList<ChunkCoordinates>();
	
	private int numMaleOccupations;
	private int numFemaleOccupations;

	public KoaVillage(World world) 
	{
		KoaVillageManager.villageList.add(this);
		
		//4-6 male hunters
		//2-4 female hunters
		
		//2-4 male fishermen
		//4-6 female fishermen
		
		//1-3 male kids
		//1-3 female kids
	}
	
	public void spawnEntities(World world) {
		for (int i = 0; i < spawnPoints.size(); i++) {
			ChunkCoordinates ksp = spawnPoints.get(i);
			System.out.println("hut # " + i + " - " + ksp.posX + " - " + ksp.posY + " - " + ksp.posZ);
			
			EntityKoaMemberNew var38;
			try {
				var38 = (EntityKoaManly)EntityKoaManly.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
				var38.setLocationAndAngles(ksp.posX, ksp.posY+1, ksp.posZ + 5, 0, 0);
				var38.homeX = ksp.posX;
				var38.homeY = ksp.posY;
				var38.homeZ = ksp.posZ;
				//var38.initJobAndStates(EnumJob.FISHERMAN);
				var38.initJobAndStates(EnumJob.FISHERMAN);
				world.spawnEntityInWorld(var38);
			} catch (Exception var32) {
				var32.printStackTrace();
			}
			
			try {
				var38 = (EntityKoaManly)EntityKoaManly.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
				var38.setLocationAndAngles(ksp.posX, ksp.posY+1, ksp.posZ + 5, 0, 0);
				var38.homeX = ksp.posX;
				var38.homeY = ksp.posY;
				var38.homeZ = ksp.posZ;
				//var38.initJobAndStates(EnumJob.FISHERMAN);
				var38.initJobAndStates(EnumJob.FISHERMAN);
				world.spawnEntityInWorld(var38);
			} catch (Exception var32) {
				var32.printStackTrace();
			}
			
			try {
				var38 = (EntityKoaManly)EntityKoaManly.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
				var38.setLocationAndAngles(ksp.posX, ksp.posY+1, ksp.posZ + 5, 0, 0);
				var38.homeX = ksp.posX;
				var38.homeY = ksp.posY;
				var38.homeZ = ksp.posZ;
				//var38.initJobAndStates(EnumJob.FISHERMAN);
				var38.initJobAndStates(EnumJob.HUNTER);
				world.spawnEntityInWorld(var38);
			} catch (Exception var32) {
				var32.printStackTrace();
			}
			
			try {
				var38 = (EntityKoaManly)EntityKoaManly.class.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
				var38.setLocationAndAngles(ksp.posX, ksp.posY+1, ksp.posZ + 5, 0, 0);
				var38.homeX = ksp.posX;
				var38.homeY = ksp.posY;
				var38.homeZ = ksp.posZ;
				//var38.initJobAndStates(EnumJob.FISHERMAN);
				var38.initJobAndStates(EnumJob.HUNTER);
				world.spawnEntityInWorld(var38);
			} catch (Exception var32) {
				var32.printStackTrace();
			}
		}
	}
	
}