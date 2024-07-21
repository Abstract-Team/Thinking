package top.alittlebot.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.ProjectileItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import top.alittlebot.enchantment.Enchantments;
import top.alittlebot.entity.FishEntity;

public class FishItem extends Item implements ProjectileItem {
    public FishItem(Item.Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            if (EnchantmentHelper.getLevel(Enchantments.EXPLOSIVE_ENCHANTMENT, itemStack) > 0) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                FishEntity fishEntity = new FishEntity(world, user);
                fishEntity.setItem(itemStack);
                fishEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
                world.spawnEntity(fishEntity);
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                itemStack.decrementUnlessCreative(1, user);
                return TypedActionResult.success(itemStack, world.isClient());
            } else {
                return ItemUsage.consumeHeldItem(world, user, hand);
            }
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player) {
            player.getHungerManager().add(4, 1.0F);
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        FishEntity fishEntity = new FishEntity(world, pos.getX(), pos.getY(), pos.getZ());
        fishEntity.setItem(stack);
        return fishEntity;
    }
}
