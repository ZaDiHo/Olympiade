package fr.zadiho.hepickstudio.olympiade.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.EGames;
import fr.zadiho.hepickstudio.olympiade.tasks.PVETask;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AdminGUI implements InventoryProvider {

    public static final SmartInventory SMART_INVENTORY = SmartInventory.builder()
            .id("main")
            .provider(new AdminGUI())
            .size(5, 9)
            .title("§cMenu administrateur")
            .manager(Olympiade.getInventoryManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {

        ClickableItem player_head_item = ClickableItem.of(new ItemBuilder(Material.PLAYER_HEAD)
                .setSkullOwner(player.getName())
                .setName(ChatColor.GREEN + "Modération joueurs.")
                .toItemStack(), e -> {
            PlayersGUI.INVENTORY.open(player);
        });

        ClickableItem border = ClickableItem.empty(new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName("§l§cOlympiade")
                .toItemStack());

        ClickableItem jump = ClickableItem.of(new ItemBuilder(Material.FEATHER)
                .setName(ChatColor.RED + EGames.PARKOUR.getName())
                .addLoreLine("§8§m-----------------------")
                .addLoreLine("§8⭓ §7Épreuve: §eJump.")
                .addLoreLine("§8⭓ §7Durée: §e" + EGames.PARKOUR.getDuration() + "min.")
                .addLoreLine("§7")
                .addLoreLine("§eCliquez pour ouvrir le menu.")
                .addLoreLine("§8§m-----------------------")
                .toItemStack(), e -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            JumpGUI.INVENTORY.open(player);
        });

        ClickableItem race = ClickableItem.of(new ItemBuilder(Material.WARPED_FUNGUS_ON_A_STICK)
                .setName(ChatColor.RED + EGames.RACE.getName())
                .addLoreLine("§8§m-----------------------")
                .addLoreLine("§8⭓ §7Épreuve: §eCourse d'arpenteur.")
                .addLoreLine("§8⭓ §7Durée: §e" + EGames.RACE.getDuration() + "min.")
                .addLoreLine("§7")
                .addLoreLine("§eCliquez pour ouvrir le menu.")
                .addLoreLine("§8§m-----------------------")
                .toItemStack(), e -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            RaceGUI.INVENTORY.open(player);
        });

        ClickableItem pve = ClickableItem.of(new ItemBuilder(Material.SHIELD)
                .setName(ChatColor.RED + EGames.PVE.getName())
                .addLoreLine("§8§m-----------------------")
                .addLoreLine("§8⭓ §7Épreuve: §ePVE.")
                .addLoreLine("§8⭓ §7Durée: §e" + EGames.PVE.getDuration() + "min.")
                .addLoreLine("§7")
                .addLoreLine("§eCliquez pour ouvrir le menu.")
                .addLoreLine("§8§m-----------------------")
                .toItemStack(), e -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            PVEGUI.INVENTORY.open(player);
        });

        ClickableItem tnt = ClickableItem.of(new ItemBuilder(Material.TNT)
                .setName(ChatColor.RED + EGames.TNT.getName())
                .addLoreLine("§8§m-----------------------")
                .addLoreLine("§8⭓ §7Épreuve: §eTNT Run.")
                .addLoreLine("§8⭓ §7Durée: §e" + EGames.TNT.getDuration() + "min.")
                .addLoreLine("§7")
                .addLoreLine("§eCliquez pour ouvrir le menu.")
                .addLoreLine("§8§m-----------------------")
                .toItemStack(), e -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            TNTGUI.INVENTORY.open(player);
        });

        ClickableItem pvp = ClickableItem.of(new ItemBuilder(Material.NETHERITE_SWORD)
                .setName(ChatColor.RED + EGames.PVP.getName())
                .addLoreLine("§8§m-----------------------")
                .addLoreLine("§8⭓ §7Épreuve: §ePVP.")
                .addLoreLine("§8⭓ §7Durée: §e" + EGames.PVP.getDuration() + "min.")
                .addLoreLine("§7")
                .addLoreLine("§eCliquez pour ouvrir le menu.")
                .addLoreLine("§8§m-----------------------")
                .toItemStack(), e -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            PVPGUI.INVENTORY.open(player);
        });

        ClickableItem surprise = ClickableItem.of(new ItemBuilder(Material.CHEST)
                .setName(ChatColor.RED + EGames.SURPRISE.getName())
                .addLoreLine("§8§m-----------------------")
                .addLoreLine("§8⭓ §7Épreuve: §eSurprise.")
                .addLoreLine("§8⭓ §7Durée: §eIllimité.")
                .addLoreLine("§7")
                .addLoreLine("§eCliquez pour ouvrir le menu.")
                .addLoreLine("§8§m-----------------------")
                .toItemStack(), e -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            SurpriseGUI.INVENTORY.open(player);
        });

        ClickableItem leave = ClickableItem.of(new ItemBuilder(Material.SPRUCE_DOOR)
                .setName(ChatColor.RED + "Fermer")
                .toItemStack(), e -> {
            player.closeInventory();
        });

        contents.set(0, 4, player_head_item);
        contents.set(2, 2, jump);
        contents.set(2, 3, race);
        contents.set(2, 4, pve);
        contents.set(2, 5, tnt);
        contents.set(2, 6, pvp);
        contents.set(2, 8, surprise);
        contents.set(4, 4, leave);

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
