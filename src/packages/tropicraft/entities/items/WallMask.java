package tropicraft.entities.items;

import java.util.List;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityHanging;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumArt;
import net.minecraft.world.World;
import tropicraft.items.TropicraftItems;
import weather.wind.WindHandler;

public class WallMask extends EntityHanging implements WindHandler {
	
	private static final int TYPE_DW = 17;
	
	private int direction;
	public EnumMask mask;
	
	public WallMask(World world) {
		super(world);
	}

	public WallMask(World par1World, int xCoord, int yCoord, int zCoord, int direction, int shellType) {
		super(par1World, xCoord, yCoord, zCoord, direction);
		this.setShellType(shellType);
		this.direction = direction;
		
		 EnumMask[] aenumask= EnumMask.values();	       
	     this.mask = aenumask[this.getShellType()];
	     
		this.setDirection(direction);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        this.setPosition(par1, par3, par5);
        this.setRotation(par7, par8);        
    }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}
	
	@Override
	public void entityInit() {
		this.dataWatcher.addObject(TYPE_DW, new Integer(0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("SexyDirection", this.direction);
		nbt.setInteger("ShellType", this.getShellType());
		super.writeEntityToNBT(nbt);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		this.direction = nbt.getInteger("SexyDirection");
		this.setShellType(nbt.getInteger("ShellType"));
		
		  EnumMask[] aenumask= EnumMask.values();	       
	      this.mask = aenumask[this.getShellType()];

		super.readEntityFromNBT(nbt);
	}

	/** X size, I think */
	@Override
	public int func_82329_d() {
		return this.mask.sizeX;
	}

	/** Y size, I think */
	@Override
	public int func_82330_g() {
		return this.mask.sizeY;
	}

	@Override
	public void dropItemStack() {
		this.entityDropItem(new ItemStack(TropicraftItems.ashenMasks, 1, getShellType()), 0.0F);
	}
	
	public int getShellType() {
		return this.dataWatcher.getWatchableObjectInt(TYPE_DW);
	}
	
	public void setShellType(int t) {
		this.dataWatcher.updateObject(TYPE_DW, new Integer(t));
	}

	@Override
	public float getWindWeight() {
		return 999999;
	}

	@Override
	public int getParticleDecayExtra() {
		return 0;
	}

}
