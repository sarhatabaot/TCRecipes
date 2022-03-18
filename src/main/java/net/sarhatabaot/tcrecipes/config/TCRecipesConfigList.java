package net.sarhatabaot.tcrecipes.config;

import net.sarhatabaot.tcrecipes.TCRecipesAddon;
import org.spongepowered.configurate.ConfigurateException;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sarhatabaot
 */
public class TCRecipesConfigList {
    private final TCRecipesAddon addon;
    private final Set<TCRecipeConfig> recipeConfigList;

    private final File folder;

    public TCRecipesConfigList(final TCRecipesAddon addon) {
        this.addon = addon;
        this.recipeConfigList = new HashSet<>();

        this.folder = new File(addon.getDataFolder() + File.separator + "recipes");
        try {
            createRecipesFolder();
        } catch (ConfigurateException e){
            addon.getAddonLogger().warning(e.getMessage());
        }
        initValues();
    }

    private void createRecipesFolder() throws ConfigurateException {
        if (!folder.exists()) {
            folder.mkdir();
            final File defaultFile = new File(folder, "zombie_to_blaze.yml");
            if(!defaultFile.exists()){
                new TCRecipeConfig(addon,"zombie_to_blaze.yml").saveDefaultConfig();
            }
        }
    }

    public void initValues() {
        if(folder.listFiles() == null) {
            addon.getAddonLogger().warning("There are no files in the recipes folder.");
            return;
        }
        for(File file: Arrays.stream(folder.listFiles()).filter(file -> file.getName().endsWith(".yml")).toList()){
            try {
                TCRecipeConfig recipeConfig = new TCRecipeConfig(addon, file.getName());
                recipeConfigList.add(recipeConfig);
            } catch (ConfigurateException e){

            }
        }
    }

    public Set<TCRecipeConfig> getRecipeConfigList() {
        return recipeConfigList;
    }


    public void reload() {
        for(TCRecipeConfig config: recipeConfigList) {
            config.reloadConfig();
        }
    }
}
