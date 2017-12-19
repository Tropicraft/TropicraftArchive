package tropicraft;

import java.io.FileNotFoundException;
import java.net.URL;

import net.minecraft.client.audio.SoundManager;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class TropicraftSoundLoader {
	
	@ForgeSubscribe
    public void onSound(SoundLoadEvent event) {
		// Records
		registerStreaming(event.manager, "Trade Winds.ogg", "/resources/streaming/Trade Winds.ogg");
		registerStreaming(event.manager, "Eastern Isles.ogg", "/resources/streaming/Eastern Isles.ogg");
		registerStreaming(event.manager, "Low Tide.ogg", "/resources/streaming/Low Tide.ogg");
		registerStreaming(event.manager, "The Tribe.ogg", "/resources/streaming/The Tribe.ogg");
		registerMusicDisk(event.manager, "Buried Treasure.ogg");
		registerStreaming(event.manager, "Summering.ogg", "/resources/streaming/Summering.ogg");
		
		// Portal sounds
		for (int a = 1; a <= 4; a++) {
			registerSound(event.manager, "ambient/tropicraft/portal" + a + ".ogg", "/resources/newsound/ambient/tropicraft/portal"+a+".ogg");
		}
		
		// Mob sounds
		registerSound(event.manager, "dartblow.ogg", "/resources/newsound/dartblow.ogg");
		registerSound(event.manager, "darthit.ogg", "/resources/newsound/darthit.ogg");
		registerSound(event.manager, "frogspit.ogg", "/resources/newsound/frogspit.ogg");
		registerSound(event.manager, "headattack.ogg", "/resources/newsound/headattack.ogg");
		registerSound(event.manager, "headdeath.ogg", "/resources/newsound/headdeath.ogg");
		registerSound(event.manager, "headlaughing.ogg", "/resources/newsound/headlaughing.ogg");
		registerSound(event.manager, "headlaughing2.ogg", "/resources/newsound/headlaughing2.ogg");
		registerSound(event.manager, "headmed.ogg", "/resources/newsound/headmed.ogg");
		registerSound(event.manager, "headpain.ogg", "/resources/newsound/headpain.ogg");
		registerSound(event.manager, "headshort.ogg", "/resources/newsound/headshort.ogg");
		registerSound(event.manager, "iggyattack.ogg", "/resources/newsound/iggyattack.ogg");
		registerSound(event.manager, "iggydeath.ogg", "/resources/newsound/iggydeath.ogg");
		registerSound(event.manager, "iggyliving.ogg", "/resources/newsound/iggyliving.ogg");
		registerSound(event.manager, "monkeyangry.ogg", "/resources/newsound/monkeyangry.ogg");
		registerSound(event.manager, "monkeydrinking.ogg", "/resources/newsound/monkeydrinking.ogg");
		registerSound(event.manager, "monkeyhiccup.ogg", "/resources/newsound/monkeyhiccup.ogg");
		registerSound(event.manager, "monkeyhurt.ogg", "/resources/newsound/monkeyhurt.ogg");
		registerSound(event.manager, "monkeyliving.ogg", "/resources/newsound/monkeyliving.ogg");
		registerSound(event.manager, "ashenLaugh.ogg", "/resources/newsound/ashenLaugh.ogg");
		registerSound(event.manager, "mine.ogg", "/resources/newsound/mine.ogg");
		
		// Other sounds
		registerSound(event.manager, "pageFlip.ogg", "/resources/newsound/pageFlip.ogg");
		registerSound(event.manager, "bongolow.wav", "/resources/newsound/bongolow.wav");
		registerSound(event.manager, "bongomedium.wav", "/resources/newsound/bongomedium.wav");
		registerSound(event.manager, "bongohigh.wav", "/resources/newsound/bongohigh.wav");
    }
	
	private void registerMusicDisk(SoundManager manager, String name) {
		registerStreaming(manager, name, String.format("/resources/streaming/%s", name));
	}
    
    private void registerSound(SoundManager manager, String name, String path) {
        try {
            URL filePath = TropicraftSoundLoader.class.getResource(path);
            if (filePath != null) {
                manager.soundPoolSounds.addSound(name, filePath);
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception ex) {
            System.out.println(String.format("Warning: unable to load sound file %s", path));
        }
    }
    
    private void registerStreaming(SoundManager manager, String name, String path) {
        try {
            URL filePath = TropicraftSoundLoader.class.getResource(path);
            if (filePath != null) {
                manager.soundPoolStreaming.addSound(name, filePath);
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception ex) {
        	try {
        		System.out.println(String.format("Warning: unable to load sound file %s"));
        	} catch (Exception ex2) {
        		ex2.printStackTrace();
        	}
        }
    }
}
