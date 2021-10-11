package net.sarhatabaot.tcrecipes.manager;

import net.sarhatabaot.tcrecipes.TCRecipe;
import net.sarhatabaot.tcrecipes.TCRecipesAddon;
import net.sarhatabaot.tcrecipes.config.TCRecipeConfig;
import net.sarhatabaot.tcrecipes.config.TCRecipesConfigList;
import net.tinetwork.tradingcards.api.TradingCardsPlugin;
import net.tinetwork.tradingcards.api.card.Card;
import net.tinetwork.tradingcards.api.model.deck.StorageEntry;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sarhatabaot
 */
public class TCRecipeManager {
    private final TradingCardsPlugin<? extends Card<? extends Card>> cardsApi;
    private final TCRecipesAddon addon;
    private final Set<TCRecipe> recipeList;
    private final TCRecipesConfigList recipesConfigList;

    private final String namespace;

    public TCRecipeManager(final TradingCardsPlugin<? extends Card<? extends Card>> cardApi,final TCRecipesAddon addon, TCRecipesConfigList recipesConfigList) {
        this.cardsApi = cardApi;
        this.addon = addon;
        this.recipeList = new HashSet<>();
        this.recipesConfigList = recipesConfigList;

        this.namespace = addon.getConfig().getString("namespace");
        loadRecipes();
    }

    private void loadRecipes() {
        for(TCRecipeConfig recipeConfig: recipesConfigList.getRecipeConfigList()) {
            recipeList.add(recipeConfig.getRecipe());
        }
    }

    public void registerAll() {
        for(TCRecipe recipe: recipeList) {
            registerRecipe(recipe);

        }
    }


    public void unRegisterAll() {
        for(TCRecipe tcRecipe: recipeList) {
            final NamespacedKey key = NamespacedKey.fromString(namespace + ":"+tcRecipe.getKey());

            Validate.notNull(key,"Key cannot be null"); //Should never be reached.
            Bukkit.removeRecipe(key);
        }
    }

    private void registerRecipe(@NotNull TCRecipe tcRecipe) {
        final ItemStack resultItemStack = getItemStackFromStorageEntry(tcRecipe.getResult());
        final NamespacedKey key = NamespacedKey.fromString(namespace+":"+tcRecipe.getKey());
        Validate.notNull(key, "Key cannot be null.");

        ShapelessRecipe recipe = new ShapelessRecipe(key,resultItemStack);
        for(StorageEntry entry: tcRecipe.getRecipe()) {
            recipe.addIngredient(getItemStackFromStorageEntry(entry));
        }
        addon.getAddonLogger().info("Registered "+ tcRecipe);
        Bukkit.addRecipe(recipe);
    }


    private ItemStack getItemStackFromStorageEntry(StorageEntry entry) {
        final Card<? extends Card> resultCard = cardsApi.getCardManager().getCard(entry.getCardId()
                ,entry.getRarityId(),entry.isShiny());
        final ItemStack resultItemStack = resultCard.build();
        resultItemStack.setAmount(entry.getAmount());
        return resultItemStack;
    }
}
