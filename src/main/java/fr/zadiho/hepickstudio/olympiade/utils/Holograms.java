package fr.zadiho.hepickstudio.olympiade.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.Objects;

public class Holograms {

    public static void createHologram(String name, Location location){
        ArmorStand armorStand = (ArmorStand) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(name);
        armorStand.setVisualFire(false);
    }

    public static void setupHolograms(){
        createHologram("§e§l⟺ §6Direction §e§l⟺", new Location(Bukkit.getWorld("OlympiadeS3_nether"), -885.5, 76, 135.5));
        createHologram("§e§l⟹ §6Direction §e§l⟹", new Location(Bukkit.getWorld("OlympiadeS3_nether"), -779.5, 45, 157.5));
        createHologram("§e§l⟺ §6Direction §e§l⟺", new Location(Bukkit.getWorld("OlympiadeS3_nether"), -728, 78, 170.5));
        createHologram("§e§l⟸ §6Direction §e§l⟸", new Location(Bukkit.getWorld("OlympiadeS3_nether"), -681, 75, 183.5));
    }

    public static void clearHolograms(){
        for (ArmorStand armorStand : Objects.requireNonNull(Bukkit.getWorld("OlympiadeS3_nether")).getEntitiesByClass(ArmorStand.class)) {
            armorStand.remove();
        }
    }
}
