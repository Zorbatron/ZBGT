package com.zorbatron.zbgt.api.items.stats;

import com.github.bsideup.jabel.Desugar;

import gregtech.api.items.metaitem.stats.IItemComponent;

@Desugar
public record ItemColorProvider(int color) implements IItemComponent {

}
