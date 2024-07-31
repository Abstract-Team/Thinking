package top.alittlebot.enchantment;

import net.minecraft.enchantment.Enchantment;

public class DandruffEnchantment extends Enchantment {
    public DandruffEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isCursed() {
        return true;
    }
}
