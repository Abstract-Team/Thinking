package top.alittlebot.util;

import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GetSounds {
    public static List<SoundEvent> getAllSoundEvents() {
        List<SoundEvent> soundEvents = new ArrayList<>();
        Field[] fields = SoundEvents.class.getDeclaredFields();

        for (Field field : fields) {
            if (SoundEvent.class.isAssignableFrom(field.getType())) {
                try {
                    SoundEvent soundEvent = (SoundEvent) field.get(null);
                    soundEvents.add(soundEvent);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return soundEvents;
    }
}
