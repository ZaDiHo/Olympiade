package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FinalTask extends BukkitRunnable {

    private int time = 0;

    @EventHandler
    public static void onMove(PlayerMoveEvent event){
        if(EGames.getCurrentState().equals(EGames.END)){
            Player player = event.getPlayer();
            player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -477.5, 64, -1242.5, -90, 0));
            event.setCancelled(true);
        }
    }

    @Override
    public void run() {

        if(time == 0){
            for(Player players : Bukkit.getOnlinePlayers()){
                players.hidePlayer(Olympiade.getInstance(), players);
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -477.5, 64, -1242.5, -90, 0));
                players.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1000000));
                List<Map.Entry<Player, Integer>> sortedList = new ArrayList<>(GameSettings.getPodium().entrySet());

                Collections.sort(sortedList, new Comparator<Map.Entry<Player, Integer>>() {
                    public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                });
                List<Player> podium = new ArrayList<>();
                for (Map.Entry<Player, Integer> entry : sortedList) {
                    podium.add(entry.getKey());
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
                WinFireworks.setWinner(podium.get(0));
                WinFireworks winFireworks = new WinFireworks();
                winFireworks.runTaskTimer(Olympiade.getInstance(), 0, 20);
                players.sendTitle("§6§lFIN DES OLYMPIADES", "§e§lLe gagnant est : " + podium.get(0).getName(), 10, 100, 10);
                Game.teleportPodium(podium);
            }
        }
        if(time == 120){
            for(Player player : Bukkit.getOnlinePlayers())
                player.kickPlayer("§cFin des olympiades ! Félicitation à tous ! ");
            Bukkit.shutdown();
        }
        time++;
    }
}
