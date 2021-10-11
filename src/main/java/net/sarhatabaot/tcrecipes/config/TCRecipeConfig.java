package net.sarhatabaot.tcrecipes.config;

import net.sarhatabaot.tcrecipes.TCRecipe;
import net.sarhatabaot.tcrecipes.TCRecipesAddon;
import net.tinetwork.tradingcards.api.addons.AddonConfigurate;
import net.tinetwork.tradingcards.api.model.deck.StorageEntry;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sarhatabaot
 */
public class TCRecipeConfig extends AddonConfigurate {
    private TCRecipe recipe;

    public TCRecipeConfig(final TCRecipesAddon addon, final String fileName) throws ConfigurateException {
        super(addon, "recipes" + File.separator, fileName, "recipes");
    }

    @Override
    protected void initValues() throws ConfigurateException {
        this.recipe = rootNode.get(TCRecipe.class);
    }

    @Override
    protected void preLoaderBuild() {
        loaderBuilder.defaultOptions(opts -> opts.serializers(builder ->
                builder.registerExact(TCRecipe.class, RecipeConfigSerializer.INSTANCE)
                        .registerExact(TCRecipe.class, RecipeConfigSerializer.INSTANCE)));
    }

    public TCRecipe getRecipe() {
        return recipe;
    }

    public static class RecipeConfigSerializer implements TypeSerializer<TCRecipe> {
        public static final RecipeConfigSerializer INSTANCE = new RecipeConfigSerializer();
        private final String KEY = "key";
        private final String RESULT = "result";
        private final String RECIPE = "recipe";

        @Override
        public TCRecipe deserialize(final Type type, final ConfigurationNode node) throws SerializationException {
            final String key = node.node(KEY).getString();
            final StorageEntry result = StorageEntry.fromString(node.node(RESULT).getString());
            final List<StorageEntry> storageList = convertToStorageEntries(node.node(RECIPE).getList(String.class));
            return new TCRecipe(key,result,storageList);
        }

        @Override
        public void serialize(final Type type, @Nullable final TCRecipe obj, final ConfigurationNode node) throws SerializationException {

        }

        //TODO, This is in DeckConfig.class
        public static @NotNull List<StorageEntry> convertToStorageEntries(final @NotNull List<String> list) {
            List<StorageEntry> entries = new ArrayList<>();
            for (String entry : list) {
                entries.add(StorageEntry.fromString(entry));
            }
            return entries;
        }
    }


}
