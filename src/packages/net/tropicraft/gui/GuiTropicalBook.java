package net.tropicraft.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.src.ModLoader;
import net.tropicraft.encyclopedia.Encyclopedia;
import net.tropicraft.encyclopedia.TropicalBook;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTropicalBook extends GuiScreen {

	private net.tropicraft.encyclopedia.TropicalBook book;
	private int indexPage = -1;
	private int selectedIndex = 0;
	private net.tropicraft.encyclopedia.TropicalBook.ContentMode contentMode = TropicalBook.ContentMode.INFO;
	private int contentPage = 0;
	private String coverBackground;
	private String pageBackground;
	private int closedTextureIndex;
	private int openTextureIndex;
	private RenderItem itemRenderer = new RenderItem();

	private final static int buttonNextIndexPage = 2000;
	private final static int buttonPrevIndexPage = 2001;
	private final static int buttonBookCover = 2003;
	private final static int buttonCraftingPage = 2010;
	private final static int buttonInfoPage = 2011;
	private final static int buttonNextContentPage = 2012;
	private final static int buttonPrevContentPage = 2013;

	public GuiTropicalBook(TropicalBook tropbook) {
		book = tropbook;
		coverBackground = tropbook.outsideTexture;
		pageBackground = tropbook.insideTexture;
		closedTextureIndex = ModLoader.getMinecraftInstance().renderEngine.getTexture(tropbook.outsideTexture);
		openTextureIndex = ModLoader.getMinecraftInstance().renderEngine.getTexture(tropbook.insideTexture); 
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui(){
		addButtons();
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {

		switch (guibutton.id) {
		case buttonBookCover:
			indexPage = 0;
			contentMode = TropicalBook.ContentMode.INFO;
			contentPage = 0;
			break;
		case buttonNextIndexPage:
			indexPage++;
			contentPage = 0;
			break;
		case buttonPrevIndexPage:
			indexPage--;
			contentPage = 0;
			break;
		case buttonCraftingPage:
			contentMode = TropicalBook.ContentMode.RECIPE;
			contentPage = 0;
			break;
		case buttonInfoPage:
			contentMode = TropicalBook.ContentMode.INFO;
			contentPage = 0;
			break;
		case buttonNextContentPage:
			contentPage++;
			break;
		case buttonPrevContentPage:
			contentPage--;
			break;
		default:
			// Selected a page from the index list
			selectedIndex = guibutton.id;
			if (book.isPageVisible(selectedIndex) && !book.hasPageBeenRead(selectedIndex)) {
				book.markPageAsRead(selectedIndex);
			}
			contentMode = TropicalBook.ContentMode.INFO;
			contentPage = 0;
		}
	}


	private void addButtons() {

		controlList.clear();

		if (indexPage == -1) {
			controlList.add(new GuiClearButton(buttonBookCover, 0, 0, width, height, "", 0, coverBackground, 0x440000));
		} else {

			// Add index buttons //
			int indexPosition = 0;
			for (int entry = indexPage * book.entriesPerIndexPage(); entry < (indexPage + 1) * book.entriesPerIndexPage(); entry++) {
				if (entry >= book.getPageCount()) {
					break;
				}

				String pageTitle = book.getPageTitleNotVisible(entry);
				int color = 0x440000;
				if (book.isPageVisible(entry)) {
					pageTitle = book.getPageTitleByIndex(entry);

					if (!book.hasPageBeenRead(entry)) {
						color = 0x3333ff;
					}
				}
				controlList.add(new GuiClearButton(entry, width / 2 - 129, height / 2 - 87 + (indexPosition) * 15, 90, 10, pageTitle, -1, pageBackground, color));
				indexPosition++;
			}


			// Add prev/next page for index //
			if (indexPage > 0) {
				controlList.add(new GuiClearButton(buttonPrevIndexPage , width / 2 - 168, height / 2 - 20 , 11, 22, "", 2, pageBackground, 0x440000));
			}
			if ((indexPage + 1) * book.entriesPerIndexPage() < book.getPageCount()) {
				controlList.add(new GuiClearButton(buttonNextIndexPage, width / 2 - 168, height / 2 - 50, 11, 22, "", 1, pageBackground, 0x440000));
			}

			if (indexPage >= 0) {
				// Add buttons to switch between content modes //
				if (book.hasRecipeList()) {
					switch(contentMode) {
					case INFO:
						List<ShapedRecipes> recipes = ((Encyclopedia)book).getRecipesForEntry(selectedIndex);
						if (recipes != null && recipes.size() > 0) {
							controlList.add(new GuiClearButton(buttonCraftingPage, width / 2 + 158, height / 2 - 80, 11, 22, "", 5, pageBackground, 0x440000));
						}
						break;
					case RECIPE:
						controlList.add(new GuiClearButton(buttonInfoPage, width / 2 + 158, height / 2 - 80, 11, 22, "", 6, pageBackground, 0x440000));
						break;
					default:
						break;
					}
				}

				// Add prev/next buttons for contents //
				if (contentPage > 0) {
					controlList.add(new GuiClearButton(buttonPrevContentPage, width/2 + 158, height/2 - 20 , 11, 22, "", 4, pageBackground, 0x440000));
				}

				if ((contentPage + 1) * book.entriesPerContentPage(contentMode) < book.getContentPageCount(selectedIndex, contentMode)) {
					controlList.add(new GuiClearButton(buttonNextContentPage, width / 2 + 158, height / 2 - 50, 11, 22, "", 3, pageBackground, 0x440000));
				}
			}
		}
	}

	public void addIcons() {

		int indexPosition = 0;
		for (int entry = indexPage * book.entriesPerIndexPage(); entry < (indexPage + 1) * book.entriesPerIndexPage(); entry++) {
			if (entry >= book.getPageCount()) {
				return;
			}
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			mc.renderEngine.bindTexture(openTextureIndex);
			GL11.glScalef(.75F, .75F, .75F);
			GL11.glTranslatef((float) width / 1.5F, (float) height / 1.5F, 0f);
			drawTexturedModalRect(-(int) (130 * 1.5), -(int) (81 * 1.5) + (indexPosition) * 20, 3, 190, 18, 18);
			GL11.glPopMatrix();

			if (book.isPageVisible(entry)) {
				RenderHelper.disableStandardItemLighting();
				GL11.glPushMatrix();
				GL11.glScalef(.75F, .75F, .75F);
				GL11.glTranslatef((float) width / 1.5F - 1F, (float) height / 1.5F, 0f);
				itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, book.getPageItemStack(entry), -(int) (129 * 1.5), -(int) (80 * 1.5) + (indexPosition) * 20);
				GL11.glPopMatrix();
			}
			indexPosition++;
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int mousebutton) {
		if (mousebutton == 0) {
			for (int l = 0; l < controlList.size(); l++) {
				GuiButton guibutton = (GuiButton) controlList.get(l);
				if (guibutton.mousePressed(mc, x, y)) {
					mc.sndManager.playSoundFX("pageFlip", 1.0F, 1.0F);
					actionPerformed(guibutton);
				}
			}

		}
	}

	@Override
	public void handleKeyboardInput() {
		super.handleKeyboardInput();
		if (Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == mc.gameSettings.keyBindInventory.keyCode) {
				mc.displayGuiScreen(new GuiInventory(mc.thePlayer));
				return;
			}
			keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
		}
	}


	@Override
	public void drawScreen(int i, int j, float f) {
		System.out.println(width + " " + height);
		drawDefaultBackground();

		if (indexPage == -1) {
			// Draw outer book cover //
			float f1 = 1.35F;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			mc.renderEngine.bindTexture(closedTextureIndex);
			GL11.glTranslatef(width / 2, height / 2, 0F);
			GL11.glScalef(f1, f1, f1);
			drawTexturedModalRect(-64, -86, 0, 0, 128, 173);
		} else {
			// Draw table of contents //
			float f2 = 1.35F;
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			mc.renderEngine.bindTexture(openTextureIndex);
			GL11.glTranslatef(width / 2, height / 2, 0F);
			GL11.glScalef(f2, f2, f2);
			drawTexturedModalRect(-128, -88, 0, 0, 256, 176);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			mc.renderEngine.bindTexture(openTextureIndex);
			drawTexturedModalRect(width / 2 - 162, height / 2 - 115, 145, 201, 111, 32);
			GL11.glPopMatrix();
			fontRenderer.drawString("Table of Contents", width / 2 - 150, height / 2 - 110, 0x440000);
			if (book.hasIndexIcons()) {
				addIcons();
			}

			// Draw content for selected index page //
			if (book.isPageVisible(selectedIndex)) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				switch (contentMode) {
				case INFO:
					String pageTitle = book.getPageTitleByIndex(selectedIndex);
					fontRenderer.drawString(pageTitle, width / 2 + 150 - fontRenderer.getStringWidth(pageTitle), height / 2 - 110, 0x440000);
					fontRenderer.drawSplitString("  " + book.getPageDescriptionsByIndex(selectedIndex), width / 2 + 20, height / 2 - 80, 135, 0x440000);
					break;
				case RECIPE:
					fontRenderer.drawString("Crafting", width / 2 + 110, height / 2 - 110, 0x440000);
					try {
						printRecipes();

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				default:
					break;
				}

				GL11.glPushMatrix();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				mc.renderEngine.bindTexture(openTextureIndex);
				drawTexturedModalRect(width / 2 + 20, height / 2 - 115, 90, 201, 142, 32);
				GL11.glPopMatrix();
				if (book.hasIndexIcons()) {
					GL11.glPushMatrix();
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					GL11.glTranslatef(width / 3F + .6F, height / 3F - 1.2F, 0F);
					itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, book.getPageItemStack(selectedIndex), 15, -73);
					GL11.glPopMatrix();
				}
			}
		}

		addButtons();
		super.drawScreen(i, j, f);
	}

	private void printRecipes() throws Exception {

		List<ShapedRecipes> recipes = ((Encyclopedia)book).getRecipesForEntry(selectedIndex);
		if (recipes == null || recipes.isEmpty()) {
			return;
		}
		int newx = width / 2 + 25;
		int newy = height / 2 - 80;

		int indexPosition = 0;
		for (int entry = contentPage * book.entriesPerContentPage(contentMode); entry < (contentPage + 1) * book.entriesPerContentPage(contentMode); entry++) {
			if (entry >= book.getContentPageCount(selectedIndex, contentMode)) {
				return;
			}
			Encyclopedia.RecipeEntry recipe = ((Encyclopedia)book).getFormattedRecipe(recipes.get(entry));            

			// Draw recipe frame //
			mc.renderEngine.bindTexture(openTextureIndex);
			drawTexturedModalRect(newx - 3, newy - 3, 0, 187, 122, 60);

			int offsetX = 18;
			int offsetY = 18;

			// Draw recipe ingredients //
			for(int row = 0; row < recipe.height; row++) {
				for (int col = 0; col < recipe.width; col++) {
					int itemIndex = (row * recipe.width) + col;
					if (recipe.ingredients[itemIndex] != null) {
						int renderX = newx + (offsetX * col) + 1;
						int renderY = newy + (offsetY * row) + 1;
						//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						RenderHelper.disableStandardItemLighting();
						itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, recipe.ingredients[itemIndex], renderX, renderY);
						RenderHelper.enableStandardItemLighting();

						checkMouseHover(recipe.ingredients[itemIndex], renderX, renderY, 18);
					}
				}
			}

			// Draw item label if mouse is hovering over an item
			/*         for (int row = 0; row < recipe.height; row++) {
                for (int col = 0; col < recipe.width; col++) {
                    int itemIndex = (row * recipe.width) + col;
                    int renderX = newx + (offsetX * col) + 1;
                    int renderY = newy + (offsetY * row) + 1;
                    checkMouseHover(recipe.ingredients[itemIndex], renderX, renderY, 18);
                }
            }	*/

			// Draw recipe output ItemStack //
			GL11.glPushMatrix();
			GL11.glScalef(1.5F, 1.5F, 1.5F);
			GL11.glTranslatef(newx / 3F + 1F, newy / 3F - .75F, 0F);
			RenderHelper.disableStandardItemLighting();
			itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, recipe.output, newx / 3 + 60, newy / 3 + 11);
			itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, recipe.output, newx / 3 + 60, newy / 3 + 11);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			checkMouseHover(recipe.output, newx + 90, newy + 20, 25);
			GL11.glPopMatrix();

			indexPosition++;
			newy += 62;
		}        
	}


	private void checkMouseHover(ItemStack itemstack, int k, int l, int size) {

		float scale;
		switch (mc.gameSettings.guiScale) {
		case 1:
			scale = 4F;
			break;
		case 2:
			scale = 2F;
			break;
		case 3:
			scale = 3F;
			break;
		default:
			scale = 2F;
		}

		System.out.println(mc.gameSettings.guiScale);

		int i = (int) (Mouse.getEventX() / scale);
		int j = (int) (height - Mouse.getEventY() / scale);
		boolean flag = (i >= k && j >= l && i < k + size && j < l + size);
		System.out.println("" + i + " " + j + " " + width + " " + height);
		//      System.out.println((i >= k) + " " + (j >=l) + " " + (i < k + size) + " " + (j < l + size));
		if (itemstack != null && flag) {
			String s = itemstack.getItem().getItemDisplayName(itemstack);
			if (s.length() > 0) {
				RenderHelper.disableStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				int l2 = fontRenderer.getStringWidth(s);
				int i2 = i - l2 - 4;
				int k2 = j;
				drawGradientRect(i2 - 3, k2 - 3, i2 + l2 + 3, k2 + 8 + 3, 0xc0000000, 0xc0000000);
				fontRenderer.drawStringWithShadow(s, i2, k2, -1);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			} 
			System.out.println(" doop " + i + " " + j + " " + width + " " + height);
			//		drawItemStackTooltip(itemstack, i, j);
		}
	}

	protected void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3)
	{
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		List var4 = par1ItemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

		if (!var4.isEmpty())
		{
			int var5 = 0;
			int var6;
			int var7;

			for (var6 = 0; var6 < var4.size(); ++var6)
			{
				var7 = this.fontRenderer.getStringWidth((String)var4.get(var6));

				if (var7 > var5)
				{
					var5 = var7;
				}
			}

			var6 = par2 + 12;
			var7 = par3 - 12;
			int var9 = 8;

			if (var4.size() > 1)
			{
				var9 += 2 + (var4.size() - 1) * 10;
			}

			/*        if (this.guiTop + var7 + var9 + 6 > this.height)
            {
                var7 = this.height - var9 - this.guiTop - 6;
            } */

			this.zLevel = 300.0F;
			itemRenderer.zLevel = 300.0F;
			int var10 = -267386864;
			this.drawGradientRect(var6 - 3, var7 - 4, var6 + var5 + 3, var7 - 3, var10, var10);
			this.drawGradientRect(var6 - 3, var7 + var9 + 3, var6 + var5 + 3, var7 + var9 + 4, var10, var10);
			this.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 + var9 + 3, var10, var10);
			this.drawGradientRect(var6 - 4, var7 - 3, var6 - 3, var7 + var9 + 3, var10, var10);
			this.drawGradientRect(var6 + var5 + 3, var7 - 3, var6 + var5 + 4, var7 + var9 + 3, var10, var10);
			int var11 = 1347420415;
			int var12 = (var11 & 16711422) >> 1 | var11 & -16777216;
			this.drawGradientRect(var6 - 3, var7 - 3 + 1, var6 - 3 + 1, var7 + var9 + 3 - 1, var11, var12);
			this.drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, var6 + var5 + 3, var7 + var9 + 3 - 1, var11, var12);
			this.drawGradientRect(var6 - 3, var7 - 3, var6 + var5 + 3, var7 - 3 + 1, var11, var11);
			this.drawGradientRect(var6 - 3, var7 + var9 + 2, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

			for (int var13 = 0; var13 < var4.size(); ++var13)
			{
				String var14 = (String)var4.get(var13);

				if (var13 == 0)
				{
					var14 = "\u00a7" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + var14;
				}
				else
				{
					var14 = "\u00a77" + var14;
				}

				this.fontRenderer.drawStringWithShadow(var14, var6, var7, -1);

				if (var13 == 0)
				{
					var7 += 2;
				}

				var7 += 10;
			}

			this.zLevel = 0.0F;
			itemRenderer.zLevel = 0.0F;
		}
	}
}
