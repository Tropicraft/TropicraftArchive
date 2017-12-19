package modconfig.forge;

import java.util.ArrayList;
import java.util.List;

import modconfig.ConfigMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class CommandModConfig extends CommandBase {

	@Override
	public String getCommandName() {
		return "config";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "";
	}
	
	@Override
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
		List<String> list = new ArrayList<String>(ConfigMod.configLookup.get(getCommandName()).valsBoolean.keySet());
		list.addAll(ConfigMod.configLookup.get(getCommandName()).valsInteger.keySet());
		list.addAll(ConfigMod.configLookup.get(getCommandName()).valsString.keySet());
        return list;
    }

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		
		try {
			if(var1 instanceof EntityPlayerMP)
			{
				int cmd = 0;
				int modid = 1;
				int field = 2;
				int vall = 3;
				EntityPlayer player = getCommandSenderAsPlayer(var1);
				if (var2.length > 0) {
					if (var2[cmd].equalsIgnoreCase("get")) {
						if (var2.length > 2) {
							Object obj = ConfigMod.getField(var2[modid], var2[field]);
							if (obj != null) {
								var1.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(var2[field] + " = " + obj));
							} else {
								var1.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("failed to get " + var2[field]));
							}
						} else {
							var1.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("get requires 3 parameters"));
						}
					} else if (var2[cmd].equalsIgnoreCase("set")) {
						if (var2.length > 2) {
							
							String val = "";
							for (int i = vall; i < var2.length; i++) val += var2[i] + (i != var2.length-1 ? " " : "");
							if (ConfigMod.updateField(var2[modid], var2[field], val)) {
								var1.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("set " + var2[field] + " to " + val));
								
								List blah = new ArrayList();
								
								blah.add((String)var2[field]);
								blah.add((String)val);
								
								MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(ConfigMod.getModConfigPacket(var2[modid]));
							} else {
								var1.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("failed to set " + var2[field]));
							}
						} else {
							var1.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("set requires 3+ parameters"));
						}
					} else if (var2[cmd].equalsIgnoreCase("update")) {
						MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(ConfigMod.getModConfigPacket(var2[modid]));
					} else if (var2[cmd].equalsIgnoreCase("menu") || var2[cmd].equalsIgnoreCase("gui")) {
						((EntityPlayerMP)player).playerNetServerHandler.sendPacketToPlayer(ConfigMod.getModConfigPacketMenu());
					}
					
				} else {
					((EntityPlayerMP)player).playerNetServerHandler.sendPacketToPlayer(ConfigMod.getModConfigPacketMenu());
				}
			}
		} catch (Exception ex) {
			System.out.println("Exception handling Config Mod command");
			ex.printStackTrace();
		}
		
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
    }

}
