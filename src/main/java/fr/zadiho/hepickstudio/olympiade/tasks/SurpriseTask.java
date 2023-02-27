package fr.zadiho.hepickstudio.olympiade.tasks;

import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SurpriseTask extends BukkitRunnable implements Listener {

    private List<Player> inSurprise = new ArrayList<>();
    private static int counter = 10;
    private static List<Player> team1 = new ArrayList<>();
    private boolean aliveBoss = true;

    public List<Player> getInSurprise() {
        return inSurprise;
    }


    private static void teleportPlayersCircle(List<Player> players) {
        Random random = new Random();
        double centerX = -485.5;
        double centerY = 100;
        double centerZ = -1242.5;
        for (Player player : players) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double x = 50 * Math.cos(angle) + centerX;
            double z = 50 * Math.sin(angle) + centerZ;
            Location location = new Location(Bukkit.getWorlds().get(0), x, centerY, z);
            player.teleport(location);
            if (player.getName().equalsIgnoreCase("Kayaato")) {
                player.teleport(new Location(Bukkit.getWorlds().get(0), -485.5, 66, -1242.5, 90, 0));
            }
        }
    }

    public static void setup(Player player) {
        player.getActivePotionEffects().clear();
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        ItemStack[] armorContents = new ItemStack[4];
        if (player.getName().equalsIgnoreCase("Kayaato")) {
            armorContents[3] = new ItemBuilder(Material.NETHERITE_HELMET).addEnchant(Enchantment.DURABILITY, 5).toItemStack();
            armorContents[2] = new ItemBuilder(Material.NETHERITE_CHESTPLATE).addEnchant(Enchantment.DURABILITY, 5).toItemStack();
            armorContents[1] = new ItemBuilder(Material.NETHERITE_LEGGINGS).addEnchant(Enchantment.DURABILITY, 5).toItemStack();
            armorContents[0] = new ItemBuilder(Material.NETHERITE_BOOTS).addEnchant(Enchantment.DURABILITY, 5).toItemStack();

            player.getInventory().setArmorContents(armorContents);
            player.getInventory().addItem(new ItemBuilder(Material.NETHERITE_SWORD).addEnchant(Enchantment.DURABILITY, 5).toItemStack());
            player.getInventory().addItem(new ItemBuilder(Material.FISHING_ROD).addEnchant(Enchantment.DURABILITY, 5).toItemStack());
            player.getInventory().addItem(new ItemStack(Material.BOW));
            player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING));
            player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 16));
            player.getInventory().addItem(new ItemStack(Material.SHIELD));
            player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 3));
            player.getInventory().addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1));

            player.getInventory().addItem(new ItemBuilder(Material.STICK).setName("§cCadeau de ZaDiHo ;)").addEnchant(Enchantment.KNOCKBACK, 8).toItemStack());
            player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
            player.getInventory().addItem(new ItemStack(Material.ARROW, 64));

            player.setGlowing(true);
        } else {
            team1.add(player);
            armorContents[3] = new ItemStack(Material.LEATHER_HELMET);
            armorContents[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
            armorContents[1] = new ItemStack(Material.LEATHER_LEGGINGS);
            armorContents[0] = new ItemStack(Material.LEATHER_BOOTS);

            player.getInventory().setArmorContents(armorContents);
            player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
            player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 4));
            player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (EGames.getCurrentState().equals(EGames.SURPRISE)) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(counter > -10);
            }

        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (EGames.getCurrentState().equals(EGames.SURPRISE)) {
            if (getInSurprise().contains(event.getEntity().getPlayer())) {
                getInSurprise().remove(event.getEntity().getPlayer());
                event.getDrops().clear();
                Bukkit.broadcastMessage("§c" + event.getEntity().getName() + " §6est mort ! Il reste §c" + (getInSurprise().size() - 1) + " §6joueurs en vie !");
                event.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);
                event.getEntity().setGameMode(GameMode.SPECTATOR);
                event.getEntity().teleport(GameSettings.spawn);
                if(event.getEntity().getName().equalsIgnoreCase("Kayaato")){
                    aliveBoss = false;
                }else{
                    team1.remove(event.getEntity());
                }
            }
        }
    }

    @Override
    public void run() {
        if (counter == 10) {
            aliveBoss = true;
            getInSurprise().clear();
            for (Player players : GameSettings.getGamePlayers()) {
                players.showPlayer(Olympiade.getInstance(), players);
                players.sendTitle("§cAttention !", "§6Epreuve suprise !", 10, 20, 10);
                if (!players.getName().equalsIgnoreCase("Kayaato")) {
                    players.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(999999999, 1));
                }
                players.stopAllSounds();
                players.playSound(players.getLocation(), Sound.MUSIC_DISC_MALL, 1, 1);
                setup(players);
                getInSurprise().add(players);
            }
            teleportPlayersCircle(getInSurprise());
            Bukkit.getWorld("OlympiadeS3").setPVP(false);
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
            Bukkit.broadcastMessage(GameSettings.prefix + "§cAlors comme ça §eKayato§c tu est indétronable ? §lMontrez lui !");
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                players.removePotionEffect(PotionEffectType.BLINDNESS);
            }

        }
        if (counter == -5) {
            Bukkit.broadcastMessage(GameSettings.prefix + "§cAttention ! La période d'invincibilité est terminée !");
            Bukkit.getWorld("OlympiadeS3").setPVP(true);
            for (Player player : getInSurprise()) {
                if (!player.getName().equalsIgnoreCase("Kayaato")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
                }
            }
        }
        if (counter < 0) {
            if (!aliveBoss) {
                Bukkit.broadcastMessage(GameSettings.prefix + "§6Epreuve terminée ! §cKayaato §6est mort ! Il reste §c" + (getInSurprise().size()) + " §6joueurs en vie !");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setGameMode(GameMode.ADVENTURE);
                    WinFireworks.setWinner(player);
                    WinFireworks winFireworks = new WinFireworks();
                    winFireworks.runTaskTimer(Olympiade.getInstance(), 0, 20);
                    if (player.getName().equalsIgnoreCase("Kayaato")) {
                        player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -468.5, 66, -1239.5, 90, 0));
                    } else {
                        player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -466.5, 69, -1242.5, 90, 0));
                    }
                    player.getInventory().clear();
                    Bukkit.getWorld("OlympiadeS3").setPVP(false);
                    EGames.setState(EGames.WAITING);
                    cancel();
                }
            }
            if (team1.size() == 0) {
                Bukkit.broadcastMessage(GameSettings.prefix + "§6Epreuve terminée ! §cKayaato §6est le dernier survivant ! Il est le grand gagnant !");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setGameMode(GameMode.ADVENTURE);
                    WinFireworks.setWinner(player);
                    WinFireworks winFireworks = new WinFireworks();
                    winFireworks.runTaskTimer(Olympiade.getInstance(), 0, 20);
                    if (player.getName().equalsIgnoreCase("Kayaato")) {
                        player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -468.5, 66, -1239.5, 90, 0));
                    } else {
                        player.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -466.5, 69, -1242.5, 90, 0));
                    }
                    player.getInventory().clear();
                }
                Bukkit.getWorld("OlympiadeS3").setPVP(false);
                EGames.setState(EGames.WAITING);
                cancel();
            }
        }
        counter--;
    }

}
