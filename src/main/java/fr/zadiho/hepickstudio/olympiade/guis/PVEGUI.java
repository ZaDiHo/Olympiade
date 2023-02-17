package fr.zadiho.hepickstudio.olympiade.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.tasks.PVETask;
import fr.zadiho.hepickstudio.olympiade.tasks.PVPTask;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class PVEGUI implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("pve")
            .provider(new PVEGUI())
            .size(5, 9)
            .title("§cPVE")
            .manager(Olympiade.getInventoryManager())
            .build();



    @Override
    public void init(Player player, InventoryContents contents) {
        ClickableItem race = ClickableItem.empty(new ItemBuilder(Material.SHIELD)
                .setName(ChatColor.RED + EGames.PVE.getName())
                .addLoreLine("§8§m-----------------------")
                .addLoreLine("§8⭓ §7Épreuve: §ePVP.")
                .addLoreLine("§8⭓ §7Durée: §e" + EGames.PVP.getDuration() + "min.")
                .addLoreLine("§8§m-----------------------")
                .toItemStack());
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
            if(PVPTask.isPlayed() || EGames.getCurrentState().equals(EGames.PVE)){
                if(PVPTask.isPlayed()){
                    player.sendMessage("§cL'épreuve à déjà été faite, vous pouvez cependant la réinitialiser.");
                }
                if(EGames.getCurrentState().equals(EGames.PVE)){
                    player.sendMessage("§cL'épreuve est déjà en cours !");
                }
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 1, 1);
            }else{
                EGames.setState(EGames.PVE);
                PVETask pveTask = new PVETask();
                pveTask.runTaskTimer(Olympiade.getInstance(), 0, 20);
            }
        });

        ClickableItem rules = ClickableItem.of(new ItemBuilder(Material.BLAZE_POWDER)
                .setName("§8§l§8» §e§lSalle des règles !")
                .toItemStack(), e -> {
            for(Player players : Bukkit.getOnlinePlayers()){
                players.teleport(new Location(Bukkit.getWorld("OlympiadeS3"), -1092.5, 86, -724.5, -180, 0));
            }
        });

        ClickableItem reset = ClickableItem.of(new ItemBuilder(Material.REDSTONE)
                .setName("§8§l§8» §c§lRéinitialiser ! §7(§6Instantané§7)")
                .toItemStack(), e -> {
            PVETask.resetPVE();
            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
        });


        contents.set(2, 3, start);
        contents.set(2, 4, rules);
        contents.set(2, 5, reset);
        contents.set(0, 4, race);
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
