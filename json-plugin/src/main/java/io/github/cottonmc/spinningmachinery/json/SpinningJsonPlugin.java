package io.github.cottonmc.spinningmachinery.json;

import com.google.common.collect.ImmutableList;
import io.github.cottonmc.jsonfactory.frontend.i18n.I18n;
import io.github.cottonmc.jsonfactory.frontend.i18n.ResourceBundleI18n;
import io.github.cottonmc.jsonfactory.gens.ContentGenerator;
import io.github.cottonmc.jsonfactory.gens.GeneratorInfo;
import io.github.cottonmc.jsonfactory.plugin.Plugin;
import io.github.cottonmc.jsonfactory.plugin.PluginLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public final class SpinningJsonPlugin implements Plugin {
    // Standard metals
    //spinning-machinery:copper,spinning-machinery:silver,spinning-machinery:lead,spinning-machinery:zinc,spinning-machinery:aluminum,spinning-machinery:cobalt,spinning-machinery:tin,spinning-machinery:titanium,spinning-machinery:tungsten,spinning-machinery:platinum,spinning-machinery:palladium,spinning-machinery:osmium,spinning-machinery:iridium,spinning-machinery:steel,spinning-machinery:brass,spinning-machinery:electrum,spinning-machinery:iron,spinning-machinery:gold,spinning-machinery:uranium,spinning-machinery:plutonium,spinning-machinery:thorium
    static final GeneratorInfo INFO = new GeneratorInfo(
            new GeneratorInfo.Category() {
                @NotNull
                @Override
                public String getId() {
                    return "category.spinning-machinery";
                }

                @Override
                public String getPlaceholderTexturePath() {
                    return "block";
                }
            }, null
    );

    @NotNull
    @Override
    public List<ContentGenerator> getGenerators() {
        return ImmutableList.of(new PlateRecipe());
    }

    @Override
    public I18n getI18n() {
        return new ResourceBundleI18n("spinning-machinery.i18n", Locale.getDefault());
    }

    // TODO: Consider using @JvmDefault in JF
    @Nullable
    @Override
    public PluginLoader getLoader() {
        return null;
    }
}
