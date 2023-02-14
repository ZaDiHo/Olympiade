package fr.zadiho.hepickstudio.olympiade.tasks;

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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SurpriseTask extends BukkitRunnable implements Listener {

    private List<Player> inSurprise = new ArrayList<>();
    private static boolean played = false;
    private static int counter = 10;

    public static void setPlayed(boolean played) {
        SurpriseTask.played = played;
    }

    public List<Player> getInSurprise() {
        return inSurprise;
    }


    private static void teleportPlayersCircle(List<Player> players) {
        Random random = new Random();
        double centerX = -485.5;
        double centerY = 66;
        double centerZ = -1242.5;
        for (Player player : players) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double x = 50 * Math.cos(angle) + centerX;
            double z = 50 * Math.sin(angle) + centerZ;
            Location location = new Location(Bukkit.getWorlds().get(0), x, centerY, z);
            player.teleport(location);
            if (player.getName().equalsIgnoreCase("ZaDiHo")) {
                player.teleport(new Location(Bukkit.getWorlds().get(0), -485.5, 66, -1242.5, 90, 0));
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if(EGames.getCurrentState().equals(EGames.SURPRISE)){
            if (event.getEntity() instanceof Player) {
                if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    event.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (getInSurprise().contains(player)) {
                getInSurprise().remove(player);
                event.getDrops().clear();
                event.setDroppedExp(0);
                Bukkit.broadcastMessage("§c" + event.getEntity().getName() + " §6est mort ! Il reste §c" + (getInSurprise().size() -1) + " §6joueurs en vie !");
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    @Override
    public void run() {
        if (counter == 10) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.stopAllSounds();
                getInSurprise().add(players);
                teleportPlayersCircle(getInSurprise());
                players.sendTitle("§cAttention !", "§6Epreuve suprise !", 10, 20, 10);
                //todo stuff
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
                players.playSound(players.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                Cuboid.clearStartRace();
            }

        }
        if (counter < 0) {
            if(!getInSurprise().contains(Bukkit.getPlayer("ZaDiHo"))){
                Bukkit.broadcastMessage("&6Epreuve terminée ! §cKayato §6est mort ! Il reste §c" + (getInSurprise().size()) + " §6joueurs en vie !");
            }
            if(getInSurprise().size() == 1 && getInSurprise().contains(Bukkit.getPlayer("ZaDiHo"))){
                Bukkit.broadcastMessage("&6Epreuve terminée ! §cKayato §6est le dernier survivant ! Il est le grand gagnant !");
            }
        }
        counter--;
    }

}
