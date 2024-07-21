package top.alittlebot.mixin.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.alittlebot.enchantment.Enchantments;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity {
    public TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ItemStack stack = ((PersistentProjectileEntityAccessor) this).getStack();
        int returnLevel = EnchantmentHelper.getLevel(Enchantments.ABSOLUTE_LOYALTY_ENCHANTMENT, stack);
        if (returnLevel > 0) {
            Entity owner = this.getOwner();
            if (owner != null && !((TridentEntityAccessor) this).getDealtDamage()) {
                Vec3d ownerPos = owner.getPos();
                this.setPos(ownerPos.x, ownerPos.y + owner.getStandingEyeHeight(), ownerPos.z);
                this.setNoClip(true);
                this.setVelocity(Vec3d.ZERO);
                if (getWorld().isClient) {
                    getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_TRIDENT_RETURN, this.getSoundCategory(), 1.0F, 1.0F, false);
                }
                ((TridentEntityAccessor) this).setDealtDamage(true);
            }
        }
    }
}