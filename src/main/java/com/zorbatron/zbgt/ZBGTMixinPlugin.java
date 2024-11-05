package com.zorbatron.zbgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import org.jetbrains.annotations.Nullable;

import zone.rong.mixinbooter.ILateMixinLoader;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.Name("ZBGTCore")
@Optional.Interface(iface = "zone.rong.mixinbooter.ILateMixinLoader", modid = "mixinbooter")
public class ZBGTMixinPlugin implements IFMLLoadingPlugin, ILateMixinLoader {

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    @Optional.Method(modid = "mixinbooter")
    public List<String> getMixinConfigs() {
        List<String> mixins = new ArrayList<>();
        mixins.add("mixins.zbgt.json");
        return mixins;
    }
}
