package build;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import org.lwjgl.input.Keyboard;

import build.config.BuildConfig;
import build.enums.EnumBuildState;
import build.enums.EnumCopyState;
import build.render.Overlays;
import build.world.Build;
import build.world.BuildJob;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class BuildClientTicks implements ITickHandler
{
	//plan to make dedi server compatible
	//client still controls the states
	//client still makes its own clip data, BUT also sends coords etc to server so it can do the same
	//server needs:
	/// handle clipboard for each player, support for array of things per player
	/// handles commands:
	//// copy command, sends all needed data, writes to players server clipboard, clients already ready
	//// build command, sends build coords and rotation
	
	//Player based fields
	public EnumBuildState buildState = EnumBuildState.NORMAL; //client
	public EnumCopyState copyState = EnumCopyState.NORMAL; //client
	
	public Build clipboardData; //client & server
	
    public MovingObjectPosition extendedMouseOver = null;
    
    public static BuildClientTicks i = null;
    
    public int direction = 0;
    
    public BuildClientTicks() {
    	clipboardData = new Build(0, 0, 0, "build");
    }
	
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
    	if (i == null) i = this;
    }

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
    
    public void worldRenderTick(float partialTicks)
    {
    	Minecraft mc = FMLClientHandler.instance().getClient();
    	
    	if (mc.renderViewEntity != null) extendedMouseOver = mc.renderViewEntity.rayTrace(20, partialTicks);
    	
    	if (buildState == EnumBuildState.PLACE) {
    		//EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
    		//int l = MathHelper.floor_double((double)((-player.rotationYaw-45) * 4.0F / 360.0F)/* + 0.5D*/) & 3;
    		Overlays.renderBuildOutline(clipboardData, direction);
    		Overlays.renderDirectionArrow(clipboardData, direction);
    	} else if (copyState == EnumCopyState.SETMAX) {
    		int x = (int)MathHelper.floor_double(mc.thePlayer.posX);
    		int y = (int)MathHelper.floor_double(mc.thePlayer.posY - mc.thePlayer.yOffset);
    		int z = (int)MathHelper.floor_double(mc.thePlayer.posZ);
    		if (extendedMouseOver != null) {
    			x = extendedMouseOver.blockX;
    			y = extendedMouseOver.blockY;
    			z = extendedMouseOver.blockZ;
    		}
    		Overlays.renderBuildOutline(sx, sy, sz, x, y, z);
    	}
    }

    public void onRenderTick()
    {
    	
    	
    }

    public void onTickInGUI(GuiScreen guiscreen)
    {
        //TODO: Your Code Here
    }
    
    public boolean wasKeyDown = false;
    
    public void onTickInGame() {
		// TODO Auto-generated method stub
		
    	Minecraft mc = FMLClientHandler.instance().getClient();
    	
    	if (mc.theWorld != null) {
    		clipboardData.dim = mc.theWorld.provider.dimensionId;
    	}
    	
    	if (buildState == EnumBuildState.PLACE) {
    		if (extendedMouseOver != null) {
    			//clipboardData.setCornerPosition(extendedMouseOver.blockX - (clipboardData.map_sizeX / 2), extendedMouseOver.blockY+1, extendedMouseOver.blockZ - (clipboardData.map_sizeZ / 2));
    			clipboardData.setCornerPosition(extendedMouseOver.blockX, extendedMouseOver.blockY+1, extendedMouseOver.blockZ);
    		} else {
    			clipboardData.setCornerPosition((int)MathHelper.floor_double(mc.thePlayer.posX), (int)MathHelper.floor_double(mc.thePlayer.posY - mc.thePlayer.yOffset), (int)MathHelper.floor_double(mc.thePlayer.posZ));
    		}
    	}
    	
    	if (BuildConfig.enableEditMode) {
    		updateInput();
    	} else {
    		copyState = EnumCopyState.NORMAL;
    		buildState = EnumBuildState.NORMAL;
    	}
    	
	}
    
    public void updateInput() {
    	
    	if (Keyboard.isKeyDown(Keyboard.getKeyIndex(BuildConfig.key_Copy))) {
    		buildState = EnumBuildState.NORMAL; //reset
			if (!wasKeyDown) {
				eventCopy();
			}
			wasKeyDown = true;
    	} else if (Keyboard.isKeyDown(Keyboard.getKeyIndex(BuildConfig.key_Build))) {
    		copyState = EnumCopyState.NORMAL; //reset
			if (!wasKeyDown) {
				eventBuild();
			}
			wasKeyDown = true;
    	} else if (Keyboard.isKeyDown(Keyboard.getKeyIndex(BuildConfig.key_Rotate))) {
    		if (!wasKeyDown && buildState == EnumBuildState.PLACE) {
    			direction++;
    			if (direction > 3) direction = 0;
    		}
			wasKeyDown = true;
		} else {
			wasKeyDown = false;
		}
    }
    
    int sx; int sy; int sz; int ex; int ey; int ez;
    
    public void eventCopy() {
    	Minecraft mc = FMLClientHandler.instance().getClient();
    	if (copyState == EnumCopyState.NORMAL) {
    		copyState = EnumCopyState.SETMIN;
    	} else if (copyState == EnumCopyState.SETMIN) {
    		copyState = EnumCopyState.SETMAX;
    		sx = (int)MathHelper.floor_double(mc.thePlayer.posX);
    		sy = (int)(mc.thePlayer.posY - mc.thePlayer.yOffset);
    		sz = (int)MathHelper.floor_double(mc.thePlayer.posZ);
    		if (extendedMouseOver != null) {
    			sx = extendedMouseOver.blockX;
    			sy = extendedMouseOver.blockY;
    			sz = extendedMouseOver.blockZ;
    		}
    	} else {
    		ex = (int)MathHelper.floor_double(mc.thePlayer.posX);
    		ey = (int)(mc.thePlayer.posY - mc.thePlayer.yOffset);
    		ez = (int)MathHelper.floor_double(mc.thePlayer.posZ);
    		if (extendedMouseOver != null) {
	    		ex = extendedMouseOver.blockX;
				ey = extendedMouseOver.blockY;
				ez = extendedMouseOver.blockZ;
    		}
			
    		//REWIRE TO SEND ACTION TO SERVER AS WELL
			clipboardData.recalculateLevelSize(sx, sy, sz, ex, ey, ez, true);
			clipboardData.scanLevelToData();
			FMLClientHandler.instance().getClient().thePlayer.sendQueue.addToSendQueue(BuildPacketHandler.getBuildCommandPacket(clipboardData, 0, -1));
			
    		copyState = EnumCopyState.NORMAL;
    	}
    	
    	System.out.println(copyState);
    }
    
    public void eventBuild() {
    	Minecraft mc = FMLClientHandler.instance().getClient();
    	if (buildState == EnumBuildState.NORMAL) {
    		buildState = EnumBuildState.PLACE;
    	} else if (buildState == EnumBuildState.PLACE) {
    		buildState = EnumBuildState.NORMAL;
    		
    		//REWIRE TO SEND ACTION TO SERVER INSTEAD
    		
    		FMLClientHandler.instance().getClient().thePlayer.sendQueue.addToSendQueue(BuildPacketHandler.getBuildCommandPacket(clipboardData, 1, direction));
    		
    		
    	}
    }
}
