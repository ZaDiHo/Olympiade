package fr.zadiho.hepickstudio.olympiade.game;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameSettings {

    public static String prefix = ChatColor.translateAlternateColorCodes('&', "§cOlympiade III §8» ");
    private static int parkour = 20;
    private static int race = 5;
    private static int tnt = 10;
    private static int pvp = 15;
    public static Location spawnNether = new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1336.5, 75, 24.5, -90, 0);
    public static Location podium1 = new Location(Bukkit.getWorld("OlympiadeS3"), -466.5, 69, -1242.5, 90, 0);
    public static Location spawnPodium = new Location(Bukkit.getWorld("OlympiadeS3"), -476.5, 64, -1242.5, -90, 0);
    private static HashMap<Player, Integer> pvpLives = new HashMap<>();
    private static HashMap<Player, Integer> podium = new HashMap<>();
    private static ArrayList<Player> gamePlayers = new ArrayList<>();
    private static ArrayList<Player> hostPlayers = new ArrayList<>();
    private static HashMap<Player, Boolean> inRace = new HashMap<>();
    private static HashMap<Player, Boolean> inJump = new HashMap<>();
    private static HashMap<Player, Boolean> inTNT = new HashMap<>();
    private static HashMap<Player, Integer> racePodium = new HashMap<>();
    private static HashMap<Player, Integer> tntPodium = new HashMap<>();
    private static HashMap<Player, Integer> jumpPodium = new HashMap<>();
    private static Iterator gamePlayersIterator;

    public static HashMap<Player, Boolean> getInTNT() {
        return inTNT;
    }

    public static Cuboid endRace = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -754.5, 40, 187.5), new Location(Bukkit.getWorld("OlympiadeS3_nether"), -762.5, 55.5, 194.5));
    public static final Location spawn = new Location(Bukkit.getWorld("OlympiadeS3"), -522.5, 71, -1242.5, -90, 0);

    public static HashMap<Player, Integer> getRacePodium() {
        return racePodium;
    }

    public static HashMap<Player, Integer> getTntPodium() {
        return tntPodium;
    }

    public static HashMap<Player, Integer> getJumpPodium() {
        return jumpPodium;
    }

    public static HashMap<Player, Boolean> getInRace() {
        return inRace;
    }

    public static HashMap<Player, Boolean> getInJump() {
        return inRace;
    }


    public static ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public static ArrayList<Player> getHostPlayers() {
        return hostPlayers;
    }

    public static int getParkourDuration() {
        return parkour;
    }
    public static int getTntDuration() {
        return tnt;
    }


    public static int setParkourDuration(int duration) {
        return parkour = duration;
    }

    public static int getRaceDuration() {
        return race;
    }


    public static int setRaceDuration(int duration) {
        return race = duration;
    }

    public static int getPVPDuration() {
        return pvp;
    }

    public static int setPVPDuration(int duration) {
        return pvp = duration;
    }

    public static String getPrefix(Player player) {
        LuckPerms luckPerms = Olympiade.getInstance().getLuckPerms();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
        return user.getCachedData().getMetaData().getPrefix() + "§" + user.getCachedData().getMetaData().getPrefix().charAt(1) + " ";
    }

    public static HashMap<Player, Integer> getPodium() {
        return podium;
    }

    public static HashMap<Player, Integer> getPvpLives() {
        return pvpLives;
    }

    public static void teleportPodium(EGames game) {
        if (game == EGames.RACE) {

            if(game == EGames.PARKOUR){
                gamePlayersIterator = getJumpPodium().entrySet().iterator();
            }
            if(game == EGames.RACE){
                gamePlayersIterator = getRacePodium().entrySet().iterator();
            }


            while (gamePlayersIterator.hasNext()) {
                Map.Entry<Player, Integer> entry = (Map.Entry) gamePlayersIterator.next();
                ((Player) entry.getKey()).teleport(spawnPodium);
                if ((Integer) entry.getValue() == 1) {
                    ((Player) entry.getKey()).teleport(podium1);
                }

                if ((Integer) entry.getValue() == 2) {
                    ((Player) entry.getKey()).teleport(podium1);
                }

                if ((Integer) entry.getValue() == 3) {
                    ((Player) entry.getKey()).teleport(podium1);
                }
            }
        }
    }
}