package extendedrenderer.particle.entity;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extendedrenderer.ExtendedRenderer;
import extendedrenderer.particle.behavior.ParticleBehaviors;

@SideOnly(Side.CLIENT)
public class EntityRotFX extends EntityFX
{
    public boolean weatherEffect = false;

    public float spawnY = 130F;
    
    //this field and 2 methods below are for backwards compatibility with old particle system from the new icon based system
    public int particleTextureIndexInt = 0;
    
    public float brightness = 0.7F;
    
    public ParticleBehaviors pb = null;
    
    public int getParticleTextureIndex()
    {
        return this.particleTextureIndexInt;
    }
    
    public void setMaxAge(int par) {
    	particleMaxAge = par;
    }
    
    public float getAlphaF()
    {
        return this.particleAlpha;
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        
        if (pb != null) pb.tickUpdate(this);
    }
    
    public void setParticleTextureIndex(int par1)
    {
        this.particleTextureIndexInt = par1;
        if (this.getFXLayer() == 0) super.setParticleTextureIndex(par1);
    }
    
    public int getFXLayer()
    {
        return 5;
    }

    public EntityRotFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        setSize(0.3F, 0.3F);
        this.isImmuneToFire = true;
    }

    public EntityRotFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int colorIndex)
    {
        super(var1, var2, var4, var6, var8, var10, var12);
        setSize(0.3F, 0.3F);
        this.isImmuneToFire = true;
    }

    public EntityRotFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, double var14, int texIDs[])
    {
        super(var1, var2, var4, var6, var8, var10, var12);
        setSize(0.3F, 0.3F);
        this.isImmuneToFire = true;
    }

    public void spawnAsWeatherEffect()
    {
        weatherEffect = true;
        ExtendedRenderer.rotEffRenderer.addEffect(this);
        this.worldObj.addWeatherEffect(this);
    }

    public int getAge()
    {
        return particleAge;
    }

    public void setAge(int age)
    {
        particleAge = age;
    }

    public int getMaxAge()
    {
        return particleMaxAge;
    }

    public void setSize(float par1, float par2)
    {
        super.setSize(par1, par2);
    }
    
    public void setGravity(float par) {
    	particleGravity = par;
    }
    
    public float maxRenderRange() {
    	return 128F;
    }
}
