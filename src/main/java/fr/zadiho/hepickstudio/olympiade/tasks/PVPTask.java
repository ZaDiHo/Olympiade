package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PVPTask extends BukkitRunnable implements Listener {

    private static int counter = 10;
    public static boolean played = false;
    public static int time = 0;
    private static List<Player> inPVP = new ArrayList<>();

    public static ArrayList<Player> alives = new ArrayList<>();


    public static void resetPVP() {
        setPlayed(false);
        counter = 10;
        alives.clear();
        time = 0;
        GameSettings.getPvpPodium().clear();
        getInPVP().clear();
    }


    public static boolean isPlayed() {
        return played;
    }

    public static void setPlayed(boolean played) {
        TNTTask.played = played;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(EGames.getCurrentState().equals(EGames.PVP)) {
            if (counter > -10) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (EGames.getCurrentState().equals(EGames.PVP)) {
            Player player = event.getPlayer();
            if (counter < 0) {
                if (EGames.getCurrentState().equals(EGames.PVP)) {
                    if (alives.contains(player)) {
                        if (player.getLocation().getY() < 60) {
                            player.setGlowing(true);
                        } else {
                            player.setGlowing(false);
                        }
                    }

                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (EGames.getCurrentState().equals(EGames.PVP)) {
            if (event.getEntity() instanceof Player player) {
                upgradeStuff(player.getKiller());
                if (getInPVP().contains(player)) {
                    alives.remove(player);
                    getInPVP().remove(player);
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 2));
                    event.setDroppedExp(0);
                    Bukkit.broadcastMessage("§c" + player.getName() + " §6est mort ! Il reste §c" + (getInPVP().size()) + " §6joueurs en vie !");
                    player.setGameMode(GameMode.SPECTATOR);
                    GameSettings.getPvpPodium().add(player);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (EGames.getCurrentState().equals(EGames.PVP)) {
            event.setDeathMessage(null);
        }
    }

    public void upgradeStuff(Player player) {
        Random random = new Random();
        int piece = random.nextInt(4) + 1;
        if (piece == 1) {
            ItemStack helmet = player.getInventory().getHelmet();
            if (helmet != null) {
                if (helmet.getType().equals(Material.IRON_HELMET)) {
                    player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                } else if (helmet.getType().equals(Material.DIAMOND_HELMET)) {
                    player.getInventory().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
                }
            }
        }
        if (piece == 2) {
            ItemStack chestplate = player.getInventory().getChestplate();
            if (chestplate != null) {
                if (chestplate.getType().equals(Material.IRON_CHESTPLATE)) {
                    player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                } else if (chestplate.getType().equals(Material.DIAMOND_CHESTPLATE)) {
                    player.getInventory().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                }
            }
        }
        if (piece == 3) {
            ItemStack leggings = player.getInventory().getLeggings();
            if (leggings != null) {
                if (leggings.getType().equals(Material.IRON_LEGGINGS)) {
                    player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                } else if (leggings.getType().equals(Material.DIAMOND_LEGGINGS)) {
                    player.getInventory().setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
                }
            }
        }
        if (piece == 4) {
            ItemStack boots = player.getInventory().getBoots();
            if (boots != null) {
                if (boots.getType().equals(Material.IRON_BOOTS)) {
                    player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                } else if (boots.getType().equals(Material.DIAMOND_BOOTS)) {
                    player.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
                }
            }
        }
    }

    public static void equipPlayer(Player player) {
        ItemStack[] armorContents = new ItemStack[4];
        armorContents[3] = new ItemStack(Material.IRON_HELMET);
        armorContents[2] = new ItemStack(Material.IRON_CHESTPLATE);
        armorContents[1] = new ItemStack(Material.IRON_LEGGINGS);
        armorContents[0] = new ItemStack(Material.IRON_BOOTS);

        player.getInventory().setArmorContents(armorContents);
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.getInventory().addItem(new ItemStack(Material.BOW));
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 16));
        player.getInventory().addItem(new ItemStack(Material.ARROW, 32));
    }

    private static void teleportPlayersCircle(List<Player> players) {
        Random random = new Random();
        double centerX = -1803.5;
        double centerY = 152.5;
        double centerZ = -1435.5;
        for (Player player : players) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double x = 175 * Math.cos(angle) + centerX;
            double z = 175 * Math.sin(angle) + centerZ;
            Location location = new Location(Bukkit.getWorlds().get(0), x, centerY, z);
            player.teleport(location);
        }
    }

    @Override
    public void run() {
        if (counter == 10) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.stopAllSounds();
                Bukkit.getWorld("OlympiadeS3").setPVP(false);
                players.playSound(players.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 1);
                players.setGameMode(GameMode.SURVIVAL);
                alives.add(players);
                getInPVP().add(players);
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -1803.5, 265, -1435.5, 0, 0));
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
                teleportPlayersCircle(alives);
                equipPlayer(players);
            }


        }
        if (counter == -10) {
            Bukkit.broadcastMessage("§6Le PVP est activé !");
            Bukkit.getWorld("OlympiadeS3").setPVP(true);
        }
        if (counter < 0) {
            for (Player players : GameSettings.getGamePlayers()) {
                if (players.isVisualFire()) {
                    players.setVisualFire(false);
                }
            }

            if (alives.size() == 1) {
                for (Player players : GameSettings.getGamePlayers()) {
                    GameSettings.getTntPodium().add(alives.get(0));
                    getInPVP().remove(alives.get(0));
                    players.sendTitle("§cPVP terminé !", "§6Victoire de §e" + alives.get(0).getDisplayName(), 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                    EGames.setState(EGames.WAITING);
                    setPlayed(true);
                }
                Game.teleportReversedPodium(GameSettings.getPvpPodium());
                Game.giveReversedPoints(GameSettings.getPvpPodium());
                cancel();
            }
            if (time / 60 >= EGames.TNT.getDuration()) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTemps écoulé !", "§6Le parcours est terminé !", 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                    EGames.setState(EGames.WAITING);
                    setPlayed(true);
                }
                Game.teleportReversedPodium(GameSettings.getPvpPodium());
                Game.giveReversedPoints(GameSettings.getPvpPodium());
                cancel();
            }
            time++;
        }
        counter--;
    }

    public static List<Player> getInPVP() {
        return inPVP;
    }
}
