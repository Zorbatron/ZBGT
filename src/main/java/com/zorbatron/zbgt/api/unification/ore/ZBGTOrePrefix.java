package com.zorbatron.zbgt.api.unification.ore;

import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;

import com.zorbatron.zbgt.api.unification.material.info.ZBGTMaterialFlags;
import com.zorbatron.zbgt.api.unification.material.info.ZBGTMaterialIconType;

import gregtech.api.unification.ore.OrePrefix;

public class ZBGTOrePrefix {

    public static final OrePrefix nanites = new OrePrefix("nanites", -1, null, ZBGTMaterialIconType.nanites,
            ENABLE_UNIFICATION, mat -> mat.hasFlag(ZBGTMaterialFlags.GENERATE_NANITES));
}
