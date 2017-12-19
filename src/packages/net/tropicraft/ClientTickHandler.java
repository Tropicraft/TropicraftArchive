package net.tropicraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.tropicraft.items.ITropicraftArmor;
import net.tropicraft.questsystem.PlayerQuests;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.EnumSet;
import java.util.List;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler
{

	public static PlayerQuests playerQuests;

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
			} else {
				onTickInGame();
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() { return null; }


	public void onRenderTick()
	{
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;

		if (playerQuests == null) {
			if (FMLClientHandler.instance().getClient().thePlayer != null) {
				playerQuests = new PlayerQuests(null, FMLClientHandler.instance().getClient().thePlayer);
			}
		} else {
			//playerQuests.renderQuestOverlay();
		}

		
		
		/*	if (player != null && (player.inventory.armorInventory[0].getItem() instanceof ITropicraftArmor
				|| player.inventory.armorInventory[1].getItem() instanceof ITropicraftArmor
				|| player.inventory.armorInventory[2].getItem() instanceof ITropicraftArmor
				|| player.inventory.armorInventory[3].getItem() instanceof ITropicraftArmor)) {
			Iterator<ITropicraftArmor> i = TropicraftMod.armorList.iterator();

			while (i.hasNext()) {
				i.next().render(player);
			}
		}
		 */


		//System.out.println("onRenderTick");
		//TODO: Your Code Here
	}

	public void onTickInGUI(GuiScreen guiscreen)
	{
		onTickInGame();
		//System.out.println("onTickInGUI");
		//TODO: Your Code Here
	}

	Field curPlayingStr = null;

	public void onTickInGame()
	{

		World world = FMLClientHandler.instance().getClient().theWorld;
		if (world != null && !FMLClientHandler.instance().getClient().isGamePaused) {
			EntityPlayer playerCl = FMLClientHandler.instance().getClient().thePlayer;
			
			if (playerCl != null) {
				
				List players = playerCl.worldObj.getEntitiesWithinAABB(EntityPlayer.class, playerCl.boundingBox.expand(96, 96, 96));
				
				for (int j = 0; j < players.size(); j++) {
					
					EntityPlayer player = (EntityPlayer)players.get(j);
				
					for (int i = 0; i < player.inventory.armorInventory.length; i++) {
						if (player.inventory.armorInventory[i] != null) {
							if (player.inventory.armorInventory[i].getItem() instanceof ITropicraftArmor) {
								((ITropicraftArmor)player.inventory.armorInventory[i].getItem()).onArmorUpdate(player);
							}
						}
					}
				}
			}
		}
	}

	static void getField(Field field, Object newValue) throws Exception {
		field.setAccessible(true);

		// remove final modifier from field
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);
	}
}
