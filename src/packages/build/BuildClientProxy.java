package build;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class BuildClientProxy extends BuildCommonProxy
{
    public static Minecraft mc;

    public BuildClientProxy()
    {
        mc = ModLoader.getMinecraftInstance();
    }

    @Override
    public void init(BuildMod pMod)
    {
        super.init(pMod);
        TickRegistry.registerTickHandler(new BuildClientTicks(), Side.CLIENT);
    }

    /**
     * This is for registering armor types, like ModLoader.addArmor used to do
     */
    public int getArmorNumber(String type)
    {
        return RenderingRegistry.addNewArmourRendererPrefix(type);
    }
    
    @Override
    public void registerRenderInformation()
    {
    	
    }

    @Override
    public void registerTileEntitySpecialRenderer()
    {
    	
    }
}
