package top.alittlebot.enchantment;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;
import top.alittlebot.entity.ExplosiveSnowballEntity;

public class ExplosiveEnchantmentHandler {

    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.getItem() == Items.SNOWBALL) {
                if (EnchantmentHelper.getLevel(Enchantments.EXPLOSIVE_ENCHANTMENT, itemStack) > 0) {
                    ExplosiveSnowballEntity explosiveSnowball = new ExplosiveSnowballEntity(world, player);
                    explosiveSnowball.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
                    explosiveSnowball.setOwner(player);
                    explosiveSnowball.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 1.0F);
                    if (!world.isClient) {
                        world.spawnEntity(explosiveSnowball);
                    }
                    if (!player.isCreative()) {
                        if (EnchantmentHelper.getLevel(net.minecraft.enchantment.Enchantments.INFINITY, itemStack) == 0) {
                            itemStack.decrement(1);
                        }
                    }
                    return TypedActionResult.success(itemStack, world.isClient());
                }
            }
            return TypedActionResult.pass(itemStack);
        });
    }
}