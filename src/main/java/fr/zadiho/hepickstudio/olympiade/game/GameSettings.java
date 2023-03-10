package fr.zadiho.hepickstudio.olympiade.game;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public class GameSettings {

    /*
     * Class Name : GameSettings
     * Description   : GameSettings class
     * Version       : 1.3
     * Date          : 13/02/2023
     * Copyright     : HepickStudio
     */

    ///////////////////////////////////////////////////////////////FINAL VARIABLES///////////////////////////////////////////////////////////////
    public static final String prefix = ChatColor.translateAlternateColorCodes('&', "§cOlympiade III §8» ");
    public static final Location spawn = new Location(Bukkit.getWorld("OlympiadeS3"), -522.5, 71, -1242.5, -90, 0);
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////PLAYERS LISTS////////////////////////////////////////////////////////////////
    private static ArrayList<Player> gamePlayers = new ArrayList<>();
    private static ArrayList<Player> hostPlayers = new ArrayList<>();

    //------------------
    public static ArrayList<Player> getGamePlayers() {
        return gamePlayers;
    }

    public static ArrayList<Player> getHostPlayers() {
        return hostPlayers;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////GAMES PODIUMS///////////////////////////////////////////////////////////////
    private static HashMap<Player, Integer> podium = new HashMap<>();
    private static List<Player> racePodium = new ArrayList<>();
    private static List<Player> pvpPodium = new ArrayList<>();
    private static List<Player> tntPodium = new ArrayList<>();
    private static List<Player> jumpPodium = new ArrayList<>();
    private static HashMap<Player, Integer> pvePodium = new HashMap<>();

    public static List<Player> pvePodiumList(){
        List<Map.Entry<Player, Integer>> list = new LinkedList<>(getPvePodium().entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Player, Integer>>() {
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        List<Player> sortedPlayers = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : list) {
            sortedPlayers.add(entry.getKey());
        }
        return sortedPlayers;
    }

    //------------------
    public static void loadPodium() {
        ConfigurationSection podiumSection = Olympiade.getInstance().getConfig().getConfigurationSection("podium");
        if (podiumSection != null) {
            for (String playerName : podiumSection.getKeys(false)) {
                Player player = Bukkit.getPlayerExact(playerName);
                if (player != null) {
                    podium.put(player, podiumSection.getInt(playerName));
                }
            }
        }
    }

    public static void savePodium() {
        ConfigurationSection podiumSection = Olympiade.getInstance().getConfig().createSection("podium");
        for (Map.Entry<Player, Integer> entry : podium.entrySet()) {
            Player player = entry.getKey();
            Integer points = entry.getValue();
            podiumSection.set(player.getName(), points);
        }
        Olympiade.getInstance().saveConfig();
    }

    public static List<Player> getRacePodium() {
        return racePodium;
    }
    public static List<Player> getTntPodium() {
        return tntPodium;
    }

    public static List<Player> getPvpPodium() {
        return pvpPodium;
    }

    public static List<Player> getJumpPodium() {
        return jumpPodium;
    }

    public static HashMap<Player, Integer> getPvePodium() {
        return pvePodium;
    }

    public static HashMap<Player, Integer> getPodium() {
        return podium;
    }

    public static int getPlace(Player player) {
        List<Map.Entry<Player, Integer>> entries = new ArrayList<>(podium.entrySet());
        Collections.sort(entries, (a, b) -> a.getValue().compareTo(b.getValue()));
        int place = 0;
        for (Map.Entry<Player, Integer> entry : entries) {
            if (entry.getKey().equals(player)) {
                return place + 1;
            }
            place++;
        }
        return -1;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}