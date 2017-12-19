package net.tropicraft;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.src.ModLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DartSettingsHandler {
	File duppedFile;
	File fileActual;
	public DartSettingsHandler() throws IOException{
		duppedFile = new File(ModLoader.getMinecraftInstance().mcDataDir, "spOption.txt");
		fileActual =  new File(ModLoader.getMinecraftInstance().mcDataDir, "options.txt");
		if(hasBeenChanged()){
			resaveOptions();
			//System.out.println("Settings Reverted");
			
		}
	}
	public void dupeOptions(int i) throws IOException{
		try {
			BufferedReader bufferedreader = new BufferedReader(new FileReader(fileActual));
			 PrintWriter printwriter = new PrintWriter(new FileWriter(duppedFile));
			 printwriter.println(i + ":i");
			 for(String s = ""; (s = bufferedreader.readLine()) != null;)
	            {
				 printwriter.println(s);
	            }
			 printwriter.close();
			 bufferedreader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void resaveOptions() throws IOException{
		BufferedReader bufferedreader = new BufferedReader(new FileReader(duppedFile));
		//PrintWriter printwriter = new PrintWriter(new FileWriter(fileActual));
		for(String s = ""; (s = bufferedreader.readLine()) != null;)
        {
		//	System.out.println(s);
			String as[] = s.split(":");
			if(as[0].equals("mouseSensitivity"))
            {
                ModLoader.getMinecraftInstance().gameSettings.mouseSensitivity = parseFloat(as[1]);
            }
            if(as[0].equals("fov"))
            {
            	 ModLoader.getMinecraftInstance().gameSettings.fovSetting = parseFloat(as[1]);
            }
            if(as[0].equals("key_key.attack")){
                ModLoader.getMinecraftInstance().gameSettings.keyBindAttack = new KeyBinding("key.attack",Integer.parseInt(as[1]));
            }
            
            if(as[0].equals("key_key.use")){
                ModLoader.getMinecraftInstance().gameSettings.keyBindUseItem = new KeyBinding("key.use", Integer.parseInt(as[1]));
            }
            if(as[0].equals("key_key.back")){
                ModLoader.getMinecraftInstance().gameSettings.keyBindBack = new KeyBinding("key.back", Integer.parseInt(as[1]));

            }
            if(as[0].equals("key_key.forward")){
                ModLoader.getMinecraftInstance().gameSettings.keyBindForward = new KeyBinding("key.forward", Integer.parseInt(as[1]));

            }
            if(as[0].equals("key_key.left")){
                ModLoader.getMinecraftInstance().gameSettings.keyBindLeft = new KeyBinding("key.left", Integer.parseInt(as[1]));

            }
            if(as[0].equals("key_key.right")){
                ModLoader.getMinecraftInstance().gameSettings.keyBindRight = new KeyBinding("key.right", Integer.parseInt(as[1]));

            }
            if(as[0].equals("key_key.jump")){
            ModLoader.getMinecraftInstance().gameSettings.keyBindJump = new KeyBinding("key.jump", Integer.parseInt(as[1]));
            }
        }
	
	// printwriter.close();
	 bufferedreader.close();
	 dupeOptions(0);
	}
	public boolean hasBeenChanged() throws IOException{
		if(!duppedFile.exists()){
			dupeOptions(0);
			return false;
		}
		BufferedReader bufferedreader = new BufferedReader(new FileReader(duppedFile));
		if(bufferedreader.readLine().charAt(0) == '1'){
			bufferedreader.close();
			return true;
		}
		else{
			bufferedreader.close();
			return false;
		}
	}
	 private float parseFloat(String s)
	    {
	        if(s.equals("true"))
	        {
	            return 1.0F;
	        }
	        if(s.equals("false"))
	        {
	            return 0.0F;
	        } else
	        {
	            return Float.parseFloat(s);
	        }
	    }
}
