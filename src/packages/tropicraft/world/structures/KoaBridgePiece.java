package tropicraft.world.structures;

public class KoaBridgePiece 
{
	public int x, y, z;
	public  boolean canBuildHere;
	public int villageID;
	
	public KoaBridgePiece(int i, int j, int k, boolean b) 
	{
		x = i;
		y = j;
		z = k;
		canBuildHere = b;
		villageID = 0;
	}
	
}