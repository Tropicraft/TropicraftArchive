package tropicraft;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class QuestEventHooksTropicraft {

	@ForgeSubscribe
	public void deathEvent(LivingDeathEvent event) {
		if (TickHandlerServer.playerQuestMan != null) TickHandlerServer.playerQuestMan.onEvent(event);
	}
	
	@ForgeSubscribe
	public void pickupEvent(EntityItemPickupEvent event) {
		if (TickHandlerServer.playerQuestMan != null) TickHandlerServer.playerQuestMan.onEvent(event);
	}

}
