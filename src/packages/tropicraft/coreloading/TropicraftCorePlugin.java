package tropicraft.coreloading;

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
/*
 * Don't let any access transformer stuff accidentally modify our classes
 * A list of package prefixes for FML to ignore
 */
@TransformerExclusions({"tropicraft.asm"})
@MCVersion("1.6.4")
public class TropicraftCorePlugin implements IFMLLoadingPlugin {

	private final String[] asmTransformers = new String[]{"tropicraft.asm.ModelBipedTransformer", "tropicraft.asm.RenderPlayerTransformer"};
    
    public static Logger logger = Logger.getLogger("Tropicraft-Core");
    
    public TropicraftCorePlugin() {
    	logger.setParent(FMLLog.getLogger());
    	logger.info("Starting to load Tropicraft CoreMods!");
    	logger.info("Constructing preloader (Modules: " + Arrays.toString(asmTransformers) + ")");
    }
  
	@Override
	/*
	 * Use if you want to download libraries. Returns a list of classes that implement the ILibrarySet interface
	 * eg return new String[] { "tutorial.asm.downloaders.DownloadUsefulLibrary " };
	 */
	public String[] getLibraryRequestClass() {
		return null;
	}
	/*
	 * The class(es) that do(es) the transforming. Needs to implement IClassTransformer in some way
	 */

	@Override

	public String[] getASMTransformerClass() {
		return asmTransformers;
	}
	/*
	 * The class that acts similarly to the @Mod annotation.
	 */
	@Override
	public String getModContainerClass() {
		return null;
	}
	/*
	 * If you want to do stuff BEFORE minecraft starts, but after your mod is loaded.
	 */
	@Override
	public String getSetupClass() {
		return null;
	}
	/*
	 * Gives the mod coremod data if it wants it.
	 */
	@Override
	public void injectData(Map<String, Object> data) {

	}

}