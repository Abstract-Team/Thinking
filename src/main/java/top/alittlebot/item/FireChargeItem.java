package top.alittlebot.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireChargeItem extends net.minecraft.item.FireChargeItem {
    public FireChargeItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            Vec3d lookDirection = player.getRotationVec(1.0F);
            FireballEntity fireballEntity = new FireballEntity(world, player, lookDirection.x, lookDirection.y, lookDirection.z, 1);
            fireballEntity.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
            world.spawnEntity(fireballEntity);
            if (!player.isCreative()) {
                itemStack.decrement(1);
            }
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, itemStack);
    }

}
