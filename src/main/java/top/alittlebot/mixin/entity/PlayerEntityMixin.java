package top.alittlebot.mixin.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.alittlebot.enchantment.Enchantments;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Final
    @Shadow
    private PlayerInventory inventory;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void onTickMovement(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        if (leggings.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.MOTIONLESS_SNEAK_ENCHANTMENT, leggings) > 0) && player.isSneaking()) {
            player.setVelocity(0, 0, 0);
        }
    }

    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"), cancellable = true)
    private void onDropItem(ItemStack itemStack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        // 检查物品是否具有“不掉落”附魔
        if (EnchantmentHelper.getLevel(Enchantments.ABSOLUTE_LOYALTY_ENCHANTMENT, itemStack) > 0) {
            // 如果有，则取消物品的丢弃
            cir.cancel();
            cir.setReturnValue(null);
            ItemStack copyStack = itemStack.copy();

            if (this.inventory != null) {
                // 尝试找到一个空槽位来放置复制的物品堆栈
                for (int slot = 0; slot < this.inventory.size(); ++slot) {
                    ItemStack currentStack = this.inventory.getStack(slot);
                    if (currentStack.isEmpty()) {
                        // 如果找到空槽位，将复制的物品堆栈放入该槽位
                        this.inventory.setStack(slot, copyStack);
                        break;
                    }
                }
            }
        }
    }
}
