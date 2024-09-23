package com.zorbatron.zbgt.mixin;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.google.gson.JsonObject;

import gregtech.api.util.FileUtility;
import gregtech.api.util.GTLog;
import gregtech.api.worldgen.config.BedrockFluidDepositDefinition;
import gregtech.api.worldgen.config.IWorldgenDefinition;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.config.WorldGenRegistry;

@Mixin(value = WorldGenRegistry.class, remap = false)
public class WorldGenRegistryMixin {

    // @Inject(at = @At("HEAD"), method = "addAddonFiles", cancellable = true)

    /**
     * @author Zorbatron
     * @reason The ore gen code is icky
     */
    @Overwrite
    private static <T extends IWorldgenDefinition> void addAddonFiles(Path root, @NotNull List<T> definitions,
                                                                      @NotNull List<T> registeredDefinitions) {
        // ConcurrentModificationException!!!!!!!
        List<T> itemsToRemove = new ArrayList<>();

        for (T definition : definitions) {
            if (definition instanceof OreDepositDefinition oreDepositDefinition) {
                // If the weight isn't set, then it's definitely not set, so initialize it
                if (oreDepositDefinition.getAssignedName() == null) {
                    JsonObject element = FileUtility
                            .tryExtractFromFile(
                                    root.resolve(FileUtility.slashToNativeSep(definition.getDepositName())));

                    if (element == null) {
                        GTLog.logger.error("Addon mod tried to register bad ore definition at {}",
                                definition.getDepositName());
                        itemsToRemove.add(definition);
                        continue;
                    }

                    definition.initializeFromConfig(element);
                }
            } else if (definition instanceof BedrockFluidDepositDefinition bedrockFluidDepositDefinition) {
                // If the weight isn't set, then it's definitely not set, so initialize it
                if (bedrockFluidDepositDefinition.getAssignedName() == null) {
                    JsonObject element = FileUtility
                            .tryExtractFromFile(
                                    root.resolve(FileUtility.slashToNativeSep(definition.getDepositName())));

                    if (element == null) {
                        GTLog.logger.error("Addon mod tried to register bad ore definition at {}",
                                definition.getDepositName());
                        itemsToRemove.add(definition);
                        continue;
                    }

                    definition.initializeFromConfig(element);
                }
            }

            try {
                registeredDefinitions.add(definition);
            } catch (RuntimeException exception) {
                GTLog.logger.error("Failed to parse addon worldgen definition {}",
                        definition.getDepositName(), exception);
            }
        }

        definitions.removeAll(itemsToRemove);
    }
}
