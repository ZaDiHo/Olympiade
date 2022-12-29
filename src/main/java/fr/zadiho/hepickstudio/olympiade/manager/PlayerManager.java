package fr.zadiho.hepickstudio.olympiade.manager;

import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerManager {

    public static void registerPlayer(Player player){
        if(!GameSettings.getGamePlayers().contains(player)){
            GameSettings.getGamePlayers().add(player);
        }
        if(!GameSettings.getPodium().containsKey(player)){
            GameSettings.getPodium().put(player, 0);
        }
    }

    public static void hostItem(Player player){
        if(GameSettings.getHostPlayers().contains(player)){
            player.getInventory().setItem(0, new ItemBuilder(Material.COMMAND_BLOCK_MINECART).setName("§8» §eHost").toItemStack());
        }
    }

    public static void unregisterPlayer(Player player){
        GameSettings.getGamePlayers().remove(player);
        GameSettings.getPodium().remove(player);
    }
}
