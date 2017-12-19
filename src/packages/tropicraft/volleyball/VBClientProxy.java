package tropicraft.volleyball;

import cpw.mods.fml.client.registry.ClientRegistry;

public class VBClientProxy extends VBCommonProxy {
	@Override
	public void registerTESRs() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCourtMaster.class, new TECourtMasterRenderer());
	}
}
