package me.lebogo.simpleshulkerbackpack.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.lebogo.simpleshulkerbackpack.SimpleShulkerBackpack;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) {
            return;
        }

        if (!item.getType().toString().contains("SHULKER_BOX")) {
            return;
        }

        if (!player.isSneaking()) {
            return;
        }

        event.setCancelled(true);

        ItemMeta itemMeta = item.getItemMeta();

        if (!(itemMeta instanceof BlockStateMeta)) {
            return;
        }

        BlockStateMeta blockStateMeta = (BlockStateMeta) itemMeta;

        BlockState blockState = blockStateMeta.getBlockState();

        if (!(blockState instanceof ShulkerBox)) {
            return;
        }

        ShulkerBox shulkerBox = (ShulkerBox) blockState;

        Inventory shulkerBoxInventory = shulkerBox.getInventory();

        player.openInventory(shulkerBoxInventory);

        // set a tag to the item of "open"
        itemMeta.getPersistentDataContainer().set(
                new NamespacedKey(SimpleShulkerBackpack.getPlugin(SimpleShulkerBackpack.class), "open"),
                PersistentDataType.STRING, "open");

        item.setItemMeta(itemMeta);

    }

}
