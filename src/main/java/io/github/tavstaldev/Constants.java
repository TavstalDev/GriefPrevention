package io.github.tavstaldev;

import org.bukkit.Material;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Constants
{
    public static final Map<Material, Duration> FUEL;

    static {
        Map<Material, Duration> unsortedMap = new LinkedHashMap<>();
        unsortedMap.put(Material.COAL, Duration.ofHours(24));
        unsortedMap.put(Material.COAL_BLOCK, Duration.ofHours(216));
        unsortedMap.put(Material.CHARCOAL, Duration.ofHours(24));
        unsortedMap.put(Material.LAVA_BUCKET, Duration.ofHours(120));
        unsortedMap.put(Material.BLAZE_ROD, Duration.ofHours(48));
        unsortedMap.put(Material.OAK_LOG, Duration.ofHours(12));
        unsortedMap.put(Material.SPRUCE_LOG, Duration.ofHours(12));
        unsortedMap.put(Material.BIRCH_LOG, Duration.ofHours(12));
        unsortedMap.put(Material.JUNGLE_LOG, Duration.ofHours(12));
        unsortedMap.put(Material.ACACIA_LOG, Duration.ofHours(12));
        unsortedMap.put(Material.DARK_OAK_LOG, Duration.ofHours(12));
        unsortedMap.put(Material.MANGROVE_LOG, Duration.ofHours(12));
        unsortedMap.put(Material.CRIMSON_STEM, Duration.ofHours(12));
        unsortedMap.put(Material.WARPED_STEM, Duration.ofHours(12));
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
