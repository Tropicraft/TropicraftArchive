package net.tropicraft.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tropicraft.enums.TropicraftOres;

public class BlockTropicraftOreBlock extends BlockTropicraft implements ITropicraftBlock {

    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", TropicraftOres.class);
    public String[] names;
    
    @Override
    protected BlockState createBlockState() {
    	return new BlockState(this, new IProperty[] { VARIANT });
    }
    
    public String getStateName(IBlockState state) {
        return ((TropicraftOres) state.getValue(VARIANT)).getName();
    }
	
	public BlockTropicraftOreBlock(String[] names) {
		super(Material.rock);
		this.names = names;
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(Block.soundTypeStone);
        this.setHarvestLevel("pickaxe", 2);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, TropicraftOres.ZIRCON));
	}
	
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {        
        for (int i = 0; i < names.length; i++) {
        	list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, TropicraftOres.values()[meta]);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        return ((TropicraftOres) state.getValue(VARIANT)).ordinal();
    }
    
    @Override
    public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state);
    }

	@Override
	public IProperty[] getProperties() {
		return new IProperty[] {VARIANT};
	}
}
