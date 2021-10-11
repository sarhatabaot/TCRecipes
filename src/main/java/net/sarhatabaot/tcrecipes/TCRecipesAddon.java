package net.sarhatabaot.tcrecipes;

import net.sarhatabaot.tcrecipes.config.TCRecipesConfigList;
import net.sarhatabaot.tcrecipes.manager.TCRecipeManager;
import net.tinetwork.tradingcards.api.TradingCardsPlugin;
import net.tinetwork.tradingcards.api.addons.AddonLogger;
import net.tinetwork.tradingcards.api.addons.TradingCardsAddon;
import net.tinetwork.tradingcards.api.card.Card;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class TCRecipesAddon extends JavaPlugin implements TradingCardsAddon {
    private TradingCardsPlugin<? extends Card<? extends Card>> tradingCardsApi;
    private AddonLogger addonLogger;

    private TCRecipesConfigList recipesConfigList;
    private TCRecipeManager recipeManager;


    @Override
    public @NotNull JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public AddonLogger getAddonLogger() {
        return addonLogger;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.tradingCardsApi = (TradingCardsPlugin<? extends Card<? extends Card>>) getServer().getPluginManager().getPlugin("TradingCards");
        this.addonLogger = new AddonLogger(getName(), tradingCardsApi);
        this.recipesConfigList = new TCRecipesConfigList(this);

        this.recipeManager = new TCRecipeManager(tradingCardsApi,this,recipesConfigList);
        this.recipeManager.registerAll();
    }

    @Override
    public void onDisable() {
        this.tradingCardsApi = null;
        this.addonLogger = null;
        this.recipeManager.unRegisterAll();
        // Plugin shutdown logic
    }

    @Override
    public void onReload() {
        onDisable();
        onEnable();
    }
}
