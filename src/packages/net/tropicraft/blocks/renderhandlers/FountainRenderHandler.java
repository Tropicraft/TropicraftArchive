package net.tropicraft.blocks.renderhandlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.tropicraft.blocks.tileentities.TileEntityLemonSqueezer;
import net.tropicraft.mods.TropicraftMod;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class FountainRenderHandler implements ISimpleBlockRenderingHandler {

	private TileEntityLemonSqueezer dummyTileEntity = new TileEntityLemonSqueezer();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		TileEntityRenderer.instance.renderTileEntityAt(dummyTileEntity, 0.0, 0.0, 0.0, 0f);

	}

	/**
	 * Basically we want to use metadata 0 for a north-south direction wall and metadata 1 for an east-west direction wall.
	 * 
	 * Then if the block above this one is anything other than this block AND not air AND is a solid block, then use a sorta quarter
	 * blockish type stoneish texture, then fill down with water.
	 * 
	 * If the block above this block is this block, then make this entire block water (if not occupied, ofc)
	 */
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		//	Tessellator tess = Tessellator.instance;

		/*	//if north-south and solid block that isn't this block
		if (world.getBlockMetadata(x, y, z) == 0 && world.isBlockNormalCube(x, y + 1, z) && world.getBlockId(x, y + 1, z) != block.blockID) {
			renderer.setRenderMinMax(0.3, 0.7, 0, 
									 0.5, 1,   1);
		} else //if east-west and solid block that isn't this block
			if (world.getBlockMetadata(x, y, z) == 1 && world.isBlockNormalCube(x, y + 1, z) && world.getBlockId(x, y + 1, z) != block.blockID) {
				renderer.setRenderMinMax(0, 0.7, 0.3,
										 1, 1, 0.5);
			} 

		int u = 32;
		int v = 0;

		tess.setBrightness(983055);
		tess.setColorOpaque_I(0xff00ff);
		tess.addVertexWithUV(0, 0, 0, u, v);
		tess.addVertexWithUV(0, 0, 1, u, v);
		tess.addVertexWithUV(1, 0, 1, u, v);
		tess.addVertexWithUV(1, 0, 0, u, v);
		tess.addVertexWithUV(0, 1, 0, u, v);
		tess.addVertexWithUV(0, 1, 1, u, v);
		tess.addVertexWithUV(1, 1, 1, u, v);
		tess.addVertexWithUV(1, 1, 0, u, v);
		 */

		//	drawCrossedSquares(block, 0, x, y, z, 0.75f);
		drawHangingFountainMount(world, block, x, y, z, 1f, world.getBlockMetadata(x, y, z));
		return true;
	}

	public void drawHangingFountainMount(IBlockAccess world, Block block, double x, double y, double z, float scale, int meta) {
		final double shortDist = 0.3;
		final double longDist = 1 - shortDist;
		double heightDist = 0.2;
		final double shortZWater = 0.5;
		final double sideSep = 0.0;

		int rgb = block.colorMultiplier(world, (int)x, (int)y, (int)z);
		int red = (rgb >> 16) & 0xFF;
		int green = (rgb >> 8) & 0xFF;
		int blue = rgb & 0xFF;

		//if full water block
		if ((meta & 0x02) != 0) {
			if ((meta & 0x01) == 0) {

				Tessellator tess = Tessellator.instance;
				int top_tex = block.getBlockTextureFromSideAndMetadata(0, meta);

				int xCoordTex = (top_tex & 15) << 4;
				int yCoordTex = top_tex & 240;

				double texX1 = (double)((float)xCoordTex / 256.0F);
				double texX2 = (double)(((float)xCoordTex + 15.99F) / 256.0F);
				double texY1 = (double)((float)yCoordTex / 256.0F);
				double texY2 = (double)(((float)yCoordTex + 15.99F) / 256.0F);
		//		System.out.println(texX1 + " " + texX2 + " " + texY1 + " " + texY2);
				double scaleDeterminant = 0.5D * (double)scale;
				double x1 = x + 0.5D - scaleDeterminant;
				double x2 = x + 0.5D + scaleDeterminant;
				double z1 = z + 0.5D - scaleDeterminant;
				double z2 = z + 0.5D + scaleDeterminant;
				double y2 = y + 1;
				double y1 = y - 0.2;

				heightDist = 0.2;				

				tess.setBrightness(200);
				tess.setColorOpaque_I(rgb);

				point(x1 + sideSep, y2 - heightDist, z2 - shortZWater, texX2, texY1);
				point(x2 - sideSep, y2 - heightDist, z2 - shortZWater, texX1, texY1);
				point(x2 - sideSep, y1, z2 - shortZWater, texX1, texY2);
				point(x1 + sideSep, y1, z2 - shortZWater, texX2, texY2);

				//		tess.setColorOpaque_I(-16777216);
			} else {
				if ((meta & 0x01) == 1) {
					Tessellator tess = Tessellator.instance;

					int top_tex = block.getBlockTextureFromSideAndMetadata(0, meta);

					int xCoordTex = (top_tex & 15) << 4;
					int yCoordTex = top_tex & 240;

					double texX1 = (double)((float)xCoordTex / 256.0F);
					double texX2 = (double)(((float)xCoordTex + 15.99F) / 256.0F);
					double texY1 = (double)((float)yCoordTex / 256.0F);
					double texY2 = (double)(((float)yCoordTex + 15.99F) / 256.0F);
					//			System.out.println(texX1 + " " + texX2 + " " + texY1 + " " + texY2);
					double scaleDeterminant = 0.5D * (double)scale;
					double x1 = x + 0.5D - scaleDeterminant;
					double x2 = x + 0.5D + scaleDeterminant;
					double z1 = z + 0.5D - scaleDeterminant;
					double z2 = z + 0.5D + scaleDeterminant;
					double y2 = y + 1;
					double y1 = y - 0.2;

					heightDist = 0.2;	

					//		tess.setColorRGBA(255, 0, 255, 200);
					tess.setBrightness(200);
					tess.setColorOpaque_I(rgb);

					point(x2 - shortZWater, y2 - heightDist, z1 + sideSep, texX2, texY1);
					point(x2 - shortZWater, y2 - heightDist, z2 - sideSep, texX1, texY1);
					point(x2 - shortZWater, y1, z2 - sideSep, texX1, texY2);
					point(x2 - shortZWater, y1, z1 + sideSep, texX2, texY2);
				}
			}

		} else {

			if ((meta & 0x01) == 0) {

				Tessellator tess = Tessellator.instance;

				//	tess.setBrightness(255);

				ForgeHooksClient.bindTexture("/tropicalmod/tropiterrain.png", 1);

				int top_tex = TropicraftMod.chunkBlock.getBlockTextureFromSideAndMetadata(0, 0);

				//		top_tex = Block.glass.getBlockTextureFromSideAndMetadata(0, 0);

				//	top_tex = TropicraftMod.fountain.getBlockTextureFromSideAndMetadata(0, meta);

				int xCoordTex = (top_tex & 15) << 4;
				int yCoordTex = top_tex & 240;

				double texX1 = (double)((float)xCoordTex / 256.0F);
				double texX2 = (double)(((float)xCoordTex + 15.99F) / 256.0F);
				double texY1 = (double)((float)yCoordTex / 256.0F);
				double texY2 = (double)(((float)yCoordTex + 15.99F) / 256.0F);
				//		System.out.println(texX1 + " " + texX2 + " " + texY1 + " " + texY2);
				double scaleDeterminant = 0.5D * (double)scale;
				double x1 = x + 0.5D - scaleDeterminant;
				double x2 = x + 0.5D + scaleDeterminant;
				double z1 = z + 0.5D - scaleDeterminant;
				double z2 = z + 0.5D + scaleDeterminant;
				double y2 = y + 1;
				double y1 = y;

				//		tess.setColorRGBA(255, 0, 255, 200);
				//tess.setColorOpaque_F(0.3f, 0.3f, 0.3f);
				//		tess.setBrightness(255);
				//START LEFT WALL
				point(x1, y2, z2 - longDist, texX1, texY1);
				point(x1, y2, z2 - shortDist, texX1, texY2);
				point(x1, y2 - heightDist, z2 - shortDist, texX2, texY2);
				point(x1, y2 - heightDist, z2 - longDist, texX2, texY1);
				/*     
	        point(x1, y2 - heightDist, z2 - longDist, texX1, texY1);
	        point(x1, y2 - heightDist, z2 - shortDist, texX1, texY2);
	        point(x1, y2, z2 - shortDist, texX2, texY2);
	        point(x1, y2, z2 - longDist, texX2, texY1);     */    
				//END LEFT WALL
				//START RIGHT WALL
				point(x2, y2, z2 - longDist, texX1, texY1);
				point(x2, y2, z2 - shortDist, texX1, texY2);
				point(x2, y2 - heightDist, z2 - shortDist, texX2, texY2);
				point(x2, y2 - heightDist, z2 - longDist, texX2, texY1);
				/*       
	        point(x2, y2 - heightDist, z2 - longDist, texX1, texY1);
	        point(x2, y2 - heightDist, z2 - shortDist, texX1, texY2);
	        point(x2, y2, z2 - shortDist, texX2, texY2);
	        point(x2, y2, z2 - longDist, texX2, texY1);	*/
				//END RIGHT WALL
				//START FRONT WALL
				point(x1, y2, z2 - shortDist, texX1, texY1);
				point(x2, y2, z2 - shortDist, texX1, texY2);
				point(x2, y2 - heightDist, z2 - shortDist, texX2, texY2);
				point(x1, y2 - heightDist, z2 - shortDist, texX2, texY1);

				/*     point(x1, y2 - heightDist, z2 - shortDist, texX1, texY1);
	        point(x2, y2 - heightDist, z2 - shortDist, texX1, texY2);
	        point(x2, y2, z2 - shortDist, texX2, texY2);
	        point(x1, y2, z2 - shortDist, texX2, texY1);	*/
				//END FRONT WALL
				//START BACK WALL
				point(x1, y2, z2 - longDist, texX1, texY1);
				point(x2, y2, z2 - longDist, texX1, texY2);
				point(x2, y2 - heightDist, z2 - longDist, texX2, texY2);
				point(x1, y2 - heightDist, z2 - longDist, texX2, texY1);

				/*    point(x1, y2 - heightDist, z2 - longDist, texX1, texY1);
	        point(x2, y2 - heightDist, z2 - longDist, texX1, texY2);
	        point(x2, y2, z2 - longDist, texX2, texY2);
	        point(x1, y2, z2 - longDist, texX2, texY1);	*/
				//END BACK WALL
				//START TOP WALL
				point(x1, y2, z2 - longDist, texX1, texY1);
				point(x2, y2, z2 - longDist, texX1, texY2);
				point(x2, y2, z2 - shortDist, texX2, texY2);
				point(x1, y2, z2 - shortDist, texX2, texY1);

				/*   point(x1, y2, z2 - shortDist, texX1, texY1);
	        point(x2, y2, z2 - shortDist, texX1, texY2);
	        point(x2, y2, z2 - longDist, texX2, texY2);
	        point(x1, y2, z2 - longDist, texX2, texY1);        */
				//END TOP WALL
				//START BOTTOM WALL
				point(x1, y2 - heightDist, z2 - longDist, texX1, texY1);
				point(x2, y2 - heightDist, z2 - longDist, texX1, texY2);
				point(x2, y2 - heightDist, z2 - shortDist, texX2, texY2);
				point(x1, y2 - heightDist, z2 - shortDist, texX2, texY1);

				/*    point(x1, y2 - heightDist, z2 - shortDist, texX1, texY1);
	        point(x2, y2 - heightDist, z2 - shortDist, texX1, texY2);
	        point(x2, y2 - heightDist, z2 - longDist, texX2, texY2);
	        point(x1, y2 - heightDist, z2 - longDist, texX2, texY1);*/
				//END BOTTOM WALL

				ForgeHooksClient.bindTexture("/tropicalmod/fountain_sheet.png", 0); 

				int tex = block.getBlockTextureFromSideAndMetadata(0, meta);

				xCoordTex = (tex & 15) << 4;
				yCoordTex = tex & 240;

				texX1 = (double)((float)xCoordTex / 256.0F);
				texX2 = (double)(((float)xCoordTex + 15.99F) / 256.0F);
				texY1 = (double)((float)yCoordTex / 256.0F);
				texY2 = (double)(((float)yCoordTex + 15.99F) / 256.0F);
				scaleDeterminant = 0.5D * (double)scale;
				x1 = x + 0.5D - scaleDeterminant;
				x2 = x + 0.5D + scaleDeterminant;
				z1 = z + 0.5D - scaleDeterminant;
				z2 = z + 0.5D + scaleDeterminant;
				y2 = y + 1;
				y1 = y - 0.2;

				tess.setBrightness(200);
				tess.setColorOpaque_I(rgb);

				//START WATER WALL
				point(x1 + sideSep, y2 - heightDist, z2 - shortZWater, texX2, texY1);
				point(x2 - sideSep, y2 - heightDist, z2 - shortZWater, texX1, texY1);
				point(x2 - sideSep, y1, z2 - shortZWater, texX1, texY2);
				point(x1 + sideSep, y1, z2 - shortZWater, texX2, texY2);

				//     point(x1 + sideSep, y1, z2 - shortZWater, texX2, texY1);
				//    point(x2 - sideSep, y1, z2 - shortZWater, texX1, texY1);
				//    point(x2 - sideSep, y2 - heightDist, z2 - shortZWater, texX1, texY2);
				//    point(x1 + sideSep, y2 - heightDist, z2 - shortZWater, texX2, texY2);
				//END WATER WALL
			}
			else
				if ((meta & 0x01) == 1) {
					Tessellator tess = Tessellator.instance;

					//		tess.setBrightness(255);

					ForgeHooksClient.bindTexture("/tropicalmod/tropiterrain.png", 1);

					int top_tex = TropicraftMod.chunkBlock.getBlockTextureFromSideAndMetadata(0, 0);

					int xCoordTex = (top_tex & 15) << 4;
					int yCoordTex = top_tex & 240;

					double texX1 = (double)((float)xCoordTex / 256.0F);
					double texX2 = (double)(((float)xCoordTex + 15.99F) / 256.0F);
					double texY1 = (double)((float)yCoordTex / 256.0F);
					double texY2 = (double)(((float)yCoordTex + 15.99F) / 256.0F);
					System.out.println(texX1 + " " + texX2 + " " + texY1 + " " + texY2);
					double scaleDeterminant = 0.5D * (double)scale;
					double x1 = x + 0.5D - scaleDeterminant;
					double x2 = x + 0.5D + scaleDeterminant;
					double z1 = z + 0.5D - scaleDeterminant;
					double z2 = z + 0.5D + scaleDeterminant;
					double y2 = y + 1;
					double y1 = y;

					//		tess.setColorRGBA(255, 0, 255, 200);
					//    tess.setColorOpaque_F(0.3f, 0.3f, 0.3f);
					//		tess.setBrightness(255);
					//START LEFT WALL				
					point(x2 - longDist, y2, z1, texX1, texY1);
					point(x2 - shortDist, y2, z1, texX1, texY2);
					point(x2 - shortDist, y2 - heightDist, z1, texX2, texY2);
					point(x2 - longDist, y2 - heightDist, z1, texX2, texY1);

					//END LEFT WALL
					//START RIGHT WALL


					point(x2 - longDist, y2, z2, texX1, texY1);
					point(x2 - shortDist, y2, z2, texX1, texY2);
					point(x2 - shortDist, y2 - heightDist, z2, texX2, texY2);
					point(x2 - longDist, y2 - heightDist, z2, texX2, texY1);

					//END RIGHT WALL
					//START FRONT WALL


					point(x2 - shortDist, y2, z1, texX1, texY1);
					point(x2 - shortDist, y2, z2, texX1, texY2);
					point(x2 - shortDist, y2 - heightDist, z2, texX2, texY2);
					point(x2 - shortDist, y2 - heightDist, z1, texX2, texY1);

					//END FRONT WALL
					//START BACK WALL


					point(x2 - longDist, y2, z1, texX1, texY1);
					point(x2 - longDist, y2, z2, texX1, texY2);
					point(x2 - longDist, y2 - heightDist, z2, texX2, texY2);
					point(x2 - longDist, y2 - heightDist, z1, texX2, texY1);

					//END BACK WALL
					//START TOP WALL

					point(x2 - longDist, y2, z1, texX1, texY1);
					point(x2 - longDist, y2, z2, texX1, texY2);
					point(x2 - shortDist, y2, z2, texX2, texY2);
					point(x2 - shortDist, y2, z1, texX2, texY1);

					//END TOP WALL
					//START BOTTOM WALL

					point(x2 - longDist, y2 - heightDist, z1, texX1, texY1);
					point(x2 - longDist, y2 - heightDist, z2, texX1, texY2);
					point(x2 - shortDist, y2 - heightDist, z2, texX2, texY2);
					point(x2 - shortDist, y2 - heightDist, z1, texX2, texY1);

					//END BOTTOM WALL

					ForgeHooksClient.bindTexture("/tropicalmod/fountain_sheet.png", 0); 

					int tex = block.getBlockTextureFromSideAndMetadata(0, meta);

					xCoordTex = (tex & 15) << 4;
					yCoordTex = tex & 240;

					texX1 = (double)((float)xCoordTex / 256.0F);
					texX2 = (double)(((float)xCoordTex + 15.99F) / 256.0F);
					texY1 = (double)((float)yCoordTex / 256.0F);
					texY2 = (double)(((float)yCoordTex + 15.99F) / 256.0F);
					scaleDeterminant = 0.5D * (double)scale;
					x1 = x + 0.5D - scaleDeterminant;
					x2 = x + 0.5D + scaleDeterminant;
					z1 = z + 0.5D - scaleDeterminant;
					z2 = z + 0.5D + scaleDeterminant;
					y2 = y + 1;
					y1 = y - 0.2;

					tess.setBrightness(200);
					tess.setColorOpaque_I(rgb);

					//START WATER WALL

					point(x2 - shortZWater, y2 - heightDist, z1 + sideSep, texX2, texY1);
					point(x2 - shortZWater, y2 - heightDist, z2 - sideSep, texX1, texY1);
					point(x2 - shortZWater, y1, z2 - sideSep, texX1, texY2);
					point(x2 - shortZWater, y1, z1 + sideSep, texX2, texY2);

					//END WATER WALL
				}
		}

	}

	private void pointEW(double x, double y, double z, double u, double v) {
		Tessellator tess = Tessellator.instance;

		tess.addVertexWithUV(z, y, x, u, v);
	}

	private void point(double x, double y, double z, double i, double j) {
		Tessellator tess = Tessellator.instance;

		tess.addVertexWithUV(x, y, z, i, j);
	}

	/**
	 * Utility function to draw crossed squares
	 */
	public void drawCrossedSquares(Block block, int meta, double x, double y, double z, float scale)
	{
		Tessellator var10 = Tessellator.instance;
		int var11 = block.getBlockTextureFromSideAndMetadata(0, meta);


		scale = 1f;

		int xCoordTex = (var11 & 15) << 4;	//208
		//   System.out.println(xCoordTex + "xtec" + Block.waterStill.blockIndexInTexture);
		int yCoordTex = var11 & 240;			//192
		//   System.out.println(yCoordTex + "fulltex");

		//   xCoordTex -= 12;
		//   yCoordTex -=64;

		double texX1 = (double)((float)xCoordTex / 256.0F);
		double texX2 = (double)(((float)xCoordTex + 15.99F) / 256.0F);
		double texY1 = (double)((float)yCoordTex / 256.0F);
		double texY2 = (double)(((float)yCoordTex + 15.99F) / 256.0F);
		System.out.println(texX1 + " " + texX2 + " " + texY1 + " " + texY2);
		double scaleDeterminant = 0.45D * (double)scale;
		double x1 = x + 0.5D - scaleDeterminant;
		double x2 = x + 0.5D + scaleDeterminant;
		double z1 = z + 0.5D - scaleDeterminant;
		double z2 = z + 0.5D + scaleDeterminant;
		var10.addVertexWithUV(x1, y + (double)scale, z1, texX1, texY1);		//1
		var10.addVertexWithUV(x1, y + 0.0D, z1, texX1, texY2);				//2
		var10.addVertexWithUV(x2, y + 0.0D, z2, texX2, texY2);				//3
		var10.addVertexWithUV(x2, y + (double)scale, z2, texX2, texY1);		//4
		var10.addVertexWithUV(x2, y + (double)scale, z2, texX1, texY1);		//5
		var10.addVertexWithUV(x2, y + 0.0D, z2, texX1, texY2);				//6
		var10.addVertexWithUV(x1, y + 0.0D, z1, texX2, texY2);				//7
		var10.addVertexWithUV(x1, y + (double)scale, z1, texX2, texY1);		//8
		var10.addVertexWithUV(x1, y + (double)scale, z2, texX1, texY1);		//9
		var10.addVertexWithUV(x1, y + 0.0D, z2, texX1, texY2);				//10
		var10.addVertexWithUV(x2, y + 0.0D, z1, texX2, texY2);				//11
		var10.addVertexWithUV(x2, y + (double)scale, z1, texX2, texY1);		//12
		var10.addVertexWithUV(x2, y + (double)scale, z1, texX1, texY1);		//13
		var10.addVertexWithUV(x2, y + 0.0D, z1, texX1, texY2);				//14
		var10.addVertexWithUV(x1, y + 0.0D, z2, texX2, texY2);				//15
		var10.addVertexWithUV(x1, y + (double)scale, z2, texX2, texY1);		//16 	*/
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return TropicraftMod.fountainRenderId;
	}

}
