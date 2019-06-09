package io.github.cottonmc.spinningmachinery.json;

import com.google.common.collect.ImmutableMap;
import io.github.cottonmc.jsonfactory.data.Identifier;
import io.github.cottonmc.jsonfactory.gens.AbstractContentGenerator;
import io.github.cottonmc.jsonfactory.gens.ResourceRoot;
import io.github.cottonmc.jsonfactory.output.MapJsonOutput;
import io.github.cottonmc.jsonfactory.output.Output;
import io.github.cottonmc.jsonfactory.output.WrappedOutputKt;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

final class PlateHammeringRecipe extends AbstractContentGenerator {
    PlateHammeringRecipe() {
        super("recipe.spinning-machinery.plate_hammering", "recipes/hammering", SpinningJsonPlugin.INFO, "json", ResourceRoot.Data);
    }

    @NotNull
    @Override
    public List<Output> generate(Identifier identifier) {
        return Collections.singletonList(WrappedOutputKt.suffixed(
                new MapJsonOutput(ImmutableMap.of(
                        "type", "spinning-machinery:hammering",
                        "input", new Identifier("c", identifier.getPath() + "_block"),
                        "output", ImmutableMap.of(
                                "item", new Identifier("c", identifier.getPath() + "_plate"),
                                "count", 4
                        )
                )),
                "plate"
        ));
    }
}
