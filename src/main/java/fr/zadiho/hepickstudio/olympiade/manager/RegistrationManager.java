package fr.zadiho.hepickstudio.olympiade.manager;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.commands.GameCommand;
import fr.zadiho.hepickstudio.olympiade.commands.HostCommand;
import fr.zadiho.hepickstudio.olympiade.listeners.PlayerJoin;
import fr.zadiho.hepickstudio.olympiade.listeners.PlayerQuit;
import fr.zadiho.hepickstudio.olympiade.tasks.JumpTask;
import fr.zadiho.hepickstudio.olympiade.tasks.OlympiadeTask;
import fr.zadiho.hepickstudio.olympiade.tasks.RaceTask;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistrationManager {

    Olympiade main = Olympiade.getInstance();

    private final List<Listener> listeners = new ArrayList<>();

    public void registration(){

        Objects.requireNonNull(main.getCommand("game")).setExecutor(new GameCommand());
        Objects.requireNonNull(main.getCommand("host")).setExecutor(new HostCommand());

        this.listeners.add(new PlayerJoin());
        this.listeners.add(new PlayerQuit());
        this.listeners.add(new RaceTask());
        this.listeners.add(new JumpTask());
        this.listeners.add(new OlympiadeTask());
        this.listeners.forEach((listener -> {
            Bukkit.getPluginManager().registerEvents(listener, main);
        }));
    }
}
