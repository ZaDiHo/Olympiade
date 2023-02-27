package fr.zadiho.hepickstudio.olympiade.listeners;

import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.manager.PlayerManager;
import fr.zadiho.hepickstudio.olympiade.manager.ScoreboardManager;
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
        event.setJoinMessage(null);
        Bukkit.broadcastMessage("§7(§a§l»§7) §f" + Game.getPlayerPrefix(player) + player.getName());
        PlayerManager.registerPlayer(player);
        //player.setResourcePack();
        ScoreboardManager.createScoreboard(player);
        player.sendTitle("§c§lOlympiade III", "§fBienvenue " + Game.getPlayerPrefix(player) + player.getName());
        if(EGames.getCurrentState() == EGames.WAITING){
            player.sendMessage("§8§m----------------------------------------");
            player.sendMessage("§7Bienvenue sur §c§lOlympiade III");
            player.sendMessage("");
            player.sendMessage("§fTu es actuellement dans la §cphase de d'attente§f de lancement.");
            player.sendMessage("§fNous éspérons que tu iras jusqu'au bout de cette aventure !");
            player.sendMessage("");
            player.sendMessage("§7(Si tu es bloqué dans le spawn, tu peux utiliser §6§l/§espawn §7!)");
            player.sendMessage("§8§m----------------------------------------");
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

        }
    }
}
