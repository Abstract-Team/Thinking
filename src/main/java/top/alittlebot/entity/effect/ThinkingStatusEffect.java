package top.alittlebot.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ThinkingStatusEffect extends StatusEffect {
    public ThinkingStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x5A5A5A); // 灰色
    }
}
