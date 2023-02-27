package fr.zadiho.hepickstudio.olympiade.manager;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.commands.HostCommand;
import fr.zadiho.hepickstudio.olympiade.commands.PointsCommand;
import fr.zadiho.hepickstudio.olympiade.commands.SpawnCommand;
import fr.zadiho.hepickstudio.olympiade.listeners.GeneralEvents;
import fr.zadiho.hepickstudio.olympiade.listeners.PlayerJoin;
import fr.zadiho.hepickstudio.olympiade.listeners.PlayerQuit;
import fr.zadiho.hepickstudio.olympiade.tasks.*;
import fr.zadiho.hepickstudio.olympiade.utils.Creatures;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistrationManager {

    Olympiade main = Olympiade.getInstance();

    private List<Listener> listeners = new ArrayList<>();

    public void registration(){

        Objects.requireNonNull(main.getCommand("host")).setExecutor(new HostCommand());
        Objects.requireNonNull(main.getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(main.getCommand("points")).setExecutor(new PointsCommand());

        this.listeners.add(new PlayerJoin());
        this.listeners.add(new PlayerQuit());
        this.listeners.add(new GeneralEvents());
        this.listeners.add(new TNTTask());
        this.listeners.add(new PVPTask());
        this.listeners.add(new SurpriseTask());
        this.listeners.add(new PVETask());
        this.listeners.add(new RaceTask());
        this.listeners.add(new JumpTask());
        this.listeners.add(new OlympiadeTask());
        this.listeners.add(new Creatures());
        this.listeners.forEach((listener -> {
            Bukkit.getPluginManager().registerEvents(listener, main);
        }));
    }
}
