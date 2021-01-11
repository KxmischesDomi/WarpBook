package net.codingarea.warpbook.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class Utils {

	/**
	 * Converts a location to a simple string representation
	 */
	@Nonnull
	@CheckReturnValue
	public static String getStringFromLocation(final @Nonnull Location location) {
		return location.getWorld().getName() + ":" +
				location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" +
				location.getYaw() + ":" + location.getPitch();
	}

	/**
	 * Converts a serialized location to a Location
	 * @param locationString - serialized location in format "world:x:y:z"
	 * @return {@link Location} with the right coords
	 */
	@Nullable
	@CheckReturnValue
	public static Location getLocationFromString(final @Nonnull String locationString) {
		try {
			final String[] parts = locationString.split(":");
			if (parts.length == 6) {
				final World w = Bukkit.getServer().getWorld(parts[0]);
				final float x = Float.parseFloat(parts[1]);
				final float y = Float.parseFloat(parts[2]);
				final float z = Float.parseFloat(parts[3]);
				final float yaw = Float.parseFloat(parts[4]);
				final float pitch = Float.parseFloat(parts[5]);
				return new Location(w, x, y, z, yaw, pitch);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	/**
	 * Decreases the amount an {@link ItemStack} of an {@link Inventory}
	 * If Amount is 1 the itemstack will be set to null
	 */
	public static void decreaseItemStack(final @Nonnull Inventory inventory, final int slot) {
		ItemStack itemStack = inventory.getItem(slot);
		if (itemStack.getAmount() == 1) {
			inventory.setItem(slot, null);
		} else {
			itemStack.setAmount(itemStack.getAmount()-1);
		}

	}

	/**
	 * Gives an item to a player, if the inventory is full it will drop below the player
	 */
	public static void safeGiveItemToPlayer(final @Nonnull Player player, final @Nonnull ItemStack itemStack) {

		if (isInventoryFull(player)) {
			player.getWorld().dropItem(player.getLocation(), itemStack);
		} else {
			player.getInventory().addItem(itemStack);
		}

	}

	/**
	 * @returns true if inventory of the player is full
	 */
	public static boolean isInventoryFull(final @Nonnull Player player) {
		return player.getInventory().firstEmpty() == -1;
	}

	public static boolean clickedOwnInventory(final @Nonnull InventoryClickEvent event) {
		return event.getClickedInventory().getHolder() == event.getWhoClicked();
	}

}
