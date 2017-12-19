package tropicraft.commands;

import tropicraft.world.TropicraftWorldUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandTeleportTropics extends CommandBase {

	@Override
	public String getCommandName() {
		return "tropics";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		EntityPlayer player = getCommandSenderAsPlayer(var1);
		if(player instanceof EntityPlayerMP)
		{
			TropicraftWorldUtils.teleportPlayer((EntityPlayerMP)player);
		}
		else
		{
			System.out.println("Not EntityPlayerMP");
		}
	}

}
