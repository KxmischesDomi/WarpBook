package net.codingarea.warpbook;

import net.codingarea.warpbook.commands.TestCommand;
import net.codingarea.warpbook.listener.InteractListener;
import net.codingarea.warpbook.listener.InventoryListener;
import net.codingarea.warpbook.listener.RestrictionListener;
import net.codingarea.warpbook.utils.WarpBookUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class WarpBookPlugin extends JavaPlugin {

    private static WarpBookPlugin plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {

        createWarpPageRecipe();
        createWarpBookRecipe();

        registerListeners();
        getCommand("test").setExecutor(new TestCommand());
    }

    public void registerListeners() {
        registerListener(new RestrictionListener());
        registerListener(new InteractListener());
        registerListener(new InventoryListener());
    }

    private void createWarpPageRecipe() {
        ItemStack itemStack = WarpBookUtils.createWarpPage();
        NamespacedKey nsKey = new NamespacedKey(plugin, "warp_page_recipe");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, itemStack);
        recipe.shape("   ", " E ", " P ");
        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('P', Material.PAPER);

        Bukkit.getServer().addRecipe(recipe);
    }

    private void createWarpBookRecipe() {
        ItemStack itemStack = WarpBookUtils.createWarpBook();
        NamespacedKey nsKey = new NamespacedKey(plugin, "warp_book_recipe");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, itemStack);
        recipe.shape("   ", " E ", " B ");
        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('B', Material.BOOK);

        Bukkit.getServer().addRecipe(recipe);
    }

    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @Nonnull
    @CheckReturnValue
    public static WarpBookPlugin getPlugin() {
        return plugin;
    }

}
