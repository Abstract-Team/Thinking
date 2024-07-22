package top.alittlebot.screen;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class CustomGrindstoneSlot extends Slot {

    public CustomGrindstoneSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        // 可为附魔金苹果祛魔
        return stack.isDamageable() || EnchantmentHelper.hasEnchantments(stack) || stack.isOf(Items.ENCHANTED_GOLDEN_APPLE);
    }
}
