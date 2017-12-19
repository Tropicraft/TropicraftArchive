package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.tropicraft.blocks.tileentities.TileEntitySifter;
import net.tropicraft.enchanting.EnchantmentManager;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

public class BlockSifter extends BlockContainer {

    public BlockSifter(int i, boolean flag) {
        super(i, Material.rock);
        this.setTextureFile("/tropicalmod/tropiterrain.png");
        setRequiresSelfNotify();
        this.setCreativeTab(TropicraftMod.tabBlock);
    }

    @Override
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return true;
    }

    @Override
    public int idDropped(int i, Random random, int j) {
        return TropicraftMod.sifter.blockID;
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int d, float f1, float f2, float f3) {
    	if (world.isRemote) {
            return true;
        }
    	
    	ItemStack stack = entityplayer.getCurrentEquippedItem();
    	
    	if (stack != null) {
    		stack.addEnchantment(EnchantmentManager.fastSwim, 10);
    	}

        TileEntitySifter tileentitysifta = (TileEntitySifter) world.getBlockTileEntity(i, j, k);

        if (tileentitysifta != null && entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().itemID == (new ItemStack(Block.sand)).itemID && !tileentitysifta.sifting) {
            entityplayer.getCurrentEquippedItem().stackSize--;
            tileentitysifta.setSifting(true);
          //  System.out.println("setting te sifta" + tileentitysifta.sifting);
        }
        return true;
    } // /o/ \o\ /o\ \o\ /o\ \o/ /o/ /o/ \o\ \o\ /o/ /o/ \o/ /o\ \o/ \o/ /o\ /o\ \o/ \o/ /o/ \o\o\o\o\o\o\o\o\o\ :D

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntitySifter();
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) {
        int l = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if (l == 0) {
            world.setBlockMetadataWithNotify(i, j, k, 2);
        }
        if (l == 1) {
            world.setBlockMetadataWithNotify(i, j, k, 5);
        }
        if (l == 2) {
            world.setBlockMetadataWithNotify(i, j, k, 3);
        }
        if (l == 3) {
            world.setBlockMetadataWithNotify(i, j, k, 4);
        }
    }

}
