package tropicraft.proxies;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import tropicraft.ModRenderIds;
import tropicraft.TickHandlerClient;
import tropicraft.Tropicraft;
import tropicraft.TropicraftSoundLoader;
import tropicraft.blocks.renderhandlers.BambooChestItemRenderer;
import tropicraft.blocks.renderhandlers.CoffeePlantRenderHandler;
import tropicraft.blocks.renderhandlers.CurareBowlRenderHandler;
import tropicraft.blocks.renderhandlers.EIHMixerRenderHandler;
import tropicraft.blocks.renderhandlers.FlowerPotRenderHandler;
import tropicraft.blocks.renderhandlers.TikiTorchRenderHandler;
import tropicraft.blocks.tileentities.TileEntityBambooChest;
import tropicraft.blocks.tileentities.TileEntityBambooMug;
import tropicraft.blocks.tileentities.TileEntityCurareBowl;
import tropicraft.blocks.tileentities.TileEntityEIHMixer;
import tropicraft.blocks.tileentities.TileEntityKoaChest;
import tropicraft.blocks.tileentities.TileEntityPurchasePlate;
import tropicraft.blocks.tileentities.TileEntitySifter;
import tropicraft.blocks.tileentities.renderers.TileEntityBambooChestRenderer;
import tropicraft.blocks.tileentities.renderers.TileEntityBambooMugRenderer;
import tropicraft.blocks.tileentities.renderers.TileEntityCurareBowlRenderer;
import tropicraft.blocks.tileentities.renderers.TileEntityEIHMixerRenderer;
import tropicraft.blocks.tileentities.renderers.TileEntityKoaChestRenderer;
import tropicraft.blocks.tileentities.renderers.TileEntityPurchasePlateRenderer;
import tropicraft.blocks.tileentities.renderers.TileEntitySifterRenderer;
import tropicraft.client.items.StarfishItemRenderer;
import tropicraft.crafting.CraftingTropicraft;
import tropicraft.encyclopedia.Encyclopedia;
import tropicraft.encyclopedia.NigelJournal;
import tropicraft.items.TropicraftItems;
import tropicraft.registry.TCRenderRegistry;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	private static String soundZipPath = "/resources/";
	private Set<String> upsideDownUsernames = new HashSet<String>();

	/**
	 * Calls TCRenderRegistry.init, client side only
	 */
	public void initTCRenderRegistry() {
		TCRenderRegistry.init();
	}

	@Override
	public void loadSounds() {
		MinecraftForge.EVENT_BUS.register(new TropicraftSoundLoader());
	}
	
	@Override
	public void initTickers() {
		// TODO Auto-generated method stub
		super.initTickers();
		TickRegistry.registerTickHandler(new TickHandlerClient(), Side.CLIENT);
	}

	/**
	 * Bind the TileEntitySpecialRenderers to their respective TileEntities here
	 */
	@Override
	public void registerTESRs() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySifter.class, new TileEntitySifterRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBambooChest.class, new TileEntityBambooChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKoaChest.class, new TileEntityKoaChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPurchasePlate.class, new TileEntityPurchasePlateRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEIHMixer.class, new TileEntityEIHMixerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBambooMug.class, new TileEntityBambooMugRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCurareBowl.class, new TileEntityCurareBowlRenderer());
	}

	@Override
	public void registerBlockRenderHandlers() {
		RenderingRegistry.registerBlockHandler(new CurareBowlRenderHandler());
		RenderingRegistry.registerBlockHandler(new BambooChestItemRenderer(ModRenderIds.bambooChestModelId, new TileEntityBambooChest()));
		RenderingRegistry.registerBlockHandler(new BambooChestItemRenderer(ModRenderIds.koaChestModelId, new TileEntityKoaChest()));
		RenderingRegistry.registerBlockHandler(new FlowerPotRenderHandler());
		RenderingRegistry.registerBlockHandler(new TikiTorchRenderHandler());
		RenderingRegistry.registerBlockHandler(new EIHMixerRenderHandler());
		RenderingRegistry.registerBlockHandler(new CoffeePlantRenderHandler());
	}	

	@Override
	public float getFOV() {
		return Minecraft.getMinecraft().gameSettings.fovSetting;
	}

	@Override
	public void setFOV(float trollov) {
		Minecraft.getMinecraft().gameSettings.fovSetting = trollov;
	}
	
	@Override
	public void registerBlockRenderIds() {
		ModRenderIds.curareBowlRenderId = RenderingRegistry.getNextAvailableRenderId();
		ModRenderIds.bambooChestModelId = RenderingRegistry.getNextAvailableRenderId();
		ModRenderIds.koaChestModelId = RenderingRegistry.getNextAvailableRenderId();
		ModRenderIds.flowerPotRenderId = RenderingRegistry.getNextAvailableRenderId();
		ModRenderIds.tikiTorchRenderId = RenderingRegistry.getNextAvailableRenderId();
		ModRenderIds.eihMixerRenderId = RenderingRegistry.getNextAvailableRenderId();
		ModRenderIds.coffeePlantRenderId = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public Set<String> getUpsideDownUsernames() {
		return upsideDownUsernames;
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}
	
	@Override
	public void registerItemRenderers() {
		MinecraftForgeClient.registerItemRenderer(TropicraftItems.starfish.itemID, new StarfishItemRenderer());
	}
	
	@Override
	public void registerBooks() {
		Tropicraft.encyclopedia = new Encyclopedia("eTsave.dat", 
				"/mods/TropicraftMod/gui/EncyclopediaTropica.txt", 
				"/mods/TropicraftMod/gui/encyclopediaTropica.png", 
				"/mods/TropicraftMod/gui/encyclopediaTropicaInside.png");
		
//		Tropicraft.journal = new NigelJournal("journal.dat", 
//				"/mods/TropicraftMod/gui/NigelsJournal.txt",
//				"/mods/TropicraftMod/gui/NigelsJournal.png",
//				"/mods/TropicraftMod/gui/NigelsJournalInside.png");
		
		CraftingTropicraft.addItemsToEncyclopedia(); // registers items for encyclopedia
		
	//TODO	for (int i = 0; i < 15; i++)
		//TODO Tropicraft.journal.includeItem(String.format("page%s", "" + (i + 1)) , new ItemStack(TropicraftItems.journalPage, 1, i));
	}
}
