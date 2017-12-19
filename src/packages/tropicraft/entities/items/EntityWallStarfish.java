package tropicraft.entities.items;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import tropicraft.entities.passive.water.StarfishType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityWallStarfish extends EntityHanging implements IEntityAdditionalSpawnData {
	private StarfishType starfishType;

	public EntityWallStarfish(World par1World) {
		super(par1World);
		setStarfishType(StarfishType.values()[0]);
	}

	public EntityWallStarfish(World world, int xCoord, int yCoord, int zCoord, int direction, StarfishType starfishType) {
		super(world, xCoord, yCoord, zCoord, direction);
		setDirection(direction);
		setStarfishType(starfishType);
	}

	@Override
	public int getWidthPixels() {
		return 9;
	}

	@Override
	public int getHeightPixels() {
		return 9;
	}

//	@Override
	public void dropItemStack() {
		//this.entityDropItem(new ItemStack(TropicraftItems.shells, 1, getShellType()), 0.0F);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("StarfishType", (byte)getStarfishType().ordinal());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setStarfishType(StarfishType.values()[nbt.getByte("StarfishType")]);
	}

	public StarfishType getStarfishType() {
		return starfishType;
	}
	
	public void setStarfishType(StarfishType starfishType) {
		this.starfishType = starfishType;
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(xPosition);
		data.writeInt(yPosition);
		data.writeInt(zPosition);
		data.writeByte(starfishType.ordinal());
		data.writeByte(hangingDirection);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		xPosition = data.readInt();
		yPosition = data.readInt();
		zPosition = data.readInt();
		starfishType = StarfishType.values()[data.readByte()];
		setDirection(data.readByte());
	}

	@Override

	/**
	 * Called when this entity is broken. Entity parameter may be null.
	 */
	public void onBroken(Entity entity) {
		// TODO Auto-generated method stub
		this.dropItemStack();
	}
}
