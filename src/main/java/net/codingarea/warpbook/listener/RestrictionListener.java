package net.codingarea.warpbook.listener;

import net.codingarea.warpbook.entities.WarpBook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class RestrictionListener implements Listener {

	@EventHandler
	public void onScroll(PlayerItemHeldEvent event) {
		if (event.getPlayer().getOpenInventory().getTitle().equals(WarpBook.bookTitle)) {
			event.setCancelled(true);
		}

	}



}