package build;

import java.util.EnumSet;

import build.world.BuildManager;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class BuildServerTicks implements ITickHandler
{
	
	public static BuildManager buildMan = new BuildManager();;
	
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.SERVER)))
        {
            onTickInGame();
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel()
    {
        return null;
    }

    public void onTickInGame()
    {
    	buildMan.updateTick();
    }
}
