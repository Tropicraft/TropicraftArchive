package tropicraft.entities.hostile.land.boss;
import java.util.Random;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

public class tc_FireCrack {
	private Tessellator tess = Tessellator.instance;
	private double width;
	private double height;
	private double posY;
	private double posX;
	private double posZ;
	private int flickerTick;
	private int controller = 0;

	public tc_FireCrack(double dx, double dy) {
		flickerTick = 0;
		width = dx;
		height = dy;
		posY = 0;
		posX = 0;
		posZ = 0;
	}

	public tc_FireCrack(double dx, double dy, double px, double py, double pz) {
		this(dx, dy);
		posX = px;
		posY = py;
		posZ = pz;
	}

	public void draw(float f, int seed, double angle, float partialTick) {
		
		if (flickerTick >= 360) {
			// flickerTick = 0;
		}

		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(3553 /* GL_TEXTURE_2D */);
		GL11.glShadeModel(7425 /* GL_SMOOTH */);
		GL11.glEnable(3042 /* GL_BLEND */);
		GL11.glBlendFunc(770, 1);
		GL11.glDisable(3008 /* GL_ALPHA_TEST */);
		//GL11.glEnable(2884 /* GL_CULL_FACE */);
		GL11.glDepthMask(false);
		double posX1 = posX;
		double posY1 = posY;
		double posZ1 = posZ;
		double posX2 = posX + Math.cos(angle/56.26)*width;
		double posY2 = posY - height; //;+ Math.sin((double) (flickerTick* 5 ) / 56.26);
		double posZ2 = posZ + Math.sin(angle/56.26)*width;
		Random rand = new Random(seed);
		
		for (int i = 0; i < (int)f; i++) {
			int k = rand.nextInt(15);
			if(rand.nextInt(2) == 0){
				k *= -1;
			}
			double d = rand.nextDouble()*.25;
			posY2 = posY1 - height - d;
			
			if(f >= 250){				
				posY2 -= height*(250- f)/50;
			}
			GL11.glPushMatrix();		
			tess.startDrawing(6);
			tess.setColorRGBA_I(0xFF0000, 255);
			tess.addVertex(posX1, posY1, posZ1);
			tess.setColorRGBA_I(0xFFB00F, rand.nextInt(90));
			tess.addVertex(posX1 , posY2, posZ1);
			tess.addVertex(posX2, posY2, posZ2);
			tess.setColorRGBA_I(0xFF0000, 255);
			tess.addVertex(posX2, posY1, posZ2);			
			tess.draw();
			
			GL11.glPopMatrix();
			posX1 = posX2;
			posZ1 = posZ2;
			posX2 +=Math.cos(.13089*k + angle/56.26)*width;
			posZ2 += Math.sin(.13089*k + angle/56.26)*width;
			//posY1 = posY2;
			//posY2 = posY + height + Math.sin((double) (flickerTick* 5 ) / 56.26);
		}
		GL11.glDepthMask(true);
		//GL11.glDisable(2884 /* GL_CULL_FACE */);
		GL11.glDisable(3042 /* GL_BLEND */);
		GL11.glShadeModel(7424 /* GL_FLAT */);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(3553 /* GL_TEXTURE_2D */);
		GL11.glEnable(3008 /* GL_ALPHA_TEST */);
		RenderHelper.enableStandardItemLighting();
		flickerTick++;

	}
}
