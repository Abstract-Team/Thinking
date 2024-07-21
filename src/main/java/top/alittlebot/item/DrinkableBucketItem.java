package top.alittlebot.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class DrinkableBucketItem extends Item {
    private final Fluid fluid;

    public DrinkableBucketItem(Fluid fluid, Settings settings) {
        super(settings);
        this.fluid = fluid;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = BucketItem.raycast(world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        BlockPos blockPos = blockHitResult.getBlockPos();
        Direction direction = blockHitResult.getSide();
        BlockPos blockPos2 = blockPos.offset(direction);

        if (!world.canPlayerModifyAt(user, blockPos) || !user.canPlaceOn(blockPos2, direction, itemStack)) {
            return TypedActionResult.fail(itemStack);
        }

        if (this.fluid == Fluids.EMPTY) {
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() instanceof FluidDrainable fluidDrainable) {
                ItemStack filledBucket = fluidDrainable.tryDrainFluid(user, world, blockPos, blockState);
                if (!filledBucket.isEmpty()) {
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    SoundEvent soundEvent = fluidDrainable.getBucketFillSound().orElse(SoundEvents.ITEM_BUCKET_FILL);
                    user.playSound(soundEvent, 1.0f, 1.0f);
                    world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);

                    if (!world.isClient) {
                        ItemStack filledEdibleBucket = null;
                        if (blockState.getFluidState().getFluid() == Fluids.WATER) {
                            filledEdibleBucket = new ItemStack(Items.DRINKABLE_WATER_BUCKET);
                        } else if (blockState.getFluidState().getFluid() == Fluids.LAVA) {
                            filledEdibleBucket = new ItemStack(Items.DRINKABLE_LAVA_BUCKET);
                        }
                        Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity) user, filledEdibleBucket);
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            return TypedActionResult.success(filledEdibleBucket, world.isClient());
                        } else {
                            if (!user.getInventory().insertStack(filledEdibleBucket)) {
                                user.dropItem(filledEdibleBucket, false);
                            }
                        }
                    }
                }
                return TypedActionResult.success(itemStack, world.isClient());
            }
            return TypedActionResult.fail(itemStack);
        } else {
            if (this.fluid == Fluids.WATER || this.fluid == Fluids.LAVA) {
                return ItemUsage.consumeHeldItem(world, user, hand);
            }
            return super.use(world, user, hand);
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (this.fluid == Fluids.WATER || this.fluid == Fluids.LAVA) {
            if (this.fluid == Fluids.LAVA) {
                user.setOnFireFor(5);
            }
            if (user instanceof PlayerEntity player) {
                if (this.fluid == Fluids.WATER) {
                    player.getHungerManager().add(1, 0.6F);
                } else {
                    player.getHungerManager().add(4, 0.4F);
                }
                return new ItemStack(Items.DRINKABLE_BUCKET);
            }
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return this.fluid == Fluids.WATER || this.fluid == Fluids.LAVA ? UseAction.DRINK : super.getUseAction(stack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return this.fluid == Fluids.WATER || this.fluid == Fluids.LAVA ? 32 : super.getMaxUseTime(stack);
    }
}
