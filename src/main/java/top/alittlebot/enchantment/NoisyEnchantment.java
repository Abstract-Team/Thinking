package top.alittlebot.enchantment;

import net.minecraft.enchantment.Enchantment;

public class NoisyEnchantment extends Enchantment {
    public NoisyEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isCursed() {
        return true;
    }
}
