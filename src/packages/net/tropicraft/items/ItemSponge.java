package net.tropicraft.items;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.entities.EntityParticleWave;

import org.lwjgl.util.glu.Sphere;

public class ItemSponge extends ItemTropicraft {

	int maxCharge = 100;
	
	public ItemSponge(int i) {
		super(i);
		this.maxStackSize = 1;
		this.setMaxDamage(2000);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		
		if (!par5) return;
		
		//cancel featrure
		if (true) return;
		
		if (par1ItemStack.stackTagCompound == null) par1ItemStack.stackTagCompound = new NBTTagCompound();
		if (!par1ItemStack.stackTagCompound.hasKey("charge")) {
			par1ItemStack.stackTagCompound.setInteger("charge", 0);
		}
		int curCharge = par1ItemStack.stackTagCompound.getInteger("charge");
		if (curCharge < maxCharge) {
			par1ItemStack.stackTagCompound.setInteger("charge", ++curCharge);
		}
		
		int curDelay = par1ItemStack.stackTagCompound.getInteger("delay");
		
		if (!par2World.isRemote) {
			if (!par1ItemStack.stackTagCompound.hasKey("delay") || curDelay <= 0) {
				if (((EntityPlayer)par3Entity).swingProgress > 0) {
					createWave(par1ItemStack, par2World, par3Entity, curCharge);
				}
			} else {
				par1ItemStack.stackTagCompound.setInteger("delay", curDelay - 1);
			}
			
		}
		
		//System.out.println("hue: " + curDelay);
	}
	
	public void createWave(ItemStack par1ItemStack, World par2World, Entity par3Entity, int charge) {
		par1ItemStack.stackTagCompound.setInteger("delay", 10);
		par1ItemStack.stackTagCompound.setInteger("charge", 0);
		
		par1ItemStack.damageItem(5, (EntityPlayer)par3Entity);
		
		System.out.println("Attack: " + par1ItemStack.getItemDamage());
		
		EntityParticleWave var4 = new EntityParticleWave(par2World);
		Entity var3 = par3Entity;
		
		double var5 = var3.posY - (double)var3.yOffset + 0.0D;
        ((Entity)var4).setLocationAndAngles(var3.posX, var5, var3.posZ, var3.rotationYaw, var3.rotationPitch);
        
        ((Entity)var4).motionX = 1.0D * (double)(-MathHelper.sin(((Entity)var4).rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(((Entity)var4).rotationPitch / 180.0F * (float)Math.PI));
        ((Entity)var4).motionZ = 1.0D * (double)(MathHelper.cos(((Entity)var4).rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(((Entity)var4).rotationPitch / 180.0F * (float)Math.PI));
        ((Entity)var4).motionY = 0.0D;
        ((Entity)var4).posX += ((Entity)var4).motionX * 2.5D;
        ((Entity)var4).posZ += ((Entity)var4).motionZ * 2.5D;
        
        par2World.spawnEntityInWorld(var4);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer){//, int x, int y, int z, int side, float par8, float par9, float par10) {
		double inc = Math.PI/12;
		if(!world.isRemote){
			for(double lat = 0; lat < 2*Math.PI; lat += inc){
				for( double lng = 0; lng < 2*Math.PI; lng += inc){
					for(double len = 1; len < 3; len +=.5D){
						int x1 = (int)(Math.cos(lat)*len);
						int z1 = (int)(Math.sin(lat)*len);
						int y1 = (int)(Math.sin(lng)*len);
						if(!removeWater(world, itemstack, entityplayer, (int)entityplayer.posX + x1, (int)(entityplayer.posY) + y1, (int)(entityplayer.posZ) + z1)){
							break;
						}
					}
				}
				
			}
			entityplayer.setItemInUse(itemstack, 1);
		}
		
		return itemstack;
//    	if(!world.isRemote){
//    		System.out.println("Side = " + side);
//    		if(side == 3){
//    			for(int z1 = 1; z1 < 3; z1++){
//    				for(int x1 = -2; x1 < 2; x1 ++){
//    					for(int y1 = -2;y1 < 2;y1 ++){
//    						removeWater(world,itemstack, entityplayer, x + x1, y + y1 , z + z1);
//    					}}}}
//    		if(side == 2){
//    			for(int z1 = -1; z1 > -3; z1--){
//    				for(int x1 = -2; x1 < 2; x1 ++){
//    					for(int y1 = -2;y1 < 2;y1 ++){
//    						removeWater(world,itemstack, entityplayer, x + x1, y + y1 , z + z1);
//    					}}}}
//    		if(side == 5){
//    			for(int z1 = -1; z1 < 3; z1++){
//    				for(int x1 = 1; x1 < 3; x1 ++){
//    					for(int y1 = -2;y1 < 2;y1 ++){
//    						removeWater(world,itemstack, entityplayer, x + x1, y + y1 , z + z1);
//    					}}}}
//    		if(side == 4){
//    			for(int z1 = -1; z1 < 3; z1++){
//    				for(int x1 = -1; x1 > -3; x1 --){
//    					for(int y1 = -2;y1 < 2;y1 ++){
//    						removeWater(world,itemstack, entityplayer, x + x1, y + y1 , z + z1);
//    					}}}}
//    		if(side == 1){
//    			for(int z1 = -1; z1 < 3; z1++){
//    				for(int x1 = -2; x1 < 2; x1++){
//    					for(int y1 = 1; y1 < 3; y1++){
//    						removeWater(world,itemstack, entityplayer, x + x1, y + y1 , z + z1);
//    					}}}}
//    		if(side == 0){
//    			for(int z1 = -1; z1 < 3; z1++){
//    				for(int x1 = -2; x1 < 2; x1++){
//    					for(int y1 = -2; y1 < 0; y1++){
//    						removeWater(world,itemstack, entityplayer, x + x1, y + y1 , z + z1);
//    					}}}}
//    		itemstack.damageItem(1, entityplayer);
//    		return true;
//    		}
//    	return false;
    }

    private boolean removeWater(World world, ItemStack itemstack, EntityPlayer player, int x, int y, int z){
    	//System.out.println("Should be chacking");
    	if(!world.isRemote){
    		   		
	    	if(world.getBlockMaterial(x, y, z) == Material.water){
	    		//System.out.println("Should remove block");
	    		itemstack.damageItem(1, player);
	    		world.setBlockWithNotify(x, y, z, 0);
	    		return true;
	    	}
	    	if(world.getBlockId(x,y,z) == 0){
	    		return true;
	    	}
    	}
    	
    	return false;
    }
   
    private void DrawSphere(double r, int lats, int longs, World world, ItemStack itemstack, EntityPlayer player)
    {
        int i, j;
        for (i = 0; i <= lats; i++)
        {
            double lat0 = Math.PI * (-0.5 + (double)(i - 1) / lats);
            double z0 = Math.sin(lat0)*r;
            double zr0 = Math.cos(lat0)*r;

            double lat1 = Math.PI * (-0.5 + (double)i / lats);
            double z1 = Math.sin(lat1)*r;
            double zr1 = Math.cos(lat1)*r;

            for (j = 0; j <= longs; j++)
            {
                double lng = 2 * Math.PI * (double)(j - 1) / longs;
                double x = Math.cos(lng)*r;
                double y = Math.sin(lng)*r;

               //removeWater(int(player.posX + x), int(player.posY + y))
            }
        }
    }
}
