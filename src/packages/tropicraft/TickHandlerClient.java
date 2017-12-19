package tropicraft;

import java.util.EnumSet;
import java.util.List;

import tropicraft.config.TropicraftConfig;
import tropicraft.entities.EntityTropicraftWaterMob;
import tropicraft.fishing.EntityHook;
import tropicraft.fishing.ItemRod;
import tropicraft.questsystem.PlayerQuests;
import tropicraft.versionchecker.VersionChecker;
import tropicraft.world.TropicraftWorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TickHandlerClient implements ITickHandler
{
	public static PlayerQuests playerQuests;
	public static EntityPlayer lastPlayer;
	public static NBTTagCompound pagesCollected = new NBTTagCompound();
	
	private VersionChecker checker;
	private boolean requestCheck = true;
	private boolean vCheck = true;
	
	public TickHandlerClient(){		
			checker =  new VersionChecker(ModInfo.versionURL, ModInfo.VERSION, ModInfo.NAME, ModInfo.dlURL);		
		
		
	}
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.RENDER)))
        {
            onRenderTick();
        }
        else if (type.equals(EnumSet.of(TickType.CLIENT)))
        {
            GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;

            if (guiscreen != null)
            {
                onTickInGUI(guiscreen);
            }
            else
            {
                onTickInGame();
            }
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
        // In my testing only RENDER, CLIENT, & PLAYER did anything on the client side.
        // Read 'cpw.mods.fml.common.TickType.java' for a full list and description of available types
    }

    @Override
    public String getLabel()
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void onRenderTick()
    {
    	Minecraft mc = FMLClientHandler.instance().getClient();

    	EntityPlayer player = mc.thePlayer;

		if (playerQuests == null || lastPlayer != player) {
			if (player != null) {
				lastPlayer = player;
				playerQuests = new PlayerQuests(null, player);
			}
		} else {
			if (mc.currentScreen == null) {
				if (playerQuests.activeQuests.size() > 0 && mc.thePlayer.dimension == TropicraftWorldUtils.TROPICS_DIMENSION_ID) playerQuests.renderQuestOverlay();
			}
		}
		
		if(mc.currentScreen == null && player != null/* && EntityTropicraftWaterMob.fishingDebug*/){
			ItemStack stack = player.getCurrentEquippedItem();
			if(stack == null) return;
			if(stack.getItem() instanceof ItemRod){
				ItemRod rod = (ItemRod)stack.getItem();
				String type = rod.rodType == 0 ? "Old" : rod.rodType == 1 ? "Good" : "Super";
				EntityHook lure = null;
				List<Entity> ents = mc.theWorld.loadedEntityList;
				for(Entity e : ents){
					if(e instanceof EntityHook){
						EntityHook hook = (EntityHook)e;
						if(hook.getAnglerID() == player.entityId){
							lure = hook;
							continue;
						}
					}
				}
				if(mc.ingameGUI != null && lure != null)
				mc.ingameGUI.drawString(mc.fontRenderer, type+" Rod - Line Length: "+lure.getWireLength(), 2, 2, 0xffffff);
				
			}
		}
		
    	
    }

    public void onTickInGUI(GuiScreen guiscreen)
    {
        onTickInGame();
        //System.out.println("onTickInGUI");
        //TODO: Your Code Here
    }
    public void onTickInGame()
    {
    	World world = FMLClientHandler.instance().getClient().theWorld;
    	if (world != null) {
			if(vCheck && TropicraftConfig.versionChecker){				
				//Debug.println(this, "Checking Version");
				String versionCheck = VersionChecker.outString;
				if(versionCheck != null){
					vCheck = false;
				}
				if(versionCheck != "" && versionCheck != null){
					FMLClientHandler.instance().getClient().thePlayer.addChatMessage(versionCheck);
				}
				
				
			}
    	}
    }
    
    private String checkVersion(){
		String s = checker.tellInfo();
		System.out.println(s);
		return s;
		
	}
    
}
