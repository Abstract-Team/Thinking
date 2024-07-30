package top.alittlebot.mixin.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.alittlebot.enchantment.Enchantments;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Final
    @Shadow
    private PlayerInventory inventory;

    @Unique
    private boolean wasSneaking = false;

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
        // 检查物品是否具有“绝对忠诚”附魔
        if (EnchantmentHelper.getLevel(Enchantments.ABSOLUTE_LOYALTY_ENCHANTMENT, itemStack) > 0) {
            // 如果有，则取消物品的丢弃
            cir.cancel(); // 貌似没啥用
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

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        // 穿了荆棘的裤子，就不能再贴贴了 ＞﹏＜
        if (leggings.hasEnchantments() && (EnchantmentHelper.getLevel(net.minecraft.enchantment.Enchantments.THORNS, leggings) > 0)) {
            boolean isSneaking = player.isSneaking();

            // 如果玩家从潜行状态切换到直立状态，或者从直立状态切换到潜行状态
            if (wasSneaking != isSneaking) {
                wasSneaking = isSneaking;
                // 获取玩家接触的实体
                List<Entity> entities = player.getWorld().getOtherEntities(player, player.getBoundingBox());
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity && entity != player) {
                        entity.damage(player.getDamageSources().playerAttack(player), Float.MAX_VALUE);
                    }
                }
            }
        }
    }
}
