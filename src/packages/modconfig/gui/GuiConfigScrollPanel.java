package modconfig.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import modconfig.ConfigMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;

@SideOnly(Side.CLIENT)
public class GuiConfigScrollPanel extends GuiBetterSlot
{
    private GuiConfigEditor config;
    private Minecraft mc;
    private String[] message;
    private int _mouseX;
    private int _mouseY;
    private int selected = -1;

    public GuiConfigScrollPanel(GuiConfigEditor controls, Minecraft mc, int startX, int startY, int height, int slotSize)
    {
        //super(mc, controls.width, controls.height, 16, (controls.height - 32) + 4, 25);
        super(mc, controls.width + 100, controls.height, startY + 8 + slotSize, height + slotSize - 2, slotSize);
        this.config = controls;
        this.mc = mc;
    }

    @Override
    protected int getSize()
    {
        return config.getData().configData.size();
    }

    @Override
    protected void elementClicked(int i, boolean flag)
    {
    	//HostileWorlds.dbg("wew: " + flag);
        if (!flag)
        {
        	selected = i;
        	KeyBinding.resetKeyBindingArrayAndHash();
        	/*if (selected == -1)
            {
                selected = i;
                
                //config.configData.get(selected).editBox.textboxKeyTyped(c, i);
            }
            else
            {
                //options.setKeyBinding(selected, -100);
                selected = -1;
                KeyBinding.resetKeyBindingArrayAndHash();
            }*/
        }
    }
    
    protected void mouseClicked(int par1, int par2, int par3)
    {
    	boolean anyHasFocus = false;
    	for (int i = 0; i < config.getData().configData.size(); i++) {
    		
    		config.getData().configData.get(i).editBox.mouseClicked(par1, par2, par3);
    		if (config.getData().configData.get(i).editBox.isFocused()) {
    			anyHasFocus = true;
    		}
    		/*if (check && !config.configData.get(i).editBox.isFocused()) {
    			String str1 = config.configData.get(i).editBox.text;
    			String str2 = config.configData.get(i).value.toString();
    			if (!config.configData.get(i).editBox.text.equals(config.configData.get(i).value.toString())) {
    				config.configData.get(i).markForUpdate = true;
    			}
    		}*/
    				
    		//}
    	}
    	if (!anyHasFocus) {
    		//selected = -1;
    	}
    	/*if (selected != -1)
        {
    		config.configData.get(selected).editBox.mouseClicked(par1, par2, par3);
        }*/
    }

    @Override
    protected boolean isSelected(int i)
    {
        return false;
    }

    @Override
    protected void drawBackground() {}
    
    @Override
    protected void drawContainerBackground(Tessellator tess) {
    	/*this.mc.renderEngine.bindTexture(BACKGROUND_IMAGE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float height = 32.0F;
        
        int startX = (this.width - config.xSize) / 2;
        int startY = (this.height - config.ySize) / 2;
        int left = startX - 50 + 3;
        int right = left + config.xSize - 6;
        
        tess.startDrawingQuads();
        tess.setColorOpaque_I(2105376);
        tess.addVertexWithUV((double)left,  (double)bottom, 0.0D, (double)(left  / height), (double)((bottom + (int)amountScrolled) / height));
        tess.addVertexWithUV((double)right, (double)bottom, 0.0D, (double)(right / height), (double)((bottom + (int)amountScrolled) / height));
        tess.addVertexWithUV((double)right, (double)top,    0.0D, (double)(right / height), (double)((top    + (int)amountScrolled) / height));
        tess.addVertexWithUV((double)left,  (double)top,    0.0D, (double)(left  / height), (double)((top    + (int)amountScrolled) / height));
        tess.draw();*/
    }

    @Override
    public void drawScreen(int mX, int mY, float f)
    {
        _mouseX = mX;
        _mouseY = mY;

        if (selected != -1 && !Mouse.isButtonDown(0) && Mouse.getDWheel() == 0)
        {
            if (Mouse.next() && Mouse.getEventButtonState())
            {
                //System.out.println(Mouse.getEventButton());
                //options.setKeyBinding(selected, -100 + Mouse.getEventButton());
                selected = -1;
                KeyBinding.resetKeyBindingArrayAndHash();
            }
        }

        try {
        	super.drawScreen(mX, mY, f);
        } catch (Exception ex) {
        	ConfigMod.dbg("exception drawing screen elements");
        }
    }

    @Override
    protected void drawSlot(int index, int xPosition, int yPosition, int l, Tessellator tessellator)
    {
        int width = 70;
        int height = slotHeight;
        xPosition -= 20;
        boolean flag = _mouseX >= xPosition && _mouseY >= yPosition && _mouseX < xPosition + width && _mouseY < yPosition + height;
        int k = (flag ? 2 : 1);

        mc.renderEngine.bindTexture("/gui/gui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //config.drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2, height);
        //config.drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + k * 20, width / 2, height);
        int stringWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(config.getData().configData.get(index).name);
        config.drawString(mc.fontRenderer, config.getData().configData.get(index).name/*options.getKeyBindingDescription(index)*/, xPosition - stringWidth + 15/* + width + 4*/, yPosition + 3, 0xFFFFFFFF);

        boolean conflict = false;
        /*for (int x = 0; x < options.keyBindings.length; x++)
        {
            if (x != index && options.keyBindings[x].keyCode == options.keyBindings[index].keyCode)
            {
                conflict = true;
                break;
            }
        }*/
        
        String value = config.getData().configData.get(index).value.toString();
        int maxWidth = (config.xSize / 2) - 45;
        //int valWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(value);
        
        value = Minecraft.getMinecraft().fontRenderer.trimStringToWidth(value, maxWidth);

        String str = (conflict ? EnumChatFormatting.RED : "") + value;//options.getOptionDisplayString(index);
        str = (index == selected ? EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<" : str);
        //config.drawString(mc.fontRenderer, str, xPosition + 20/* + (width / 2)*/, yPosition + (height - 8) / 2, 0xFFFFFFFF);
        config.getData().configData.get(index).editBox.xPos = xPosition + 20;
        config.getData().configData.get(index).editBox.yPos = yPosition/* + (height - 8) / 2*/;
        //config.configData.get(index).editBox.text = config.configData.get(index).value.toString();
        config.getData().configData.get(index).editBox.drawTextBox();
    }

    public boolean keyTyped(char c, int i)
    {
    	//System.out.println(i);
        if (selected != -1 && config.getData().configData.get(selected).editBox.isFocused()/* && i != 28*/)
        {
        	config.getData().configData.get(selected).editBox.textboxKeyTyped(c, i);
        	if (i == 28) {
        		selected = -1;
        		return true;
        	}
        	//config.configData.get(selected).value = config.configData.get(selected).editBox.text;
            //options.setKeyBinding(selected, i);
            //selected = -1;
            //KeyBinding.resetKeyBindingArrayAndHash();
            return false;
        } else {
        	
        }
        return true;
    }
}
