package net.tropicraft.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
/*
 * Don't let any access transformer stuff accidentally modify our classes
 * A list of package prefixes for FML to ignore
 */
@TransformerExclusions({"net.tropicraft.asm" , "net.tropicraft"})
public class TropicraftCorePlugin implements IFMLLoadingPlugin {

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
        	return new String[] {
    			"net.tropicraft.asm.RenderPlayerTransformer",
    			"net.tropicraft.asm.GuiIngameTransformer",
    			"net.tropicraft.asm.ModelBipedTransformer"
			};
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