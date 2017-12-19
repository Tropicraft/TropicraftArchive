package tropicraft.proxies;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tropicraft.TickHandlerServer;
import tropicraft.Tropicraft;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements IGuiHandler {
	private Set<String> upsideDownUsernames = new HashSet<String>();

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void initTickers() {
		TickRegistry.registerTickHandler(new TickHandlerServer(Tropicraft.instance), Side.SERVER);
	}

	public void initTCRenderRegistry() {

	}

	public void loadSounds() {
		// TODO Auto-generated method stub

	}

	public void registerTESRs() {
		// TODO Auto-generated method stub

	}

	public void registerBlockRenderHandlers() {
		// TODO Auto-generated method stub		
	}

	public float getFOV() {
		return 0;
	}

	public void setFOV(float trollov) {
	}

	public void registerBlockRenderIds() {
		
	}

	public Set<String> getUpsideDownUsernames() {
		return upsideDownUsernames;
	}

	public EntityPlayer getClientPlayer() {
		return null;
	}

	public void registerItemRenderers() {
	}
	
	public void registerBooks() {
		//CraftingTropicraft.addItemsToEncyclopedia(); // registers items for encyclopedia
	}
}
