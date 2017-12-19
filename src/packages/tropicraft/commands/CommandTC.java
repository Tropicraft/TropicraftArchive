package tropicraft.commands;

import java.io.File;
import java.util.Random;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.ai.WorldDirector;
import tropicraft.economy.ItemEntry;
import tropicraft.economy.ItemValues;
import tropicraft.enchanting.EnchantmentManager;
import tropicraft.entities.hostile.land.tribes.koa.EntityKoaMemberNew;
import tropicraft.items.TropicraftItems;
import tropicraft.packets.TropicraftPacketHandler;
import tropicraft.questsystem.PlayerQuestManager;
import tropicraft.volleyball.Court;
import tropicraft.volleyball.CourtHelper;
import tropicraft.volleyball.TileEntityCourtMaster;
import tropicraft.volleyball.Volleyball;
import tropicraft.world.schematic.CustomGenKoaHut;
import tropicraft.world.structures.KoaVillageGenerator;
import CoroAI.entity.EnumJob;
import build.BuildServerTicks;
import build.world.BuildJob;

public class CommandTC extends CommandBase {

	@Override
	public String getCommandName() {
		return "tc";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		
		try {
			if(var1 instanceof EntityPlayerMP)
			{
				EntityPlayer player = getCommandSenderAsPlayer(var1);
	
				String prefix = "TropicraftMod.";
				String mobToSpawn = "Koa Man";
				
				if (var2.length > 0) {
					if (var2[0].equalsIgnoreCase("spawn")) {
						if (var2[1].equalsIgnoreCase("koa")) {
							mobToSpawn = "Koa Man";
						} else if (var2[1].equalsIgnoreCase("ashen")) {
							mobToSpawn = "Ashen Hunter";
						} else {
							mobToSpawn = var2[1];
						}
						
						int count = 1;
						
						if (var2.length > 2) {
							count = Integer.valueOf(var2[2]);
						}
	
						for (int i = 0; i < count; i++) {
							Entity ent = EntityList.createEntityByName(prefix + mobToSpawn, player.worldObj);
							
							if (ent != null) {
								double dist = 4D;
								
								double finalX = player.posX - (Math.sin(player.rotationYaw * 0.01745329F) * dist);
								double finalZ = player.posZ + (Math.cos(player.rotationYaw * 0.01745329F) * dist);
								
								double finalY = player.posY;//player.worldObj.getHeightValue((int)finalX, (int)finalZ) + 0.5;
								
								ent.setPosition(finalX, finalY, finalZ);
								//ent.setPosition(randX, player.worldObj.getHeightValue(randX, randZ), randZ);
								
								if (ent instanceof EntityKoaMemberNew) {
									EnumJob job = EnumJob.HUNTER;
									if (var2.length > 3) {
										if (var2[3].contains("trad")) {
											job = EnumJob.TRADING;
										} else if (var2[3].contains("fish")) {
											job = EnumJob.FISHERMAN;
										} else if (var2[3].equals("")) {
											
										}
									}
					            	//((EntityKoaMemberNew)entity).initJobAndStates(((EntityKoaMemberNew)entity).getOccupation(((EntityKoaMemberNew)entity).gender));
					            	((EntityKoaMemberNew)ent).initJobAndStates(job);
					            	((EntityKoaMemberNew)ent).homeX = (int)finalX;
					            	((EntityKoaMemberNew)ent).homeY = (int)finalY;
					            	((EntityKoaMemberNew)ent).homeZ = (int)finalZ;
					            }
								
								//if (ent instanceof ICoroAI) ((ICoroAI) ent).getAIAgent().spawnedOrNBTReloadedInit();
								
								if (ent instanceof EntityLiving) ((EntityLiving) ent).initCreature();
								
								player.worldObj.spawnEntityInWorld(ent);
								System.out.println("Spawned: " + mobToSpawn);
							} else {
								System.out.println("failed to spawn");
								break;
							}
						}
					} else if (var2[0].equalsIgnoreCase("quest")) {
						if (var2[1].equalsIgnoreCase("add")) {
							int questID = 0;
							try {
								questID = Integer.valueOf(var2[2]);
							} catch (Exception ex) {}
							PlayerQuestManager.i.giveQuest(questID, player.username, false);
						} else if (var2[1].equalsIgnoreCase("clear")) {
							PlayerQuestManager.i.clearQuests(player.username, false, true);
						}
					} else if (var2[0].equalsIgnoreCase("volleyball")) {
						if (var2[1].equalsIgnoreCase("new")) {
							
							World world = player.worldObj;
							
							int size = 8;
							try {
								size = Integer.valueOf(var2[2]);
							} catch (Exception ex) {}
							int x = (int)player.posX;
							int y = (int)player.posY;
							int z = (int)player.posZ;
							
							ChunkCoordinates[] coords = new ChunkCoordinates[] { new ChunkCoordinates(x, y, z), new ChunkCoordinates(x+size, y, z), new ChunkCoordinates(x+size, y, z+size), new ChunkCoordinates(x, y, z+size) };
							
							//ChunkCoordinates[] coords = CourtHelper.checkCourtForValidity(world, x, y, z);
					    	
					    	if (coords != null && coords.length == 4) {
					    		Court court = CourtHelper.buildCourt(world, coords);
					    		
					    		if (court != null) {
					    			int xCoord = (int) (court.minX + MathHelper.floor_double(court.xLength / 2));
					    			int yCoord = (int) court.y;
					    			int zCoord = (int) (court.minZ + MathHelper.floor_double(court.zLength / 2));
					    			
					    			world.setBlock(xCoord, yCoord, zCoord, Volleyball.courtMaster.blockID);
					    			((TileEntityCourtMaster)world.getBlockTileEntity(xCoord, yCoord, zCoord)).onFirstPlacement(court);
					    		}
					    	}
						}
					} else if (var2[0].equalsIgnoreCase("corotest")) {
						ItemStack item = new ItemStack(TropicraftItems.scaleChestplate, 1, 80);
						ItemEntry ie = ItemValues.getItemEntry(item);
						if (ie != null) {
							System.out.println(ie.getTotalValue(item));
						}
					} else if (var2[0].equalsIgnoreCase("page")) {
						if (var2[1].equalsIgnoreCase("add")) {
							int pageID = 0;
							try {
								pageID = Integer.valueOf(var2[2]);
							} catch (Exception ex) {}
							
							NBTTagCompound pNBT = WorldDirector.getPlayerNBT(player.username);
							NBTTagCompound nbt = pNBT.getCompoundTag("pagesData");
							if (nbt == null) nbt = new NBTTagCompound();
							nbt.setBoolean("p" + pageID, true);
							pNBT.setCompoundTag("pagesData", nbt);
							TropicraftPacketHandler.syncPagesData(player.username);
						}
					} else if (var2[0].equalsIgnoreCase("enchant")) {
						EntityPlayer player4 = getCommandSenderAsPlayer(var1);
						
						try {
							player4.inventory.getCurrentItem().addEnchantment(EnchantmentManager.fruitNinja, Integer.valueOf(var2[1]));
						} catch (Exception e) {
						}
					} else if (var2[0].equalsIgnoreCase("gen")) {
						if (var2[1].equalsIgnoreCase("village")) {
							int dir = -1;
							try {
								dir = Integer.valueOf(var2[2]);
							} catch (Exception ex) {}
							if (dir != -1) {
								new KoaVillageGenerator(player.worldObj).generate(player.worldObj, new Random(), (int)player.posX, player.worldObj.getHeightValue((int)player.posX, (int)player.posZ), (int)player.posZ, dir);
							} else {
								new KoaVillageGenerator(player.worldObj).generate(player.worldObj, new Random(), (int)player.posX, player.worldObj.getHeightValue((int)player.posX, (int)player.posZ), (int)player.posZ);
							}
						} else if (var2[1].contains("schem")) {
							int buildID = -99;
							String str = "Lobby2";
							try {
								str = var2[2];
							} catch (Exception ex) {}
					    	BuildJob bj = new BuildJob(-99, (int)Math.floor(player.posX), 1+player.worldObj.getHeightValue((int)Math.floor(player.posX), (int)Math.floor(player.posZ)), (int)Math.floor(player.posZ), WorldDirector.getSaveFolderPath() + "TCSchematics" + File.separator + str);
							bj.build.dim = player.worldObj.provider.dimensionId;
							bj.useFirstPass = false; //skip air setting pass
							//bj.useRotationBuild = true;
							bj.build.newFormat = true;
							//bj.customGenCallback = new CustomGenKoaHut();
							
							//schematic orientation expectations:
							//minX,minZ -> maxX,minZ is the front face
							//this is what would be adjusted for schematics that do not use the above rule 
							int offset = 0;
							
							int l = MathHelper.floor_double((double)((-player.rotationYaw-45) * 4.0F / 360.0F)/* + 0.5D*/) & 3;
							bj.setDirection(l);
							bj.setDirection(0);
							//bj.rotation = (l * 90) + 180 + offset;
							
							//System.out.println(bj.rotation);
							
							BuildServerTicks.buildMan.addBuild(bj);
						}
						
					}
					
				}
				
			}
		} catch (Exception ex) {
			System.out.println("Exception handling Tropicraft command");
			ex.printStackTrace();
		}
		
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
    }

}
