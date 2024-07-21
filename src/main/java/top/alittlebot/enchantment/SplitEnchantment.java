package top.alittlebot.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SplitEnchantment extends Enchantment {
    public SplitEnchantment(Properties properties) {
        super(properties);
    }

    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (user instanceof PlayerEntity) {
            World world = user.getWorld();
            if (!world.isClient) {
                LivingEntity splitEntity = (LivingEntity) target.getType().create(world);
                if (splitEntity != null) {
                    splitEntity.setPos(target.getX(), target.getY(), target.getZ());
                    world.spawnEntity(splitEntity);
                }
            }
        }
        super.onTargetDamaged(user, target, level);
    }
}
