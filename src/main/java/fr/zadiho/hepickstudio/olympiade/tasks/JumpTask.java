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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JumpTask extends BukkitRunnable implements Listener {

    private static List<Player> inJump = new ArrayList<>();
    private static int counter = 20;
    public static boolean played = false;
    public static int time = 0;
    private static int totalPlayers = 0;
    private static Cuboid endJump = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1003.5, 73.5, -1.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -997.5, 67, 10.5));
    private static Cuboid checkpoint2 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1104.5, 67, 47.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1095.5, 68, 8.5));
    private static Cuboid checkpoint3 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1095.5, 68, 8.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1091.5, 73, -1.5));
    private static Cuboid checkpoint4 = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1056.5, 71, 8.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1050.5, 63, -2.5));
    private static HashMap<Player, Integer> checkPoints = new HashMap<>();
    private static HashMap<Player, Boolean> hidePlayer = new HashMap<>();

    public static void resetRace(){
        setPlayed(false);
        totalPlayers = 0;
        counter = 10;
        time = 0;
        GameSettings.getJumpPodium().clear();
        getInJump().clear();
        checkPoints.clear();
        hidePlayer.clear();
    }

    private static void resetCheckpoint(Player player) {
        if(checkPoints.get(player) == 1){
            player.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1147.5, 67, 41.5, -90, 0));
        }
        if(checkPoints.get(player) == 2){
            player.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1102.5, 67, 35.5, 180, 0));
        }
        if(checkPoints.get(player) == 3){
            player.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1092.5, 68, 4.5, -90, 0));
        }
        if(checkPoints.get(player) == 4) {
            player.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1056.5, 67, 5.5, -90, 0));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() == null){
            return;
        }
        if(player.getItemInHand() == null){
            return;
        }
        if(player.getItemInHand().getItemMeta().getDisplayName().equals("§6Retour au checkpoint")){
            resetCheckpoint(player);
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1, 1);
        }
        if(player.getItemInHand().getItemMeta().getDisplayName().equals("§6Masquer les joueurs")){
            if(hidePlayer.get(player)){
                hidePlayer.remove(player);
                hidePlayer.put(player, false);
                player.sendMessage("§cVous avez affiché les joueurs.");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                player.getInventory().setItem(8, new ItemBuilder(Material.LIGHT_BLUE_DYE).setName("§6Masquer les joueurs").toItemStack());
                for(Player players : GameSettings.getGamePlayers()){
                    player.showPlayer(Olympiade.getInstance(), players);
                }
            }else{
                hidePlayer.remove(player);
                hidePlayer.put(player, true);
                player.sendMessage("§aVous avez masqué les joueurs.");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                player.getInventory().setItem(8, new ItemBuilder(Material.RED_DYE).setName("§6Masquer les joueurs").toItemStack());
                for(Player players : GameSettings.getGamePlayers()){
                    player.hidePlayer(Olympiade.getInstance(), players);
                }
            }

        }
    }

    public static boolean isPlayed() {
        return played;
    }

    public static void setPlayed(boolean played) {
        JumpTask.played = played;
    }




    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(EGames.getCurrentState().equals(EGames.PARKOUR)){
            event.setCancelled(true);
        }
    }

    @Override
    public void run() {
        if (counter == 20) {
            Cuboid.fillStartJump();
            for (Player players : Bukkit.getOnlinePlayers()) {

                hidePlayer.put(players, false);
                getInJump().add(players);
                checkPoints.put(players, 1);
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1147.5, 67, 41.5, -90, 0));

                players.getInventory().setItem(7, new ItemBuilder(Material.DARK_OAK_DOOR).setName("§6Retour au checkpoint").toItemStack());
                players.getInventory().setItem(8, new ItemBuilder(Material.LIGHT_BLUE_DYE).setName("§6Masquer les joueurs").toItemStack());
                players.stopAllSounds();
                players.playSound(players.getLocation(), Sound.MUSIC_DISC_13, 1, 1);
            }
        }
        if(counter == 10){
            for(Player players : Bukkit.getOnlinePlayers()){
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
                Cuboid.clearStartJump();
            }

        }
        if (counter < 0) {
            for (Player players : GameSettings.getGamePlayers()) {
                if (players.isVisualFire()) {
                    players.setVisualFire(false);
                }
                players.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6Chronomètre: §e" + Chrono.format(time)));

                if(checkpoint2.isIn(players) && checkPoints.get(players) == 1){
                    checkPoints.remove(players);
                    checkPoints.put(players, 2);
                    players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    players.sendMessage("§aCheckpoint passé !");
                }
                if(checkpoint3.isIn(players) && checkPoints.get(players) == 2){
                    checkPoints.remove(players);
                    checkPoints.put(players, 3);
                    players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    players.sendMessage("§aCheckpoint passé !");
                }
                if(checkpoint4.isIn(players) && checkPoints.get(players) == 3){
                    checkPoints.remove(players);
                    checkPoints.put(players, 4);
                    players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    players.sendMessage("§aCheckpoint passé !");
                }
                if (endJump.isIn(players)) {
                    if (getInJump().contains(players)) {
                        GameSettings.getJumpPodium().add(players);
                        Bukkit.broadcastMessage("§a" + players.getName() + " §6a terminé le jump à la position §e" + (totalPlayers - inJump.size()) + " §6! Son chronomètre affichait §e" + Chrono.format(time));
                        getInJump().remove(players);
                        players.setGameMode(GameMode.SPECTATOR);
                    }
                }
            }
            if(GameSettings.getGamePlayers().size() == GameSettings.getJumpPodium().size()){
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTout le monde à terminé !", "§6Le parcours est terminé !", 10, 20, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    EGames.setState(EGames.WAITING);
                    players.setGameMode(GameMode.ADVENTURE);
                    setPlayed(true);
                    Cuboid.fillStartJump();
                }
                Game.teleportPodium(GameSettings.getJumpPodium());
                Game.givePoints(GameSettings.getJumpPodium());
                cancel();
            }
            if (time / 60 >= EGames.PARKOUR.getDuration()) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTemps écoulé !", "§6Le parcours est terminé !", 10, 20, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    EGames.setState(EGames.WAITING);
                    players.setGameMode(GameMode.ADVENTURE);
                    setPlayed(true);
                    Cuboid.fillStartJump();
                }
                Game.teleportPodium(GameSettings.getJumpPodium());
                Game.givePoints(GameSettings.getJumpPodium());
                cancel();
            }
            time++;
        }
        counter--;
    }

    public static List<Player> getInJump() {
        return inJump;
    }
}