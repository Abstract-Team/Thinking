package top.alittlebot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.alittlebot.enchantment.*;
import top.alittlebot.item.ItemGroups;
import top.alittlebot.item.Items;

public class Thinking implements ModInitializer {

    public static final String MOD_ID = "thinking";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        Items.registerItems();
        Enchantments.registerEnchantments();
        ItemGroups.registerItemGroups();
        SoundEvents.registerSounds();

        ServerLifecycleEvents.SERVER_STARTING.register(ServerManager::setServerInstance);

        LOGGER.info("Thinking Mod is running :)");
    }
}

