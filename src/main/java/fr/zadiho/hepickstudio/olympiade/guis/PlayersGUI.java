package fr.zadiho.hepickstudio.olympiade.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import fr.zadiho.hepickstudio.olympiade.Olympiade;
import fr.zadiho.hepickstudio.olympiade.game.Game;
import fr.zadiho.hepickstudio.olympiade.game.GameSettings;
import fr.zadiho.hepickstudio.olympiade.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayersGUI implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("players")
            .provider(new PlayersGUI())
            .size(6, 9)
            .title("§cModération joueurs")
            .manager(Olympiade.getInventoryManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        ClickableItem border = ClickableItem.empty(new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName("§l§cOlympiade")
                .toItemStack());

        ClickableItem[] items = new ClickableItem[GameSettings.getGamePlayers().size()];

        for (int i = 0; i < items.length; i++) {
            Player target = GameSettings.getGamePlayers().get(i);
            items[i] = ClickableItem.of(new ItemBuilder(Material.PLAYER_HEAD)
                    .setSkullOwner(target.getDisplayName())
                    .setName(ChatColor.GREEN + target.getDisplayName())
                    .addLoreLine("§8§m-----------------------")
                    .addLoreLine("§8⭓ §fGrade: §e" + Game.getPlayerPrefix(target))
                    .addLoreLine("§8⭓ §fPoints: §e" + GameSettings.getPodium().get(target))
                    .addLoreLine("§8⭓ §fClassements: §e" + GameSettings.getPlace(target))
                    .addLoreLine("§8⭓ §fKills: §e0")
                    .addLoreLine("§8§m-----------------------")
                    .addLoreLine("§8§l§8» §cCliquez pour vous y §ntéléporter.")
                    .toItemStack(), e -> {
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.teleport(target);
            });
        }

        pagination.setItems(items);
        pagination.setItemsPerPage(27);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 0));


        contents.fillRow(0, border);
        contents.fillRow(4, border);

        if(!pagination.isFirst()){
            contents.set(5, 3, ClickableItem.of(new ItemBuilder(Material.ARROW).setName("§aPage précédente").toItemStack(), e -> PlayersGUI.INVENTORY.open(player, pagination.previous().getPage())));
        }
        if(!pagination.isLast()){
            contents.set(5, 5, ClickableItem.of(new ItemBuilder(Material.ARROW).setName("§aPage suivante").toItemStack(), e -> PlayersGUI.INVENTORY.open(player, pagination.next().getPage())));
        }
        if (items.length > 27){
            contents.set(5, 5, ClickableItem.of(new ItemStack(Material.ARROW),
                    e -> INVENTORY.open(player, pagination.next().getPage())));
        }

        ClickableItem leave = ClickableItem.of(new ItemBuilder(Material.SPRUCE_DOOR)
                .setName(ChatColor.RED + "Fermer")
                .toItemStack(), e -> {
            player.closeInventory();
        });

        contents.set(5, 4, leave);

        ClickableItem barrier = ClickableItem.of(new ItemBuilder(Material.BARRIER)
                .setName(ChatColor.RED + "Retour")
                .toItemStack(), e -> {
            AdminGUI.SMART_INVENTORY.open(player);
        });

        contents.set(5, 0, barrier);
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
