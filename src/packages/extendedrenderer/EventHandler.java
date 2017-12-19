package extendedrenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extendedrenderer.particle.ParticleRegistry;

public class EventHandler {

	
	public long lastWorldTime;

	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
    public void worldRender(RenderWorldLastEvent event)
    {
		Minecraft mc = Minecraft.getMinecraft();
		
		if (mc.theWorld != null && mc.theWorld.getWorldInfo().getWorldTime() != lastWorldTime)
        {
            lastWorldTime = mc.theWorld.getWorldInfo().getWorldTime();

            if (!mc.isGamePaused)
            {
                ExtendedRenderer.rotEffRenderer.updateEffects();
            }
        }

        //Rotating particles hook
        ExtendedRenderer.rotEffRenderer.renderParticles((Entity)mc.renderViewEntity, (float)event.partialTicks);
    }
	
	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void registerIcons(TextureStitchEvent event) {
		ParticleRegistry.init(event);
	}

	
}
