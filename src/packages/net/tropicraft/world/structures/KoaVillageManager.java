package net.tropicraft.world.structures;

import java.util.ArrayList;
import java.util.HashMap;

public final class KoaVillageManager 
{
	public static int id = 0;
	public static ArrayList<KoaVillage> villageList;
	public static final HashMap<Integer, KoaVillage> villageMap = new HashMap<Integer, KoaVillage>();
	
	public KoaVillageManager() 
	{
		villageList = new ArrayList<KoaVillage>();
	}
	
	public static final KoaVillageManager instance = new KoaVillageManager();
}
