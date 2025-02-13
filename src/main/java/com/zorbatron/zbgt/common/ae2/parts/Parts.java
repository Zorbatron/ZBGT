package com.zorbatron.zbgt.common.ae2.parts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.zorbatron.zbgt.Tags;
import com.zorbatron.zbgt.common.ae2.items.ZBGTAEBaseItem;

import appeng.api.AEApi;
import appeng.api.parts.IPart;
import appeng.core.features.DamagedItemDefinition;
import appeng.util.Platform;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class Parts {

    private final Object2ObjectOpenHashMap<String, DamagedItemDefinition> byId = new Object2ObjectOpenHashMap<>();

    private final ZBGTAEBaseItem itemPart;
    private final DamagedItemDefinition p2pLaser;

    public Parts() {
        itemPart = new ZBGTAEBaseItem();

        var partModels = AEApi.instance().registries().partModels();
        for (var partType : PartType.values()) {
            partModels.registerModels(partType.getModels());
        }

        p2pLaser = createPart(itemPart, PartType.P2P_TUNNEL_GTCEU_LASER);
    }

    @NotNull
    private DamagedItemDefinition createPart(ZBGTAEBaseItem baseItemPart, PartType partType) {
        var def = new DamagedItemDefinition(partType.getId(), baseItemPart.createPart(partType));

        this.byId.put(partType.id, def);
        return def;
    }

    public enum PartType {

        P2P_TUNNEL_GTCEU_LASER("p2p_tunnel_gtceu_laser", PartP2PGTCEuLaserPower.class);

        private final String id;
        private final Class<? extends IPart> clazz;
        private List<ModelResourceLocation> itemModels;
        private final Set<ResourceLocation> models;

        PartType(String id, Class<? extends IPart> clazz) {
            this.id = id;
            this.clazz = clazz;

            if (Platform.isClientInstall()) {
                this.itemModels = this.createItemModels(id);
            }

            this.models = new HashSet<>();
        }

        public String getId() {
            return this.id;
        }

        public Class<? extends IPart> getPart() {
            return this.clazz;
        }

        @SideOnly(Side.CLIENT)
        public List<ModelResourceLocation> getItemModels() {
            return this.itemModels;
        }

        public Set<ResourceLocation> getModels() {
            return this.models;
        }

        @SideOnly(Side.CLIENT)
        private List<ModelResourceLocation> createItemModels(String baseName) {
            return ImmutableList.of(modelFromBaseName(baseName));
        }

        @SideOnly(Side.CLIENT)
        private static ModelResourceLocation modelFromBaseName(String baseName) {
            return new ModelResourceLocation(new ResourceLocation(Tags.MODID, "part/" + baseName), "inventory");
        }
    }
}
