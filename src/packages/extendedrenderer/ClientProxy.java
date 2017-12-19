package extendedrenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extendedrenderer.render.RotatingEffectRenderer;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    public static Minecraft mc;

    public ClientProxy()
    {
        mc = ModLoader.getMinecraftInstance();
    }
    
    @Override
    public void preInit(ExtendedRenderer pMod)
    {
    	super.preInit(pMod);
    }
    
    @Override
    public void postInit(ExtendedRenderer pMod)
    {
    	super.postInit(pMod);
    	ExtendedRenderer.rotEffRenderer = new RotatingEffectRenderer(mc.theWorld, mc.renderEngine);
    }

    @Override
    public void init(ExtendedRenderer pMod)
    {
        super.init(pMod);
    }
    
}
