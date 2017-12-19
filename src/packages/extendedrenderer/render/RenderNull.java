package extendedrenderer.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class RenderNull extends Render
{
    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
    	var1.lastTickPosX = var1.posX;
    	var1.lastTickPosY = var1.posY;
    	var1.lastTickPosZ = var1.posZ;
    }
}
