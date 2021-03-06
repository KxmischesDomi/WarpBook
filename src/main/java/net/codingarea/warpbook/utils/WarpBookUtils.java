package net.codingarea.warpbook.utils;

import de.tr7zw.nbtapi.NBTItem;
import net.codingarea.warpbook.entities.WarpBook;
import net.codingarea.warpbook.entities.WarpPage;
import net.codingarea.warpbook.manager.RecipeManager.ColoredWarpItem;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

import static net.codingarea.warpbook.WarpBookPlugin.getInstance;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class WarpBookUtils {

	/**
	 * @param itemStack the item to check
	 * @return checks if the item is a valid WarpBook
	 */
	@CheckReturnValue
	public static boolean isWarpBook(final @Nonnull ItemStack itemStack) {
		return false;
	}

	/**
	 * @param itemStack the item to check
	 * @return checks if the item is a valid WarpPage
	 */
	@CheckReturnValue
	public static boolean isWarpPage(final @Nonnull ItemStack itemStack) {
		return getWarpPage(itemStack) != null;
	}

	/**
	 * Will give a {@link WarpPage} instance of the item.
	 * @return null when the item is not a valid WarpPage
	 */
	@Nullable
	@CheckReturnValue
	public static WarpPage getWarpPage(final @Nonnull ItemStack itemStack) {
		if (itemStack.getType() != Material.PAPER) {
			return null;
		}

		NBTItem nbtItem = new NBTItem(itemStack);
		int modelData = nbtItem.getInteger("CustomModelData");

		if (modelData != WarpPage.unusedCustomModelData && !ColoredWarpItem.isColoredPageModelData(modelData)) {
			return null;
		}

		Location location = Utils.getLocationFromString(nbtItem.getString("location"));

		return new WarpPage(itemStack, location);
	}
	/**
	 * Will give a {@link WarpBook} instance of the item.
	 * @return null when the item is not a valid WarpBook
	 *
	 */
	@Nullable
	@CheckReturnValue
	public static WarpBook getWarpBook(final @Nonnull ItemStack itemStack) {
		if (itemStack.getType() != Material.ENCHANTED_BOOK) {
			return null;
		}

		NBTItem nbtItem = new NBTItem(itemStack);
		int modelData = nbtItem.getInteger("CustomModelData");

		if (modelData != WarpBook.customModelData) {
			return null;
		}

		return new WarpBook(itemStack);
	}

	/**
	 * @param itemStacks array to check
	 * @return if one of the items is a {@link WarpPage} or a {@link WarpBook}
	 */
	@CheckReturnValue
	public static boolean hasWarpItem(final @Nonnull ItemStack[] itemStacks) {
		return Arrays.stream(itemStacks).filter(Objects::nonNull).filter(itemStack -> isWarpPage(itemStack) || isWarpBook(itemStack)).count() >= 1;
	}

	/**
	 * @return a new WarpPage Item
	 */
	@Nonnull
	@CheckReturnValue
	public static ItemStack createWarpPage() {
		NBTItem nbtItem = new NBTItem(new ItemStack(Material.PAPER));
		nbtItem.setInteger("CustomModelData", WarpPage.unusedCustomModelData);

		ItemBuilder itemBuilder = new ItemBuilder(nbtItem.getItem());
		itemBuilder.setDisplayName("§fWarp Page");
		itemBuilder.setLore(WarpPage.getUnboundLore());
		return itemBuilder.build();
	}

	/**
	 * @return a new WarpBook item
	 */
	@Nonnull
	@CheckReturnValue
	public static ItemStack createWarpBook() {
		NBTItem nbtItem = new NBTItem(new ItemStack(Material.ENCHANTED_BOOK));
		nbtItem.setInteger("CustomModelData", WarpBook.customModelData);

		ItemBuilder itemBuilder = new ItemBuilder(nbtItem.getItem());
		itemBuilder.setDisplayName("§fWarp Book");
		itemBuilder.setLore("§5A Book for the Warp Pages");
		return itemBuilder.build();
	}

	/**
	 * Saves the WarpBook inventory in the NBT Tags of the Item
	 */
	public static void safeWarpBook(final @Nonnull ItemStack itemStack, final @Nonnull Inventory inventory) {
		NBTItem nbtItem = new NBTItem(itemStack);
		nbtItem.setString("inventory", InventoryStringDeSerializer.toBase64(inventory));
		itemStack.setItemMeta(nbtItem.getItem().getItemMeta());
	}

	/**
	 * Teleports a player with the Warp effects
	 */
	public static void teleportPlayer(final @Nonnull Player player, final @Nonnull Location location) {
		player.closeInventory();
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,  25, 1));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,  25, 1));

		Bukkit.getScheduler().runTaskLater(getInstance(), () -> {
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 4, 1);
			player.teleport(location);
			player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 1);
		}, 5);

	}

}