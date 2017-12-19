package net.tropicraft;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.tropicraft.commands.CommandTeleportTropics;
import net.tropicraft.entities.EntityChair;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.questsystem.PlayerQuestManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ServerTickHandler implements ITickHandler
{

	private HashMap isUsingSnorkelEffect = new HashMap();
	private HashMap snorkelHelper = new HashMap();
	private HashMap lastTickWasInWater = new HashMap();
	public boolean needPreGen = true;
	public World lastWorld = null;
	public static PlayerQuestManager playerQuestMan;

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
		if (FMLCommonHandler.instance() == null || FMLCommonHandler.instance().getMinecraftServerInstance() == null)
		{
			return;
		}

		World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

		ServerConfigurationManager scm = MinecraftServer.getServerConfigurationManager(MinecraftServer.getServer());

		for (int ii = 0; ii < scm.playerEntityList.size(); ii++) {
			Entity entity1 = (Entity)scm.playerEntityList.get(ii);

			EntityPlayer player = (EntityPlayer) entity1;
			
			int length = TropicraftMod.armorList.size();
			
			for (int i = 0; i < length; i++) {
				TropicraftMod.armorList.get(i).onArmorUpdate(player);
			}
		}
		/*      
        ServerConfigurationManager scm = MinecraftServer.getServerConfigurationManager(MinecraftServer.getServer());

        checkListValidity(isUsingSnorkelEffect, scm.playerEntityList);
        checkListValidity(snorkelHelper, scm.playerEntityList);
        checkListValidity(lastTickWasInWater, scm.playerEntityList);

        for (int ii = 0; ii < scm.playerEntityList.size() ; ii++)
        {
            Entity entity1 = (Entity)scm.playerEntityList.get(ii);

            EntityPlayer player = (EntityPlayer) entity1;
    		ItemStack helm = player.inventory.armorInventory[3];
    		ItemStack boots = player.inventory.armorInventory[0];

    		boolean helper = false;

    		if(!lastTickWasInWater.containsKey(player) && player.isInsideOfMaterial(Material.water) && helm != null && helm.itemID == TropicraftMod.snorkel.shiftedIndex)
    		{
    			setBool(isUsingSnorkelEffect, player, true);
    			setBool(snorkelHelper, player, true);
    			setBool(lastTickWasInWater, player, true);
    		}
    		else if(!lastTickWasInWater.containsKey(player) && player.isInsideOfMaterial(Material.water))
    		{
    			setBool(lastTickWasInWater, player, true);
    		}
    		else if(!lastTickWasInWater.containsKey(player))
    		{
    			setBool(lastTickWasInWater, player, false);
    		}

    		if(player.isEntityAlive() && player.isInsideOfMaterial(Material.water))
    		{
        		helper = true;
        		if(!getOrCreateBool(isUsingSnorkelEffect, player, false) && helm != null && helm.itemID == TropicraftMod.snorkel.shiftedIndex)
        		{
        			setBool(isUsingSnorkelEffect, player, true);
        		}
    		}
    		else
    		{
    			helper = false;
    			setBool(isUsingSnorkelEffect, player, false);
    		}

    		if(!getOrCreateBool(snorkelHelper, player, false) && getOrCreateBool(isUsingSnorkelEffect, player, false))
    		{
    			if(!getOrCreateBool(lastTickWasInWater, player, false))
    			{
    				player.setAir(1200);
    			}
    			setBool(snorkelHelper, player, true);
    		}
    		else if(!getOrCreateBool(isUsingSnorkelEffect, player, false) && getOrCreateBool(snorkelHelper, player, false))
    		{
    			setBool(snorkelHelper, player, false);
    		}

    		if(!getOrCreateBool(lastTickWasInWater, player, true) && getOrCreateBool(isUsingSnorkelEffect, player, false) && player.isEntityAlive() && player.isInsideOfMaterial(Material.water) && ((helm != null && helm.itemID != TropicraftMod.snorkel.shiftedIndex) || helm == null))
    		{
    			if(player.getAir() - 900 < 0)
    			{
        			player.setAir(0);
    			}
    			else
    			{
    				player.setAir(player.getAir() - 900);
    			}
    			setBool(isUsingSnorkelEffect, player, false);
    		}

    		if(!getOrCreateBool(snorkelHelper, player, false))
    		{
    			setBool(lastTickWasInWater, player, helper);
    		}

    		//Now that that shit's over
    		//FLIPPERS
    		if (boots != null && boots.itemID == TropicraftMod.flippers.shiftedIndex) {
    			if (player.isInsideOfMaterial(Material.water)) {
    			} else {
    			}
    		}
        }
		 */
		if (lastWorld != world && world != null) {

			lastWorld = world;
			((ServerCommandManager)FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager()).registerCommand(new CommandTeleportTropics());

			World tropics = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(TropicraftMod.tropicsDimensionID);
			if (tropics != null) playerQuestMan = new PlayerQuestManager(tropics);
		}

		if (playerQuestMan == null) {
			World tropics = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(TropicraftMod.tropicsDimensionID);
			if (tropics != null) playerQuestMan = new PlayerQuestManager(tropics);
		} else {
			playerQuestMan.tick();
		}

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
						TropicraftMod.teleportPlayerToTropics((EntityPlayerMP)entity1);
					}

					/*        if (world.getBlockId((int) entity1.posX, (int) entity1.posY, (int) entity1.posZ) == TropicraftMod.tropicraftPortal.blockID && entity1.isInWater())
                    {
                    //	System.out.println(((EntityPlayerMP)entity1).timeUntilPortal);
                        entity1.setAir(300);
                        ((EntityPlayerMP)entity1).timeUntilPortal++;

                        //System.out.println(((EntityPlayerMP)entity1).timeInPortal);
                        if (((EntityPlayerMP)entity1).timeUntilPortal > 250)
                        {
                            ((EntityPlayerMP)entity1).timeUntilPortal = 0;
                            TropicraftMod.teleportPlayerToTropics((EntityPlayerMP)entity1);
                        }
                    }
                    else
                    {
                        if (((EntityPlayerMP)entity1).timeUntilPortal > 25)   //nether portal interferance prevention
                        {
                            ((EntityPlayerMP)entity1).timeUntilPortal = 0;
                        }
                    }			*/




				}
			}

			/*if (this.drunkCounter > 0 && (ingameHours12 == 17 || ingameHours12 == 18) && mc.thePlayer.ridingEntity instanceof EntityChair) {
                ModLoader.getMinecraftInstance().usePortal(mod_TropicraftDimension.tropicDimensionID, new TeleporterTropics());
            }*/
		}

		/*     world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(TropicraftMod.tropicsDimensionID);

        if (world != null && world instanceof WorldServer)
        {
            if (false && needPreGen)
            {
                //System.out.println("STARTING TROPI PREGEN");
                TropicraftMod.instance.initialWorldChunkLoad((WorldServer)world);
                needPreGen = false;
            }
/*
            for (int ii = 0; ii < world.playerEntities.size(); ii++)
            {
                Entity entity1 = (Entity)world.playerEntities.get(ii);

                if (entity1 instanceof EntityPlayerMP)
                {
                    int hmm = world.getBlockId((int) entity1.posX, (int) entity1.posY, (int) entity1.posZ);

                    //System.out.println(hmm);
                    if (world.getBlockId((int) entity1.posX, (int) entity1.posY, (int) entity1.posZ) == TropicraftMod.tropicraftPortal.blockID && world.getBlockId((int) entity1.posX, (int) entity1.posY-1, (int) entity1.posZ) != TropicraftMod.tropicraftPortal.blockID)
                    {
                        entity1.setAir(300);
                        ((EntityPlayerMP)entity1).timeUntilPortal++;
                        //System.out.println(((EntityPlayerMP)entity1).timeInPortal);

                        if (((EntityPlayerMP)entity1).timeUntilPortal > 80)
                        {
                            ((EntityPlayerMP)entity1).timeUntilPortal = 0;
                            TropicraftMod.teleportPlayerToTropics((EntityPlayerMP)entity1);
                        }
                    }
                    else
                    {
                        if (((EntityPlayerMP)entity1).timeUntilPortal > 25)   //nether portal interferance prevention
                        {
                            ((EntityPlayerMP)entity1).timeUntilPortal = 0;
                        }
                    }
                } 
            }

            //System.out.println("FINISHED TROPI PREGEN");
            //WeatherMod.weather(Side.SERVER);
            //sWMan.tick(Side.SERVER, world);
            //sSMan.tick(Side.SERVER);
        } */

		//System.out.println("onTickInGame");
		//TODO: Your Code Here
	}

	private void checkListValidity(HashMap<EntityPlayer, Boolean> map, List playerList)
	{
		/* 	for(EntityPlayer player : temp)
    	{
    		if(!playerList.contains(player))
    		{
    			map.remove(player);
    		}
    	} */

		//this fixes a ConcurrentModificationException caused by the for-each loop seen above
		for (Iterator<EntityPlayer> it = map.keySet().iterator(); it.hasNext();) {
			EntityPlayer p = (EntityPlayer)it.next();

			if (!playerList.contains(p)) {
				it.remove();
			}
		}
	}

	private boolean getOrCreateBool(HashMap<EntityPlayer, Boolean> map, EntityPlayer key, boolean defaultVal)
	{
		if(!map.containsKey(key))
		{
			map.put(key, defaultVal);
		}
		return map.get(key);
	}

	private void setBool(HashMap<EntityPlayer, Boolean> map, EntityPlayer key, boolean val)
	{
		map.put(key, val);
	}

	private boolean isSunset(World world)
	{
		return !world.isDaytime();
		//THIS IS ONLY RELATIVE TO THE FIRST DAY THE GAME IS PLAYED ASDFASDFASDFASDFASDFSDFA 	return world.getWorldTime() > 12200 && world.getWorldTime() < 14000;
	}

	/*if (world.isRemote)
        						world.spawnParticle(
        								"bubble",
        								player.posX - f1,
        								player.posY + .35F,
        								player.posZ + f2,
        								player.motionX,
        								player.motionY
        										- (double) ((new Random()).nextFloat() * 0.2F),
        								player.motionZ);*/


}

