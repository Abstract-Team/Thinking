package top.alittlebot.mixin.entity;

import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.alittlebot.enchantment.Enchantments;
import top.alittlebot.item.Items;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        World world = entity.getWorld();

        if (!world.isClient) {
            ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);
            if (helmet.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.SOCIAL_PHOBIA_ENCHANTMENT, helmet) > 0)) {
                for (LivingEntity otherEntity : world.getEntitiesByClass(LivingEntity.class, entity.getBoundingBox().expand(10.0), e -> e != entity)) {
                    if (otherEntity.canSee(entity)) {
                        entity.damage(entity.getDamageSources().magic(), 0.5F);
                        if (entity instanceof MobEntity mobEntity) {  // 逃跑
                            Vec3d direction = mobEntity.getPos().subtract(otherEntity.getPos()).normalize();
                            Vec3d targetPos = mobEntity.getPos().add(direction.multiply(16));
                            mobEntity.getNavigation().startMovingTo(targetPos.x, targetPos.y, targetPos.z, 1.2);
                            return;
                        }
                        break;
                    }
                }
            }
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void onTickMovement(CallbackInfo info) {
        LivingEntity user = (LivingEntity) (Object) this;
        ItemStack leggings = user.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feet = user.getEquippedStack(EquipmentSlot.FEET);
        if (leggings.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.CRAWL_ENCHANTMENT, leggings) > 0)) {
            if (!user.isSwimming() && !user.isSpectator() && !user.isSleeping()) {
                user.setPose(EntityPose.SWIMMING);
                user.setSwimming(true);
            }
        }
        if (feet.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.FIRE_WALKING_ENCHANTMENT, feet) > 0)) {
            user.setOnFireFor(1);
            user.getWorld().addParticle(ParticleTypes.FLAME, user.getX(), user.getY(), user.getZ(), 0, 0, 0);
        }
    }

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    private void onJump(CallbackInfo ci) {
        LivingEntity player = (LivingEntity) (Object) this;
        if (player.getPose() == EntityPose.SWIMMING) {
            player.setVelocity(player.getVelocity().x, 0.5, player.getVelocity().z);
            ci.cancel();
        }
        // player.getWorld().playSound(player.getX(), player.getY(), player.getZ(), SoundEvents.PIPE, player.getSoundCategory(), 5.0F, 1.0F, false);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (isExplosionDamage(source)) {
            int level = EnchantmentHelper.getEquipmentLevel(Enchantments.BLAST_EJECTION_ENCHANTMENT, entity);
            if (level > 0) {
                double bounceHeight = 4.5 + level * 2;
                Vec3d velocity = entity.getVelocity();
                entity.setVelocity(velocity.x, bounceHeight, velocity.z);
            }
        }
        ItemStack leggings = entity.getEquippedStack(EquipmentSlot.FEET);
        if (source.getName().contains("fall") && !entity.getWorld().isClient) {
            if (leggings.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.MLG_LAVA_ENCHANTMENT, leggings) > 0) || leggings.getItem() == Items.LAVA_NETHERITE_BOOTS) {
                BlockPos pos = new BlockPos((int) entity.getX(), (int) (entity.getY()), (int) entity.getZ());
                ServerWorld world = (ServerWorld) entity.getWorld();
                world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);
            }
        }
        // entity.getWorld().playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PIPE, entity.getSoundCategory(), 5.0F, 1.0F, true);
    }

    @Unique
    private boolean isExplosionDamage(DamageSource source) {
        return source.getName().contains("explosion");
    }
}
