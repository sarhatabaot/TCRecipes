package net.sarhatabaot.tcrecipes;

import net.tinetwork.tradingcards.api.model.deck.StorageEntry;

import java.util.List;

public class TCRecipe {
    private final String key;
    private final StorageEntry result;
    private final List<StorageEntry> recipe;

    public TCRecipe(final String key, final StorageEntry result, final List<StorageEntry> recipe) {
        this.key = key;
        this.result = result;
        this.recipe = recipe;
    }

    public String getKey() {
        return key;
    }

    public StorageEntry getResult() {
        return result;
    }

    public List<StorageEntry> getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "TCRecipe{" +
                "key='" + key + '\'' +
                ", result=" + result.toString() +
                ", recipe=" + recipe +
                '}';
    }
}
