package tropicraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSandStone;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockPortalWall extends BlockSandStone {

    public BlockPortalWall(int par1)
    {
        super(par1);
        this.setBlockUnbreakable();
        this.setResistance(6000000.0F);
        this.setCreativeTab(null);
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int doop, float doo, float doe, float doa) {
        if (par5EntityPlayer.getCurrentEquippedItem() != null &&
                par5EntityPlayer.getCurrentEquippedItem().getItem() instanceof ItemBucket) {
            return true;
        }

        return false;
    }
}