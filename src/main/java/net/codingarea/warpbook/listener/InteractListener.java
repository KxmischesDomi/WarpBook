package net.codingarea.warpbook.listener;

import net.codingarea.warpbook.entities.WarpBook;
import net.codingarea.warpbook.entities.WarpPage;
import net.codingarea.warpbook.utils.AnimationSound;
import net.codingarea.warpbook.utils.Utils;
import net.codingarea.warpbook.utils.WarpBookUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class InteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (!event.getAction().toString().contains("RIGHT")) return;
		if (event.getItem() == null) return;

		final int slot = event.getPlayer().getInventory().getHeldItemSlot();
		WarpPage warpPage = WarpBookUtils.getWarpPage(event.getItem());

		if (warpPage != null) {
			if (!warpPage.isUsed()) {
				Utils.decreaseItemStack(event.getPlayer().getInventory(), slot);
				warpPage.setPageLocation(event.getPlayer().getLocation());
				if (event.getItem().getAmount() == 1) {
					event.getPlayer().getInventory().setItem(slot, warpPage.getItemStack());
				} else {
					Utils.safeGiveItemToPlayer(event.getPlayer(), warpPage.getItemStack());
				}
			}
			return;
		}

		WarpBook warpBook = WarpBookUtils.getWarpBook(event.getItem());

		if (warpBook != null) {
			AnimationSound.OPEN_SOUND.play(event.getPlayer());
			event.getPlayer().openInventory(warpBook.getInventory());
		}

	}

}