package net.codingarea.warpbook.listener;

import de.tr7zw.nbtapi.NBTItem;
import net.codingarea.warpbook.WarpBookPlugin;
import net.codingarea.warpbook.entities.WarpPage;
import net.codingarea.warpbook.manager.RecipeManager.ColoredWarpItem;
import net.codingarea.warpbook.utils.ItemBuilder;
import net.codingarea.warpbook.utils.WarpBookUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class CraftingListener implements Listener {

	// TODO: Decrease method size
	@EventHandler
	public void onCraft(PrepareItemCraftEvent event) {
		if (event.getRecipe() == null) return;

		if (WarpBookPlugin.getInstance().getRecipeManager().isColoredPageRecipe(event.getRecipe())) {
			event.getInventory().setResult(null);

			WarpPage warpPage = null;
			ColoredWarpItem coloredPage = null;

			for (ItemStack itemStack : event.getInventory().getMatrix()) {
				if (itemStack == null) {
					continue;
				}
				WarpPage currentWarpPage = WarpBookUtils.getWarpPage(itemStack);
				if (currentWarpPage == null) {
					ColoredWarpItem currentColoredPage = ColoredWarpItem.getByMaterial(itemStack.getType());
					if (currentColoredPage != null) {
						coloredPage = currentColoredPage;
					}
					continue;
				}
				if (currentWarpPage.isUsed()) {
					warpPage = currentWarpPage;
				}
			}

			if (warpPage != null && coloredPage != null) {
				NBTItem nbtItem = new NBTItem(warpPage.getItemStack());

				if (nbtItem.getInteger("CustomModelData") == coloredPage.customModelData) {
					event.getInventory().setResult(null);
					return;
				}

				nbtItem.setInteger("CustomModelData", coloredPage.customModelData);
				ItemBuilder itemBuilder = new ItemBuilder(nbtItem.getItem());
				itemBuilder.setAmount(1);
				event.getInventory().setResult(itemBuilder.build());
			}

			return;
		}

		if (WarpBookUtils.hasWarpItem(event.getInventory().getMatrix())) {
			event.getInventory().setResult(null);
		}

	}

}
