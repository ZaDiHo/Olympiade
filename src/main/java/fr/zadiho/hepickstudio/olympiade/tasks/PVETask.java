package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PVETask extends BukkitRunnable implements Listener {

    private static int counter = 10;
    public static boolean played = false;
    private static Cuboid arene = new Cuboid(new Location(Bukkit.getWorld("OlympiadeS3"), -971.5, 55, -935), new Location(Bukkit.getWorld("OlympiadeS3"), -1183.5, -6, -731.5));
    public static int time = 0;
    private static List<Player> inPVE = new ArrayList<>();
    private static List<Player> inRound = new ArrayList<>();
    public static int round = 0;

    public static ArrayList<Player> alives = new ArrayList<>();
    public static int entities = 0;

    public static int getRound() {
        return round;
    }

    public static void resetPVP() {
        setPlayed(false);
        counter = 10;
        alives.clear();
        time = 0;
        GameSettings.getPvePodium().clear();
        getInPVE().clear();
    }

    public static boolean isAirLocation(Location location) {
        World world = location.getWorld();
        if (world == null) {
            return false;
        }

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    Block block = world.getBlockAt(x + dx, y + dy, z + dz);
                    if (block.getType() != Material.AIR) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static Location randomLoc() {
        return arene.getRandomLocation();
    }

    @EventHandler
    public void onEat(EntityChangeBlockEvent event){
        if(EGames.getCurrentState().equals(EGames.PVE)){
            if (event.getEntity().getType() == EntityType.WITHER_SKULL){
                event.setCancelled(true);
            }
            if (event.getEntity().getType() == EntityType.WITHER){
                event.setCancelled(true);
            }
        }
    }

    public static void startRound() {
        if (round == 1) {
            entities = 500;

            for (int i = 125; i > 0; i--) {
                Location loc = randomLoc();
                while (!isAirLocation(loc)) {
                    loc = randomLoc();
                }
                Frog frog = (Frog) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.FROG);
                Skeleton skeleton = (Skeleton) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.SKELETON);
                Spider spider = (Spider) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.SPIDER);
                Zombie zombie = (Zombie) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.ZOMBIE);
                zombie.setGlowing(true);
                zombie.setCustomName("§cZombie");
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
                zombie.setCustomNameVisible(true);
                zombie.setAdult();

                skeleton.setGlowing(true);
                skeleton.setCustomName("§cSquelette");
                skeleton.setCustomNameVisible(true);

                spider.setGlowing(true);
                spider.setCustomName("§cAraignée");
                spider.setCustomNameVisible(true);

                frog.setGlowing(true);
                frog.setCustomName("§cGrenouille");
                frog.setCustomNameVisible(true);

            }
        }

        if (round == 2) {
            entities = 300;
            Location loca = randomLoc();
            while (!isAirLocation(loca)) {
                loca = randomLoc();
            }
            for (int i = 60; i > 0; i--) {
                Location loc = randomLoc();
                while (!isAirLocation(loc)) {
                    loc = randomLoc();
                }
                Pillager pillager = (Pillager) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.PILLAGER);
                Vindicator vindicator = (Vindicator) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.VINDICATOR);
                Vex vex = (Vex) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.VEX);
                Allay allay = (Allay) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.ALLAY);


                pillager.setGlowing(true);
                pillager.setCustomName("§cPillager");
                pillager.setCustomNameVisible(true);

                vindicator.setGlowing(true);
                vindicator.setCustomName("§cVindicateur");
                vindicator.setCustomNameVisible(true);

                vex.setGlowing(true);
                vex.setCustomName("§cVex");
                vex.setCustomNameVisible(true);

                allay.setGlowing(true);
                allay.setCustomName("§cAllay");
                allay.setCustomNameVisible(true);
            }
            Wither wither = (Wither) Bukkit.getWorld("OlympiadeS3").spawnEntity(loca, EntityType.WITHER);
            wither.setGlowing(true);
            wither.setCustomName("§cWither");
            wither.setCustomNameVisible(true);
        }
    }

    public static boolean isPlayed() {
        return played;
    }

    public static void setPlayed(boolean played) {
        TNTTask.played = played;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (EGames.getCurrentState().equals(EGames.PVE)) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                event.setCancelled(true);
            }
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE)) {
                event.setCancelled(true);
            }
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
                event.setCancelled(true);
            }
            if (counter > -10) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (EGames.getCurrentState().equals(EGames.PVE)) {
            if(getRound() == 1){
                if(event.getEntity().getType() == EntityType.SKELETON){
                    entities -= 1;
                }
                if(event.getEntity().getType() == EntityType.ZOMBIE){
                    entities -= 1;
                }
                if(event.getEntity().getType() == EntityType.SPIDER){
                    entities -= 1;
                }
                if(event.getEntity().getType() == EntityType.FROG){
                    entities -= 1;
                }
            }
            if(getRound() == 2){
                if(event.getEntity().getType() == EntityType.PILLAGER){
                    entities -= 1;
                }
                if(event.getEntity().getType() == EntityType.VINDICATOR){
                    entities -= 1;
                }
                if(event.getEntity().getType() == EntityType.VEX){
                    entities -= 1;
                }
                if(event.getEntity().getType() == EntityType.ALLAY){
                    entities -= 1;
                }
                if(event.getEntity().getType() == EntityType.WITHER){
                    entities -= 1;
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (EGames.getCurrentState().equals(EGames.PVE)) {
            event.setDeathMessage(null);
        }
    }

    public static void equipPlayer(Player player) {
        ItemStack[] armorContents = new ItemStack[4];
        armorContents[3] = new ItemStack(Material.IRON_HELMET);
        armorContents[2] = new ItemStack(Material.IRON_CHESTPLATE);
        armorContents[1] = new ItemStack(Material.IRON_LEGGINGS);
        armorContents[0] = new ItemStack(Material.IRON_BOOTS);

        player.getInventory().setArmorContents(armorContents);
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.getInventory().addItem(new ItemStack(Material.SHIELD));
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 32));
        player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
    }

    @Override
    public void run() {
        if (counter == 10) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.stopAllSounds();
                Bukkit.getWorld("OlympiadeS3").setPVP(false);
                players.playSound(players.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 1);
                players.setGameMode(GameMode.SURVIVAL);
                alives.add(players);
                getInPVE().add(players);
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -1092.5, 86, -724.5, -180, 0));
                players.sendTitle("§cAttention !", "§6Placez vous devant la ligne de départ !", 10, 20, 10);
            }
        }
        if (counter == 5) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§95", "§bTenez vous prêt !", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.E));
            }
        }
        if (counter == 4) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§24", "§aPas de faux départs !", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.D));
            }
        }
        if (counter == 3) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§e3", "§6A vos marques", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.C));
            }
        }
        if (counter == 2) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§62", "§ePrêt ?", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.B));
            }
        }
        if (counter == 1) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§c1", "§4C'est partie !", 10, 20, 10);
                players.playNote(players.getLocation(), Instrument.BASS_GUITAR, Note.natural(1, Note.Tone.A));
            }
        }
        if (counter == 0) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                round = 1;
                players.playSound(players.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -1080.5, 50, -844.5, -180, 0));
                equipPlayer(players);
                startRound();
            }
        }
        if (counter == -10) {
            Bukkit.broadcastMessage("§6Invincibilité désactivée !");
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
            }
        }
        if (counter < 0) {
            for (Player players : GameSettings.getGamePlayers()) {
                if (players.isVisualFire()) {
                    players.setVisualFire(false);
                }
            }
            if(entities <= 0){
                if(getRound() == 1){
                    Bukkit.broadcastMessage("Fin de la vague 1, préparez vous à la vague 2");
                    round = 2;
                    entities = 301;
                }
            }

            if (alives.size() == 0) {
                for (Player players : GameSettings.getGamePlayers()) {
                    getInPVE().remove(alives.get(0));
                    players.sendTitle("§cPVP terminé !", "§6Victoire de §e" + alives.get(0).getDisplayName(), 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                    EGames.setState(EGames.WAITING);
                    setPlayed(true);
                }
                cancel();
            }
            if (time / 60 >= EGames.PVE.getDuration()) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTemps écoulé !", "§6Le parcours est terminé !", 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                    EGames.setState(EGames.WAITING);
                    setPlayed(true);
                }
                cancel();
            }
            time++;
        }
        counter--;
    }

    public static List<Player> getInPVE() {
        return inPVE;
    }
}

