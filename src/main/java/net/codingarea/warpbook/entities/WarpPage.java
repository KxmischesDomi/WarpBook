package net.codingarea.warpbook.entities;

import de.tr7zw.nbtapi.NBTItem;
import net.codingarea.warpbook.manager.RecipeManager.ColoredWarpItem;
import net.codingarea.warpbook.utils.ItemBuilder;
import net.codingarea.warpbook.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class WarpPage {

	public static final int unusedCustomModelData = 700;

	private final ItemStack itemStack;
	private boolean used;
	private Location pageLocation;

	public WarpPage(final @Nonnull ItemStack itemStack, final @Nullable Location location) {
		this.itemStack = itemStack;
		this.pageLocation = location;
		this.used = location != null;
	}

	/**
	 * Bounds the WarpPage to a specific Location
	 * @param pageLocation the location to bound to page
	 */
	public void bound(final @Nonnull Location pageLocation) {
		used = true;
		this.pageLocation = pageLocation;

		NBTItem nbtItem = new NBTItem(itemStack, true);
		nbtItem.setString("location", Utils.getStringFromLocation(pageLocation));
		nbtItem.setInteger("CustomModelData", ColoredWarpItem.getDefaultModelData());

		ItemBuilder itemBuilder = new ItemBuilder(nbtItem.getItem());
		itemBuilder.setLore(getLore());
		itemBuilder.setDisplayName(this.itemStack.getItemMeta().getDisplayName());

		itemStack.setItemMeta(itemBuilder.getItemMeta());
	}

	/**
	 * Removes the bounded position from the page and changes the texture
	 */
	public void unbound() {
		used = false;
		this.pageLocation = null;

		NBTItem nbtItem = new NBTItem(itemStack, true);
		nbtItem.removeKey("location");
		nbtItem.setInteger("CustomModelData", unusedCustomModelData);

		ItemBuilder itemBuilder = new ItemBuilder(nbtItem.getItem());
		itemBuilder.setLore(getUnboundLore());

		itemStack.setItemMeta(itemBuilder.getItemMeta());
	}

	/**
	 * @return the lore for the state the page has
	 */
	@Nonnull
	@CheckReturnValue
	private String getLore() {
		if (isUsed()) {
		return "§5Bound to ("
				+ pageLocation.getBlockX() + ", " + pageLocation.getBlockY() + ", " + pageLocation.getBlockZ() +
				") in World " + pageLocation.getWorld().getName();
		}
		return getUnboundLore();
	}

	/**
	 * @return the lore for the unbound pages
	 */
	@Nonnull
	@CheckReturnValue
	public static String getUnboundLore() {
		return "§5Page not bound yet";
	}

	/**
	 * @return if the page is bound to a location
	 */
	@CheckReturnValue
	public boolean isUsed() {
		return used;
	}

	/**
	 * @return the location of the WarpPage, null if the page is not bound
	 */
	@Nullable
	@CheckReturnValue
	public Location getPageLocation() {
		return pageLocation;
	}

	/**
	 * @return the itemstack instance of the WarpPage
	 */
	@Nonnull
	@CheckReturnValue
	public ItemStack getItemStack() {
		return itemStack;
	}

}