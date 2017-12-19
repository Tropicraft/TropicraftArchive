package net.tropicraft.blocks.tileentities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.tropicraft.blocks.BlockLemonSqueezer.FruitType;

public class ModelLemonSqueezer extends ModelBase {
	public ModelRenderer base;
	public ModelRenderer edge1;
	public ModelRenderer edge2;
	public ModelRenderer edge3;
	public ModelRenderer edge4;
	public ModelRenderer squeezertip;
	public ModelRenderer squeezerbase;
	public ModelRenderer orangejuice;
	public ModelRenderer lemonjuice;
	public ModelRenderer lemonfruit;
	public ModelRenderer limejuice;
	public ModelRenderer grapefruitjuice;
	public ModelRenderer limefruit;
	public ModelRenderer orangefruit;
	public ModelRenderer grapefruitfruit;

	public boolean renderFruit;
	public boolean renderJuice;
	public FruitType fruitType;
	public int ticks;

	public ModelLemonSqueezer() {
		textureWidth = 128;
		textureHeight = 64;

		base = new ModelRenderer(this, 0, 0);
		base.addBox(-8F, 16F, -8F, 16, 8, 16);
		base.setRotationPoint(0F, 0F, 0F);
		base.setTextureSize(128, 64);
		base.mirror = true;
		setRotation(base, 0F, 0F, 0F);
		edge1 = new ModelRenderer(this, 64, 0);
		edge1.addBox(-8F, 14F, -8F, 16, 2, 2);
		edge1.setRotationPoint(0F, 0F, 0F);
		edge1.setTextureSize(128, 64);
		edge1.mirror = true;
		setRotation(edge1, 0F, 0F, 0F);
		edge2 = new ModelRenderer(this, 64, 0);
		edge2.addBox(-8F, 14F, 6F, 16, 2, 2);
		edge2.setRotationPoint(0F, 0F, 0F);
		edge2.setTextureSize(128, 64);
		edge2.mirror = true;
		setRotation(edge2, 0F, 0F, 0F);
		edge3 = new ModelRenderer(this, 100, 0);
		edge3.addBox(6F, 14F, -6F, 2, 2, 12);
		edge3.setRotationPoint(0F, 0F, 0F);
		edge3.setTextureSize(128, 64);
		edge3.mirror = true;
		setRotation(edge3, 0F, 0F, 0F);
		edge4 = new ModelRenderer(this, 100, 0);
		edge4.addBox(-8F, 14F, -6F, 2, 2, 12);
		edge4.setRotationPoint(0F, 0F, 0F);
		edge4.setTextureSize(128, 64);
		edge4.mirror = true;
		setRotation(edge4, 0F, 0F, 0F);
		squeezertip = new ModelRenderer(this, 80, 8);
		squeezertip.addBox(-1F, 10F, -1F, 2, 2, 2);
		squeezertip.setRotationPoint(0F, 0F, 0F);
		squeezertip.setTextureSize(128, 64);
		squeezertip.mirror = true;
		setRotation(squeezertip, 0F, 0F, 0F);
		squeezerbase = new ModelRenderer(this, 64, 8);
		squeezerbase.addBox(-2F, 12F, -2F, 4, 4, 4);
		squeezerbase.setRotationPoint(0F, 0F, 0F);
		squeezerbase.setTextureSize(128, 64);
		squeezerbase.mirror = true;
		setRotation(squeezerbase, 0F, 0F, 0F);
		orangejuice = new ModelRenderer(this, 0, 49);
		orangejuice.addBox(-6F, 15F, -6F, 12, 1, 12);
		orangejuice.setRotationPoint(0F, 0F, 0F);
		orangejuice.setTextureSize(128, 64);
		orangejuice.mirror = true;
		setRotation(orangejuice, 0F, 0F, 0F);
		lemonjuice = new ModelRenderer(this, 0, 36);
		lemonjuice.addBox(-6F, 15F, -6F, 12, 1, 12);
		lemonjuice.setRotationPoint(0F, 0F, 0F);
		lemonjuice.setTextureSize(128, 64);
		lemonjuice.mirror = true;
		setRotation(lemonjuice, 0F, 0F, 0F);
		lemonfruit = new ModelRenderer(this, 0, 24);
		lemonfruit.addBox(-3F, 8F, -3F, 6, 6, 6);
		lemonfruit.setRotationPoint(0F, 0F, 0F);
		lemonfruit.setTextureSize(128, 64);
		lemonfruit.mirror = true;
		setRotation(lemonfruit, 0F, 0F, 0F);
		limejuice = new ModelRenderer(this, 48, 36);
		limejuice.addBox(-6F, 15F, -6F, 12, 1, 12);
		limejuice.setRotationPoint(0F, 0F, 0F);
		limejuice.setTextureSize(128, 64);
		limejuice.mirror = true;
		setRotation(limejuice, 0F, 0F, 0F);
		grapefruitjuice = new ModelRenderer(this, 48, 49);
		grapefruitjuice.addBox(-6F, 15F, -6F, 12, 1, 12);
		grapefruitjuice.setRotationPoint(0F, 0F, 0F);
		grapefruitjuice.setTextureSize(128, 64);
		grapefruitjuice.mirror = true;
		setRotation(grapefruitjuice, 0F, 0F, 0F);
		limefruit = new ModelRenderer(this, 24, 24);
		limefruit.addBox(-3F, 8F, -3F, 6, 6, 6);
		limefruit.setRotationPoint(0F, 0F, 0F);
		limefruit.setTextureSize(128, 64);
		limefruit.mirror = true;
		setRotation(limefruit, 0F, 0F, 0F);
		orangefruit = new ModelRenderer(this, 48, 24);
		orangefruit.addBox(-3F, 8F, -3F, 6, 6, 6);
		orangefruit.setRotationPoint(0F, 0F, 0F);
		orangefruit.setTextureSize(128, 64);
		orangefruit.mirror = true;
		setRotation(orangefruit, 0F, 0F, 0F);
		grapefruitfruit = new ModelRenderer(this, 72, 24);
		grapefruitfruit.addBox(-3F, 8F, -3F, 6, 6, 6);
		grapefruitfruit.setRotationPoint(0F, 0F, 0F);
		grapefruitfruit.setTextureSize(128, 64);
		grapefruitfruit.mirror = true;
		setRotation(grapefruitfruit, 0F, 0F, 0F);
	}

	public void renderLemonSqueezer() {
		float f5 = 0.0625F;
		base.render(f5);
		edge1.render(f5);
		edge2.render(f5);
		edge3.render(f5);
		edge4.render(f5);
		squeezertip.render(f5);
		squeezerbase.render(f5);
		
		if (renderJuice) {
			switch (fruitType) {
			case LEMON:
				lemonjuice.render(f5);
				break;
			case LIME:
				limejuice.render(f5);
				break;
			case ORANGE:
				orangejuice.render(f5);
				break;
			case GRAPEFRUIT:
				grapefruitjuice.render(f5);
			}
		}
		
		if (renderFruit) {
			GL11.glPushMatrix();
			float angle = (270/20)*(ticks%20);
			GL11.glRotatef(angle, 0f, 1f, 0f);
			switch (fruitType) {
			case LEMON:
				lemonfruit.render(f5);
				break;
			case LIME:
				limefruit.render(f5);
				break;
			case ORANGE:
				orangefruit.render(f5);
				break;
			case GRAPEFRUIT:
				grapefruitfruit.render(f5);
				break;
			}
			GL11.glPopMatrix();
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
