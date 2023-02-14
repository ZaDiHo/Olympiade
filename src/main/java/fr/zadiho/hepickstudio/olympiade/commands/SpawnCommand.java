
package fr.zadiho.hepickstudio.olympiade.commands;

import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    /*
     * Class Name : SpawnCommand
     * Description   : Back to spawn
     * Version       : 1.3
     * Date          : 13/02/2023
     * Copyright     : HepickStudio
     */

    //////////////////////////////////////////////SPAWN COMMAND EXECUTOR//////////////////////////////////////////////
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (EGames.getCurrentState() == EGames.WAITING) {
                player.teleport(GameSettings.spawn);
            } else {
                player.sendMessage("§cVous ne pouvez pas vous téléporter au spawn en cours de partie !");
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', "§cErreur !"), ChatColor.translateAlternateColorCodes('&', "§eUne épreuve est en cours !"), 10, 40, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
            }
        }
        return false;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}