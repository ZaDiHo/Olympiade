package fr.zadiho.hepickstudio.olympiade.game;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.tasks.WinFireworks;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class Game {

    /*
     * Class Name : Game
     * Description   : Game class
     * Version       : 1.3
     * Date          : 13/02/2023
     * Copyright     : HepickStudio
     */

    ////////////////////////////////////////POINTS/////////////////////////////////////////
    public static void addPoints(Player player, int points) {
        int currentPoints = GameSettings.getPodium().get(player);
        GameSettings.getPodium().put(player, currentPoints + points);
    }

    public static void removePoints(Player player, int points) {
        int currentPoints = GameSettings.getPodium().get(player);
        GameSettings.getPodium().put(player, currentPoints - points);
    }

    public static void setPoints(Player player, int points) {
        GameSettings.getPodium().put(player, points);
    }

    public static int getPoints(Player player) {
        return GameSettings.getPodium().get(player);
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////PODIUM/////////////////////////////////////////
    public static void teleportPodium(List<Player> podium) {
        for(Player player : Bukkit.getOnlinePlayers()){
            player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -477.5, 64, -1242.5, -90, 0));
        }
        for (Player player : podium) {
            if (podium.size() >= 1){
                podium.get(0).teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -466.5, 69, -1242.5, 90, 0));
                WinFireworks.setWinner(podium.get(0));
                WinFireworks winFireworks = new WinFireworks();
                winFireworks.runTaskTimer(Olympiade.getInstance(), 0, 20);
            }
            if (podium.size() >= 2){
                podium.get(1).teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -467.5, 67, -1245.5, 90, 0));
            }
            if (podium.size() >= 3){
                podium.get(2).teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -468.5, 66, -1239.5, 90, 0));
            }
        }

    }

    public static void reversedTeleportPodium(List<Player> podium) {
        Collections.reverse(podium);
        System.out.println(podium);
        for(Player player : Bukkit.getOnlinePlayers()){
            player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -477.5, 64, -1242.5, -90, 0));
        }
        for (int i = 0; i < podium.size(); i++) {
            Player player = podium.get(i);
            if (podium.size() >= 1 && i == 0){
                player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -466.5, 69, -1242.5, 90, 0));
                WinFireworks.setWinner(player);
                WinFireworks winFireworks = new WinFireworks();
                winFireworks.runTaskTimer(Olympiade.getInstance(), 0, 20);
            }
            if (podium.size() >= 2 && i == 1){
                player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -467.5, 67, -1245.5, 90, 0));
            }
            if (podium.size() >= 3 && i == 2){
                player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -468.5, 66, -1239.5, 90, 0));
            }
        }
    }

    public static void reversedGivePoints(List<Player> podium) {
        for (int i = 0; i < podium.size(); i++) {
            if (i == 0) {
                addPoints(podium.get(i), 10);
            } else if (i == 1) {
                addPoints(podium.get(i), 8);
            } else if (i == 2) {
                addPoints(podium.get(i), 6);
            } else if (i >= 3 && i < 10) {
                addPoints(podium.get(i), 5);
            } else {
                addPoints(podium.get(i), 2);
            }
        }
    }

    public static void givePoints(List<Player> podium){
        for(int i = 0; i<podium.size(); i++){
            if(i == 0){
                addPoints(podium.get(i), 10);
            }else if(i == 1){
                addPoints(podium.get(i), 8);
            }else if(i == 2){
                addPoints(podium.get(i), 6);
            }else if(i >= 3 && i < 10){
                addPoints(podium.get(i), 5);
            }else {
                addPoints(podium.get(i), 2);
            }
        }
    }

    /////////////////////////////////////////////////////////////PREFIX//////////////////////////////////////////////////////////
    public static String getPlayerPrefix(Player player) {
        LuckPerms luckPerms = Olympiade.getInstance().getLuckPerms();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
        return user.getCachedData().getMetaData().getPrefix() + "§" + user.getCachedData().getMetaData().getPrefix().charAt(1) + " ";
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
