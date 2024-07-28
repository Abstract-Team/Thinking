package top.alittlebot.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PotionThrowingGloveItem extends Item {
    public PotionThrowingGloveItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            ItemStack potionStack = findPotionInInventory(player);
            if (potionStack != null) {
                throwPotion(world, player, potionStack);
                return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
            }
        }
        return new TypedActionResult<>(ActionResult.FAIL, player.getStackInHand(hand));
    }

    private ItemStack findPotionInInventory(PlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == Items.SPLASH_POTION || stack.getItem() == Items.LINGERING_POTION) {
                return stack;
            }
        }
        return null;
    }

    private void throwPotion(World world, PlayerEntity player, ItemStack potionStack) {
        PotionEntity potionEntity = new PotionEntity(world, player);
        potionEntity.setItem(potionStack);
        potionEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 2.0F, 1.0F); // 调整速度为2.0F，投掷更远
        world.spawnEntity(potionEntity);
        if (!player.isCreative()) {
            potionStack.decrement(1);
        }
    }
}
