package com.zorbatron.zbgt.api.util;

import net.minecraftforge.fml.common.Loader;

public enum ZBGTMods {

    GCYM(Names.GCYM);

    private final String ID;
    private Boolean modLoaded;

    ZBGTMods(String id) {
        this.ID = id;
    }

    public boolean isModLoaded() {
        if (this.modLoaded == null) {
            this.modLoaded = Loader.isModLoaded(this.ID);
        }
        return this.modLoaded;
    }

    public static class Names {

        public static final String GCYM = "gcym";
    }
}
