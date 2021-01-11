package net.codingarea.warpbook.entities;

import de.tr7zw.nbtapi.NBTItem;
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

	public static int unusedCustomModelData = 700;
	public static int usedCustomModelData = 701;

	private ItemStack itemStack;
	private boolean used;
	private Location pageLocation;

	public WarpPage(final @Nonnull ItemStack itemStack, final @Nullable Location location) {
		this.itemStack = itemStack;
		this.pageLocation = location;
		this.used = location != null;
	}

	public void setPageLocation(final @Nonnull Location pageLocation) {
		used = true;
		this.pageLocation = pageLocation;

		NBTItem nbtItem = new NBTItem(new ItemStack(Material.PAPER));
		nbtItem.setString("location", Utils.getStringFromLocation(pageLocation));
		nbtItem.setInteger("CustomModelData", usedCustomModelData);

		ItemBuilder itemBuilder = new ItemBuilder(nbtItem.getItem());
		itemBuilder.setLore(getLore());
		itemBuilder.setDisplayName(this.itemStack.getItemMeta().getDisplayName());

		itemStack = itemBuilder.build();
	}

	private String getLore() {
		return "§5Bound to ("
				+ pageLocation.getBlockX() + ", " + pageLocation.getBlockY() + ", " + pageLocation.getBlockZ() +
				") in World " + pageLocation.getWorld().getName();
	}

	@CheckReturnValue
	public boolean isUsed() {
		return used;
	}

	@Nullable
	@CheckReturnValue
	public Location getPageLocation() {
		return pageLocation;
	}

	@Nonnull
	@CheckReturnValue
	public ItemStack getItemStack() {
		return itemStack;
	}

}