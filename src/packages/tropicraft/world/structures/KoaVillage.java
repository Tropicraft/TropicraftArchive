package tropicraft.world.structures;

import java.util.LinkedList;
import java.util.Random;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import tropicraft.entities.hostile.land.tribes.koa.v3.EntityKoaBase;
import tropicraft.entities.hostile.land.tribes.koa.v3.EntityKoaTrader;

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
	public LinkedList<ChunkCoordinates> tradePoints = new LinkedList<ChunkCoordinates>();
	
	private int numMaleOccupations;
	private int numFemaleOccupations;
	
	public boolean hasPlacedTrader;

	public KoaVillage(World world) 
	{
		hasPlacedTrader = false;
		KoaVillageManager.villageList.add(this);
		
		//4-6 male hunters
		//2-4 female hunters
		
		//2-4 male fishermen
		//4-6 female fishermen
		
		//1-3 male kids
		//1-3 female kids
	}
	
	public void spawnEntities(World world) {
		
		for (int i = 0; i < tradePoints.size(); i++) {
			ChunkCoordinates ksp = tradePoints.get(i);
			//System.out.println("trade hut home # " + i + " - " + ksp.posX + " - " + ksp.posY + " - " + ksp.posZ);
			
			EntityKoaBase koa;
			
			//for (int j = 0; j < 2; j++) {
				koa = new EntityKoaTrader(world);
				koa.setLocationAndAngles(ksp.posX, ksp.posY+1, ksp.posZ + 5, 0, 0);
				koa.getAIAgent().homeX = ksp.posX;
				koa.getAIAgent().homeY = ksp.posY;
				koa.getAIAgent().homeZ = ksp.posZ;
				world.spawnEntityInWorld(koa);
				koa.getAIAgent().spawnedOrNBTReloadedInit();
			//}
		}
		
		for (int i = 0; i < spawnPoints.size(); i++) {
			ChunkCoordinates ksp = spawnPoints.get(i);
			//System.out.println("hut # " + i + " - " + ksp.posX + " - " + ksp.posY + " - " + ksp.posZ);
			
			//switched to koa chests that manage population
			/*EntityKoaBase koa;
			
			for (int j = 0; j < 2; j++) {
				koa = new EntityKoaHunter(world);
				koa.setLocationAndAngles(ksp.posX, ksp.posY+1, ksp.posZ + 5, 0, 0);
				koa.getAIAgent().homeX = ksp.posX;
				koa.getAIAgent().homeY = ksp.posY;
				koa.getAIAgent().homeZ = ksp.posZ;
				world.spawnEntityInWorld(koa);
				koa.getAIAgent().spawnedOrNBTReloadedInit();
			}
			
			for (int j = 0; j < 2; j++) {
				koa = new EntityKoaFisher(world);
				koa.setLocationAndAngles(ksp.posX, ksp.posY+1, ksp.posZ + 5, 0, 0);
				koa.getAIAgent().homeX = ksp.posX;
				koa.getAIAgent().homeY = ksp.posY;
				koa.getAIAgent().homeZ = ksp.posZ;
				world.spawnEntityInWorld(koa);
				koa.getAIAgent().spawnedOrNBTReloadedInit();
			}*/
		}
	}
	
}