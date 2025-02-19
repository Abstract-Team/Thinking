package top.alittlebot.mixin.entity;

import net.minecraft.entity.projectile.TridentEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {
    @Accessor("dealtDamage")
    boolean getDealtDamage();

    @Accessor("dealtDamage")
    void setDealtDamage(boolean dealtDamage);
}
