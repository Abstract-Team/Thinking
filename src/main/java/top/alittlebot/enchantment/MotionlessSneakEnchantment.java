package top.alittlebot.enchantment;

import net.minecraft.enchantment.Enchantment;

public class MotionlessSneakEnchantment extends Enchantment {

    public MotionlessSneakEnchantment(Enchantment.Properties properties) {
        super(properties);
    }


    @Override
    public boolean isCursed() {
        return true;
    }
}
