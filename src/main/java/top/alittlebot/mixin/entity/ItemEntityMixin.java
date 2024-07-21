package top.alittlebot.mixin.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        World world = itemEntity.getWorld();
        List<Entity> entities = world.getEntitiesByClass(Entity.class, itemEntity.getBoundingBox().expand(0.5, 0.5, 0.5), e -> e != itemEntity);

        for (Entity entity : entities) {
            ItemStack itemStack = itemEntity.getStack();
            if (itemStack.getItem() == Items.BREAD && EnchantmentHelper.getLevel(Enchantments.THORNS, itemStack) > 0) {
                entity.damage(entity.getDamageSources().magic(), 1.0F);
            }
        }
    }
}