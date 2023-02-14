package fr.zadiho.hepickstudio.olympiade.commands;

import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HostCommand implements CommandExecutor {

    /*
     * Class Name : HostCommand
     * Description   : Add host command
     * Version       : 1.3
     * Date          : 13/02/2023
     * Copyright     : HepickStudio
     */

    //////////////////////////////////////////////HOST COMMAND EXECUTOR//////////////////////////////////////////////
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        GameSettings.getHostPlayers().add(Bukkit.getPlayer(args[0]));
        System.out.println(GameSettings.getHostPlayers());
        return true;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
