package net.tropicraft.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.koa.JobTrade;
import net.tropicraft.mods.TropicraftMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import CoroAI.entity.EnumJob;
import CoroAI.entity.EnumJobState;
import CoroAI.entity.c_EnhAI;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityKoaMemberNew extends c_EnhAI implements IEntityAdditionalSpawnData
{
	
	//Trading fields - temp
	public float tradeDistTrigger = 3F;
	public int tradeTimeout = 0;
	public int tradeLastItemOffer;
	
	public boolean gender;	//true for male, false for female
    public int nameIndex;
    //public String koaName;
	
	public boolean displayquote;
	public String contentquote;
	
	public final ItemStack fightingHeldItem = new ItemStack(Item.swordDiamond, 1);
	public int speakingphase;
    public int timeuntilspeak;
    
    public final String[] greetinglines = new String[]{"Aloha!","Oaloha!"};
    public final String[] chatlines = new String[]{"doop", "derp", "herp", "snarf"};
    public final String[] contentlines = new String[]{"Maika'i", "Hau'oli", "Hoaloha", "Laule'a"};
    
    protected final List trade_PlayerOfferings = Arrays.asList(TropicraftMod.zirconItem, TropicraftMod.eudialyteItem, TropicraftMod.axeEudialyte,
    		TropicraftMod.axeZircon, TropicraftMod.blowGun,
    		TropicraftMod.hoeEudialyte, TropicraftMod.hoeZircon,  
    		TropicraftMod.pickaxeEudialyte, TropicraftMod.pickaxeZircon, TropicraftMod.shellRare1,
    		TropicraftMod.shellCommon1, TropicraftMod.shellCommon2, TropicraftMod.shellCommon3, TropicraftMod.shellStarfish,
    		TropicraftMod.shovelEudialyte, TropicraftMod.shovelZircon, 
    		Item.diamond, TropicraftMod.froxEasternIsles, TropicraftMod.froxTradeWinds);
    
    protected final List trade_KoaOfferings =  Arrays.asList(
    TropicraftMod.fishingNet,
    TropicraftMod.marlinRaw,
    TropicraftMod.marlinCooked,
    TropicraftMod.turtleShell);
    

	
	
	
	
	
	
	
	
	
	
	
    /*public final EnumJob[] maleOccupations = {EnumJob.CHIEF, EnumJob.FISHERMAN, EnumJob.GATHERER,
			EnumJob.HUNTER, EnumJob.SHAMAN, EnumJob.WEATHERGURU};*/
    
    public final EnumJob[] maleOccupations = {EnumJob.HUNTER, EnumJob.FISHERMAN, EnumJob.TRADING/*, EnumJob.GATHERER, EnumJob.HUNTER*/};
	
    public final EnumJob[] femaleOccupations = {EnumJob.COOK, EnumJob.FISHERMAN, EnumJob.GATHERER,
			EnumJob.HUNTER};
	
	
	
    public final String[] tribalMaleNames = new String[]{"Akamu", "Ekewaka", "Ikaika", "Iukini", "Kai", "Kaimana", "Kaimi", "Kanoa", "Kapena", "Keahi", "Keaweaheulu","Kekipi",
    		"Kekoa", "Konani", "Makani", "Mano", "Nahele"};
    
    public final String[] nameMaleMeanings = new String[]{"Earth, or Red", "Guardian of Prosperity", "Well Born", "Strong", "Sea", "Diamond", "The Seeker",
    		"The Free One", "The Captain", "Flames", "Cool Breeze Over the Mountains","Rebel", "The Brave One", "Bright", "Wind", "Shark", "Forest"};
    
    public final String[] tribalFemaleNames = new String[]{"Alamea", "Alani", "Alaula", "Ewa", "Kai", "Kaiolohia", "Kalama", "Keala", "Kiele", "Mahina",
    		"Makala", "Maylea", "Noe"};
    
    public final String[] nameFemaleMeanings = new String[]{"Precious", "Orange Tree", "Dawn", "Life", "Sea", "Calm Sea", "Flaming Torch", "The Pathway", "Fragant Blossom",
    		"Moon, or Moonlight", "Myrtle", "Wildflower", "Mist or Misty Rain"};
    
    
    
	
    public ArrayList<Double> likedItemValues;
	public HashMap<Integer, Double> likedValueMap;
	
	public ItemStack[] likedItemArray;
	//public String name;
	public ConversationHandler conversationHandler;
	public boolean isInVillage;
	
	public EntityKoaMemberNew(World world) {
		super(world);
		enhanceAIEnemies = true;
		grabItems = true;
		grabXP = true;
		
		job.jobTypes.put(EnumJob.TRADING, new JobTrade(job));
		
	}
	
	protected boolean isAIEnabled()
    {
        return true;
    }
	
	protected void updateAITasks()
    {
        ++this.entityAge;
        this.despawnEntity();
        
        if (fakePlayer == null) return;
        
        //this.func_48090_aM().func_48481_a();
        //this.targetTasks.onUpdateTasks();
        //this.tasks.onUpdateTasks();
        checkPathfindLock();
        this.getNavigator().onUpdateNavigation();
        updateAI();
        //this.func_48097_s_();
        this.getMoveHelper().onUpdateMoveHelper();
        this.getLookHelper().onUpdateLook();
        this.getJumpHelper().doJump();
    }
	
	public EnumJob getOccupation(boolean gender)
	{	
		{
			int i = rand.nextInt(maleOccupations.length);

			return maleOccupations[i];
		}
	}
	
	public void setOccupationItems() {
		super.setOccupationItems();
		if (job.getJob() == EnumJob.FISHERMAN) {
			
		} else if (job.getJob() == EnumJob.HUNTER) {
			
			
			
		}
		
		//unique trade items
		List ids = new LinkedList();
		
		/*for (int i = 0; i < 3; i++) {
			Object randItem;
			int safety = 0;
			while (ids.contains(randItem = this.trade_KoaOfferings.get(this.rand.nextInt(trade_KoaOfferings.size()-1)))); { if (safety++ > 1000) { System.out.println("trade items addition endless loop, breaking"); break; } }
			ids.add(randItem);
			if (randItem instanceof Item) {
				inventory.mainInventory[slot_Trade-i] = new ItemStack((Item)randItem);
			} else {
				inventory.mainInventory[slot_Trade-i] = new ItemStack((Block)randItem);
			}
		}*/
		//System.out.println(ids);
	}
	
	/*@Override*/
	public boolean interactOld(EntityPlayer entityplayer)
    {
		if (entityplayer.getCurrentEquippedItem() != null) {
			if (this.job.getJob() == EnumJob.TRADING && this.job.getJobState() == EnumJobState.W2) {
				if (this.trade_PlayerOfferings.contains(entityplayer.getCurrentEquippedItem().getItem())) {
					//trade!
					(this.job.getJobClass()).koaTrade(entityplayer);
					return false;
				}
			}
		}
		
		
		//if (mod_EntMover.masterDebug) ModLoader.getMinecraftInstance().displayGuiScreen(new GuiTrade(this.inventory, entityplayer.inventory));
		if (true) return false;
		if(entityplayer.getCurrentEquippedItem() != null)
		{
			if(isItemLikedForTrading(entityplayer.getCurrentEquippedItem().itemID))
			{
				this.displayQuote(speakingphase);
				conversationHandler.onTradeInitiated(isItemLikedForTrading(entityplayer.getCurrentEquippedItem().itemID));
			}
				
			return isItemLikedForTrading(entityplayer.getCurrentEquippedItem().itemID);
		}
		
		return false;
    }
	
	//ugh
    private boolean isItemLikedForTrading(int i)
	{
		if(likedValueMap.get((Integer)i) == null)
		{
			System.out.println("EntityKoaMemberNew: null" + i);
			return false;
		}
		
		Random rand = new Random();
		double d = rand.nextDouble() + likedValueMap.get(i).doubleValue() + rand.nextGaussian() + rand.nextGaussian();	
		System.out.println("EntityKoaMemberNew: " + d + "    " + (d < likedValueMap.get(i).doubleValue()) + "    " + likedValueMap.get(i).doubleValue());
		
		return isItemInLikedList(i) && ((d < likedValueMap.get(i).doubleValue()) || d > 3.93673D || rand.nextInt(20) == 0);
	}
	
	private boolean isItemInLikedList(int i)
	{
		for(ItemStack is : likedItemArray)
		{
			if(i == is.itemID)
			{
				System.out.println("EntityKoaMemberNew: " + i + " " + is.itemID);
				return true;
			}
		}
		
		return false;
	}
	
	public String getRandomQuote()
	{
		return chatlines[(rand.nextInt(chatlines.length))];
	}
	
	public String getContentQuote()
	{
		return contentlines[(rand.nextInt(contentlines.length))];
	}
	
	public String getGreetingQuote()
	{
		return greetinglines[(rand.nextInt(greetinglines.length))];
	}
	
	public void displayQuote(int phase)
	{
		switch(phase)
		{
		case 0:
			getGreetingQuote();		//greet the player
		}
	}
	
	public boolean getGender()
    {
    	return gender;
    }
    
	public void setName(String s)
    {
    	name = s;
    }
    
    public String getName(boolean b)
	{
		if(b)
		{
			nameIndex = rand.nextInt(tribalMaleNames.length);
			return tribalMaleNames[nameIndex];
		}
		else
		{
			nameIndex = rand.nextInt(tribalFemaleNames.length);
			return tribalFemaleNames[nameIndex];
		}
	}
	
	public String getNameMeaning()
	{
		if(gender)
		{
			return this.nameMaleMeanings[this.nameIndex];
		}
		else
		{
			return nameFemaleMeanings[nameIndex];
		}
	}
	
	public ItemStack[] n(Item[] i, Block[] b)
	{
		ItemStack[] s = new ItemStack[i.length+b.length];
		int count = 0;
		double value = 0.0D;
		
		for(Item item : i)
		{
			s[count] = new ItemStack(item);
			count++;
		//	value += 0.3D;
			value = rand.nextInt(7) + rand.nextGaussian() + rand.nextDouble();
			likedItemValues.add(value);
		}
		
		value = 0;		//this allows for the 'value' of the item to start over at 0 for blocks
		
		for(Block block : b)
		{
			s[count] = new ItemStack(block);
			count++;
			value = rand.nextInt(7) + rand.nextGaussian() + rand.nextDouble();
			likedItemValues.add(value);
		}
		
		return s;
	}
	
	public void assignValueMap(ArrayList<Double> b)
	{
		int d = 0; 		//count
		for(ItemStack c : this.likedItemArray){
			likedValueMap.put(c.itemID, b.get(d));
		//	System.out.println("Putting " + c + " @ " + b.get(d));
			d++;
		}
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		// TODO Auto-generated method stub
		data.writeUTF(name);
		data.writeUTF(texture);
		
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		// TODO Auto-generated method stub
		String temp = data.readUTF();
		if (temp.length() > 0) {
			name = temp;
			texture = data.readUTF();
		}
		
	}

	@Override
	public void initJobAndStates(EnumJob job, boolean initItems) {
		super.initJobAndStates(job, initItems);
		if (job == EnumJob.HUNTER) {
			texture = "/tropicalmod/mobs/KoaManHunter.png";
		} else {
			texture = "/tropicalmod/mobs/KoaMan3.png";
		}
	}
	
	@Override
	public boolean canDespawn() {
		return false;
	}
}
