package tropicraft.volleyball;

import java.io.File;

import modconfig.IConfigCategory;

public class ModIds implements IConfigCategory {

	/* Block ids */
	public static int BLOCK_VOLLEYBALLCOURTMASTER_ID = 4040;
	public static int BLOCK_VOLLEYBALLCOURTPOST_ID = 4041;
	
	@Override
	public String getConfigFileName() {
		// TODO Auto-generated method stub
		return "Tropicraft" + File.separator + "VolleyballIDs";
	}
	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Tropicraft Volleyball Ids";
	}
	@Override
	public void hookUpdatedValues() {
		// TODO Auto-generated method stub
		
	}
}
