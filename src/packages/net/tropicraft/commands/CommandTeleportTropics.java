package net.tropicraft.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.tropicraft.mods.TropicraftMod;

public class CommandTeleportTropics extends CommandBase {

	@Override
	public String getCommandName() {
		return "tropics";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		EntityPlayer player = getCommandSenderAsPlayer(var1);
		if(player instanceof EntityPlayerMP)
		{
			TropicraftMod.teleportPlayerToTropics((EntityPlayerMP)player);
		}
		else
		{
			System.out.println("Not EntityPlayerMP");
		}
	}

}
