
package fr.zadiho.hepickstudio.olympiade.commands;

import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            player.teleport(GameSettings.spawn);
        }
        return false;
    }
}