package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.utils.Cuboid;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
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

    private static int counter = 16;
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

    public static void resetPVE() {
        setPlayed(false);
        counter = 16;
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
    public void onEat(EntityChangeBlockEvent event) {
        if (EGames.getCurrentState().equals(EGames.PVE)) {
            if (event.getEntity().getType() == EntityType.WITHER_SKULL) {
                event.setCancelled(true);
            }
            if (event.getEntity().getType() == EntityType.WITHER) {
                event.setCancelled(true);
            }
        }
    }

    public static void startRound() {
        if (round == 1) {
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
        if (round == 3) {
            for (int i = 75; i > 0; i--) {
                Location loc = randomLoc();
                while (!isAirLocation(loc)) {
                    loc = randomLoc();
                }
                Witch witch = (Witch) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.WITCH);
                Slime slime = (Slime) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.SLIME);
                Phantom phantom = (Phantom) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.PHANTOM);
                WitherSkeleton witherSkeleton = (WitherSkeleton) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.WITHER_SKELETON);

                witch.setGlowing(true);
                witch.setCustomName("§cSorcière");
                witch.setCustomNameVisible(true);

                slime.setGlowing(true);
                slime.setCustomName("§cSlime");
                slime.setCustomNameVisible(true);

                phantom.setGlowing(true);
                phantom.setCustomName("§cFantôme");
                phantom.setCustomNameVisible(true);

                witherSkeleton.setGlowing(true);
                witherSkeleton.setCustomName("§cWither squelette");
                witherSkeleton.setCustomNameVisible(true);
            }
        }
        if (round == 4) {
            for (int i = 50; i > 0; i--) {
                Location loc = randomLoc();
                while (!isAirLocation(loc)) {
                    loc = randomLoc();
                }
                EnderDragon enderDragon = (EnderDragon) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.ENDER_DRAGON);
                WitherSkeleton witherSkeleton = (WitherSkeleton) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.WITHER_SKELETON);
                Blaze blaze = (Blaze) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.BLAZE);
                Stray stray = (Stray) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.STRAY);
                Husk husk = (Husk) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.HUSK);

                enderDragon.setGlowing(true);
                enderDragon.setCustomName("§cEnder dragon");
                enderDragon.setCustomNameVisible(true);

                witherSkeleton.setGlowing(true);
                witherSkeleton.setCustomName("§cWither squelette");
                witherSkeleton.setCustomNameVisible(true);

                blaze.setGlowing(true);
                blaze.setCustomName("§cBlaze");
                blaze.setCustomNameVisible(true);

                stray.setGlowing(true);
                stray.setCustomName("§cPingouin brutal");
                stray.setCustomNameVisible(true);

                husk.setGlowing(true);
                husk.setCustomName("§cPingouin");
                husk.setCustomNameVisible(true);
            }
        }
        if (round == 5) {
            for (int i = 50; i > 0; i--) {
                Location loc = randomLoc();
                while (!isAirLocation(loc)) {
                    loc = randomLoc();
                }
                Warden warden = (Warden) Bukkit.getWorld("OlympiadeS3").spawnEntity(loc, EntityType.WARDEN);

                warden.setGlowing(true);
                warden.setCustomName("§cWarden");
                warden.setCustomNameVisible(true);
            }
        }

    }

    public static boolean isPlayed() {
        return played;
    }

    public static void setPlayed(boolean played) {
        TNTTask.played = played;
    }

    private static void removeEntityTypes(EntityType type) {
        for (Entity ent : Bukkit.getWorld("OlympiadeS3").getEntitiesByClass(type.getEntityClass())) {
            ent.remove();
        }
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
            Player killer = event.getEntity().getKiller();
            if (getRound() == 1) {
                if (event.getEntity().getType() == EntityType.SKELETON) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.ZOMBIE) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.SPIDER) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.FROG) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
            }
            if (getRound() == 2) {
                if (event.getEntity().getType() == EntityType.PILLAGER) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.VINDICATOR) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.VEX) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.ALLAY) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.RAVAGER) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
            }
            if (getRound() == 3) {
                if (event.getEntity().getType() == EntityType.SLIME) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.WITCH) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.PHANTOM) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.WITHER) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
            }
            if (getRound() == 4) {
                if (event.getEntity().getType() == EntityType.ENDER_DRAGON) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.WITHER_SKELETON) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.BLAZE) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.PIGLIN) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
                if (event.getEntity().getType() == EntityType.PIGLIN_BRUTE) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
            }
            if (getRound() == 5) {
                if (event.getEntity().getType() == EntityType.WARDEN) {
                    GameSettings.getPvePodium().put(killer, GameSettings.getPvePodium().get(killer) + 1);
                    entities -= 1;
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (EGames.getCurrentState().equals(EGames.PVE)) {
            event.setDeathMessage(null);
            alives.remove(event.getEntity().getPlayer());
        }
    }

    public static void equipPlayer(Player player) {
        ItemStack[] armorContents = new ItemStack[4];
        armorContents[3] = new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.DURABILITY, 10).toItemStack();
        armorContents[2] = new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.DURABILITY, 10).toItemStack();
        armorContents[1] = new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.DURABILITY, 10).toItemStack();
        armorContents[0] = new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.DURABILITY, 10).toItemStack();

        player.getInventory().setArmorContents(armorContents);
        player.getInventory().addItem(new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1).addEnchant(Enchantment.DURABILITY, 10).toItemStack());
        player.getInventory().addItem(new ItemStack(Material.SHIELD));
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 32));
        player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
    }

    @Override
    public void run() {
        if (counter == 16) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.stopAllSounds();
                Bukkit.getWorld("OlympiadeS3").setPVP(false);
                players.showPlayer(Olympiade.getInstance(), players);
                players.getActivePotionEffects().clear();
                players.playSound(players.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 1);
                players.setGameMode(GameMode.SURVIVAL);
                alives.add(players);
                getInPVE().add(players);
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -1092.5, 86, -724.5, -180, 0));
                players.stopAllSounds();
                players.playSound(players.getLocation(), Sound.MUSIC_DISC_FAR, 1, 1);
                GameSettings.getPvePodium().put(players, 0);
            }
        }
        if (counter == 10) {
            for (Player players : Bukkit.getOnlinePlayers()) {
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
                entities = 10;
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
            if (entities <= 0) {
                if (getRound() == 1 && entities <= 0) {
                    removeEntityTypes(EntityType.ZOMBIE);
                    removeEntityTypes(EntityType.SKELETON);
                    removeEntityTypes(EntityType.SPIDER);
                    removeEntityTypes(EntityType.FROG);
                    Bukkit.broadcastMessage("Fin de la vague 1, préparez vous à la vague 2");
                    round = 2;
                    entities = 10;
                    Bukkit.getScheduler().runTaskLater(Olympiade.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            startRound();
                            Bukkit.broadcastMessage("C'est parti pour la vague 2 !");
                        }
                    }, 20 * 10);
                }
                if (getRound() == 2 && entities <= 0) {
                    removeEntityTypes(EntityType.PILLAGER);
                    removeEntityTypes(EntityType.VINDICATOR);
                    removeEntityTypes(EntityType.VEX);
                    removeEntityTypes(EntityType.ALLAY);
                    removeEntityTypes(EntityType.WITHER);
                    Bukkit.broadcastMessage("Fin de la vague 2, préparez vous à la vague 3");
                    round = 3;
                    entities = 10;
                    startRound();
                }
                if (getRound() == 3 && entities <= 0) {
                    removeEntityTypes(EntityType.WITCH);
                    removeEntityTypes(EntityType.SLIME);
                    removeEntityTypes(EntityType.PHANTOM);
                    removeEntityTypes(EntityType.WITHER_SKELETON);
                    Bukkit.broadcastMessage("Fin de la vague 3, préparez vous à la vague 4");
                    round = 4;
                    entities = 10;
                    startRound();
                }
                if (getRound() == 4 && entities <= 0) {
                    removeEntityTypes(EntityType.ENDER_DRAGON);
                    removeEntityTypes(EntityType.WITHER_SKELETON);
                    removeEntityTypes(EntityType.BLAZE);
                    removeEntityTypes(EntityType.STRAY);
                    removeEntityTypes(EntityType.HUSK);
                    Bukkit.broadcastMessage("Fin de la vague 4, préparez vous à la vague 5");
                    round = 5;
                    entities = 10;
                    startRound();
                }
                if (getRound() == 5 && entities <= 0) {
                    removeEntityTypes(EntityType.WARDEN);
                    Bukkit.broadcastMessage("Fin de la vague 5, préparez vous à la vague 2");
                    for (Player players : GameSettings.getGamePlayers()) {
                        players.sendTitle("§cPVE Terminé !", "§6Toutes les vagues ont été terminées !", 10, 100, 10);
                        players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                        players.getInventory().clear();
                        players.setGameMode(GameMode.ADVENTURE);
                    }
                    EGames.setState(EGames.WAITING);
                    setPlayed(true);
                    Game.teleportPodium(GameSettings.pvePodiumList());
                    Game.givePoints(GameSettings.pvePodiumList());
                    cancel();
                }
            }

            if (alives.size() == 0) {
                for (Player players : GameSettings.getGamePlayers()) {
                    if (getRound() == 1) {
                        removeEntityTypes(EntityType.ZOMBIE);
                        removeEntityTypes(EntityType.SKELETON);
                        removeEntityTypes(EntityType.SPIDER);
                        removeEntityTypes(EntityType.FROG);
                        players.sendTitle("§cDéfaite!", "§6Vous n'avez pas remporté cette vague...", 10, 100, 10);
                        players.playSound(players.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
                        equipPlayer(players);
                        players.setGameMode(GameMode.SURVIVAL);
                        players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -1080.5, 50, -844.5, -180, 0));
                        Bukkit.broadcastMessage("Fin de la vague 1, préparez vous à la vague 2");
                        round = 2;
                        entities = 10;
                        Bukkit.getScheduler().runTaskLater(Olympiade.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                startRound();
                                Bukkit.broadcastMessage("C'est parti pour la vague 2 !");
                            }
                        }, 20 * 10);
                    }
                    if (getRound() == 2) {
                        removeEntityTypes(EntityType.PILLAGER);
                        removeEntityTypes(EntityType.VINDICATOR);
                        removeEntityTypes(EntityType.VEX);
                        removeEntityTypes(EntityType.ALLAY);
                        removeEntityTypes(EntityType.WITHER);
                        players.sendTitle("§cDéfaite!", "§6Vous n'avez pas remporté cette vague...", 10, 100, 10);
                        players.playSound(players.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
                        equipPlayer(players);
                        players.setGameMode(GameMode.SURVIVAL);
                        players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -1080.5, 50, -844.5, -180, 0));
                        Bukkit.broadcastMessage("Fin de la vague 2, préparez vous à la vague 3");
                        round = 3;
                        entities = 10;
                        Bukkit.getScheduler().runTaskLater(Olympiade.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                startRound();
                                Bukkit.broadcastMessage("C'est parti pour la vague 3 !");
                            }
                        }, 20 * 10);
                    }
                    if (getRound() == 3) {
                        removeEntityTypes(EntityType.WITCH);
                        removeEntityTypes(EntityType.SLIME);
                        removeEntityTypes(EntityType.PHANTOM);
                        removeEntityTypes(EntityType.WITHER_SKELETON);
                        players.sendTitle("§cDéfaite!", "§6Vous n'avez pas remporté cette vague...", 10, 100, 10);
                        players.playSound(players.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
                        equipPlayer(players);
                        players.setGameMode(GameMode.SURVIVAL);
                        players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -1080.5, 50, -844.5, -180, 0));
                        Bukkit.broadcastMessage("Fin de la vague 3, préparez vous à la vague 4");
                        round = 4;
                        entities = 10;
                        Bukkit.getScheduler().runTaskLater(Olympiade.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                startRound();
                                Bukkit.broadcastMessage("C'est parti pour la vague 4 !");
                            }
                        }, 20 * 10);
                    }
                    if (getRound() == 4) {
                        removeEntityTypes(EntityType.ENDER_DRAGON);
                        removeEntityTypes(EntityType.WITHER_SKELETON);
                        removeEntityTypes(EntityType.BLAZE);
                        removeEntityTypes(EntityType.STRAY);
                        removeEntityTypes(EntityType.HUSK);
                        players.sendTitle("§cDéfaite!", "§6Vous n'avez pas remporté cette vague...", 10, 100, 10);
                        players.playSound(players.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
                        equipPlayer(players);
                        players.setGameMode(GameMode.SURVIVAL);
                        players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -1080.5, 50, -844.5, -180, 0));
                        Bukkit.broadcastMessage("Fin de la vague 4, préparez vous à la vague 5");
                        round = 5;
                        entities = 10;
                        Bukkit.getScheduler().runTaskLater(Olympiade.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                startRound();
                                Bukkit.broadcastMessage("C'est parti pour la vague 5 !");
                            }
                        }, 20 * 10);
                    }
                    if (getRound() == 5) {
                        removeEntityTypes(EntityType.WARDEN);
                        Bukkit.broadcastMessage("Fin de la vague 5, préparez vous à la vague 2");
                        players.sendTitle("§cPVE Terminé !", "§6Toutes les vagues ont été terminées !", 10, 100, 10);
                        players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                        players.getInventory().clear();
                        players.setGameMode(GameMode.ADVENTURE);
                        EGames.setState(EGames.WAITING);
                        setPlayed(true);
                        Game.teleportPodium(GameSettings.pvePodiumList());
                        Game.givePoints(GameSettings.pvePodiumList());
                        cancel();
                    }
                    alives.add(players);

                }
            }
            if (time / 60 >= EGames.PVE.getDuration()) {
                for (Player players : GameSettings.getGamePlayers()) {
                    players.sendTitle("§cTemps écoulé !", "§6Le parcours est terminé !", 10, 100, 10);
                    players.playSound(players.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1, 1);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.ADVENTURE);
                }
                EGames.setState(EGames.WAITING);
                setPlayed(true);
                Game.teleportPodium(GameSettings.pvePodiumList());
                Game.givePoints(GameSettings.pvePodiumList());
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

