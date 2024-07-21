package top.alittlebot.item;

import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class MixinItems {
    public final static Item TOTEM_OF_UNDYING = new Item(
            new Item.Settings().rarity(Rarity.UNCOMMON).maxCount(64).food(FoodComponents.ENCHANTED_GOLDEN_APPLE)
    );
    public final static Item FIRE_CHARGE = new FireChargeItem(new Item.Settings());
}
