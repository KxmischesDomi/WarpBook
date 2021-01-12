package net.codingarea.warpbook.entities;

import de.tr7zw.nbtapi.NBTItem;
import net.codingarea.warpbook.utils.InventoryStringDeSerializer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class WarpBook {

	public static final int customModelData = 700;
	public static String bookTitle = "§5Warp Book";

	private final ItemStack itemStack;

	public WarpBook(final @Nonnull ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	@CheckReturnValue
	public Inventory getInventory() {
		try {
			Inventory inventory = Bukkit.createInventory(null, 6*9, bookTitle);
			NBTItem nbtItem = new NBTItem(itemStack);
			if (!nbtItem.getString("inventory").equals("")) {
				Inventory deserializedInventory = InventoryStringDeSerializer.fromBase64(nbtItem.getString("inventory"));
				inventory.setContents(deserializedInventory.getContents());
			}
			return inventory;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Nonnull
	@CheckReturnValue
	public ItemStack getItemStack() {
		return itemStack;
	}

}