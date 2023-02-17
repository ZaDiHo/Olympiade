package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.utils.Chrono;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Strider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RaceTask extends BukkitRunnable implements Listener {

    /*
     * Class Name : RaceTask
     * Description   : Race game
     * Version       : 1.4
     * Date          : 13/02/2023
     * Copyright     : HepickStudio
     */


    //////////////////////////////////////VARIABLES////////////////////////////////////////
    private static Cuboid portal1 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -754.5, 40, 187.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -762.5, 55.5, 194.5));
    private static Cuboid portal2 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -656, 69, 222.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -662.5, 83, 225.5));
    public static Cuboid endRace = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -564.5, 45, 270.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -570.5, 30, 277.5));
    private static List<Player> inRace = new ArrayList<>();
    private static int counter = 17;
    private static int place = 1;
    public static int time = 0;
    private static boolean played = false;
    private static int totalPlayers = 0;
    /////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////GETTERS & METHODS/////////////////////////////////////
    public static void resetRace() {
        setPlayed(false);
        totalPlayers = 0;
        counter = 17;
        place = 1;
        time = 0;
        getInRace().clear();
        GameSettings.getRacePodium().clear();
    }

    public static boolean isPlayed() {
        return played;
    }

    private static void setPlayed(boolean played) {
        RaceTask.played = played;
    }

    public static List<Player> getInRace() {
        return inRace;
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////EVENTS//////////////////////////////////
    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(EGames.getCurrentState().equals(EGames.RACE)){
            if (event.getEntityType() == EntityType.STRIDER) {
                event.setCancelled(true);
            }
            if (event.getEntityType() == EntityType.PLAYER) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }
    //////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////RUN///////////////////////////////////////////////////////////
    @Override
    public void run() {
        if(counter == 17){
            for(Player players : Bukkit.getOnlinePlayers()){
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -882.7, 91.5, 57.0, 90, 0));
                players.stopAllSounds();
                players.playSound(players.getLocation(), Sound.MUSIC_DISC_BLOCKS, 1, 1);
                Strider monture = (Strider) players.getWorld().spawnEntity(players.getLocation(), EntityType.STRIDER);
                monture.setSaddle(true);
                monture.addPassenger(players);
                players.getInventory().setItem(4, new ItemBuilder(Material.WARPED_FUNGUS_ON_A_STICK).setName("§6Bâton de course").toItemStack());
                players.showPlayer(Olympiade.getInstance(), players);
                players.getActivePotionEffects().clear();
                getInRace().add(players);
            }
        }
        if (counter == 10) {
            Cuboid.fillStartRace();
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
                Cuboid.clearStartRace();
            }

        }
        if (counter < 0) {
            totalPlayers = getInRace().size();
            for (Player players : GameSettings.getGamePlayers()) {
                if (players.isVisualFire()) {
                    players.setVisualFire(false);
                }
                players.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6Chronomètre: §e" + Chrono.format(time)));
                if (portal1.isIn(players)) {
                    Objects.requireNonNull(players.getVehicle()).remove();
                    players.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -762.5, 77, 187.5, -160, 0));
                    Strider monture = (Strider) players.getWorld().spawnEntity(players.getLocation(), EntityType.STRIDER);
                    monture.setSaddle(true);
                    monture.addPassenger(players);
                }
                if (portal2.isIn(players)) {
                    Objects.requireNonNull(players.getVehicle()).remove();
                    players.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -650, 75, 272.5, -90, 0));
                    Strider monture = (Strider) players.getWorld().spawnEntity(players.getLocation(), EntityType.STRIDER);
                    monture.setSaddle(true);
                    monture.addPassenger(players);
                }
                if (endRace.isIn(players)) {
                    if (getInRace().contains(players)) {
                        getInRace().remove(players);
                        GameSettings.getRacePodium().add(players);
                        Bukkit.broadcastMessage("§a" + players.getName() + " §6a terminé la course à la position §e" + place + " §6! Son chronomètre affichait §e" + Chrono.format(time));
                        place ++;
                        Objects.requireNonNull(players.getVehicle()).remove();
                        players.setGameMode(GameMode.SPECTATOR);
                    }
                    getInRace().remove(players);
                }
            }
            if (GameSettings.getGamePlayers().size() == GameSettings.getRacePodium().size()) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cCourse terminée !", "§6Le parcours est terminé !", 10, 20, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                    EGames.setState(EGames.WAITING);
                    setPlayed(true);
                    Cuboid.fillStartRace();
                }
                Game.teleportPodium(GameSettings.getRacePodium());
                Game.givePoints(GameSettings.getRacePodium());
                cancel();
            }
            if (time / 60 >= EGames.RACE.getDuration()) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTemps écoulé !", "§6La course est terminée !", 10, 20, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                    EGames.setState(EGames.WAITING);
                    setPlayed(true);
                    Cuboid.fillStartRace();
                }
                Game.teleportPodium(GameSettings.getRacePodium());
                Game.givePoints(GameSettings.getRacePodium());
                cancel();
            }
            time++;
        }
        counter--;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}