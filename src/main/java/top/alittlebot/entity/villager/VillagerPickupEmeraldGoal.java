package top.alittlebot.entity.villager;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;

import java.util.EnumSet;
import java.util.List;

public class VillagerPickupEmeraldGoal extends Goal {
    private final VillagerEntity villager;
    private ItemEntity targetEmerald;

    public VillagerPickupEmeraldGoal(VillagerEntity villager) {
        this.villager = villager;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        Box box = new Box(villager.getPos().add(-8, -8, -8), villager.getPos().add(8, 8, 8));
        List<ItemEntity> items = villager.getWorld().getEntitiesByClass(ItemEntity.class, box, item -> item.getStack().getItem() == Items.EMERALD);

        if (!items.isEmpty()) {
            this.targetEmerald = items.getFirst();
            return true;
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.targetEmerald != null && this.targetEmerald.isAlive() && villager.squaredDistanceTo(this.targetEmerald) > 1.0;
    }

    @Override
    public void stop() {
        this.targetEmerald = null;
    }

    @Override
    public void tick() {
        if (this.targetEmerald != null && this.targetEmerald.isAlive()) {
            this.villager.getLookControl().lookAt(this.targetEmerald);
            this.villager.getNavigation().startMovingTo(this.targetEmerald, 1.0);

            if (this.villager.squaredDistanceTo(this.targetEmerald) < 1.0) {
                SimpleInventory inventory = ((VillagerInventoryProvider) this.villager).thinking_1_20_6$getInventory();
                if (inventory.canInsert(this.targetEmerald.getStack())) {
                    inventory.addStack(this.targetEmerald.getStack());
                    this.targetEmerald.discard();
                }
            }
        }
    }
}