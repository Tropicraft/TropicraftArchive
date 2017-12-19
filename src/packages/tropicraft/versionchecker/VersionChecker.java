package tropicraft.versionchecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import cpw.mods.fml.client.FMLClientHandler;

public class VersionChecker implements Runnable{
	Thread runner;
	public static String outString;
	private static URL versionUrl;
	private String serverVersion;
	private String modVersion;
	private String dlURL;
	private String modName;
	
	/**
	 * VersionChecker is a simple class that checks a version file on your web site and
	 * compares it to the current version of your mod.
	 * 
	 * @param URL The url where your version file is located
	 * @param modVer The version of the mod 
	 * @param name The name of your mod
	 * @param dl Where your mod can be downloaded from
	 **/
	public VersionChecker(String URL, String modVer, String name, String dl){
		runner = new Thread(this, "Version Checker");
		
		modVersion = modVer;
		dlURL = dl;
		modName = name;
		try {
			versionUrl = new URL(URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		runner.start();
		
	}
	private String checkVersion(){
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(versionUrl.openStream()));
			serverVersion = reader.readLine();			
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tellInfo();
	}
	
	private boolean doVersionsMatch(){
		if(serverVersion == null){
			return true;
		}
		return serverVersion.equals(modVersion);
	}
	/**
	 * This method returns a string that can be used for console spam or as an in game 
	 * chat message. It is recommended that you call this in your onTickInGame method when the world
	 * is not null. Be sure to only call this method once. 
	 * 
	 * @return Returns a string containing info on the latest version of your mod, and a link to where
	 * the user can download the mod.
	 **/
	public String tellInfo(){
		if(!doVersionsMatch()){
			return "Your current version of " + modName + " is out of date. The current version is " + 
						serverVersion + " and can be downloaded at " + dlURL;
		}
		return "";
	}
	@Override
	public void run() {
		String s = checkVersion();		
			outString = s;
		
		
	}
}
