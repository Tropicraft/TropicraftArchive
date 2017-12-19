package tropicraft.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.enchanting.EnchantmentManager;

public class ItemEnchantWand extends ItemTropicraft {

	public ItemEnchantWand(int id) {
		super(id);
		maxStackSize = 1;
		this.setCreativeTab(TropiCreativeTabs.tabMisc);
	}

	@Override
	public String getImageName() {
		return "enchantwand";
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {

		double playerX = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX);
		double playerY = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) + 1.62D - (double) entityplayer.yOffset;
		double playerZ = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ);

		MovingObjectPosition target = this.getMovingObjectPositionFromPlayer(world, entityplayer, true);

		if (target == null || world.isRemote) {
			return itemstack;
		} else {
			if (target.typeOfHit == EnumMovingObjectType.TILE) {
				int x = target.blockX;
				int y = target.blockY;
				int z = target.blockZ;
				int l = world.getBlockMetadata(x, y, z);

				if (!world.canMineBlock(entityplayer, x, y, z)) {
					return itemstack;
				}

				if (!entityplayer.canPlayerEdit(x, y, z, l, itemstack)) {
					return itemstack;
				}

				List<ChunkCoordinates> enchantAreaCoords = canActivate(world, x, y, z);
				
				if (!enchantAreaCoords.isEmpty()) {
					entityplayer.swingItem();
					itemstack.damageItem(1, entityplayer);
					
					int minX = getMinX(enchantAreaCoords);
					int minZ = getMinZ(enchantAreaCoords);
					
					List<Entity> items = world.getEntitiesWithinAABB(EntityItem.class, 
							AxisAlignedBB.getBoundingBox(minX, y - 1, minZ, minX + 2, y + 1, minZ + 2));
					
					for (Entity e : items) {
						if (e instanceof EntityItem) {
							EntityItem item = (EntityItem)e;
							
							ItemStack i = item.getEntityItem();
							
							//EnchantmentData ee = EnchantmentManager.enchant(i);

							
						}
					}
				}
			}
		}

		return itemstack;
	}
	
	private int getMinX(List<ChunkCoordinates> coords) {
		int min = coords.get(0).posX;
		if (coords.get(1).posX < min)
			min = coords.get(1).posX;
		if (coords.get(2).posX < min)
			min = coords.get(2).posX;
		if (coords.get(3).posX < min)
			min = coords.get(3).posX;
		
		return min;
	}
	
	private int getMinZ(List<ChunkCoordinates> coords) {
		int min = coords.get(0).posZ;
		if (coords.get(1).posZ < min)
			min = coords.get(1).posZ;
		if (coords.get(2).posZ < min)
			min = coords.get(2).posZ;
		if (coords.get(3).posZ < min)
			min = coords.get(3).posZ;
		
		return min;
	}

	/**
	 * Can this wand activate an enchanting area? Eg, is it being clicked somewhere where it is just a 2x2 square of water?
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private List<ChunkCoordinates> canActivate(World world, int x, int y, int z) {
		List<ChunkCoordinates> waterCoords = new ArrayList<ChunkCoordinates>();

		// make sure there isn't too much water, i can't think of a shorter way
		if (!isWater(world, x, y, z) || 
				(isWater(world, x - 1, y, z) && isWater(world, x + 1, y, z)) ||
				(isWater(world, x, y, z - 1) && isWater(world, x, y, z + 1)) ||
				(isWater(world, x - 1, y, z) && isWater(world, x - 2, y, z)) ||
				(isWater(world, x + 1, y, z) && isWater(world, x + 2, y, z)) ||
				(isWater(world, x, y, z - 1) && isWater(world, x, y, z - 2)) ||
				(isWater(world, x, y, z + 1) && isWater(world, x, y, z + 2)) ||
				(isWater(world, x - 1, y, z - 1) && isWater(world, x - 1, y, z + 1)) ||
				(isWater(world, x + 1, y, z - 1) && isWater(world, x + 1, y, z + 1)) ||
				(isWater(world, x - 1, y, z - 1) && isWater(world, x + 1, y, z - 1)) ||
				(isWater(world, x - 1, y, z + 1) && isWater(world, x + 1, y, z + 1))
				)
			return waterCoords;

		// once we know there isn't too much water, check to make sure there is enough water!
		if (isWater(world, x, y, z)) {
			waterCoords.add(new ChunkCoordinates(x, y, z));
			if (isWater(world, x - 1, y, z)) {
				waterCoords.add(new ChunkCoordinates(x - 1, y, z));

				if ( (isWater(world, x, y, z + 1) && isWater(world, x - 1, y, z + 1))) {
					waterCoords.add(new ChunkCoordinates(x, y, z + 1));
					waterCoords.add(new ChunkCoordinates(x - 1, y, z + 1));
				}
				else
					if ( (isWater(world, x, y, z - 1) && isWater(world, x - 1, y, z - 1)) ) {
						waterCoords.add(new ChunkCoordinates(x, y, z - 1));
						waterCoords.add(new ChunkCoordinates(x - 1, y, z - 1));
					}
				return waterCoords;
			} else 
				if (isWater(world, x + 1, y, z)) {
					waterCoords.add(new ChunkCoordinates(x + 1, y, z));
					if ( (isWater(world, x, y, z + 1) && isWater(world, x + 1, y, z + 1))) {
						waterCoords.add(new ChunkCoordinates(x, y, z + 1));
						waterCoords.add(new ChunkCoordinates(x + 1, y, z + 1));
					}
					else 
						if ((isWater(world, x, y, z - 1) && isWater(world, x + 1, y, z - 1)) ) {
							waterCoords.add(new ChunkCoordinates(x, y, z - 1));
							waterCoords.add(new ChunkCoordinates(x + 1, y, z - 1));
						}						
				} else
					if (isWater(world, x, y, z - 1)) {
						waterCoords.add(new ChunkCoordinates(x, y, z - 1));

						if ( (isWater(world, x + 1, y, z) && isWater(world, x + 1, y, z - 1))) {
							waterCoords.add(new ChunkCoordinates(x + 1, y, z));
							waterCoords.add(new ChunkCoordinates(x + 1, y, z - 1));
						}
						else
							if ((isWater(world, x - 1, y, z) && isWater(world, x - 1, y, z - 1))) {
								waterCoords.add(new ChunkCoordinates(x - 1, y, z));
								waterCoords.add(new ChunkCoordinates(x - 1, y, z - 1));
							}
					} else
						if (isWater(world, x, y, z + 1)) {
							waterCoords.add(new ChunkCoordinates(x, y, z + 1));

							if ( (isWater(world, x + 1, y, z) && isWater(world, x + 1, y, z + 1))) {
								waterCoords.add(new ChunkCoordinates(x + 1, y, z));
								waterCoords.add(new ChunkCoordinates(x + 1, y, z + 1));
							}
							else
								if ((isWater(world, x - 1, y, z) && isWater(world, x - 1, y, z + 1))) {
									waterCoords.add(new ChunkCoordinates(x - 1, y, z));
									waterCoords.add(new ChunkCoordinates(x - 1, y, z + 1));
								}								
						}
		}

		return waterCoords;
	}

	private boolean isWater(World world, int x, int y, int z) {
		return world.getBlockId(x, y, z) == TropicraftBlocks.tropicsWaterStationary.blockID;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

}
