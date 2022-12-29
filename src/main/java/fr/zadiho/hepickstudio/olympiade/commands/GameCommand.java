package fr.zadiho.hepickstudio.olympiade.commands;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.guis.AdminGUI;
import fr.zadiho.hepickstudio.olympiade.tasks.JumpTask;
import fr.zadiho.hepickstudio.olympiade.tasks.RaceTask;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            if(player.hasPermission("olympiade.orga")){
                if(EGames.getCurrentState() != EGames.WAITING){
                    player.sendMessage(GameSettings.prefix + ChatColor.translateAlternateColorCodes('&', "§cUne épreuve est déjà commencée !"));
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "§cErreur !"), ChatColor.translateAlternateColorCodes('&', "§eUne partie est déjà commencée..."), 10, 40, 10);
                    player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
                    return true;
                }
                if(args.length > 1){
                    player.sendMessage(GameSettings.prefix
                    + "§cVeuillez spécifier le type d'épreuve !"
                    + "§8§l §6Jump"
                    + "§8§l §6Course"
                    + "§8§l §6FFA");
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "§cErreur !"), ChatColor.translateAlternateColorCodes('&', "§eVeuillez préciser le type d'épreuve !"), 10, 40, 10);
                    player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
                    return true;
                }
                if(args[0].equalsIgnoreCase("course")){
                    EGames.setState(EGames.RACE);
                    RaceTask raceTask = new RaceTask();
                    raceTask.runTaskTimer(Olympiade.getInstance(), 0, 20);
                }
                if(args[0].equalsIgnoreCase("jump")){
                    EGames.setState(EGames.PARKOUR);
                    JumpTask jumpTask = new JumpTask();
                    jumpTask.runTaskTimer(Olympiade.getInstance(), 0, 20);
                }
            }else{
                player.sendMessage(GameSettings.prefix + ChatColor.translateAlternateColorCodes('&', "§cVous n'avez pas la permission d'utiliser cette commande !"));
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', "§cErreur !"), ChatColor.translateAlternateColorCodes('&', "§ePermission requise..."), 10, 40, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
                return true;
            }

        }
        return false;
    }
}
