package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

public class BlockTropicalStone extends TropicraftBlock {

    public BlockTropicalStone(int i) {
        super(i, Material.rock);
        this.setCreativeTab(TropicraftMod.tabBlock);
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, World world, int par3, int par4, int par5) {
        return entityplayer.getCurrentPlayerStrVsBlock(Block.stone, 0) / blockHardness / 30F;
    }
    
    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
        if ((metadata & 1) > 0) {
            // if this side is the one with the slot on it
            if (side > 1 && (side - 2) == (metadata & 6) >> 1) {
                if ((metadata & 8) == 0) {
                    return 11;//empty azurite slot
                } else {
                    return 10;//filled azurite slot
                }
                
            } else {
                return 9;//default stone texture
            }
            
        }
        
        return blockIndexInTexture;
    }

    @Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        // TODO: Add player interaction with azurite
        super.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
    }

    @Override
    public int idDropped(int metadata, Random par2Random, int par3) {
        
        if ((metadata & 1) > 0) {
            return Block.stoneBrick.blockID;
        } else {
            return this.blockID;
        }
    }

    @Override
    public int damageDropped(int metadata) {
        if ((metadata & 1) > 0) {
            // Change for a different type of stone brick to drop (3 = square pattern)
            return 3;
        } else {
            // Chunk O' Head damage value
            return 0;
        }
    }
    
    
}
