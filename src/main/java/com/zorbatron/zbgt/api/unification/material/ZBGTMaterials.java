package com.zorbatron.zbgt.api.unification.material;

import com.zorbatron.zbgt.api.unification.material.materials.*;
import com.zorbatron.zbgt.api.unification.material.modifications.ZBGTMaterialExtraFlags;
import com.zorbatron.zbgt.api.unification.material.modifications.ZBGTMaterialExtraProperties;
import com.zorbatron.zbgt.api.unification.material.ore.ZBGTOrePrefix;

import gregtech.api.unification.material.Material;
import gregtech.common.items.MetaItems;

public final class ZBGTMaterials {

    // Elements
    public static Material Adamantium;
    public static Material Quantium;

    // First degree materials
    public static Material Indalloy140;
    public static Material MAR_M200;
    public static Material TanmolyiumBetaC;
    public static Material AdamantiumAlloy;
    public static Material Artherium_Sn;
    public static Material PreciousMetalsAlloy;
    public static Material Alumina;
    public static Material AluminumNitride;
    public static Material YttriumOxide;
    public static Material Luminessence;
    public static Material EglinSteelBase;
    public static Material Inconel792;
    public static Material SnowPowder;
    public static Material Grisium;
    public static Material Nitinol60;
    public static Material HastelloyN;
    public static Material HastelloyW;
    public static Material MaragingSteel250;
    public static Material WoodsGlass;
    public static Material Tumbaga;
    public static Material Tantalloy60;
    public static Material Inconel625;
    public static Material Inconel690;
    public static Material NiobiumCarbide;
    public static Material IncoloyDS;
    public static Material Staballoy;
    public static Material Talonite;
    public static Material Incoloy020;
    public static Material ZirconiumCarbide;
    public static Material MaragingSteel350;
    public static Material TriniumNaquadahAlloy;
    public static Material BabbitAlloy;
    public static Material Prasiolite;
    public static Material Dibismuthhydroborat;
    public static Material CubicZirconia;
    public static Material BismuthTelluride;
    public static Material RedZircon;
    public static Material Fayalite;
    public static Material GreenFuchsite;
    public static Material RedFuchsite;

    // Second degree materials
    public static Material MAR_CE_M200;
    public static Material HDCS;
    public static Material Dalisenite;
    public static Material EglinSteel;
    public static Material Tantalloy61;
    public static Material TriniumNaquadahCarbonite;
    public static Material CircuitCompoundMk_3;
    public static Material MagnetoResonance;

    // Third degree materials
    public static Material Hikarium;
    public static Material Pikyonium64b;

    // Nomifactory materials
    public static Material Ardite;
    public static Material Manyullyn;
    public static Material Signalum;
    public static Material Lumium;
    public static Material Enderium;
    public static Material FluxedElectrum;

    public static Material EnergeticAlloy;
    public static Material VibrantAlloy;

    public static Material Blizz;
    public static Material Pyrotheum;
    public static Material Cryotheum;

    // Unknown Composition Materials
    public static Material SpecialCeramics;
    public static Material LowGradeCoolant;
    public static Material HotLowGradeCoolant;
    public static Material SluiceJuice;
    public static Material SluiceSand;
    public static Material ChronomaticGlass;

    public static void init() {
        MetaItems.addOrePrefix(ZBGTOrePrefix.nanites);

        ZBGTElementMaterials.register();
        ZBGTFirstDegreeMaterials.register();
        ZBGTSecondDegreeMaterials.register();
        ZBGTThirdDegreeMaterials.register();
        ZBGTUnknownCompositionMaterials.register();
    }

    public static void initChanges() {
        ZBGTMaterialExtraProperties.init();
        ZBGTMaterialExtraFlags.init();
    }

    public static void initLateChanges() {
        ZBGTMaterialExtraProperties.initLate();
    }
}
