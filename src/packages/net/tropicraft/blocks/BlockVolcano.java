package net.tropicraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.tropicraft.blocks.tileentities.TileEntityVolcano;

public class BlockVolcano extends BlockContainer {

    public BlockVolcano(int i) {
        super(i, Material.rock);
        this.setBlockUnbreakable();
        this.setResistance(6000000.0F);
        this.setCreativeTab(null);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityVolcano();
    }
}
