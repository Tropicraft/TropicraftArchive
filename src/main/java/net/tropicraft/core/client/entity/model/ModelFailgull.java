package net.tropicraft.core.client.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelFailgull extends ModelBase {
	ModelRenderer leftFoot;
	ModelRenderer rightFoot;
	ModelRenderer lowerLeftLeg;
	ModelRenderer LowestBody;
	ModelRenderer lowerRightLeg;
	ModelRenderer LowerBody1;
	ModelRenderer LowerBody2;
	ModelRenderer rightWing;
	ModelRenderer leftWing;
	ModelRenderer neck;
	ModelRenderer nose;
	
	public ModelFailgull() {
		leftFoot = new ModelRenderer(this, 0, 0);
		leftFoot.addBox(0F, 0F, 0F, 1, 0, 1);
		leftFoot.setRotationPoint(-1F, 23F, 0F);
		leftFoot.rotateAngleX = 0F;
		leftFoot.rotateAngleY = 0F;
		leftFoot.rotateAngleZ = 0F;
		leftFoot.mirror = false;
		rightFoot = new ModelRenderer(this, 0, 0);
		rightFoot.addBox(0F, 0F, 0F, 1, 0, 1);
		rightFoot.setRotationPoint(1F, 23F, 0F);
		rightFoot.rotateAngleX = 0F;
		rightFoot.rotateAngleY = 0F;
		rightFoot.rotateAngleZ = 0F;
		rightFoot.mirror = false;
		lowerLeftLeg = new ModelRenderer(this, 0, 0);
		lowerLeftLeg.addBox(0F, -1F, 0F, 1, 2, 0);
		lowerLeftLeg.setRotationPoint(1F, 22F, 1F);
		lowerLeftLeg.rotateAngleX = 0F;
		lowerLeftLeg.rotateAngleY = 0F;
		lowerLeftLeg.rotateAngleZ = 0F;
		lowerLeftLeg.mirror = false;
		LowestBody = new ModelRenderer(this, 0, 0);
		LowestBody.addBox(0F, 0F, 0F, 3, 1, 4);
		LowestBody.setRotationPoint(-1F, 20F, 0F);
		LowestBody.rotateAngleX = 0F;
		LowestBody.rotateAngleY = 0F;
		LowestBody.rotateAngleZ = 0F;
		LowestBody.mirror = false;
		lowerRightLeg = new ModelRenderer(this, 0, 0);
		lowerRightLeg.addBox(0F, 0F, 0F, 1, 2, 0);
		lowerRightLeg.setRotationPoint(-1F, 21F, 1F);
		lowerRightLeg.rotateAngleX = 0F;
		lowerRightLeg.rotateAngleY = 0F;
		lowerRightLeg.rotateAngleZ = 0F;
		lowerRightLeg.mirror = false;
		LowerBody1 = new ModelRenderer(this, 0, 0);
		LowerBody1.addBox(0F, 0F, 0F, 3, 1, 8);
		LowerBody1.setRotationPoint(-1F, 19F, -1F);
		LowerBody1.rotateAngleX = 0F;
		LowerBody1.rotateAngleY = 0F;
		LowerBody1.rotateAngleZ = 0F;
		LowerBody1.mirror = false;
		LowerBody2 = new ModelRenderer(this, 0, 0);
		LowerBody2.addBox(0F, 0F, 0F, 3, 1, 7);
		LowerBody2.setRotationPoint(-1F, 18F, -2F);
		LowerBody2.rotateAngleX = 0F;
		LowerBody2.rotateAngleY = 0F;
		LowerBody2.rotateAngleZ = 0F;
		LowerBody2.mirror = false;
		rightWing = new ModelRenderer(this, 0, 0);
		rightWing.addBox(0F, 0F, 0F, 0, 2, 5);
		rightWing.setRotationPoint(-1F, 18F, 0F);
		rightWing.rotateAngleX = -0.06981F;
		rightWing.rotateAngleY = -0.06981F;
		rightWing.rotateAngleZ = 0F;
		rightWing.mirror = false;
		leftWing = new ModelRenderer(this, 0, 0);
		leftWing.addBox(0F, 0F, 0F, 0, 2, 5);
		leftWing.setRotationPoint(2F, 18F, 0F);
		leftWing.rotateAngleX = -0.06981F;
		leftWing.rotateAngleY = 0.06981F;
		leftWing.rotateAngleZ = 0F;
		leftWing.mirror = false;
		neck = new ModelRenderer(this, 0, 0);
		neck.addBox(0F, 0F, 0F, 3, 2, 2);
		neck.setRotationPoint(-1F, 16F, -3F);
		neck.rotateAngleX = 0F;
		neck.rotateAngleY = 0F;
		neck.rotateAngleZ = 0F;
		neck.mirror = false;
		nose = new ModelRenderer(this, 14, 0);
		nose.addBox(0F, 0F, 0F, 1, 1, 2);
		nose.setRotationPoint(0F, 17F, -5F);
		nose.rotateAngleX = 0F;
		nose.rotateAngleY = 0F;
		nose.rotateAngleZ = 0F;
		nose.mirror = false;
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		lowerLeftLeg.render(f5);
		LowestBody.render(f5);
		lowerRightLeg.render(f5);
		LowerBody1.render(f5);
		LowerBody2.render(f5);
		GlStateManager.enableCull();
	    leftFoot.render(f5);
	    rightFoot.render(f5);
		rightWing.render(f5);
		leftWing.render(f5);
		GlStateManager.disableCull();
		neck.render(f5);
		nose.render(f5);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		lowerRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		//lowerLeftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;

		rightWing.rotateAngleZ = MathHelper.sin(ageInTicks) + 0.15F;
		leftWing.rotateAngleZ = MathHelper.sin(-ageInTicks) - 0.15F;
	}

}