package build.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import build.world.Build;
import build.world.BuildManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Overlays {

	
	public static void renderBuildOutline(Build b, int dir) {
		float x = b.map_coord_minX;// + 0.5F;
		float y = b.map_coord_minY;// + 0.5F;
		float z = b.map_coord_minZ;// + 0.5F;
		float x1 = b.map_sizeX + x - 1F;
		float y1 = b.map_sizeY + y - 1F;
		float z1 = b.map_sizeZ + z - 1F;
		
		ChunkCoordinates c1 = BuildManager.rotate(new ChunkCoordinates((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z)), dir, Vec3.createVectorHelper(b.map_coord_minX, b.map_coord_minY, b.map_coord_minZ), Vec3.createVectorHelper(b.map_sizeX, b.map_sizeY, b.map_sizeZ));
		ChunkCoordinates c2 = BuildManager.rotate(new ChunkCoordinates((int)Math.floor(x1), (int)Math.floor(y1), (int)Math.floor(z1)), dir, Vec3.createVectorHelper(b.map_coord_minX, b.map_coord_minY, b.map_coord_minZ), Vec3.createVectorHelper(b.map_sizeX, b.map_sizeY, b.map_sizeZ));
		
		renderBuildOutline(c1.posX, c1.posY, c1.posZ, c2.posX, c2.posY, c2.posZ);
	}
	
	public static void renderDirectionArrow(Build b, int dir) {
		float x = (float)b.map_coord_minX + 0.5F + (float)b.map_sizeX / 2F;// + 0.5F;
		float y = b.map_coord_minY;
		float z = (float)b.map_coord_minZ + (float)b.map_sizeZ / 2F;// + 0.5F;
		float x1 = (float)b.map_coord_minX + (float)b.map_sizeX / 2F;// + 0.5F;
		float y1 = b.map_coord_minY;
		float z1 = (float)b.map_coord_minZ + (float)b.map_sizeZ / 2F;// + 0.5F;
		float x2 = b.map_sizeX/2 + x - 1F;
		float y2 = b.map_sizeY + y - 1F;
		float z2 = b.map_sizeZ/2 + z - 1F;
		
		ChunkCoordinates c1 = BuildManager.rotate(new ChunkCoordinates((int)Math.floor(x), (int)Math.floor(y), (int)Math.floor(z)), dir, Vec3.createVectorHelper(b.map_coord_minX, b.map_coord_minY, b.map_coord_minZ), Vec3.createVectorHelper(b.map_sizeX, b.map_sizeY, b.map_sizeZ));
		ChunkCoordinates c2 = BuildManager.rotate(new ChunkCoordinates((int)Math.floor(x1), (int)Math.floor(y1), (int)Math.floor(z1)), dir, Vec3.createVectorHelper(b.map_coord_minX, b.map_coord_minY, b.map_coord_minZ), Vec3.createVectorHelper(b.map_sizeX, b.map_sizeY, b.map_sizeZ));
		
		renderLineFromToBlock(c1.posX + 0.5, y + 3.5, c1.posZ + 0.5, c2.posX + 0.5, y + 3.5, c2.posZ + 0.5, 0x00FF00);
	}
	
	/*public static void renderDirectionArrow(Vec3 pos, int dir) {
		
		//double vecX = 
		double arrowSize = 4;
		ChunkCoordinates c1 = BuildManager.rotate(new ChunkCoordinates((int)pos.xCoord, (int)pos.yCoord, (int)pos.zCoord), dir, Vec3.createVectorHelper(pos.xCoord - arrowSize/2, pos.yCoord - arrowSize/2, pos.zCoord - arrowSize/2), Vec3.createVectorHelper(arrowSize, arrowSize, arrowSize));
		ChunkCoordinates c2 = BuildManager.rotate(new ChunkCoordinates((int)pos.xCoord + 1, (int)pos.yCoord, (int)pos.zCoord), dir, Vec3.createVectorHelper(pos.xCoord - arrowSize/2, pos.yCoord - arrowSize/2, pos.zCoord - arrowSize/2), Vec3.createVectorHelper(arrowSize, arrowSize, arrowSize));
		
		renderLineFromToBlock(c1.posX, c1.posY, c1.posZ, c2.posX, c2.posY, c2.posZ, 0x00FF00);
	}*/
	
	public static void renderBuildOutline(float x, float y, float z, float x1, float y1, float z1) {
		
		x += 0.5F;
		y += 0.5F;
		z += 0.5F;
		x1 += 0.5F;
		y1 += 0.5F;
		z1 += 0.5F;
		
		//x += (mod_ZombieCraft.worldRef.rand.nextFloat() * 0.3F);
		
		int color = 0xFF0000;
		
		//cross intersects
		renderLineFromToBlock(x, y, z, x1, y1, z1, color);
		renderLineFromToBlock(x1, y, z1, x, y1, z, color);
		renderLineFromToBlock(x, y, z1, x1, y1, z, color);
		renderLineFromToBlock(x1, y, z, x, y1, z1, color);
		
		//corner 0
		renderLineFromToBlock(x, y, z, x, y, z1, color);
		renderLineFromToBlock(x, y, z, x, y1, z, color);
		renderLineFromToBlock(x, y, z, x1, y, z, color);
		
		//corner 1
		renderLineFromToBlock(x1, y, z, x1, y1, z, color);
		renderLineFromToBlock(x1, y, z, x1, y, z1, color);
		
		//corner 2
		renderLineFromToBlock(x1, y, z1, x1, y1, z1, color);
		renderLineFromToBlock(x1, y, z1, x, y, z1, color);
		
		//corner 3
		renderLineFromToBlock(x, y, z1, x, y1, z1, color);
		//renderLineFromToBlock(x1, y, z1, x, y, z1);
		
		//top parts
		renderLineFromToBlock(x, y1, z, x1, y1, z, color);
		renderLineFromToBlock(x1, y1, z, x1, y1, z1, color);
		renderLineFromToBlock(x1, y1, z1, x, y1, z1, color);
		renderLineFromToBlock(x, y1, z1, x, y1, z, color);
		
		//renderLineFromToBlock(x1, y, z, x1, y1, z1);
		
	}

	public static void renderLine(PathPoint ppx, PathPoint ppx2, double d, double d1, double d2, float f, float f1, int stringColor) {
		renderLineFromToBlock(ppx.xCoord, ppx.yCoord, ppx.zCoord, ppx2.xCoord, ppx2.yCoord, ppx2.zCoord, stringColor);
	}
	
	public static void renderLineFromToBlockCenter(double x1, double y1, double z1, double x2, double y2, double z2, int stringColor) {
		renderLineFromToBlock(x1+0.5D, y1+0.5D, z1+0.5D, x2+0.5D, y2+0.5D, z2+0.5D, stringColor);
	}
	
	public static void renderLineFromToBlock(double x1, double y1, double z1, double x2, double y2, double z2, int stringColor) {
	    Tessellator tessellator = Tessellator.instance;
	    RenderManager rm = RenderManager.instance;
	    
	    float castProgress = 1.0F;
	
	    float f10 = 0F;
	    double d4 = MathHelper.sin(f10);
	    double d6 = MathHelper.cos(f10);
	
	    double pirateX = x1;
	    double pirateY = y1;
	    double pirateZ = z1;
	    double entX = x2;
	    double entY = y2;
	    double entZ = z2;
	
	    double fishX = castProgress*(entX - pirateX);
	    double fishY = castProgress*(entY - pirateY);
	    double fishZ = castProgress*(entZ - pirateZ);
	    GL11.glDisable(GL11.GL_TEXTURE_2D);
	    GL11.glDisable(GL11.GL_LIGHTING);
	    tessellator.startDrawing(GL11.GL_LINE_STRIP);
	    //GL11.GL_LINE_WIDTH
	    //int stringColor = 0x888888;
	    //if (((EntityNode)entitypirate).render) {
	    	//stringColor = 0x880000;
	    //} else {
	    	//stringColor = 0xEF4034;
		//}
	    tessellator.setColorOpaque_I(stringColor);
	    int steps = 1;
	
	    for (int i = 0; i <= steps; ++i) {
	        float f4 = i/(float)steps;
	        tessellator.addVertex(
	            pirateX - rm.renderPosX + fishX * f4,//(f4 * f4 + f4) * 0.5D + 0.25D,
	            pirateY - rm.renderPosY + fishY * f4,//(f4 * f4 + f4) * 0.5D + 0.25D,
	            pirateZ - rm.renderPosZ + fishZ * f4);
	    }
	    
	    tessellator.draw();
	    GL11.glEnable(GL11.GL_LIGHTING);
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
}
