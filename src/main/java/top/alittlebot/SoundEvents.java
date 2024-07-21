package top.alittlebot;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static top.alittlebot.Thinking.MOD_ID;

public class SoundEvents {
    public static final SoundEvent PIPE = SoundEvent.of(new Identifier(MOD_ID, "pipe"));

    private static SoundEvent register(String name, SoundEvent soundEvent) {
        return Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, name), soundEvent);
    }

    public static void registerSounds() {
        register("pipe", SoundEvent.of(new Identifier(MOD_ID, "pipe")));
    }
}
