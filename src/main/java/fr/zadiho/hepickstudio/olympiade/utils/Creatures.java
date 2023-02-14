package fr.zadiho.hepickstudio.olympiade.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class Creatures implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if(event.getLocation().getWorld().getName() != "OlympiadeS3_world_the_end" && event.getLocation().getWorld().getName() != "OlympiadeS3") {
            if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
                event.setCancelled(true);
            }
        }
    }

}