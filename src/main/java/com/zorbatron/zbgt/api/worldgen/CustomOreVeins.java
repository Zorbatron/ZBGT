package com.zorbatron.zbgt.api.worldgen;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zorbatron.zbgt.api.util.ZBGTLog;

import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.config.WorldGenRegistry;

public class CustomOreVeins {

    public static void init() throws IOException {
        ZBGTLog.logger.info("Registering ore veins...");

        JsonParser parser = new JsonParser();
        JsonObject definition = parser.parse("""
                {
                  "name": "zbgt.vein.adamantium",
                  "weight": 40,
                  "density": 0.05,
                  "min_height": 10,
                  "max_height": 80,
                  "dimension_filter": [
                    "name:the_end"
                  ],
                  "generator": {
                    "type": "layered",
                    "radius": [
                      20,
                      24
                    ]
                  },
                  "filler": {
                    "type": "layered",
                    "values": [
                      {
                        "primary": "ore:zbgt:adamantium"
                      },
                      {
                        "secondary": "ore:zbgt:adamantium"
                      },
                      {
                        "between": "ore:zbgt:adamantium"
                      },
                      {
                        "sporadic": "ore:zbgt:adamantium"
                      }
                    ]
                  }
                }
                """).getAsJsonObject();

        OreDepositDefinition depositDefinition = new OreDepositDefinition("adamantium");
        depositDefinition.initializeFromConfig(definition);

        WorldGenRegistry worldGenRegistry = WorldGenRegistry.INSTANCE;
        worldGenRegistry.addVeinDefinitions(depositDefinition);

        worldGenRegistry.reinitializeRegisteredVeins();
    }
}
