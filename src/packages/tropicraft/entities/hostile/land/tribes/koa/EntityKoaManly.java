package tropicraft.entities.hostile.land.tribes.koa;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityKoaManly extends EntityKoaMemberNew {

	private static int engagedId;

	public static void setEngaged(boolean b, int i) {
        engagedId = i;
    }

    public static boolean getIsEngaged(int i) {
        return engagedId == i;		//returns if the user is being spoken to be a tribal bro already
    }

	public EntityKoaManly(World world) 
	{
		super(world);
		gender = true;
		health = 20;
		speakingphase = 0;
		timeuntilspeak = 300;
		
		//DEFAULT ONLY, job type method changes their texture on the fly
		texture = "/mods/TropicraftMod/textures/entities/koa/KoaMan3.png";

		setName(getName(gender));

		
		if (!world.isRemote) {
			initJobAndStates(getOccupation(gender), false);
		}
		//initJobAndStates(EnumJob.FISHERMAN, true);
		//setOccupationItems();
		
		displayquote = false;

		contentquote = this.getContentQuote();

		likedItemValues = new ArrayList<Double>();
		likedValueMap = new HashMap<Integer, Double>();

		likedItemArray  = n(new Item[]{Item.pumpkinSeeds, Item.melon, Item.appleGold, Item.cake, Item.coal, Item.ingotGold, Item.ingotIron, 
				Item.pocketSundial, Item.diamond}, new Block[]{Block.dispenser,
				Block.jukebox, Block.fire, Block.ice, Block.mobSpawner, Block.netherrack, Block.obsidian, Block.oreCoal, Block.oreDiamond, Block.oreGold, Block.oreIron, Block.oreLapis,
				Block.oreRedstone, Block.rail, Block.railDetector, Block.railPowered, Block.sponge, Block.thinGlass, Block.glass, Block.tnt, Block.web, Block.blockGold, Block.blockLapis, 
				Block.blockIron, Block.blockDiamond});

		assignValueMap(likedItemValues);

		//conversationHandler = new ConversationHandler(this);

		if(likedValueMap.get(likedItemArray[0]) != null)
        {
			//System.out.println(likedValueMap.get(likedItemArray[0]));
        }

	}
	
	@Override
	public int getMaxHealth() {
		//fakeplayer makes max health changes an issue, use armor!
		/*if (this.job != null && !this.worldObj.isRemote) {
			if (this.job.priJob == EnumJob.HUNTER) {
				return 30;
			}
		}*/
		
    	return 20;
    }

	public boolean attackEntityFrom(DamageSource damagesource, int i)
	{
		if(super.attackEntityFrom(damagesource, i))
		{
			Entity entity = damagesource.getEntity();
			if(riddenByEntity == entity || ridingEntity == entity)
			{
				return true;
			}
			if(entity != this)
			{
				entityToAttack = entity;
			}
			return true;
		} else
		{
			return false;
		}
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
	}

	@Override
	protected void updateEntityActionState()
	{
		super.updateEntityActionState();

		updateAI();

	}

	public void followingPath() {

	}

	

}
