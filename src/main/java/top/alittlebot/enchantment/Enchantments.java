package top.alittlebot.enchantment;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static top.alittlebot.Thinking.MOD_ID;

public class Enchantments {
    public static final TagKey<Item> EXPLOSIVE_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier("thinking", "explosive_tag"));
    // public static final TagKey<Item> HOT_DOOR_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier("thinking", "hot_door_tag"));
    public static final ExplosiveEnchantment EXPLOSIVE_ENCHANTMENT = new ExplosiveEnchantment(
            Enchantment.properties(
                    EXPLOSIVE_TAG,
                    1,
                    1,
                    Enchantment.leveledCost(10, 10),
                    Enchantment.leveledCost(25, 10),
                    1,
                    EquipmentSlot.MAINHAND
            )
    );

    public static final MotionlessSneakEnchantment MOTIONLESS_SNEAK_ENCHANTMENT = new MotionlessSneakEnchantment(
            Enchantment.properties(
                    ItemTags.LEG_ARMOR_ENCHANTABLE,
                    1,
                    1,
                    Enchantment.leveledCost(25, 25),
                    Enchantment.leveledCost(75, 25),
                    8,
                    EquipmentSlot.LEGS
            )
    );

    public static final CrawlEnchantment CRAWL_ENCHANTMENT = new CrawlEnchantment(
            Enchantment.properties(
                    ItemTags.LEG_ARMOR_ENCHANTABLE,
                    1,
                    1,
                    Enchantment.leveledCost(25, 25),
                    Enchantment.leveledCost(75, 25),
                    8,
                    EquipmentSlot.LEGS
            )
    );

    public static final BlastEjectionEnchantment BLAST_EJECTION_ENCHANTMENT = new BlastEjectionEnchantment(
            Enchantment.properties(
                    ItemTags.ARMOR_ENCHANTABLE,
                    1,
                    4,
                    Enchantment.leveledCost(5, 8),
                    Enchantment.leveledCost(13, 8),
                    4,
                    EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
            )
    );

    public static final AbsoluteLoyaltyEnchantment ABSOLUTE_LOYALTY_ENCHANTMENT = new AbsoluteLoyaltyEnchantment(
            Enchantment.properties(
                    ItemTags.TRIDENT_ENCHANTABLE,
                    10,
                    1,
                    Enchantment.leveledCost(1, 11),
                    Enchantment.leveledCost(21, 11),
                    1,
                    EquipmentSlot.MAINHAND
            )
    );

    public static final NemesisEnchantment NEMESIS_ENCHANTMENT = new NemesisEnchantment(
            Enchantment.properties(
                    ItemTags.HEAD_ARMOR_ENCHANTABLE,
                    10,
                    1,
                    Enchantment.leveledCost(1, 11),
                    Enchantment.leveledCost(21, 11),
                    1,
                    EquipmentSlot.HEAD
            )
    );

    public static final MLGLavaEnchantment MLG_LAVA_ENCHANTMENT = new MLGLavaEnchantment(
            Enchantment.properties(
                    ItemTags.FOOT_ARMOR_ENCHANTABLE,
                    2,
                    1,
                    Enchantment.leveledCost(10, 10),
                    Enchantment.leveledCost(25, 10),
                    4,
                    EquipmentSlot.FEET)
    );

    public static final FireWalkingEnchantment FIRE_WALKING_ENCHANTMENT = new FireWalkingEnchantment(
            Enchantment.properties(
                    ItemTags.FOOT_ARMOR_ENCHANTABLE,
                    1,
                    1,
                    Enchantment.leveledCost(25, 25),
                    Enchantment.leveledCost(75, 25),
                    8,
                    EquipmentSlot.FEET
            )
    );

    public static final SocialPhobiaEnchantment SOCIAL_PHOBIA_ENCHANTMENT = new SocialPhobiaEnchantment(
            Enchantment.properties(
                    ItemTags.HEAD_ARMOR_ENCHANTABLE,
                    10,
                    1,
                    Enchantment.leveledCost(1, 11),
                    Enchantment.leveledCost(21, 11),
                    1,
                    EquipmentSlot.HEAD
            )
    );
    /*
    public static final HotDoorEnchantment HOT_DOOR_ENCHANTMENT = new HotDoorEnchantment(
            Enchantment.properties(
                    HOT_DOOR_TAG,
                    10,
                    1,
                    Enchantment.leveledCost(1, 11),
                    Enchantment.leveledCost(21, 11),
                    1,
                    EquipmentSlot.MAINHAND
            )
    );

    */

    public static final BabblingEnchantment BABBLING_ENCHANTMENT = new BabblingEnchantment(
            Enchantment.properties(
                    ItemTags.HEAD_ARMOR_ENCHANTABLE,
                    10,
                    1,
                    Enchantment.leveledCost(1, 11),
                    Enchantment.leveledCost(21, 11),
                    1,
                    EquipmentSlot.HEAD
            )
    );

    public static final DandruffEnchantment DANDRUFF_ENCHANTMENT = new DandruffEnchantment(
            Enchantment.properties(
                    ItemTags.HEAD_ARMOR_ENCHANTABLE,
                    10,
                    1,
                    Enchantment.leveledCost(1, 11),
                    Enchantment.leveledCost(21, 11),
                    1,
                    EquipmentSlot.HEAD
            )
    );

    public static final NoisyEnchantment NOISY_ENCHANTMENT = new NoisyEnchantment(
            Enchantment.properties(
                    ItemTags.HEAD_ARMOR_ENCHANTABLE,
                    10,
                    1,
                    Enchantment.leveledCost(1, 11),
                    Enchantment.leveledCost(21, 11),
                    1,
                    EquipmentSlot.HEAD
            )
    );


    public static void registerEnchantments() {
        registerEnchantment("explosive", EXPLOSIVE_ENCHANTMENT);
        registerEnchantment("motionless_sneak", MOTIONLESS_SNEAK_ENCHANTMENT);
        registerEnchantment("crawl", CRAWL_ENCHANTMENT);
        registerEnchantment("blast_ejection", BLAST_EJECTION_ENCHANTMENT);
        registerEnchantment("absolute_loyalty", ABSOLUTE_LOYALTY_ENCHANTMENT);
        registerEnchantment("nemesis", NEMESIS_ENCHANTMENT);
        registerEnchantment("mlg_lava", MLG_LAVA_ENCHANTMENT);
        registerEnchantment("fire_walking", FIRE_WALKING_ENCHANTMENT);
        registerEnchantment("social_phobia", SOCIAL_PHOBIA_ENCHANTMENT);
        // registerEnchantment("hot_door", HOT_DOOR_ENCHANTMENT);
        registerEnchantment("babbling", BABBLING_ENCHANTMENT);
        registerEnchantment("dandruff", DANDRUFF_ENCHANTMENT);
        registerEnchantment("noisy", NOISY_ENCHANTMENT);

        ExplosiveEnchantmentHandler.register();

        registerEnchantmentRecipes(EXPLOSIVE_ENCHANTMENT, 1);
        registerEnchantmentRecipes(MOTIONLESS_SNEAK_ENCHANTMENT, 1);
        registerEnchantmentRecipes(CRAWL_ENCHANTMENT, 1);
        registerEnchantmentRecipes(BLAST_EJECTION_ENCHANTMENT, 4);
        registerEnchantmentRecipes(ABSOLUTE_LOYALTY_ENCHANTMENT, 1);
        registerEnchantmentRecipes(NEMESIS_ENCHANTMENT, 1);
        registerEnchantmentRecipes(MLG_LAVA_ENCHANTMENT, 1);
        registerEnchantmentRecipes(FIRE_WALKING_ENCHANTMENT, 1);
        registerEnchantmentRecipes(SOCIAL_PHOBIA_ENCHANTMENT, 1);
        // registerEnchantmentRecipes(HOT_DOOR_ENCHANTMENT, 1);
        registerEnchantmentRecipes(BABBLING_ENCHANTMENT, 1);
        registerEnchantmentRecipes(DANDRUFF_ENCHANTMENT, 1);
        registerEnchantmentRecipes(NOISY_ENCHANTMENT, 1);
    }

    private static void registerEnchantment(String name, Enchantment enchantment) {
        Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, name), enchantment);
    }

    private static void registerEnchantmentRecipes(Enchantment enchantment, int level) {
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        enchantedBook.addEnchantment(enchantment, level);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> content.add(enchantedBook));
    }
}
