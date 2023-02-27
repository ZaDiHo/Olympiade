package fr.zadiho.hepickstudio.olympiade.commands;

import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PointsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("olympiade.staff")) {
                if (args.length > 2 && args[0].equalsIgnoreCase("add")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null && GameSettings.getPodium().containsKey(target)) {
                        Game.addPoints(target, Integer.parseInt(args[2]));
                        return true;
                    } else {
                        player.sendMessage("Le joueur spécifié n'est pas en ligne ou n'est pas sur le podium.");
                        return false;
                    }
                } else if (args.length > 2 && args[0].equalsIgnoreCase("remove")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null && GameSettings.getPodium().containsKey(target)) {
                        Game.removePoints(target, Integer.parseInt(args[2]));
                        return true;
                    } else {
                        player.sendMessage("Le joueur spécifié n'est pas en ligne ou n'est pas sur le podium.");
                        return false;
                    }
                } else if (args.length > 2 && args[0].equalsIgnoreCase("set")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null && GameSettings.getPodium().containsKey(target)) {
                        Game.setPoints(target, Integer.parseInt(args[2]));
                        return true;
                    } else {
                        player.sendMessage("Le joueur spécifié n'est pas en ligne ou n'est pas sur le podium.");
                        return false;
                    }
                } else {
                    player.sendMessage("Utilisation : /points <add|remove|set> <joueur> <points>");
                    return false;
                }
            } else {
                player.sendMessage("Vous n'avez pas la permission d'exécuter cette commande.");
                return false;
            }
        } else {
            Bukkit.getConsoleSender().sendMessage("Vous devez être un joueur pour exécuter cette commande.");
            return false;
        }
    }
}
