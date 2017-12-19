package tropicraft.blocks.tileentities;


import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extendedrenderer.particle.ParticleRegistry;
import extendedrenderer.particle.behavior.ParticleBehaviors;
import extendedrenderer.particle.entity.EntityIconWindFX;
import extendedrenderer.particle.entity.EntityRotFX;



public class TileEntityFirePit extends TileEntity
{
	@SideOnly(Side.CLIENT)
	public ParticleBehaviors pm;
	//public List<EntityRotFX> particles = new ArrayList<EntityRotFX>();
	
	public TileEntityFirePit()
    {
		
    }
    
    public void onClicked(boolean rightClick) {
    	//MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(this.getDescriptionPacket());
    }

    public void updateEntity()
    {
    	//itemIndex = 4;
    	if (!this.worldObj.isRemote) {
	    	
    	} else {
    		if (pm == null) pm = new ParticleBehaviors(Vec3.createVectorHelper(xCoord, yCoord, zCoord));
    		tickAnimate();
    	}

        super.updateEntity();
        
    }
    
    @SideOnly(Side.CLIENT)
	public void tickAnimate() {
		
		//debug
		int amount = 5 / (Minecraft.getMinecraft().gameSettings.particleSetting+1);
		
		//System.out.println(amount);
		Random rand = new Random();
		for (int i = 0; i < amount; i++)
        {
        	double speed = 0.15D;
        	
        	EntityRotFX entityfx = pm.spawnNewParticle(worldObj, ParticleRegistry.smoke, xCoord + rand.nextDouble(), yCoord + 0.2D + rand.nextDouble() * 0.2D, zCoord + rand.nextDouble(), (rand.nextDouble() - rand.nextDouble()) * speed, 0.03D, (rand.nextDouble() - rand.nextDouble()) * speed);
        	pm.setParticleRandoms(entityfx, true, true);
        	pm.setParticleFire(entityfx);
        	entityfx.setMaxAge(100+rand.nextInt(300));
			entityfx.spawnAsWeatherEffect();
			pm.particles.add(entityfx);
			
        }
	}
    
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	try {
    		super.readFromNBT(par1NBTTagCompound);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	try {
	        super.writeToNBT(par1NBTTagCompound);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
    	this.readFromNBT(pkt.customParam1);
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 0, var1);
    }
}
