package net.codingarea.warpbook;

import net.codingarea.warpbook.listener.CraftingListener;
import net.codingarea.warpbook.listener.InteractListener;
import net.codingarea.warpbook.listener.InventoryListener;
import net.codingarea.warpbook.listener.RestrictionListener;
import net.codingarea.warpbook.manager.RecipeManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class WarpBookPlugin extends JavaPlugin {

    private static WarpBookPlugin plugin;

    private RecipeManager recipeManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        recipeManager = new RecipeManager();
        registerListeners();
    }

    public void registerListeners() {
        registerListener(new RestrictionListener(),
                new InteractListener(),
                new InventoryListener(),
                new CraftingListener());
    }

    public void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @Nonnull
    @CheckReturnValue
    public static WarpBookPlugin getInstance() {
        return plugin;
    }

    @Nonnull
    @CheckReturnValue
    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

}