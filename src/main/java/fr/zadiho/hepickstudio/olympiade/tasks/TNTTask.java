package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.listeners.PlayerQuit;
import fr.zadiho.hepickstudio.olympiade.utils.Chrono;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.*;

public class TNTTask extends BukkitRunnable implements Listener {

    public static int counter = 18;
    public static boolean played = false;
    public static int time = 0;
    private static List<Player> inTNT = new ArrayList<>();
    private static int totalPlayer;
    private static Map<Player, Location> previousLocation = new HashMap<>();

    public static ArrayList<Player> alives = new ArrayList<>();

    private static Cuboid arena = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -217.5, -55.5, -803.5), new Location(Bukkit.getWorld("OlympiadeS3"), -172.5, 71, -847));
    private static Cuboid endTNT = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -171.5, -61, -802), new Location(Bukkit.getWorld("OlympiadeS3"), -221.5, -52.5, -851.5));
    //Iron blocks
    private static Cuboid floor1 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -216.5, 43, -804.5), new Location(Bukkit.getWorld("OlympiadeS3"), -173.5, 43, -847.5));
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


    @EventHandler
    public static void onLeave(PlayerQuitEvent event) {
        if (EGames.getCurrentState().equals(EGames.TNT)) {
            alives.remove(event.getPlayer());
            totalPlayer--;
            getInTNT().remove(event.getPlayer());
        }
    }

    @EventHandler
    public static void onConnect(PlayerJoinEvent event) {
        if (EGames.getCurrentState().equals(EGames.TNT)) {
            Player player = event.getPlayer();
            alives.remove(player);
            getInTNT().remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            player.sendMessage(GameSettings.prefix + "§cL'épreuve est déjà commencée ! Pas d'inquiétude, vous l'avez déjà terminée !");
            player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -196.5, 44, -810.5));
        }

    }

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

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (EGames.getCurrentState().equals(EGames.TNT)) {
            if (alives.contains(event.getPlayer())) {
                alives.remove(event.getPlayer());
            }
            getInTNT().remove(event.getPlayer());
        }
    }

    public static void resetRace() {
        setPlayed(false);
        counter = 18;
        alives.clear();
        time = 0;
        totalPlayer = 0;
        GameSettings.getTntPodium().clear();
        getInTNT().clear();
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
        if (EGames.getCurrentState() == EGames.TNT) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (counter < 0) {
            if (EGames.getCurrentState().equals(EGames.TNT) && arena.isIn(player)) {
                if (alives.contains(player)) {
                    Block block = player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 0));
                    Block block1 = player.getWorld().getBlockAt(player.getLocation());
                    Bukkit.getScheduler().runTaskLater(Olympiade.getInstance(), () -> {
                        block.setType(Material.AIR);
                        block1.setType(Material.AIR);
                    }, 10);
                }
            }
        }
    }


    @Override
    public void run() {
        if (counter == 18) {
            fillFloors();
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -196.5, 44, -810.5));
                players.stopAllSounds();
                players.playSound(players.getLocation(), Sound.MUSIC_DISC_CAT, 1, 1);
                players.showPlayer(Olympiade.getInstance(), players);
                players.getActivePotionEffects().clear();
                alives.add(players);
                getInTNT().add(players);
                totalPlayer++;
            }
        }
        if (counter == 10) {
            fillFloors();
            for (Player players : Bukkit.getOnlinePlayers()) {
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
            System.out.println(previousLocation);
            for (Player players : GameSettings.getGamePlayers()) {
                if (alives.contains(players)) {

                    previousLocation.put(players, players.getLocation().subtract(0, 1, 0));

                    Block block = players.getWorld().getBlockAt(players.getLocation().subtract(0, 1, 0));
                    Block block1 = players.getWorld().getBlockAt(players.getLocation());
                    Bukkit.getScheduler().runTaskLater(Olympiade.getInstance(), () -> {
                        block.setType(Material.AIR);
                        block1.setType(Material.AIR);
                    }, 10);
                }
                if (players.isVisualFire()) {
                    players.setVisualFire(false);
                }
                if (TNTTask.alives.contains(players)) {
                    Location location = players.getLocation();
                    Block block = location.getBlock();
                    Block adjacentBlock1 = block.getRelative(BlockFace.NORTH);
                    Block adjacentBlock2 = block.getRelative(BlockFace.SOUTH);
                    if (adjacentBlock1.getType() != Material.AIR && adjacentBlock2.getType() != Material.AIR) {
                        BoundingBox boundingBox = players.getBoundingBox();
                        if (boundingBox.overlaps(adjacentBlock1.getBoundingBox()) && boundingBox.overlaps(adjacentBlock2.getBoundingBox())) {
                            Bukkit.getScheduler().runTaskLater(Olympiade.getInstance(), () -> {
                                adjacentBlock1.setType(Material.AIR);
                                adjacentBlock2.setType(Material.AIR);
                            }, 10);
                        }
                    } else {
                        block.setType(Material.AIR);
                    }
                }
                if (endTNT.isIn(players)) {
                    if (getInTNT().contains(players)) {
                        getInTNT().remove(players);
                        GameSettings.getTntPodium().add(players);
                        alives.remove(players);
                        Bukkit.broadcastMessage("§a" + players.getName() + " §6est mort ! §7(§c" + alives.size() + "§8/§e" + totalPlayer + "§7)");
                        players.setGameMode(GameMode.SPECTATOR);
                    }
                }
            }

            if (alives.size() == 1) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTntRun terminé !", "§6Victoire de §e" + alives.get(0).getDisplayName(), 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                }
                GameSettings.getTntPodium().add(alives.get(0));
                getInTNT().remove(alives.get(0));
                EGames.setState(EGames.WAITING);
                setPlayed(true);
                Game.reversedTeleportPodium(GameSettings.getTntPodium());
                Game.reversedGivePoints(GameSettings.getTntPodium());
                cancel();
            }
            if (alives.size() == 0) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTntRun terminé !", "§6Égalité !", 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                }
                getInTNT().clear();
                EGames.setState(EGames.WAITING);
                setPlayed(true);
                Game.reversedTeleportPodium(GameSettings.getTntPodium());
                Game.reversedGivePoints(GameSettings.getTntPodium());
                cancel();
            }
            if (time / 60 >= EGames.TNT.getDuration()) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTemps écoulé !", "§6Le parcours est terminé !", 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                }
                EGames.setState(EGames.WAITING);
                setPlayed(true);
                Game.reversedTeleportPodium(GameSettings.getTntPodium());
                Game.reversedGivePoints(GameSettings.getTntPodium());
                cancel();
            }
            time++;
        }
        counter--;
    }

    public static List<Player> getInTNT() {
        return inTNT;
    }
}