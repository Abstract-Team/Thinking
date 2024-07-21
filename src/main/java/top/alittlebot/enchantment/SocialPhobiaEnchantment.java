package top.alittlebot.enchantment;

import net.minecraft.enchantment.Enchantment;

public class SocialPhobiaEnchantment extends Enchantment {
    public SocialPhobiaEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isCursed() {
        return true;
    }
}
