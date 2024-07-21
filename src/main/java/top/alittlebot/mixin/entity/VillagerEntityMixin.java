package top.alittlebot.mixin.entity;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.alittlebot.entity.villager.VillagerLookAtPlayerGoal;
import top.alittlebot.entity.villager.VillagerPickupEmeraldGoal;
import top.alittlebot.entity.villager.VillagerInventoryProvider;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin implements VillagerInventoryProvider {

    @Unique
    private final SimpleInventory inventory = new SimpleInventory(8);

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        VillagerEntity villager = (VillagerEntity) (Object) this;
        GoalSelector goalSelector = ((MobEntityAccessor) villager).getGoalSelector();
        goalSelector.add(5, new VillagerPickupEmeraldGoal(villager));
        goalSelector.add(6, new VillagerLookAtPlayerGoal(villager));
    }

    @Override
    public SimpleInventory thinking_1_20_6$getInventory() {
        return this.inventory;
    }
}
