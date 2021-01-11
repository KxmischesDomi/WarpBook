package net.codingarea.warpbook.commands;

import net.codingarea.warpbook.utils.WarpBookUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class TestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player player = ((Player) sender);
		player.getInventory().addItem(WarpBookUtils.createWarpPage());

		return false;
	}

}
