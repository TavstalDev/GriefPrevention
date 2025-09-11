package io.github.tavstaldev;

import org.bukkit.Material;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Constants
{
    public static final Map<Material, Duration> FUEL;
    // TODO: Make it configurable when I have time
    static {
        Map<Material, Duration> unsortedMap = new LinkedHashMap<>();
        // OAK
        unsortedMap.put(Material.OAK_LOG,Duration.ofSeconds(20));
        unsortedMap.put(Material.OAK_PLANKS, Duration.ofSeconds(20));
        unsortedMap.put(Material.OAK_WOOD, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_OAK_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_OAK_WOOD, Duration.ofSeconds(20));
        // SPRUCE
        unsortedMap.put(Material.SPRUCE_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.SPRUCE_PLANKS, Duration.ofSeconds(20));
        unsortedMap.put(Material.SPRUCE_WOOD, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_SPRUCE_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_SPRUCE_WOOD, Duration.ofSeconds(20));
        // BIRCH
        unsortedMap.put(Material.BIRCH_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.BIRCH_PLANKS, Duration.ofSeconds(20));
        unsortedMap.put(Material.BIRCH_WOOD, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_BIRCH_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_BIRCH_WOOD, Duration.ofSeconds(20));
        // JUNGLE
        unsortedMap.put(Material.JUNGLE_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.JUNGLE_PLANKS, Duration.ofSeconds(20));
        unsortedMap.put(Material.JUNGLE_WOOD, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_JUNGLE_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_JUNGLE_WOOD, Duration.ofSeconds(20));
        // ACACIA
        unsortedMap.put(Material.ACACIA_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.ACACIA_PLANKS, Duration.ofSeconds(20));
        unsortedMap.put(Material.ACACIA_WOOD, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_ACACIA_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_ACACIA_WOOD, Duration.ofSeconds(20));
        // DARK OAK
        unsortedMap.put(Material.DARK_OAK_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.DARK_OAK_PLANKS, Duration.ofSeconds(20));
        unsortedMap.put(Material.DARK_OAK_WOOD, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_DARK_OAK_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_DARK_OAK_WOOD, Duration.ofSeconds(20));
        // MANGROVE
        unsortedMap.put(Material.MANGROVE_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.MANGROVE_PLANKS, Duration.ofSeconds(20));
        unsortedMap.put(Material.MANGROVE_WOOD, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_MANGROVE_LOG, Duration.ofSeconds(20));
        unsortedMap.put(Material.STRIPPED_MANGROVE_WOOD, Duration.ofSeconds(20));

        // STONE STUFF
        unsortedMap.put(Material.STONE, Duration.ofSeconds(30));
        unsortedMap.put(Material.SMOOTH_STONE, Duration.ofSeconds(30));
        unsortedMap.put(Material.ANDESITE, Duration.ofSeconds(30));
        unsortedMap.put(Material.POLISHED_ANDESITE, Duration.ofSeconds(30));
        unsortedMap.put(Material.DIORITE, Duration.ofSeconds(30));
        unsortedMap.put(Material.POLISHED_DIORITE, Duration.ofSeconds(30));
        unsortedMap.put(Material.GRANITE, Duration.ofSeconds(30));
        unsortedMap.put(Material.POLISHED_GRANITE, Duration.ofSeconds(30));
        unsortedMap.put(Material.STONE_BRICKS, Duration.ofSeconds(30));
        unsortedMap.put(Material.MOSSY_STONE_BRICKS, Duration.ofSeconds(30));
        unsortedMap.put(Material.CRACKED_STONE_BRICKS, Duration.ofSeconds(30));
        unsortedMap.put(Material.CHISELED_STONE_BRICKS, Duration.ofSeconds(30));
        unsortedMap.put(Material.COBBLESTONE, Duration.ofSeconds(30));
        unsortedMap.put(Material.MOSSY_COBBLESTONE, Duration.ofSeconds(30));
        unsortedMap.put(Material.SANDSTONE, Duration.ofSeconds(30));
        unsortedMap.put(Material.CHISELED_SANDSTONE, Duration.ofSeconds(30));
        unsortedMap.put(Material.CUT_SANDSTONE, Duration.ofSeconds(30));
        unsortedMap.put(Material.RED_SANDSTONE, Duration.ofSeconds(30));
        unsortedMap.put(Material.CHISELED_RED_SANDSTONE, Duration.ofSeconds(30));
        unsortedMap.put(Material.CUT_RED_SANDSTONE, Duration.ofSeconds(30));

        // NETHER STUFF
        unsortedMap.put(Material.NETHERRACK, Duration.ofMinutes(8));
        unsortedMap.put(Material.CRIMSON_HYPHAE, Duration.ofMinutes(8));
        unsortedMap.put(Material.WARPED_HYPHAE, Duration.ofMinutes(8));
        unsortedMap.put(Material.NETHER_BRICKS, Duration.ofMinutes(8));
        unsortedMap.put(Material.CRACKED_NETHER_BRICKS, Duration.ofMinutes(8));
        unsortedMap.put(Material.CHISELED_NETHER_BRICKS, Duration.ofMinutes(8));
        unsortedMap.put(Material.RED_NETHER_BRICKS, Duration.ofMinutes(8));
        unsortedMap.put(Material.CRIMSON_STEM,  Duration.ofMinutes(8));
        unsortedMap.put(Material.WARPED_STEM,  Duration.ofMinutes(8));
        unsortedMap.put(Material.NETHER_WART_BLOCK, Duration.ofMinutes(8));
        unsortedMap.put(Material.WARPED_WART_BLOCK, Duration.ofMinutes(8));
        unsortedMap.put(Material.BASALT, Duration.ofMinutes(8));
        unsortedMap.put(Material.POLISHED_BASALT, Duration.ofMinutes(8));
        unsortedMap.put(Material.BLACKSTONE, Duration.ofMinutes(8));
        unsortedMap.put(Material.POLISHED_BLACKSTONE, Duration.ofMinutes(8));
        unsortedMap.put(Material.POLISHED_BLACKSTONE_BRICKS, Duration.ofMinutes(8));
        unsortedMap.put(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS, Duration.ofMinutes(8));
        unsortedMap.put(Material.CHISELED_POLISHED_BLACKSTONE, Duration.ofMinutes(8));
        unsortedMap.put(Material.GILDED_BLACKSTONE, Duration.ofMinutes(8));
        unsortedMap.put(Material.QUARTZ_BLOCK, Duration.ofMinutes(8));
        unsortedMap.put(Material.QUARTZ_BRICKS, Duration.ofMinutes(8));
        unsortedMap.put(Material.CHISELED_QUARTZ_BLOCK, Duration.ofMinutes(8));
        unsortedMap.put(Material.QUARTZ_PILLAR, Duration.ofMinutes(8));
        unsortedMap.put(Material.SMOOTH_QUARTZ, Duration.ofMinutes(8));
        unsortedMap.put(Material.SOUL_SAND, Duration.ofMinutes(8));
        unsortedMap.put(Material.SOUL_SOIL, Duration.ofMinutes(8));
        unsortedMap.put(Material.NETHER_SPROUTS, Duration.ofMinutes(8));
        unsortedMap.put(Material.BLAZE_ROD, Duration.ofMinutes(8));
        unsortedMap.put(Material.WITHER_ROSE, Duration.ofMinutes(8));
        unsortedMap.put(Material.SHROOMLIGHT, Duration.ofMinutes(8));
        unsortedMap.put(Material.GLOWSTONE, Duration.ofMinutes(8));
        unsortedMap.put(Material.MAGMA_BLOCK, Duration.ofMinutes(8));

        // END STUFF
        unsortedMap.put(Material.END_STONE, Duration.ofMinutes(10));
        unsortedMap.put(Material.PURPUR_BLOCK, Duration.ofMinutes(10));
        unsortedMap.put(Material.PURPUR_PILLAR, Duration.ofMinutes(10));
        unsortedMap.put(Material.END_STONE_BRICKS, Duration.ofMinutes(10));
        unsortedMap.put(Material.END_ROD, Duration.ofMinutes(10));
        unsortedMap.put(Material.CHORUS_PLANT, Duration.ofMinutes(10));
        unsortedMap.put(Material.CHORUS_FLOWER, Duration.ofMinutes(10));
        unsortedMap.put(Material.POPPED_CHORUS_FRUIT, Duration.ofMinutes(10));

        // ORES & INGOTS
        unsortedMap.put(Material.OBSIDIAN, Duration.ofMinutes(10));
        unsortedMap.put(Material.CRYING_OBSIDIAN, Duration.ofMinutes(10));

        unsortedMap.put(Material.COAL, Duration.ofSeconds(30));
        unsortedMap.put(Material.CHARCOAL, Duration.ofSeconds(25));
        unsortedMap.put(Material.COPPER_INGOT, Duration.ofSeconds(20));
        unsortedMap.put(Material.IRON_INGOT, Duration.ofSeconds(40));
        unsortedMap.put(Material.QUARTZ, Duration.ofSeconds(50));
        unsortedMap.put(Material.REDSTONE, Duration.ofSeconds(75));
        unsortedMap.put(Material.GOLD_INGOT, Duration.ofMinutes(2));
        unsortedMap.put(Material.LAPIS_LAZULI, Duration.ofMinutes(25));
        unsortedMap.put(Material.DIAMOND, Duration.ofMinutes(45));
        unsortedMap.put(Material.NETHERITE_SCRAP, Duration.ofHours(2));
        unsortedMap.put(Material.NETHERITE_INGOT, Duration.ofHours(6));

        // Add all other entries

        FUEL = unsortedMap.entrySet().stream()
                .sorted(Map.Entry.<Material, Duration>comparingByValue().reversed()) // Sort by duration descending
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    public static final Material CORE_BLOCK_MATERIAL = Material.BARREL;
}
