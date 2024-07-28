package top.alittlebot.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static top.alittlebot.Thinking.MOD_ID;

public class Items {
    public static final Item DRINKABLE_BUCKET = new DrinkableBucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16));
    public static final Item DRINKABLE_WATER_BUCKET = new DrinkableBucketItem(Fluids.WATER, new Item.Settings().maxCount(1));
    public static final Item DRINKABLE_LAVA_BUCKET = new DrinkableBucketItem(Fluids.LAVA, new Item.Settings().maxCount(1));
    public static final Item FISH = new FishItem(new Item.Settings());
    public static final Item BRAIN = new Item(new Item.Settings()
            .food(new FoodComponent.Builder()
                    .nutrition(4)
                    .saturationModifier(0.3f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 600), 1.0f)
                    .build()
            )
    );
    public static final Item PASTA = new Item(new Item.Settings()
            .food(new FoodComponent.Builder()
                    .nutrition(6)
                    .saturationModifier(1.2f)
                    .build()));
    public static final Item LAVA_NETHERITE_BOOTS = new ArmorItem(
            ArmorMaterials.NETHERITE,
            net.minecraft.item.ArmorItem.Type.BOOTS,
            (new Item.Settings())
                    .fireproof()
                    .maxDamage(net.minecraft.item.ArmorItem.Type.BOOTS.getMaxDamage(37))
                    .food(new FoodComponent.Builder()
                            .nutrition(6)
                            .saturationModifier(1.2f)
                            .build()
                    )
    );
    public static final Item POTION_THROWING_GLOVE = new PotionThrowingGloveItem(new Item.Settings());

    public static void registerItems() {
        registerItem("drinkable_bucket", DRINKABLE_BUCKET);
        registerItem("drinkable_water_bucket", DRINKABLE_WATER_BUCKET);
        registerItem("drinkable_lava_bucket", DRINKABLE_LAVA_BUCKET);
        registerItem("fish", FISH);
        registerItem("brain", BRAIN);
        registerItem("pasta", PASTA);
        registerItem("lava_netherite_boots", LAVA_NETHERITE_BOOTS);
        registerItem("potion_throwing_glove", POTION_THROWING_GLOVE);
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

}
