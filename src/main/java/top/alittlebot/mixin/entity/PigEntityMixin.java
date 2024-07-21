package top.alittlebot.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.SaddledComponent;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity implements ItemSteerable, Saddleable {

    public PigEntityMixin(EntityType<? extends PigEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("RETURN"))
    protected void initGoals(CallbackInfo ci) {
            this.goalSelector.add(4, new TemptGoal(this, 1.2, (stack) -> stack.isOf(Items.PORKCHOP), false));
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        AnimalEntity animal = (AnimalEntity) (Object) this;

        if (animal.isBreedingItem(player.getStackInHand(hand))) {
            return;
        }

        if (player.getStackInHand(hand).getItem() == Items.COOKED_PORKCHOP || player.getStackInHand(hand).getItem() == Items.PORKCHOP) {
            if (animal.getBreedingAge() == 0 && animal.canEat()) {
                animal.lovePlayer(player);
                player.getStackInHand(hand).decrement(1);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
