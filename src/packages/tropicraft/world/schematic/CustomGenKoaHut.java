package tropicraft.world.schematic;

import tropicraft.blocks.TropicraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import build.ICustomGen;
import build.world.BuildJob;
import build.world.BuildManager;

public class CustomGenKoaHut implements ICustomGen {

	BuildJob bj;
	
	@Override
	public void genPassPre(World world, BuildJob parBuildJob, int parPass) {
		bj = parBuildJob;
		int id = TropicraftBlocks.tropicsFence.blockID;
		placeDownTilGround(world, 1, -1, 10, id);
		placeDownTilGround(world, parBuildJob.build.map_sizeZ-2, -1, 10, id);
		placeDownTilGround(world, 1, -1, parBuildJob.build.map_sizeX-2, id);
		placeDownTilGround(world, parBuildJob.build.map_sizeZ-2, -1, parBuildJob.build.map_sizeX-2, id);
	}
	
	protected void placeDownTilGround(World world, int x, int y, int z, int id) {
		int absY = bj.build_startY+y;
		int idCheck = world.getBlockId(bj.build_startX+x, absY, bj.build_startZ+z);
		
        while (absY > 0 && (idCheck == 0 || Block.blocksList[idCheck].blockMaterial == Material.water)) {
        	setRel(x, y, z, id, 0);
            y--;
            absY = bj.build_startY+y;
            idCheck = world.getBlockId(bj.build_startX+x, absY, bj.build_startZ+z);
        }
    }

	public void setRel(int width, int height, int depth, int id, int meta) {
		BuildManager.rotateSet(bj, new ChunkCoordinates(bj.build_startX+depth, bj.build_startY+height, bj.build_startZ+width), id, meta);
	}
	
	public void set(int x, int y, int z, int id, int meta) {
		BuildManager.rotateSet(bj, new ChunkCoordinates(x, y, z), id, meta);
	}

}
