package top.alittlebot.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ExplosiveSnowballEntity extends SnowballEntity {

    public ExplosiveSnowballEntity(World world, PlayerEntity entityType) {
        super(world, entityType);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient){
            createExplosion();
            this.discard();
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.getWorld().isClient) {
            createExplosion();
            this.discard();
        }
    }

    private void createExplosion() {
        this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 3.0F, false, World.ExplosionSourceType.TNT);
    }
}
