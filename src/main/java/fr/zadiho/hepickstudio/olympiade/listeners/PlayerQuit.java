package fr.zadiho.hepickstudio.olympiade.listeners;

import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.manager.PlayerManager;
import fr.zadiho.hepickstudio.olympiade.manager.ScoreboardManager;
import fr.zadiho.hepickstudio.olympiade.tasks.RaceTask;
import fr.zadiho.hepickstudio.olympiade.tasks.TNTTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        Bukkit.broadcastMessage("§7(§c§l«§7) §f" + Game.getPlayerPrefix(player) + player.getName());
        PlayerManager.unregisterPlayer(player);
        GameSettings.getHostPlayers().remove(player);
        ScoreboardManager.deleteScoreboard(player);
    }
}
