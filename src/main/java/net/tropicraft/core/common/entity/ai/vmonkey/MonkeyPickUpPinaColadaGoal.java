package net.tropicraft.core.common.entity.ai.vmonkey;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.drinks.MixerRecipes;
import net.tropicraft.core.common.entity.neutral.VMonkeyEntity;

import java.util.EnumSet;
import java.util.List;

public class MonkeyPickUpPinaColadaGoal extends Goal {

    private VMonkeyEntity entity;
    private ItemEntity drinkEntity;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private float oldWaterCost;
    
    public MonkeyPickUpPinaColadaGoal(VMonkeyEntity monkey) {
        entity = monkey;
        setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE));
        speedModifier = 1.0F;
        stopDistance = 1.0F;
        navigation = entity.getNavigation();
        drinkEntity = null;
    }
    
    @Override
    public boolean canContinueToUse() {
        return !entity.isTame() && !entity.selfHoldingDrink(Drink.PINA_COLADA) && drinkEntity != null;
    }

    @Override
    public boolean canUse() {
        // Add some variablity / throttling
        if (entity.getRandom().nextInt(20) != 0) {
            return false;
        }
        return !entity.isTame() && !entity.selfHoldingDrink(Drink.PINA_COLADA) && hasNearbyDrink(Drink.PINA_COLADA) && drinkEntity != null;
    }

    @Override
    public void stop() {
        navigation.stop();
        entity.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
        oldWaterCost = entity.getPathfindingMalus(BlockPathTypes.WATER);
        entity.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    private boolean hasNearbyDrink(final Drink drink) {
        ItemStack stack = MixerRecipes.getItemStack(drink);

        List<ItemEntity> list = entity.level.getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(10.0D));
        
        if (!list.isEmpty()) {
            for (ItemEntity item : list) {
                if (!item.isInvisible()) {
                    if (item.getItem().sameItem(stack) && item.isAlive()) {
                        drinkEntity = item;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        if (drinkEntity != null && !entity.isLeashed()) {
            entity.getLookControl().setLookAt(drinkEntity, 10.0F, (float) entity.getMaxHeadXRot());

            if (!drinkEntity.isAlive()) {
                drinkEntity = null;
                entity.setMadAboutStolenAlcohol(true);
                return;
            }

            if (entity.distanceToSqr(drinkEntity) > (double)(stopDistance * stopDistance)) {
                if (--timeToRecalcPath <= 0) {
                    timeToRecalcPath = 10;
                    double d0 = entity.getX() - drinkEntity.getX();
                    double d1 = entity.getY() - drinkEntity.getY();
                    double d2 = entity.getZ() - drinkEntity.getZ();
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                    if (d3 > (double)(stopDistance * stopDistance)) {
                        navigation.moveTo(drinkEntity, speedModifier);
                    } else {
                        navigation.stop();

                        if (d3 <= (double)stopDistance) {
                            double d4 = drinkEntity.getX() - entity.getX();
                            double d5 = drinkEntity.getZ() - entity.getZ();
                            navigation.moveTo(entity.getX() - d4, entity.getY(), entity.getZ() - d5, speedModifier);
                        }
                    }
                }
            } else {
                entity.setItemInHand(InteractionHand.MAIN_HAND, drinkEntity.getItem());
                drinkEntity.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }

}
