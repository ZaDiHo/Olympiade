package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.utils.Chrono;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TNTTask extends BukkitRunnable implements Listener {

    private static int counter = 10;
    public static boolean played = false;
    public static int time = 0;

    public static ArrayList<Player> alives = new ArrayList<>();
    private static HashMap<Player, Integer> podium = new HashMap();

    private static Cuboid arena = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -217.5, -55.5, -803.5), new Location(Bukkit.getWorld("OlympiadeS3"), -172.5, 71, -847));
    private static Cuboid endTNT = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -171.5, -61, -802), new Location(Bukkit.getWorld("OlympiadeS3"), -221.5, -52.5, -851.5));
    //Iron blocks
    private static Cuboid floor1 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -216.5, 43, -804), new Location(Bukkit.getWorld("OlympiadeS3"), -173.5, 43, -847.5));
    //Moss blocks
    private static Cuboid floor2 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -174, 25, -805), new Location(Bukkit.getWorld("OlympiadeS3"), -217, 25, -848));
    //Dripstone blocks
    private static Cuboid floor3 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -217, 7, -848), new Location(Bukkit.getWorld("OlympiadeS3"), -174, 7, -805));
    //deepslate
    private static Cuboid floor4 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -174, -12, -805), new Location(Bukkit.getWorld("OlympiadeS3"), -217, -12, -848));
    //sculk sensor
    private static Cuboid floor5 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -217, -31, -848), new Location(Bukkit.getWorld("OlympiadeS3"), -174, -31, -805));
    //red glass
    private static Cuboid floor6 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -174, -50, -805), new Location(Bukkit.getWorld("OlympiadeS3"), -217, -50, -848));

    public static void fillFloors() {
        for (Iterator<Block> it = floor1.blockList(); it.hasNext(); ) {
            Block block = it.next();
            block.setType(Material.IRON_BLOCK);
        }
        for (Iterator<Block> it = floor2.blockList(); it.hasNext(); ) {
            Block block = it.next();
            block.setType(Material.MOSS_BLOCK);
        }
        for (Iterator<Block> it = floor3.blockList(); it.hasNext(); ) {
            Block block = it.next();
            block.setType(Material.DRIPSTONE_BLOCK);
        }
        for (Iterator<Block> it = floor4.blockList(); it.hasNext(); ) {
            Block block = it.next();
            block.setType(Material.DEEPSLATE);
        }
        for (Iterator<Block> it = floor5.blockList(); it.hasNext(); ) {
            Block block = it.next();
            block.setType(Material.SCULK_SENSOR);
        }
        for (Iterator<Block> it = floor6.blockList(); it.hasNext(); ) {
            Block block = it.next();
            block.setType(Material.RED_STAINED_GLASS);
        }
    }

    public static void resetRace() {
        setPlayed(false);
        counter = 10;
        time = 0;
        GameSettings.getTntPodium().clear();
        GameSettings.getInTNT().clear();
        fillFloors();
    }


    public static boolean isPlayed() {
        return played;
    }

    public static void setPlayed(boolean played) {
        TNTTask.played = played;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (counter < 0) {
            if (EGames.getCurrentState().equals(EGames.TNT) && arena.isIn(player)) {
                if (alives.contains(player)) {
                    Block block = player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 0));
                    Bukkit.getScheduler().runTaskLater(Olympiade.getInstance(), () -> {
                        block.setType(Material.AIR);
                    }, 10);
                }
            }
        }
    }

    @Override
    public void run() {
        if (counter == 10) {
            fillFloors();
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.stopAllSounds();
                alives.add(players);
                GameSettings.getInTNT().put(players, false);
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -196.5, 44, -810.5));
                players.sendTitle("§cAttention !", "§6Placez vous devant la ligne de départ !", 10, 20, 10);
            }
        }
        if (counter == 5) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§95", "§bTenez vous prêt !", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.E));
            }
        }
        if (counter == 4) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§24", "§aPas de faux départs !", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.D));
            }
        }
        if (counter == 3) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§e3", "§6A vos marques", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.C));
            }
        }
        if (counter == 2) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§62", "§ePrêt ?", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.B));
            }
        }
        if (counter == 1) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§c1", "§4C'est partie !", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.A));
            }
        }
        if (counter == 0) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }

        }
        if (counter < 0) {
            for (Player players : GameSettings.getGamePlayers()) {
                if (players.isVisualFire()) {
                    players.setVisualFire(false);
                }

                if (endTNT.isIn(players)) {
                    if (!GameSettings.getInTNT().get(players)) {
                        GameSettings.getInTNT().remove(players);
                        GameSettings.getTntPodium().put(players, GameSettings.getJumpPodium().size() + 1);
                        alives.remove(players);
                        Bukkit.broadcastMessage("§a" + players.getName() + " §6est mort ! §7(§c" + alives.size() + "§8/§e" + GameSettings.getGamePlayers().size() + "§7)");
                        players.setGameMode(GameMode.SPECTATOR);
                    }
                    GameSettings.getInTNT().put(players, true);
                }
            }

            if (alives.size() == 1) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTntRun terminé !", "§6Victoire de §e" + alives.get(0).getDisplayName(), 10, 100, 10);
                    WinFireworks.setWinner(alives.get(0));
                    WinFireworks winFireworks = new WinFireworks();
                    winFireworks.runTaskTimer(Olympiade.getInstance(), 0, 20);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    GameSettings.teleportPodium(EGames.TNT);
                    EGames.setState(EGames.WAITING);
                    setPlayed(true);
                }
                cancel();
            }
            if (time / 60 >= GameSettings.getTntDuration()) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTemps écoulé !", "§6Le parcours est terminé !", 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.teleport(GameSettings.spawn);
                    EGames.setState(EGames.WAITING);
                    setPlayed(true);
                }
                cancel();
            }
            time++;
        }
        counter--;
    }
}