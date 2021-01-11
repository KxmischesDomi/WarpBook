package net.codingarea.warpbook.listener;

import net.codingarea.warpbook.entities.WarpBook;
import net.codingarea.warpbook.entities.WarpPage;
import net.codingarea.warpbook.utils.AnimationSound;
import net.codingarea.warpbook.utils.Utils;
import net.codingarea.warpbook.utils.WarpBookUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class InventoryListener implements Listener {

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (event.getView().getTitle().equals(WarpBook.bookTitle)) {
			WarpBookUtils.safeWarpBook(event.getPlayer().getInventory().getItemInMainHand(), event.getInventory());
			AnimationSound.PLOP_SOUND.play(((Player) event.getPlayer()));
		}

	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getCurrentItem() == null) return;
		if (event.getView().getTitle().equals(WarpBook.bookTitle)) {
			if (Utils.clickedOwnInventory(event)) {
				WarpPage warpPage = WarpBookUtils.getWarpPage(event.getCurrentItem());
				if (warpPage == null || !warpPage.isUsed()) {
					event.setCancelled(true);
				}
				return;
			}

			if (event.getClick().toString().contains("LEFT")) {
				event.setCancelled(true);

				Player player = ((Player) event.getWhoClicked());
				Location location = WarpBookUtils.getWarpPage(event.getCurrentItem()).getPageLocation();
				if (location == null) return;
				WarpBookUtils.teleportPlayer(player, location);
			}
		}
	}

}