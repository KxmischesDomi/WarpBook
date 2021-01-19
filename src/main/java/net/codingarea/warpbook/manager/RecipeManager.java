package net.codingarea.warpbook.manager;

import de.tr7zw.nbtapi.NBTItem;
import net.codingarea.warpbook.utils.WarpBookUtils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.codingarea.warpbook.WarpBookPlugin.getInstance;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class RecipeManager {

	private ShapedRecipe warpPageRecipe;
	private final List<Recipe> coloredPageRecipeList = new ArrayList<>();

	public RecipeManager() {
		createWarpPageRecipe();
		createWarpBookRecipe();
		for (ColoredWarpItem value : ColoredWarpItem.values()) {
			createColoredPageRecipe(value);
		}
	}

	private void createWarpBookRecipe() {
		ItemStack itemStack = WarpBookUtils.createWarpBook();
		NamespacedKey nsKey = new NamespacedKey(getInstance(), "warp_book_recipe");
		ShapedRecipe recipe = new ShapedRecipe(nsKey, itemStack);
		recipe.shape("   ", " E ", " B ");
		recipe.setIngredient('E', Material.ENDER_PEARL);
		recipe.setIngredient('B', Material.BOOK);

		Bukkit.getServer().addRecipe(recipe);
	}

	private void createWarpPageRecipe() {
		ItemStack itemStack = WarpBookUtils.createWarpPage();
		NamespacedKey nsKey = new NamespacedKey(getInstance(), "warp_page_recipe");
		warpPageRecipe = new ShapedRecipe(nsKey, itemStack);
		warpPageRecipe.shape("   ", " E ", " P ");
		warpPageRecipe.setIngredient('E', Material.ENDER_PEARL);
		warpPageRecipe.setIngredient('P', Material.PAPER);

		Bukkit.getServer().addRecipe(warpPageRecipe);
	}

	private void createColoredPageRecipe(final @Nonnull ColoredWarpItem coloredPage) {
		NBTItem nbtItem = new NBTItem(WarpBookUtils.createWarpPage());
		nbtItem.setInteger("CustomModelData", coloredPage.customModelData);
		ItemStack itemStack = nbtItem.getItem();

		NamespacedKey nsKey = new NamespacedKey(getInstance(), "warp_page_recipe_" + coloredPage.color.name().toLowerCase());
		ShapelessRecipe warpPageRecipe = new ShapelessRecipe(nsKey, itemStack);
		warpPageRecipe.addIngredient(coloredPage.material);
		warpPageRecipe.addIngredient(Material.PAPER);

		Bukkit.getServer().addRecipe(warpPageRecipe);
		coloredPageRecipeList.add(warpPageRecipe);
	}

	@CheckReturnValue
	public boolean isColoredPageRecipe(final @Nonnull Recipe recipe) {

		for (Recipe recipes : coloredPageRecipeList) {
			if (recipe.getResult().isSimilar(recipes.getResult())) {
				return true;
			}
		}
		return false;
	}

	@Nonnull
	@CheckReturnValue
	public ShapedRecipe getWarpPageRecipe() {
		return warpPageRecipe;
	}

	@Nonnull
	@CheckReturnValue
	public List<Recipe> getColoredPageRecipeList() {
		return coloredPageRecipeList;
	}

	public enum ColoredWarpItem {

		PURPLE(Material.PURPLE_DYE, DyeColor.PURPLE, 701),
		LIME(Material.LIME_DYE, DyeColor.LIME, 702),
		RED(Material.RED_DYE, DyeColor.RED, 703),
		WHITE(Material.WHITE_DYE, DyeColor.WHITE, 704),
		GRAY(Material.GRAY_DYE, DyeColor.GRAY, 705),
		LIGHT_GRAY(Material.LIGHT_GRAY_DYE, DyeColor.LIGHT_GRAY, 706),
		BLACK(Material.BLACK_DYE, DyeColor.BLACK, 707),
		;

		public final Material material;
		public final DyeColor color;
		public final int customModelData;

		ColoredWarpItem(Material material, DyeColor color, int customModelData) {
			this.material = material;
			this.color = color;
			this.customModelData = customModelData;
		}

		@CheckReturnValue
		public static boolean isColoredPageModelData(int customModelData) {
			for (ColoredWarpItem value : ColoredWarpItem.values()) {
				if (value.customModelData == customModelData) {
					return true;
				}
			}
			return false;
		}

		@Nullable
		@CheckReturnValue
		public static ColoredWarpItem getByMaterial(Material material) {
			for (ColoredWarpItem value : ColoredWarpItem.values()) {
				if (value.material == material) {
					return value;
				}
			}
			return null;
		}

		@CheckReturnValue
		public static int getDefaultModelData() {
			return values()[0].customModelData;
		}

	}

}
