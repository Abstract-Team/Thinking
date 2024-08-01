package top.alittlebot.mixin.entity;

import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
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
import top.alittlebot.entity.effect.StatusEffects;
import top.alittlebot.item.Items;
import top.alittlebot.util.GetSounds;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {

    @Unique
    private static final List<SoundEvent> Sounds = GetSounds.getAllSoundEvents();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        World world = entity.getWorld();
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);
        if (!world.isClient) {
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
        LivingEntity entity = (LivingEntity) (Object) this;
        World world = entity.getWorld();
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack leggings = entity.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feet = entity.getEquippedStack(EquipmentSlot.FEET);
        if (leggings.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.CRAWL_ENCHANTMENT, leggings) > 0)) {
            if (!entity.isSwimming() && !entity.isSpectator() && !entity.isSleeping()) {
                entity.setPose(EntityPose.SWIMMING);
                entity.setSwimming(true);
            }
        }
        if (feet.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.FIRE_WALKING_ENCHANTMENT, feet) > 0)) {
            entity.setOnFireFor(1);
            entity.getWorld().addParticle(ParticleTypes.FLAME, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
        }
        if (helmet.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.NOISY_ENCHANTMENT, helmet) > 0)) {
            SoundEvent randomSound = Sounds.get(random.nextInt(Sounds.size()));
            entity.getWorld().playSound(entity.getX(), entity.getY(), entity.getZ(), randomSound, entity.getSoundCategory(), 5.0F, 1.0F, false);
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

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void onTravel(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        World world = entity.getWorld();
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);
        StatusEffectInstance effectInstance = entity.getStatusEffect(StatusEffects.THINKING);
        if (effectInstance != null) {
            ci.cancel();
        }
        if (world.isClient) {
            if (helmet.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.DANDRUFF_ENCHANTMENT, helmet) > 0)) {
                Vec3d pos = entity.getPos();
                world.addParticle(ParticleTypes.WHITE_ASH, pos.x, pos.y + entity.getStandingEyeHeight() + 0.2, pos.z, 0.0, 0.0, 0.0);
            }
        }
    }
}
