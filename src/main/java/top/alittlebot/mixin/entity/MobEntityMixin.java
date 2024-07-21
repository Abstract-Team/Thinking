package top.alittlebot.mixin.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.alittlebot.enchantment.Enchantments;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onTickMovement(CallbackInfo info) {
        MobEntity mob = (MobEntity) (Object) this;

        for (LivingEntity entity : mob.getWorld().getEntitiesByClass(LivingEntity.class, mob.getBoundingBox().expand(16), e -> true)) {
            ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);

            // 检查实体是否戴着“不受欢迎”附魔的头盔
            if (EnchantmentHelper.getLevel(Enchantments.NEMESIS_ENCHANTMENT, helmet) > 0) {
                // 计算逃跑方向并确保目标位置在16格以外
                Vec3d direction = mob.getPos().subtract(entity.getPos()).normalize();
                Vec3d targetPos = mob.getPos().add(direction.multiply(16));

                mob.getNavigation().startMovingTo(targetPos.x, targetPos.y, targetPos.z, 1.5);
                return;
            }
        }
    }
}
