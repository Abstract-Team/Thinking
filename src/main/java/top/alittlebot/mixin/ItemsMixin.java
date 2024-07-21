package top.alittlebot.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.alittlebot.item.MixinItems;

@Mixin(Items.class)
public class ItemsMixin {
    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("TAIL"), cancellable = true)
    private static void onStaticInit(String id, Item item, CallbackInfoReturnable<Item> cir) {
        if ("totem_of_undying".equals(id)) {
            Item modifiedItem = MixinItems.TOTEM_OF_UNDYING;
            cir.setReturnValue(modifiedItem);
            Registry.register(Registries.ITEM, new Identifier("minecraft", id), modifiedItem);
        }
        if ("fire_charge".equals(id)) {
            Item modifiedItem = MixinItems.FIRE_CHARGE;
            cir.setReturnValue(modifiedItem);
            Registry.register(Registries.ITEM, new Identifier("minecraft", id), modifiedItem);
        }
    }
}
