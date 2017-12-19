package net.tropicraft.events;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.tropicraft.ServerTickHandler;
import net.tropicraft.mods.TropicraftMod;

public class TropicraftEventHooks {

	@ForgeSubscribe
	public void tropicsWaterBucketDrop(FillBucketEvent fbe) {
		World world = fbe.world;

		if (!world.isRemote) {

			Block block = Block.blocksList[world.getBlockId(fbe.target.blockX, fbe.target.blockY, fbe.target.blockZ)];

			if (block.blockID == TropicraftMod.waterStillTropics.blockID) {
				fbe.result = new ItemStack(TropicraftMod.bucketTropicsWater.shiftedIndex, 1, 0);
				world.setBlockWithNotify(fbe.target.blockX, fbe.target.blockY, fbe.target.blockZ, 0);
			}

			if (fbe.result != null && fbe.result.getItem().shiftedIndex == TropicraftMod.bucketTropicsWater.shiftedIndex) {
				fbe.setResult(Result.ALLOW);
			}
		}
	}
	
	@ForgeSubscribe
	public void teleportPlayerWhenSleepInBed(PlayerSleepInBedEvent psibe) {
		if (psibe.entity.worldObj.provider.dimensionId == TropicraftMod.tropicsDimensionID) {
			System.out.println(psibe.entity.worldObj.provider.dimensionId);
			if (psibe.entityPlayer.worldObj.getBlockLightValue(psibe.x, psibe.y, psibe.z) < 7)
			TropicraftMod.teleportPlayerToTropics((EntityPlayerMP) psibe.entityPlayer);
		} else
			psibe.setResult(Result.ALLOW);
	}

	@ForgeSubscribe
	public void deathEvent(LivingDeathEvent event) {
		if (ServerTickHandler.playerQuestMan != null) ServerTickHandler.playerQuestMan.onEvent(event);
	}
	
	@ForgeSubscribe
	public void pickupEvent(EntityItemPickupEvent event) {
		if (ServerTickHandler.playerQuestMan != null) ServerTickHandler.playerQuestMan.onEvent(event);
	}

}
