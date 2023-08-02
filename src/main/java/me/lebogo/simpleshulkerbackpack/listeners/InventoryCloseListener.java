package me.lebogo.simpleshulkerbackpack.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.lebogo.simpleshulkerbackpack.SimpleShulkerBackpack;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();
        Inventory playerInventory = player.getInventory();
        Inventory inventory = event.getInventory();

        for (ItemStack itemStack : playerInventory.getContents()) {
            // check if item is null
            if (itemStack == null) {
                continue;
            }

            // check if item is a shulker box
            if (!itemStack.getType().toString().contains("SHULKER_BOX")) {
                continue;
            }

            // check if item has the tag "open"
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null) {
                continue;
            }

            if (!itemMeta.getPersistentDataContainer().has(
                    new NamespacedKey(SimpleShulkerBackpack.getPlugin(SimpleShulkerBackpack.class), "open"),
                    PersistentDataType.STRING)) {
                continue;
            }

            // check if item is a block state meta
            if (!(itemMeta instanceof BlockStateMeta)) {
                continue;
            }

            BlockStateMeta blockStateMeta = (BlockStateMeta) itemMeta;

            // check if block state is a shulker box
            BlockState blockState = blockStateMeta.getBlockState();
            if (!(blockState instanceof ShulkerBox)) {
                continue;
            }

            // set the inventory of the shulker box to the event inventory
            ShulkerBox shulkerBox = (ShulkerBox) blockState;
            shulkerBox.getInventory().setContents(inventory.getContents());

            // update the block state meta
            blockStateMeta.setBlockState(shulkerBox);

            // remove the tag "open"
            itemMeta.getPersistentDataContainer().remove(
                    new NamespacedKey(SimpleShulkerBackpack.getPlugin(SimpleShulkerBackpack.class), "open"));

            // update the item meta
            itemStack.setItemMeta(itemMeta);

            // update the player
            if (player instanceof Player) {
                ((Player) player).updateInventory();
            }

            // break out of the loop
            break;

        }
    }

}
