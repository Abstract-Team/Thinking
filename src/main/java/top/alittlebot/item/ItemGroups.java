package top.alittlebot.item;


import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static top.alittlebot.Thinking.MOD_ID;

public class ItemGroups {
    private static final ItemGroup THINKING_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.BRAIN))
            .displayName(Text.translatable("itemGroup.thinking.thinking_group"))
            .entries((context, entries) -> {
                entries.add(Items.FISH);
                entries.add(Items.BRAIN);
                entries.add(Items.DRINKABLE_BUCKET);
                entries.add(Items.DRINKABLE_WATER_BUCKET);
                entries.add(Items.DRINKABLE_LAVA_BUCKET);
                entries.add(Items.LAVA_NETHERITE_BOOTS);
            })
            .build();

    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "thinking_group"), THINKING_GROUP);
    }
}
