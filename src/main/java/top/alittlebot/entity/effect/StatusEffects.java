package top.alittlebot.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import top.alittlebot.Thinking;

public class StatusEffects {
    public static final RegistryEntry<StatusEffect> THINKING;
    static {
        THINKING = register(new ThinkingStatusEffect());
    }

    public static void registerStatusEffects() {
        register(new ThinkingStatusEffect());
    }

    private static RegistryEntry<StatusEffect> register(StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(Thinking.MOD_ID), statusEffect); // 有bug
    }
}
