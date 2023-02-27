package fr.zadiho.hepickstudio.olympiade.manager;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerManager {

    public static void registerPlayer(Player player){
        GameSettings.savePodium();
        if(!GameSettings.getGamePlayers().contains(player)){
            GameSettings.getGamePlayers().add(player);
        }
        if(!GameSettings.getPodium().containsKey(player)){
            GameSettings.getPodium().put(player, Olympiade.getInstance().getConfig().getInt("podium." + player.getName()));
            GameSettings.savePodium();
        }
        GameSettings.loadPodium();
    }

    public static void hostItem(Player player){
        if(GameSettings.getHostPlayers().contains(player)){
            player.getInventory().setItem(0, new ItemBuilder(Material.COMMAND_BLOCK_MINECART).setName("§8» §eHost").toItemStack());
        }
    }

    public static void unregisterPlayer(Player player){
        GameSettings.getGamePlayers().remove(player);
        GameSettings.savePodium();
        GameSettings.loadPodium();
    }
}