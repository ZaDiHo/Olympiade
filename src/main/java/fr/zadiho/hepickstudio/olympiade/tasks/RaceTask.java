package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.utils.Chrono;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Strider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class RaceTask extends BukkitRunnable implements Listener {

    private static int counter = 10;
    public static int time = 0;

    public static void resetRace(){
        counter = 10;
        time = 0;
        GameSettings.getInRace().clear();
        GameSettings.getRacePodium().clear();
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.STRIDER) {
            event.setCancelled(true);
        }
        if (event.getEntityType() == EntityType.PLAYER) {
            event.setCancelled(true);
        }
    }

    @Override
    public void run() {
        if (counter == 10) {
            for(Entity strider : Objects.requireNonNull(Bukkit.getWorld("OlympiadeS3_nether")).getEntities()){
                if(strider.getType() == EntityType.STRIDER){
                    strider.remove();
                }
            }
            Cuboid.fillStartRace();
            for (Player players : Bukkit.getOnlinePlayers()) {
                GameSettings.getInRace().put(players, false);
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -882.7, 91.5, 57.0, 90, 0));
                players.sendTitle("§cAttention !", "§6Placez vous devant la ligne de départ !", 10, 20, 10);
                Strider monture = (Strider) players.getWorld().spawnEntity(players.getLocation(), EntityType.STRIDER);
                monture.setSaddle(true);
                monture.addPassenger(players);
                players.getInventory().setItem(4, new ItemBuilder(Material.WARPED_FUNGUS_ON_A_STICK).setName("§6Bâton de course").toItemStack());
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
            for (Player players : GameSettings.getGamePlayers()) {
                if (players.isVisualFire()) {
                    players.setVisualFire(false);
                }
                players.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6Chronomètre: §e" + Chrono.format(time)));
                if (GameSettings.endRace.isIn(players)) {
                    if (!GameSettings.getInRace().get(players)) {
                        GameSettings.getInRace().remove(players);
                        GameSettings.getRacePodium().put(players, GameSettings.getRacePodium().size() + 1);
                        Bukkit.broadcastMessage("§a" + players.getName() + " §6a terminé la course à la position §e" + GameSettings.getRacePodium().get(players) + " §6! Son chronomètre affichait §e" + Chrono.format(time));
                        players.setGameMode(GameMode.SPECTATOR);
                    }
                    GameSettings.getInRace().put(players, true);
                }
            }
            if(GameSettings.getGamePlayers().size() == GameSettings.getRacePodium().size()){
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cCourse terminée !", "§6Le parcours est terminé !", 10, 20, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.SURVIVAL);
                    GameSettings.teleportPodium(EGames.RACE);
                    EGames.setState(EGames.WAITING);
                }
                cancel();
            }
            if (time / 60 >= GameSettings.getRaceDuration()) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTemps écoulé !", "§6La course est terminée !", 10, 20, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.teleport(GameSettings.spawn);
                    EGames.setState(EGames.WAITING);
                }
                cancel();
            }
            time++;
        }
        counter--;
    }
}