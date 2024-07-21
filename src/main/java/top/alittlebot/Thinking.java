package top.alittlebot;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.alittlebot.enchantment.*;
import top.alittlebot.item.ItemGroups;
import top.alittlebot.item.Items;
import top.alittlebot.util.PlayerHandler;

public class Thinking implements ModInitializer {

    public static final String MOD_ID = "thinking";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        Items.registerItems();
        Enchantments.registerEnchantments();
        ItemGroups.registerItemGroups();
        SoundEvents.registerSounds();
        // PlayerHandler.register();

        LOGGER.info("Thinking Mod is running :)");
    }
}

