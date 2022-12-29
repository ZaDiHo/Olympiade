package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.mrmicky.fastboard.FastBoard;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.guis.AdminGUI;
import fr.zadiho.hepickstudio.olympiade.manager.PlayerManager;
import fr.zadiho.hepickstudio.olympiade.utils.Chrono;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class OlympiadeTask extends BukkitRunnable implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("olympiade.staff")) {
            event.setFormat(GameSettings.getPrefix(player) + player.getName() + " §8§l» §f" + event.getMessage());
        } else {
            event.setFormat(GameSettings.getPrefix(player) + player.getName() + " §8§l» §7" + event.getMessage());
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
            if(EGames.getCurrentState() == EGames.WAITING){
                if(!(players.getGameMode() == GameMode.ADVENTURE)){
                    players.setGameMode(GameMode.ADVENTURE);
                }
            }

        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            Cuboid spawnPortal = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -456.5, 66, -1244.5), new Location(Bukkit.getWorld("OlympiadeS3"), -454.5, 75, -1240.5));
            if (spawnPortal.isIn(players)) {
                players.teleport(GameSettings.spawnNether);
            }
            Cuboid netherPortal = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1339.5, 73, 22.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1337.5, 82, 26.5));
            if (netherPortal.isIn(players)) {
                players.teleport(GameSettings.spawn);
            }
            FastBoard board = new FastBoard(players);
            PlayerManager.registerPlayer(players);
            PlayerManager.hostItem(players);
            //if (players.isOp()) {
            //    if (GameSettings.getHostPlayers().contains(players)) {
            //        GameSettings.getGamePlayers().remove(players);
            //    }
            //}
            players.setPlayerListHeader("\n" +
                    "§8§l» §c§nOlympiade III§r §8§l«\n" +
                    "\n" +
                    "§7Joueurs: §f" + Bukkit.getOnlinePlayers().size() + " §7Ping: §f" + players.getPing() + " §7TPS: §a20*" +
                    "\n");
            players.setPlayerListFooter("\n" + "§8■ §cDiscord: §fdiscord.gg/wcAFza8SnQ\n" + "§8■ §cOrganisateur: §fCrazyOfTheGame\n" + "\n" + "§7Plugin: §c§lHepick§6§lStudio");
            if (EGames.getCurrentState() == EGames.WAITING) {
                board.updateTitle("§c§nOlympiade III");
                board.updateLines(
                        "§7§m------------------", // Empty line
                        "§8■ §fCompte §7➢ §6" + players.getName(),
                        "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(players),
                        "",
                        "§8■ §fÉpreuve §7➢ §cEn attente...",
                        "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                        "§7§m------------------",
                        "§6play.olympiade.fr"
                );
            }
            if (EGames.getCurrentState() == EGames.RACE) {
                board.updateTitle("§c§nOlympiade III");
                board.updateLines(
                        "§7§m------------------", // Empty line
                        "§8■ §fCompte §7➢ §6" + players.getName(),
                        "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(players),
                        "",
                        "§8■ §fÉpreuve §7➢ §cCourse d'Arpenteur",
                        "§8■ §fChrono §7➢ §e" + Chrono.format(RaceTask.time),
                        "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                        "§7§m------------------",
                        "§6play.olympiade.fr"
                );
            }
            if (EGames.getCurrentState() == EGames.PARKOUR) {
                board.updateTitle("§c§nOlympiade III");
                board.updateLines(
                        "§7§m------------------", // Empty line
                        "§8■ §fCompte §7➢ §6" + players.getName(),
                        "§8■ §fPoints §7➢ §6" + GameSettings.getPodium().get(players),
                        "",
                        "§8■ §fÉpreuve §7➢ §cParcours",
                        "§8■ §fChrono §7➢ §e" + Chrono.format(JumpTask.time),
                        "§8■ §fJoueurs §7➢ §e" + GameSettings.getGamePlayers().size(),
                        "§7§m------------------",
                        "§6play.olympiade.fr"
                );
            }
        }
    }
}