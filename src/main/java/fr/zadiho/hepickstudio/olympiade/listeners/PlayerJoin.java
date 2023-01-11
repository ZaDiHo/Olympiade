package fr.zadiho.hepickstudio.olympiade.listeners;

import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(EGames.getCurrentState() == EGames.WAITING){
            event.setJoinMessage(null);
            Bukkit.broadcastMessage("§7(§a§l»§7) §f" + GameSettings.getPrefix(player) + player.getName());
            player.sendTitle("§c§lOlympiade III", "§fBienvenue " + GameSettings.getPrefix(player) + player.getName());
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20);
            player.playSound(player.getLocation(), Sound.ENTITY_MOOSHROOM_CONVERT, 1, 1);
            player.getInventory().clear();
            player.setExp(0);
            player.setLevel(2023);
            player.teleport(GameSettings.spawn);
            if(player.hasPermission("olympiade.staff")){
                player.setAllowFlight(true);
            }
            player.playSound(player.getLocation(), Sound.MUSIC_DISC_11, 1, 1);
            PlayerManager.registerPlayer(player);
        }

        //if(player.isOp()){
        //    GameSettings.getHostPlayers().add(player);
        //}else{
        //    GameSettings.getGamePlayers().add(player);
        //}
    }
}
