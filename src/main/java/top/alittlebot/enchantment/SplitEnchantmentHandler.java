package top.alittlebot.enchantment;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public class SplitEnchantmentHandler {
    public static void register() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClient && hand == player.getActiveHand()) {
                ItemStack itemStack = player.getStackInHand(hand);
                if (EnchantmentHelper.getLevel(Enchantments.SPLIT_ENCHANTMENT, itemStack) > 0)  {
                    LivingEntity target = (LivingEntity) entity;
                    LivingEntity splitEntity = (LivingEntity) target.getType().create(world);
                    if (splitEntity != null) {
                        splitEntity.setPos(target.getX(), target.getY(), target.getZ());
                        world.spawnEntity(splitEntity);
                    }
                }
            }
            return ActionResult.PASS;
        });
    }
}
