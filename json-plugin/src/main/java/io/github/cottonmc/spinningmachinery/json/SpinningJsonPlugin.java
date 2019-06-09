package io.github.cottonmc.spinningmachinery.json;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.github.cottonmc.jsonfactory.frontend.AutoFill;
import io.github.cottonmc.jsonfactory.frontend.i18n.I18n;
import io.github.cottonmc.jsonfactory.frontend.i18n.ResourceBundleI18n;
import io.github.cottonmc.jsonfactory.frontend.plugin.Plugin;
import io.github.cottonmc.jsonfactory.frontend.plugin.PluginLoader;
import io.github.cottonmc.jsonfactory.gens.ContentGenerator;
import io.github.cottonmc.jsonfactory.gens.GeneratorInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

public final class SpinningJsonPlugin implements Plugin {
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
        return ImmutableList.of(
                new PlatePressingRecipe(),
                new PlateHammeringRecipe()
        );
    }

    @Override
    public I18n getI18n() {
        return new ResourceBundleI18n("spinning-machinery.i18n", Locale.getDefault());
    }

    @Nullable
    @Override
    public PluginLoader getLoader() {
        return null;
    }

    @NotNull
    @Override
    public Collection<AutoFill> getAutoFills() {
        return ImmutableSet.of(
                new AutoFill("auto_fill.spinning-machinery.standard_metals", "spinning-machinery:copper,spinning-machinery:silver,spinning-machinery:lead,spinning-machinery:zinc,spinning-machinery:aluminum,spinning-machinery:cobalt,spinning-machinery:tin,spinning-machinery:titanium,spinning-machinery:tungsten,spinning-machinery:platinum,spinning-machinery:palladium,spinning-machinery:osmium,spinning-machinery:iridium,spinning-machinery:steel,spinning-machinery:brass,spinning-machinery:electrum,spinning-machinery:iron,spinning-machinery:gold,spinning-machinery:uranium,spinning-machinery:plutonium,spinning-machinery:thorium")
        );
    }
}
