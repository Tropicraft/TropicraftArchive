package net.tropicraft.questsystem;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.tropicraft.questsystem.quests.ActiveQuest;
import net.tropicraft.questsystem.quests.ItemQuest;
import net.tropicraft.questsystem.quests.KillEntityQuest;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerQuests {

	public PlayerQuestManager playerQuestMan;
	public String playerName = "Player";
	public EntityPlayer playerInstance;
	
	//public ArrayList<ActiveQuest> questInstances;
	public ArrayList<ActiveQuest> activeQuests;
	
	public PlayerQuests(PlayerQuestManager parMan, EntityPlayer player) {
		playerQuestMan = parMan;
		playerInstance = player;
		playerName = player.username;
		activeQuests = new ArrayList();
		
		//TEMP
		giveQuest(0);
		
		readQuestsFile();
	}
	
	
	
	public void tick() {
		for (int i = 0; i < activeQuests.size(); i++) {
			activeQuests.get(i).tick();
			if (activeQuests.get(i).isComplete()) onComplete(i, true);
		}
	}
	
	public void saveAndSync() {
		boolean syncAllPlayers = true;
		if (syncAllPlayers) {
			playerQuestMan.saveAndSync();
		} else {
			saveAndSyncImpl();
		}
	}
	
	public void saveAndSyncImpl() {
		sync();
		writeQuestsFile();
	}
	
	public void sync() {
		if (MinecraftServer.getServer() != null)
        {
			
			NBTTagCompound nbt = new NBTTagCompound();
			
			save(nbt);

            try
            {
            	byte[] data = CompressedStreamTools.compress(nbt);
            	
            	
            	//System.out.println("packet byte count: " + data.length);
            	
            	ByteArrayOutputStream bos = new ByteArrayOutputStream((Byte.SIZE * data.length) + Short.SIZE);
                DataOutputStream dos = new DataOutputStream(bos);

                writeNBTTagCompound(nbt, dos, data);
                
                //dos.write(data);

	            Packet250CustomPayload pkt = new Packet250CustomPayload();
	            pkt.channel = "QuestData";
	            pkt.data = bos.toByteArray();
	            pkt.length = bos.size();
	            MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(playerName).playerNetServerHandler.sendPacketToPlayer(pkt);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
	}
	
	protected static void writeNBTTagCompound(NBTTagCompound par0NBTTagCompound, DataOutputStream par1DataOutputStream, byte[] data) throws IOException
    {
        if (par0NBTTagCompound == null)
        {
            par1DataOutputStream.writeShort(-1);
        }
        else
        {
            //byte[] var2 = CompressedStreamTools.compress(par0NBTTagCompound);
            par1DataOutputStream.writeShort((short)data.length);
            par1DataOutputStream.write(data);
        }
    }
	
	public void onComplete(int questIndex, boolean completeForAllInWorld) {
		if (completeForAllInWorld) {
			playerQuestMan.markQuestCompleteForAll(activeQuests.get(questIndex));
		} else {
			removeQuest(activeQuests.get(questIndex));
		}
	}
	
	public void giveQuest(int questID) {
		boolean exists = false;
		for (int i = 0; i < activeQuests.size(); i++) {
			if (activeQuests.get(i).questID == questID) {
				exists = true;
			}
		}
		
		if (!exists) {
			activeQuests.add(QuestCreator.getNewQuestInstance(this, questID));
		}
	}
	
	public ActiveQuest getQuestByID(int parID) {
		//boolean exists = false;
		for (int i = 0; i < activeQuests.size(); i++) {
			if (activeQuests.get(i).questID == parID) {
				return activeQuests.get(i);
			}
		}
		
		return null;
	}
	
	public void removeQuest(ActiveQuest quest) {
		for (int i = 0; i < activeQuests.size(); i++) {
			if (activeQuests.get(i).questID == quest.questID) {
				activeQuests.remove(i);
			}
		}
	}
	
	public void onEvent(Event event) {
		for (int i = 0; i < activeQuests.size(); i++) {
			if (event instanceof LivingDeathEvent) {
				if (activeQuests.get(i) instanceof KillEntityQuest) {
					((KillEntityQuest)activeQuests.get(i)).deathEvent((LivingDeathEvent)event);
				}
			} else if (event instanceof EntityItemPickupEvent) {
				if (activeQuests.get(i) instanceof ItemQuest) {
					((ItemQuest)activeQuests.get(i)).pickupEvent((EntityItemPickupEvent)event);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void renderQuestOverlay() {
		//System.out.println("quests: " + activeQuests.size());
		
		int startX = 10;
		int startY = 10;
		
		Minecraft mc = FMLClientHandler.instance().getClient();
		mc.fontRenderer.drawStringWithShadow("Quests:", startX, startY, 0xFFFFFF);
		
		startY += 10;
		
		for (int i = 0; i < activeQuests.size(); i++) {
			//System.out.println("client side active quest id: " + activeQuests.get(i).questID);
			
			String qStr = "";
			String qStr2 = "";
			
			if (activeQuests.get(i) instanceof KillEntityQuest) {
				qStr = "Kill some " + EntityList.classToStringMapping.get(((KillEntityQuest)activeQuests.get(i)).neededMob);
				qStr2 = "Killed: " + ((KillEntityQuest)activeQuests.get(i)).curKillCount + " / " + ((KillEntityQuest)activeQuests.get(i)).neededKillCount;
			}
			
			mc.fontRenderer.drawStringWithShadow(qStr, startX, startY + (i+1 * 10), 0xFFFFFF);
			mc.fontRenderer.drawStringWithShadow(qStr2, startX, startY + 10 + (i+1 * 10), 0xFFFFFF);
		}
	}
	
	public void syncFromServer(NBTTagCompound parNBT) {
		load(parNBT);
	}
	
	public void readQuestsFile() {
		FileInputStream fis = null;
		
    	try {
	    	fis = new FileInputStream(getSaveFolderPath() + playerName + ".dat");
	    	
	    	NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(fis);
	    	
			load(nbttagcompound);
			
			if (fis != null) {
    			fis.close();
    		}
			
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	} finally {
    		
    		
    	}
	}
	
	public void writeQuestsFile() {
		try {
			
			NBTTagCompound nbt = new NBTTagCompound();
			
			save(nbt);
			
			new File(getSaveFolderPath()).mkdirs();
				
			FileOutputStream fos = new FileOutputStream(getSaveFolderPath() + playerName + ".dat");
			
	    	CompressedStreamTools.writeCompressed(nbt, fos);
	    	
	    	fos.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static String getSaveFolderPath() {
    	if (MinecraftServer.getServer() == null || MinecraftServer.getServer().isSinglePlayer()) {
    		return getClientSidePath() + File.separator + "Tropicraft" + File.separator;
    	} else {
    		return new File(".").getAbsolutePath() + File.separator + "Tropicraft" + File.separator;
    	}
    }
    
    @SideOnly(Side.CLIENT)
	public static String getClientSidePath() {
		return FMLClientHandler.instance().getClient().getMinecraftDir().getPath();
	}
	
	public void load(NBTTagCompound parNBT) {
		
		//FIX!!! iterates over active quests, not quests to load from nbt!!!!
		
		for (int i = 0; i < QuestCreator.maxQuestCount; i++) {
			NBTTagCompound questNBT = parNBT.getCompoundTag("q" + i);
			if (!questNBT.hasNoTags()) {
				ActiveQuest quest = getQuestByID(i);
				if (quest == null) {
					quest = QuestCreator.getNewQuestInstance(this, i);
					activeQuests.add(quest);
				}
				quest.load(questNBT);
				//activeQuests.get(i).load(questNBT);
			}
		}
	}
	
	public void save(NBTTagCompound parNBT) {
		for (int i = 0; i < activeQuests.size(); i++) {
			NBTTagCompound questNBT = new NBTTagCompound();
			activeQuests.get(i).save(questNBT);
			parNBT.setTag("q" + activeQuests.get(i).questID, questNBT);
		}
	}
}
