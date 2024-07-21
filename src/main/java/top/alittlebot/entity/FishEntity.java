package top.alittlebot.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import top.alittlebot.item.Items;

public class FishEntity extends ThrownItemEntity {

    public FishEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    public FishEntity(World world, double x, double y, double z) {
        super(EntityType.SNOWBALL, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.FISH;
    }

    @Override
    protected boolean updateWaterState() {
        return false; // 奇奇怪怪的方法 qwq
    }


    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.getWorld().isClient) {
            createExplosive();
            this.discard();
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            createExplosive();
            this.discard();
        }
    }

    private void createExplosive() {
        this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 4.0F, World.ExplosionSourceType.BLOW);
    }
}
