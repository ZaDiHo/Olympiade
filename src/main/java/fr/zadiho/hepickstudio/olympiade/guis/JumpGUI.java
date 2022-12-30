package fr.zadiho.hepickstudio.olympiade.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.tasks.JumpTask;
import fr.zadiho.hepickstudio.olympiade.tasks.RaceTask;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class JumpGUI implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("jump")
            .provider(new JumpGUI())
            .size(5, 9)
            .title("§cParcours")
            .manager(Olympiade.getInventoryManager())
            .build();



    @Override
    public void init(Player player, InventoryContents contents) {
        ClickableItem jump = ClickableItem.of(new ItemBuilder(Material.FEATHER)
                .setName(ChatColor.RED + EGames.PARKOUR.getName())
                .addLoreLine("§8§m-----------------------")
                .addLoreLine("§8⭓ §7Épreuve: §eJump.")
                .addLoreLine("§8⭓ §7Durée: §e" + GameSettings.getParkourDuration() + "min.")
                .addLoreLine("§8§m-----------------------")
                .toItemStack(), e -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        });
        ClickableItem border = ClickableItem.empty(new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName("§l§cOlympiade")
                .toItemStack());
        ClickableItem leave = ClickableItem.of(new ItemBuilder(Material.SPRUCE_DOOR)
                .setName(ChatColor.RED + "Fermer")
                .toItemStack(), e -> {
            player.closeInventory();
        });
        ClickableItem back = ClickableItem.of(new ItemBuilder(Material.ARROW)
                .setName(ChatColor.RED + "Retour")
                .toItemStack(), e -> {
            AdminGUI.SMART_INVENTORY.open(player);
        });

        ClickableItem start = ClickableItem.of(new ItemBuilder(Material.SLIME_BALL)
                .setName("§8§l§8» §a§lC'est parti !")
                .toItemStack(), e -> {
            AdminGUI.SMART_INVENTORY.open(player);
            if(JumpTask.isPlayed() || EGames.getCurrentState().equals(EGames.PARKOUR)){
                if(JumpTask.isPlayed()){
                    player.sendMessage("§cL'épreuve à déjà été faite, vous pouvez cependant la réinitialiser.");
                }
                if(EGames.getCurrentState().equals(EGames.PARKOUR)){
                    player.sendMessage("§cL'épreuve est déjà en cours !");
                }
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
            }else{
                EGames.setState(EGames.PARKOUR);
                JumpTask jumpTask = new JumpTask();
                jumpTask.runTaskTimer(Olympiade.getInstance(), 0, 20);
            }
        });

        ClickableItem rules = ClickableItem.of(new ItemBuilder(Material.BLAZE_POWDER)
                .setName("§8§l§8» §e§lSalle des règles !")
                .toItemStack(), e -> {
            for(Player players : Bukkit.getOnlinePlayers()){
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3_nether"), -1171.5, 68, 40.5, -90, 0));
            }
        });

        ClickableItem reset = ClickableItem.of(new ItemBuilder(Material.REDSTONE)
                .setName("§8§l§8» §c§lRéinitialiser ! §7(§6Instantané§7)")
                .toItemStack(), e -> {
            JumpTask.resetRace();
            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
        });


        contents.set(2, 3, start);
        contents.set(2, 4, rules);
        contents.set(2, 5, reset);
        contents.set(0, 4, jump);
        contents.set(4,4, leave);
        contents.set(4,3, back);
        contents.set(0, 0, border);
        contents.set(0, 1, border);
        contents.set(0, 7, border);
        contents.set(0, 8, border);
        contents.set(1, 0, border);
        contents.set(1, 8, border);
        contents.set(3, 0, border);
        contents.set(3, 8, border);
        contents.set(4, 0, border);
        contents.set(4, 1, border);
        contents.set(4, 7, border);
        contents.set(4, 8, border);

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
