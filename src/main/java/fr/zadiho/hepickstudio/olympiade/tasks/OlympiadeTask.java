package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.mrmicky.fastboard.FastBoard;
import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.guis.AdminGUI;
import fr.zadiho.hepickstudio.olympiade.manager.PlayerManager;
import fr.zadiho.hepickstudio.olympiade.manager.ScoreboardManager;
import fr.zadiho.hepickstudio.olympiade.utils.Chrono;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OlympiadeTask extends BukkitRunnable implements Listener {

    private Location spawnNether = new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1336.5, 75, 24.5, -90, 0);

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("olympiade.staff")) {
            event.setFormat(Game.getPlayerPrefix(player) + player.getName() + " §8§l» §f" + event.getMessage());
        } else {
            event.setFormat(Game.getPlayerPrefix(player) + player.getName() + " §8§l» §7" + event.getMessage());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null) {
            return;
        }
        if (player.getItemInHand() == null) {
            return;
        }
        if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("§8» §eHost")) {
            AdminGUI.SMART_INVENTORY.open(event.getPlayer());
        }
    }

    @Override
    public void run() {
        for(Player players : GameSettings.getGamePlayers()){
            if((EGames.getCurrentState() == EGames.WAITING || EGames.getCurrentState() == EGames.PVE && !(GameSettings.getHostPlayers().contains(players)))){
                players.setVisualFire(false);
                players.showPlayer(Olympiade.getInstance(), players);
                if(!(players.getGameMode() == GameMode.ADVENTURE)){
                    players.setGameMode(GameMode.ADVENTURE);
                }
            }

        }
        if(!(EGames.getCurrentState() == EGames.PVE)){
            WorldBorder worldBorder = Bukkit.getWorld("OlympiadeS3").getWorldBorder();
            worldBorder.setSize(999999999);
        }
        if((EGames.getCurrentState() == EGames.WAITING || EGames.getCurrentState() == EGames.RACE || EGames.getCurrentState() == EGames.PVE || EGames.getCurrentState() == EGames.PARKOUR)){
            Bukkit.getWorld("OlympiadeS3").setPVP(false);
            Bukkit.getWorld("OlympiadeS3_nether").setPVP(false);
            Bukkit.getWorld("OlympiadeS3_the_end").setPVP(false);
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            Cuboid spawnPortal = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -456.5, 66, -1244.5), new Location(Bukkit.getWorld("OlympiadeS3"), -454.5, 75, -1240.5));
            if (spawnPortal.isIn(players)) {
                players.teleport(spawnNether);
            }
            Cuboid netherPortal = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1339.5, 73, 22.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1337.5, 82, 26.5));
            if (netherPortal.isIn(players)) {
                players.teleport(GameSettings.spawn);
            }
            if(EGames.getCurrentState() == EGames.WAITING) {
                players.showPlayer(Olympiade.getInstance(), players);
                players.getActivePotionEffects().clear();
                players.setGlowing(false);
            }
            PlayerManager.registerPlayer(players);
            PlayerManager.hostItem(players);

            players.setPlayerListHeader("\n" +
                    "§8§l» §c§nOlympiade III§r §8§l«\n" +
                    "\n" +
                    "§7Joueurs: §f" + Bukkit.getOnlinePlayers().size() + " §7Ping: §f" + players.getPing() + " §7TPS: §a20*" +
                    "\n");
            players.setPlayerListFooter("\n" + "§8■ §cDiscord: §fdiscord.gg/wcAFza8SnQ\n" + "§8■ §cOrganisateur: §fCrazyOfTheGame\n" + "\n" + "§7Plugin: §c§lHepick§6§lStudio");
            ScoreboardManager.updateScoreboard(players);
        }
    }
}