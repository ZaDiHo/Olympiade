package fr.zadiho.hepickstudio.olympiade;

import fr.minuskube.inv.InventoryManager;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.manager.RegistrationManager;
import fr.zadiho.hepickstudio.olympiade.tasks.OlympiadeTask;
import fr.zadiho.hepickstudio.olympiade.tasks.TNTTask;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import fr.zadiho.hepickstudio.olympiade.utils.Holograms;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Olympiade extends JavaPlugin {

    private static Olympiade instance;
    private static InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Starting Olympiade par ZaDiHo#2199");
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("Looking for games...");
        for(EGames games : EGames.values()){
            Bukkit.getLogger().info(games.getName() + " has been loaded !");
        }
        Bukkit.getLogger().info("");
        Bukkit.getLogger().info("===========================");
        Bukkit.getLogger().info("Trying to hook with Luckperms...");
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            Bukkit.getLogger().info("Luckperms has been hooked !");
            Bukkit.getLogger().info(" ");
            Bukkit.getLogger().info("All is good, have a good day !");
            Bukkit.getLogger().info("===========================");

        } else {
            Bukkit.getLogger().info("Luckperms not found ! Disabling plugin...");
            Bukkit.getLogger().info("===========================");
            Bukkit.getPluginManager().disablePlugin(this);

        }

        TNTTask.fillFloors();
        Holograms.clearHolograms();
        Holograms.setupHolograms();
        Cuboid.fillStartJump();
        Cuboid.fillStartRace();



        EGames.setState(EGames.WAITING);
        RegistrationManager registrationManager = new RegistrationManager();
        registrationManager.registration();

        OlympiadeTask olympiadeTask = new OlympiadeTask();
        olympiadeTask.runTaskTimer(this, 0, 20);

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        super.onEnable();
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static Olympiade getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        Holograms.clearHolograms();
        super.onDisable();
    }

    public LuckPerms getLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        return provider.getProvider();
    }
}
