package tropicraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.audio.SoundManager;
import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TropicraftSoundLoader {
	
	public List<String> records = new ArrayList<String>();
	
	@ForgeSubscribe
    public void onSound(SoundLoadEvent event) {
		
		//1.6.2 note
		//VERIFY ALL OF THESE SOUNDS, a decent amount need relocation on disk
		
		// Records
		registerRecord(event.manager, "Trade Winds");
		registerRecord(event.manager, "Eastern Isles");
		registerRecord(event.manager, "Low Tide");
		registerRecord(event.manager, "The Tribe");
		registerRecord(event.manager, "Buried Treasure");
		registerRecord(event.manager, "Summering");
		
		// Portal sounds
		for (int a = 1; a <= 4; a++) {
			//registerSound(event.manager, "ambient/tropicraft/portal" + a + ".ogg", "/resources/newsound/ambient/tropicraft/portal"+a+".ogg");
			registerSound(event.manager, Tropicraft.modID + ":ambient/tropicraft/portal"+a+".ogg");
		}
		
		// Mob sounds
		registerSound(event.manager, Tropicraft.modID + ":dartblow.ogg");
		registerSound(event.manager, Tropicraft.modID + ":darthit.ogg");
		registerSound(event.manager, Tropicraft.modID + ":frogspit.ogg");
		registerSound(event.manager, Tropicraft.modID + ":headattack.ogg");
		registerSound(event.manager, Tropicraft.modID + ":headdeath.ogg");
		registerSound(event.manager, Tropicraft.modID + ":headlaughing.ogg");
		registerSound(event.manager, Tropicraft.modID + ":headlaughing2.ogg");
		registerSound(event.manager, Tropicraft.modID + ":headmed.ogg");
		registerSound(event.manager, Tropicraft.modID + ":headpain.ogg");
		registerSound(event.manager, Tropicraft.modID + ":headshort.ogg");
		registerSound(event.manager, Tropicraft.modID + ":iggyattack.ogg");
		registerSound(event.manager, Tropicraft.modID + ":iggydeath.ogg");
		registerSound(event.manager, Tropicraft.modID + ":iggyliving.ogg");
		registerSound(event.manager, Tropicraft.modID + ":monkeyangry.ogg");
		registerSound(event.manager, Tropicraft.modID + ":monkeydrinking.ogg");
		registerSound(event.manager, Tropicraft.modID + ":monkeyhiccup.ogg");
		registerSound(event.manager, Tropicraft.modID + ":monkeyhurt.ogg");
		registerSound(event.manager, Tropicraft.modID + ":monkeyliving.ogg");
		registerSound(event.manager, Tropicraft.modID + ":ashenLaugh.ogg");
		registerSound(event.manager, Tropicraft.modID + ":mine.ogg");
		
		// Other sounds
		registerSound(event.manager, Tropicraft.modID + ":pageFlip.ogg");
		registerSound(event.manager, Tropicraft.modID + ":bongolow.wav");
		registerSound(event.manager, Tropicraft.modID + ":bongomedium.wav");
		registerSound(event.manager, Tropicraft.modID + ":bongohigh.wav");
    }
	
	private void registerSound(SoundManager manager, String path) {
        try {
        	manager.addSound(path);
        } catch (Exception ex) {
            System.out.println(String.format("Warning: unable to load sound file %s", path));
        }
    }
    
    private void registerStreaming(SoundManager manager, String path) {
        try {
            manager.soundPoolStreaming.addSound(path);
        } catch (Exception ex) {
            System.out.println(String.format("Warning: unable to load streaming file %s", path));
        }
    }
    
    //pass the record name without extension only, required for internal name indexing for fixing on play
    private void registerRecord(SoundManager manager, String name) {
        try {
            manager.soundPoolStreaming.addSound(Tropicraft.modID + ":" + name + ".ogg");
            records.add(name);
        } catch (Exception ex) {
            System.out.println(String.format("Warning: unable to load music file %s", name));
        }
    }
    
    @SideOnly(Side.CLIENT)
	@ForgeSubscribe
	public void onPlayStreaming(PlayStreamingEvent event) 
	{
    	System.out.println(event.name);
    	if (records.contains(event.name)) {
    		FMLClientHandler.instance().getClient().sndManager.playStreaming(Tropicraft.modID + ":" + event.name, (float) event.x + 0.5F, (float) event.y + 0.5F, (float) event.z + 0.5F);
    	}
		/*if (event.name == "bopdisc")
		{
			FMLClientHandler.instance().getClient().sndManager.playStreaming("biomesoplenty:bopdisc", (float) event.x + 0.5F, (float) event.y + 0.5F, (float) event.z + 0.5F);
		}
		else if (event.name == "bopdiscmud")
		{
			FMLClientHandler.instance().getClient().sndManager.playStreaming("biomesoplenty:bopdiscmud", (float) event.x + 0.5F, (float) event.y + 0.5F, (float) event.z + 0.5F);
		}*/
	}
}
