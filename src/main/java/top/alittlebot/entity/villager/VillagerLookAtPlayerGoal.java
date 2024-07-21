package top.alittlebot.entity.villager;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

import java.util.EnumSet;
import java.util.List;

public class VillagerLookAtPlayerGoal extends Goal {
    private final VillagerEntity villager;
    private PlayerEntity targetPlayer;

    public VillagerLookAtPlayerGoal(VillagerEntity villager) {
        this.villager = villager;
        this.setControls(EnumSet.of(Control.LOOK));
    }

    @Override
    public boolean canStart() {
        List<? extends PlayerEntity> players = villager.getWorld().getPlayers();
        for (PlayerEntity player : players) {
            if (player.getMainHandStack().getItem() == Items.EMERALD || player.getOffHandStack().getItem() == Items.EMERALD) {
                this.targetPlayer = player;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.targetPlayer != null && (this.targetPlayer.getMainHandStack().getItem() == Items.EMERALD || this.targetPlayer.getOffHandStack().getItem() == Items.EMERALD);
    }

    @Override
    public void stop() {
        this.targetPlayer = null;
    }

    @Override
    public void tick() {
        if (this.targetPlayer != null) {
            this.villager.getLookControl().lookAt(this.targetPlayer);
            this.villager.getNavigation().startMovingTo(this.targetPlayer, 0.5);
        }
    }
}
