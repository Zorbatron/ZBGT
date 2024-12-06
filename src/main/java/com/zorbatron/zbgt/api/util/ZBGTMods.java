package com.zorbatron.zbgt.api.util;

import net.minecraftforge.fml.common.Loader;

public enum ZBGTMods {

    GCYM(Names.GCYM),
    ULV_COVERS(Names.ULV_COVERS),
    NOMI_LABS(Names.NOMI_LABS);

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
        public static final String ULV_COVERS = "ulv_covers";
        public static final String NOMI_LABS = "nomilabs";
    }
}
