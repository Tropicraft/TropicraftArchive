package tropicraft;

import java.util.EnumSet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import tropicraft.ai.WorldDirector;
import tropicraft.entities.placeable.EntityChair;
import tropicraft.fishing.FishingManager;
import tropicraft.questsystem.PlayerQuestManager;
import tropicraft.world.TropicraftWorldUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandlerServer implements ITickHandler
{

	public static Tropicraft mod;
	public static World lastWorld = null;

	public static WorldDirector wd;
	public static PlayerQuestManager playerQuestMan;



	public TickHandlerServer(Tropicraft pMod) {
		mod = pMod;

	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

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
	public String getLabel() { return null; }


	public void onTickInGame()
	{
		boolean reInitQuests = false;

		if (lastWorld != DimensionManager.getWorld(0)) {
			lastWorld = DimensionManager.getWorld(0);
			reInitQuests = true;
		}

		if (playerQuestMan == null || reInitQuests) {
			World tropics = DimensionManager.getWorld(TropicraftWorldUtils.TROPICS_DIMENSION_ID);
			if (tropics != null) playerQuestMan = new PlayerQuestManager(tropics);
		} else {
			playerQuestMan.tick();
		}

		try{
			FishingManager.onServerTick();
		}catch(Exception e){
			e.printStackTrace();
		}

		if (wd == null) wd = new WorldDirector();
		wd.onTick();

		World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

		if (world != null && world instanceof WorldServer)
		{
			//System.out.println("STARTING TROPI PREGEN");
			for (int ii = 0; ii < world.playerEntities.size(); ii++)
			{
				Entity entity1 = (Entity)world.playerEntities.get(ii);

				if (entity1 instanceof EntityPlayerMP)
				{
					if (((EntityPlayerMP)entity1).isPotionActive(Potion.confusion) && isSunset(world) && entity1.ridingEntity instanceof EntityChair)
					{
						entity1.ridingEntity = null;
						TropicraftWorldUtils.teleportPlayer((EntityPlayerMP)entity1);
					}
				}
			}
		}

	}

	private boolean isSunset(World world)
	{
		long timeDay = world.getWorldTime() % 24000;
		return timeDay > 12200 && timeDay < 14000;
		//return !world.isDaytime();
		//THIS IS ONLY RELATIVE TO THE FIRST DAY THE GAME IS PLAYED ASDFASDFASDFASDFASDFSDFA     return world.getWorldTime() > 12200 && world.getWorldTime() < 14000;
	}
}
