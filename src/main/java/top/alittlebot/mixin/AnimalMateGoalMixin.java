package top.alittlebot.mixin;

import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AnimalMateGoal.class)
public abstract class AnimalMateGoalMixin {

    @Shadow
    protected AnimalEntity animal;

    @Shadow
    protected World world;

    @Inject(method = "canStart", at = @At("RETURN"), cancellable = true)
    private void canStart(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && animal.isInLove()) {
            // Check if any player is holding cooked porkchop or raw porkchop within 8 blocks
            List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class, animal.getBoundingBox().expand(8.0), player -> true);
            for (PlayerEntity player : players) {
                if (player.getMainHandStack().getItem() == Items.COOKED_PORKCHOP || player.getMainHandStack().getItem() == Items.PORKCHOP
                        || player.getOffHandStack().getItem() == Items.COOKED_PORKCHOP || player.getOffHandStack().getItem() == Items.PORKCHOP) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
        cir.setReturnValue(false);
    }
}