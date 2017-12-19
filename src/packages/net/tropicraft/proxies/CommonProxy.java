package net.tropicraft.proxies;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.tropicraft.ServerTickHandler;
import net.tropicraft.blocks.liquids.LAPITextureFX;
import net.tropicraft.crafting.CraftingTropicraft;
import net.tropicraft.encyclopedia.Encyclopedia;
import net.tropicraft.entities.renderers.RenderPlayerTropicraft;
import net.tropicraft.mods.TropicraftMod;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import CoroAI.entity.c_EnhAI;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements IGuiHandler {
	private Set<String> upsideDownUsernames = new HashSet<String>();

	public RenderPlayerTropicraft rpt;
	
	public CommonProxy() {
	}
	
	public RenderPlayerTropicraft getTropiPlayerRenderer()
	{
		return rpt;
	}
	
	public float getFOV() {
		return 0;
	}
	
	public void setFOV(float trollov) {
		
	}
	
	public void encyclopediaInit() {
	//	CraftingTropicraft.addItemsToEncyclopedia(); // registers items for encyclopedia
	//	TropicraftMod.encyclopedia = new Encyclopedia("eTsave.dat", "/tropicalmod/EncyclopediaTropica.txt", "/tropicalmod/gui/encyclopediaTropica.png", "/tropicalmod/gui/encyclopediaTropicaInside.png");
	}
	
	public void preEncyclopediaInit() {
	//	TropicraftMod.encyclopedia = new Encyclopedia("eTsave.dat", "/tropicalmod/EncyclopediaTropica.txt", "/tropicalmod/gui/encyclopediaTropica.png", "/tropicalmod/gui/encyclopediaTropicaInside.png");
	}
	
	public void init() {
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
	}
	
	public int getUniqueTextureLoc() {
		return 0;
	}
	
	public int getArmorNumber(String type) {
		return 0;
	}
	
	public int getUniqueTropicraftLiquidID() {
		return 0;
	}
	
	public void loadSounds() {
		
	}
	
	public void registerTextureFxInfo(LAPITextureFX... textureFxs) {
		
	}
	
	public void registerRenderInformation()
	{

	}

	public void registerTileEntitySpecialRenderer()
	{

	}
	
	public void displayRecordGui(String displayText) {
		
	}
	
	public World getClientWorld() {
		return null;
	}
	
	public EntityPlayer getClientPlayer() {
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	public void initRenderRegistry() {
		//TCEntityRegistry.init(mod);
	}
	
	public Entity getEntByID(int id) {
		//return FMLClientHandler.instance().().theWorld.getEntityByID(id);
		//return FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0).getEntityByID(id) // bad, need world ref
		return null;
	}
	
	public c_EnhAI getEntByPersistantID(World world, int id) {
		try {
			//System.out.println("search for id: " + id);
			for (int i = 0; i < world.loadedEntityList.size(); i++) {
				Entity ent = (Entity)world.loadedEntityList.get(i);
				if (ent instanceof c_EnhAI) {
					if (((c_EnhAI) ent).entID == id) {
						//System.out.println("found ent by id: " + ((c_EnhAI) ent).entID);
						return (c_EnhAI)ent;
					} else {
						//System.out.println("non matching id: " + ((c_EnhAI) ent).entID);
					}
				}
			}
		} catch (Exception ex) {
			//System.out.println("FAIL: " + world);
			ex.printStackTrace();
		}
		//return FMLClientHandler.instance().().theWorld.getEntityByID(id);
		//return FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0).getEntityByID(id) // bad, need world ref
		//System.out.println("nothing found");
		return null;
	}

	public void rpapi() {
		rpt = null;
	}

	public File appDirFile(String s) {
		return null;
	}

	public Set<String> getUpsideDownUsernames() {
		return upsideDownUsernames;
	}

	public void renderGameOverlay(float par1, boolean par2, int par3, int par4) {
	}

	public void addRecipes() {
		CraftingTropicraft.addRecipes(true);    //adds recipes, mixer, curare, and crafting		
	}
}
