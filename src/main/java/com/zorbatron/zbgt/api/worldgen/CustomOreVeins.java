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

        JsonObject adamantiumDefinition = parser.parse("""
                {
                  "name": "zbgt.vein.adamantium",
                  "weight": 25,
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
                        "between": "ore:gregtech:naquadah"
                      },
                      {
                        "sporadic": "ore:gregtech:titanium"
                      }
                    ]
                  }
                }
                """).getAsJsonObject();
        OreDepositDefinition adamantiumDeposit = new OreDepositDefinition("adamantium");
        adamantiumDeposit.initializeFromConfig(adamantiumDefinition);

        JsonObject quantiumDefinition = parser.parse("""
                {
                  "name": "zbgt.vein.quantium",
                  "weight": 20,
                  "density": 0.04,
                  "min_height": 10,
                  "max_height": 60,
                  "dimension_filter": [
                    "name:the_end"
                  ],
                  "generator": {
                    "type": "layered",
                    "radius": [
                      16,
                      24
                    ]
                  },
                  "filler": {
                    "type": "layered",
                    "values": [
                      {
                        "primary": "ore:gregtech:niobium"
                      },
                      {
                        "secondary": "ore:zbgt:quantium"
                      },
                      {
                        "between": "ore:gregtech:ytterbium"
                      },
                      {
                        "sporadic": "ore:gregtech:ytterbium"
                      }
                    ]
                  }
                }
                """).getAsJsonObject();
        OreDepositDefinition quantiumDeposit = new OreDepositDefinition("quantium");
        quantiumDeposit.initializeFromConfig(quantiumDefinition);

        WorldGenRegistry worldGenRegistry = WorldGenRegistry.INSTANCE;
        worldGenRegistry.addVeinDefinitions(adamantiumDeposit);
        worldGenRegistry.addVeinDefinitions(quantiumDeposit);

        worldGenRegistry.reinitializeRegisteredVeins();
    }
}
