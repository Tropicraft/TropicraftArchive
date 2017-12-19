package net.tropicraft.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.tropicraft.ClientTickHandler;
import net.tropicraft.SoundLoader;
import net.tropicraft.TCRenderRegistry;
import net.tropicraft.blocks.liquids.LAPI;
import net.tropicraft.blocks.liquids.LAPITextureFX;
import net.tropicraft.blocks.liquids.TropicraftLAPITextureFX;
import net.tropicraft.blocks.renderhandlers.CoffeePlantRenderHandler;
import net.tropicraft.blocks.renderhandlers.EIHMixerRenderHandler;
import net.tropicraft.blocks.renderhandlers.FlowerPotRenderHandler;
import net.tropicraft.blocks.renderhandlers.FountainRenderHandler;
import net.tropicraft.blocks.renderhandlers.LemonSqueezerRenderHandler;
import net.tropicraft.blocks.renderhandlers.TikiTorchRenderHandler;
import net.tropicraft.blocks.tileentities.CurareBowlRenderHandler;
import net.tropicraft.blocks.tileentities.TileEntityBambooChest;
import net.tropicraft.blocks.tileentities.TileEntityBambooChestRenderer;
import net.tropicraft.blocks.tileentities.TileEntityBambooMug;
import net.tropicraft.blocks.tileentities.TileEntityBambooMugRenderer;
import net.tropicraft.blocks.tileentities.TileEntityCurareBowl;
import net.tropicraft.blocks.tileentities.TileEntityCurareBowlRenderer;
import net.tropicraft.blocks.tileentities.TileEntityEIHMixer;
import net.tropicraft.blocks.tileentities.TileEntityEIHMixerRenderer;
import net.tropicraft.blocks.tileentities.TileEntityKoaChest;
import net.tropicraft.blocks.tileentities.TileEntityKoaChestRenderer;
import net.tropicraft.blocks.tileentities.TileEntityLemonSqueezer;
import net.tropicraft.blocks.tileentities.TileEntityLemonSqueezerRenderer;
import net.tropicraft.blocks.tileentities.TileEntityPurchasePlate;
import net.tropicraft.blocks.tileentities.TileEntityPurchasePlateRenderer;
import net.tropicraft.blocks.tileentities.TileEntitySifter;
import net.tropicraft.blocks.tileentities.TileEntitySifterRenderer;
import net.tropicraft.blocks.tileentities.TileEntityWallShell;
import net.tropicraft.blocks.tileentities.TileEntityWallShellRender;
import net.tropicraft.cojodungeon.BasicPuzzleFactory;
import net.tropicraft.crafting.CraftingTropicraft;
import net.tropicraft.encyclopedia.Encyclopedia;
import net.tropicraft.entities.renderers.RenderPlayerTropicraft;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.texturefx.FountainTextureFX;
import net.tropicraft.texturefx.TextureTropicsPortalFX;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	private Set<String> upsideDownUsernames = new HashSet<String>();
	private Minecraft mc = Minecraft.getMinecraft();

	private static String soundZipPath = "/resources/";
	
	public ClientProxy() {
		
	}
	
	@Override
	public void addRecipes() {
		CraftingTropicraft.addRecipes(false);    //adds recipes, mixer, curare, and crafting
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
	public void encyclopediaInit() {
//		TropicraftMod.encyclopedia = new Encyclopedia("eTsave.dat", "/tropicalmod/EncyclopediaTropica.txt", "/tropicalmod/gui/encyclopediaTropica.png", "/tropicalmod/gui/encyclopediaTropicaInside.png");
		CraftingTropicraft.addItemsToEncyclopedia(); // registers items for encyclopedia
	}
	
	@Override
	public void preEncyclopediaInit() {
		TropicraftMod.encyclopedia = new Encyclopedia("eTsave.dat", "/tropicalmod/EncyclopediaTropica.txt", "/tropicalmod/gui/encyclopediaTropica.png", "/tropicalmod/gui/encyclopediaTropicaInside.png");
		BasicPuzzleFactory.file = new File("/tropicalmod/Puzzles.txt");
		BasicPuzzleFactory.createRandomPuzzle();
	}
	
	@Override
	public File appDirFile(String s) {
		return new File(Minecraft.getAppDir("minecraft"), s);
	}
	
	@Override
	public void rpapi() {
		//RenderPlayerAPI.register("Tropicraft", RenderPlayerTropicraft.class);
		rpt = new RenderPlayerTropicraft();
	}
	
	@Override
	public void init() {
		super.init();
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
	}
	
	@Override
	public int getUniqueTextureLoc() {
		return RenderingRegistry.getUniqueTextureIndex("/terrain.png");
	}
	
	/**
	 * This is for registering armor types, like ModLoader.addArmor used to do
	 */
	public int getArmorNumber(String type) {
		return RenderingRegistry.addNewArmourRendererPrefix(type);
	}
	
	/**
	 * This is for getting the LAPI Liquid ids for the
	 * blockindexintextures...just, don't mess with this :P
	 */
	public int getUniqueTropicraftLiquidID() {
		if (LAPI.nextID < 40) {
			LAPI.nextID++;
			int a = LAPI.getUniqueStationaryID();
			int b = LAPI.getUniqueFlowingID();

			TropicraftLAPITextureFX altfx1 = new TropicraftLAPITextureFX(a, false);
			altfx1.opacity = 90;
			TropicraftLAPITextureFX altfx2 = new TropicraftLAPITextureFX(b, true);
			altfx2.opacity = 90;

			registerTextureFxInfo(altfx1, altfx2);

			LAPI.terrainIDMap.put(a, LAPI.nextID);

			return a;
		} else {
			try {
				throw new Exception("No more unique LAPI block textures available!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return 0;
	}
	
	@Override
	public void loadSounds() {
		MinecraftForge.EVENT_BUS.register(new SoundLoader());
	}
	
	@Override
	public void displayRecordGui(String displayText) {
		System.out.println("displayRecordGui");
		ModLoader.getMinecraftInstance().ingameGUI.setRecordPlayingMessage(displayText);
	}
	
	@Override
	public void registerRenderInformation() {
		MinecraftForgeClient.preloadTexture("/tropicalmod/tropiterrain.png");
		MinecraftForgeClient.preloadTexture("/tropicalmod/tropiitems.png");
		MinecraftForgeClient.preloadTexture("/tropicalmod/fountain_sheet.png");
		Minecraft.getMinecraft().renderEngine.registerTextureFX(new TextureTropicsPortalFX());
		Minecraft.getMinecraft().renderEngine.registerTextureFX(new FountainTextureFX(0));
		RenderingRegistry.registerBlockHandler(new TikiTorchRenderHandler());
		RenderingRegistry.registerBlockHandler(new LemonSqueezerRenderHandler());
		RenderingRegistry.registerBlockHandler(new EIHMixerRenderHandler());
		RenderingRegistry.registerBlockHandler(new FlowerPotRenderHandler());
		RenderingRegistry.registerBlockHandler(new FountainRenderHandler());
		RenderingRegistry.registerBlockHandler(new CurareBowlRenderHandler());
		RenderingRegistry.registerBlockHandler(new CoffeePlantRenderHandler());
//		TropicraftMod.tropicraftPortal.blockIndexInTexture = ModLoader.getUniqueSpriteIndex("/terrain.png");
	}
	
	@Override
	public void registerTextureFxInfo(LAPITextureFX... textureFxs) {
		for(LAPITextureFX tfx : textureFxs) {
			Minecraft.getMinecraft().renderEngine.registerTextureFX(tfx);
		}
	}
	
	@Override
	public void initRenderRegistry() {
		TCRenderRegistry.init();
	}

	@Override
	public void registerTileEntitySpecialRenderer() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySifter.class, new TileEntitySifterRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBambooChest.class, new TileEntityBambooChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKoaChest.class, new TileEntityKoaChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWallShell.class, new TileEntityWallShellRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLemonSqueezer.class, new TileEntityLemonSqueezerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEIHMixer.class, new TileEntityEIHMixerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBambooMug.class, new TileEntityBambooMugRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCurareBowl.class, new TileEntityCurareBowlRenderer());
ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPurchasePlate.class, new TileEntityPurchasePlateRenderer());	} 

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	@Override
	public EntityPlayer getClientPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}
	
	@Override
	public Entity getEntByID(int id) {
		return FMLClientHandler.instance().getClient().theWorld.getEntityByID(id);
	}
	
	@Override
	public Set<String> getUpsideDownUsernames() {
		return upsideDownUsernames;
	}

	@Override
	public void renderGameOverlay(float par1, boolean par2, int par3, int par4) {
		ItemStack helm = mc.thePlayer.inventory.armorInventory[3];

		if (helm != null) {
			if (helm.itemID == TropicraftMod.snorkel.shiftedIndex) {
				renderOverlay("/tropicalmod/gui/snorkelGrad.png");
			} else if (helm.itemID == TropicraftMod.ashenMask.shiftedIndex) {
				renderOverlay("/tropicalmod/gui/maskblur.png");
			}
		}
	}
	
    private void renderOverlay(String texture) {
        ScaledResolution var5 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int scaledWidth = var5.getScaledWidth();
        int scaledHeight = var5.getScaledHeight();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("%blur%"+texture));
        Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0D, (double)scaledHeight, -90.0D, 0.0D, 1.0D);
        var3.addVertexWithUV((double)scaledWidth, (double)scaledHeight, -90.0D, 1.0D, 1.0D);
        var3.addVertexWithUV((double)scaledWidth, 0.0D, -100.0D, 1.0D, 0.0D);
        var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
