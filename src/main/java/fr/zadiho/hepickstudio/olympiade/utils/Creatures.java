package fr.zadiho.hepickstudio.olympiade.utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class Creatures implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        /*
         * This will check to see if the world the event is being fired on is
         * one of the specified worlds you allow (can use a list/map etc... for
         * multiple)
         */
        if(event.getLocation().getWorld().getName() != "OlympiadeS3_world_the_end" && event.getLocation().getWorld().getName() != "OlympiadeS3") {
            if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
                event.setCancelled(true);
            }
        }
    }

}