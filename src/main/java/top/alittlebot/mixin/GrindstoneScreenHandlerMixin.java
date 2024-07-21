package top.alittlebot.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.alittlebot.screen.CustomGrindstoneSlot;

@Mixin(GrindstoneScreenHandler.class)
public class GrindstoneScreenHandlerMixin {

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("TAIL"))
    private void onInit(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        Inventory input = ((GrindstoneScreenHandler) (Object) this).getSlot(0).inventory;
        ((GrindstoneScreenHandler) (Object) this).slots.set(0, new CustomGrindstoneSlot(input, 0, 49, 19));
        ((GrindstoneScreenHandler) (Object) this).slots.set(1, new CustomGrindstoneSlot(input, 1, 49, 40));
    }

    @Inject(method = "getOutputStack", at = @At("HEAD"), cancellable = true)
    private void getOutputStack(ItemStack firstInput, ItemStack secondInput, CallbackInfoReturnable<ItemStack> cir) {
        if (firstInput.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
            cir.setReturnValue(new ItemStack(Items.GOLDEN_APPLE));
        } else if (secondInput.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
            cir.setReturnValue(new ItemStack(Items.GOLDEN_APPLE));
        }
    }
}
