package com.zorbatron.zbgt.common.ae2.items;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.Nullable;

import com.google.common.base.Preconditions;
import com.zorbatron.zbgt.common.ae2.parts.Parts;

import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import appeng.core.features.ActivityState;
import appeng.core.features.ItemStackSrc;
import appeng.items.AEBaseItem;

public class ZBGTAEBaseItem extends AEBaseItem implements IPartItem<IPart> {

    @Override
    public @Nullable IPart createPartFromItemStack(ItemStack itemStack) {
        return null;
    }

    @Nonnull
    public final ItemStackSrc createPart(Parts.PartType mat) {
        Preconditions.checkNotNull(mat);
        return this.createPart(mat, 0);
    }

    public ItemStackSrc createPart(Parts.PartType partType, int varID) {
        assert partType != null;
        assert varID >= 0;

        // verify
        for (final var p : this.registered.values()) {
            if (p.part == partType && p.variant == varID) {
                throw new IllegalStateException("Cannot create the same material twice...");
            }
        }

        var enabled = partType.isEnabled();

        final var partDamage = partType.getBaseDamage() + varID;
        final var state = ActivityState.from(enabled);
        final var output = new ItemStackSrc(this, partDamage, state);

        final var pti = new PartTypeWithVariant(partType, varID);

        this.processMetaOverlap(enabled, partDamage, partType, pti);

        return output;
    }
}
