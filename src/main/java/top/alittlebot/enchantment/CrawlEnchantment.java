package top.alittlebot.enchantment;

import net.minecraft.enchantment.Enchantment;

public class CrawlEnchantment extends Enchantment {
    public CrawlEnchantment(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isCursed() {
        return true;
    }
}
