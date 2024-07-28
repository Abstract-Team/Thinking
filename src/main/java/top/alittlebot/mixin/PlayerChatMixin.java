package top.alittlebot.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.alittlebot.enchantment.Enchantments;

import java.util.List;
import java.util.Random;

@Mixin(ServerPlayNetworkHandler.class)
public class PlayerChatMixin {

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onChatMessage(SignedMessage message, MessageType.Parameters params, CallbackInfo ci) {
        ServerPlayNetworkHandler handler = (ServerPlayNetworkHandler) (Object) this;
        ServerPlayerEntity player = handler.player;
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        if (helmet.hasEnchantments() && (EnchantmentHelper.getLevel(Enchantments.BABBLING_ENCHANTMENT, helmet) > 0)) {
            String originalMessage = message.getContent().getString();
            String scrambledMessage = scrambleMessage(originalMessage);
            player.sendMessage(Text.literal("<" + player.getName().getString() + "> " + scrambledMessage), false);
            List<Entity> nearbyEntities = player.getWorld().getEntitiesByClass(Entity.class, player.getBoundingBox().expand(8), e -> e != player);
            // 如果有实体存在，随机选择一个并使其死亡
            if (!nearbyEntities.isEmpty()) {
                Entity target = nearbyEntities.get(new Random().nextInt(nearbyEntities.size()));
                target.kill();
            }
            ci.cancel();
        }
    }

    @Unique
    private String scrambleMessage(String message) {
        Random random = new Random();
        StringBuilder scrambled = new StringBuilder();
        for (char c : message.toCharArray()) {
            // Randomly shift each character by 0-5 positions in the ASCII table
            scrambled.append((char) (c + random.nextInt(6)));
        }
        return scrambled.toString();
    }
}