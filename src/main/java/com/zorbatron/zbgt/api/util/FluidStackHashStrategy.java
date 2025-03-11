package com.zorbatron.zbgt.api.util;

import java.util.Objects;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.Hash;

public interface FluidStackHashStrategy extends Hash.Strategy<FluidStack> {

    FluidStackHashStrategy compareFluid = builder()
            .compareFluid()
            .build();

    static Builder builder() {
        return new Builder();
    }

    class Builder {

        private boolean fluid, amount, tag;

        public Builder compareFluid() {
            fluid = true;
            return this;
        }

        public Builder compareAmount() {
            amount = true;
            return this;
        }

        public Builder compareTag() {
            tag = true;
            return this;
        }

        public FluidStackHashStrategy build() {
            return new FluidStackHashStrategy() {

                @Override
                public int hashCode(FluidStack o) {
                    return o == null ? 0 : Objects.hash(
                            fluid ? o.getFluid() : null,
                            amount ? o.amount : null,
                            tag ? o.tag : null);
                }

                @Override
                public boolean equals(@Nullable FluidStack a, @Nullable FluidStack b) {
                    if (a == null || b == null) return false;

                    return (!fluid || a.getFluid() == b.getFluid()) &&
                            (!amount || a.amount == b.amount) &&
                            (!tag || Objects.equals(a.tag, b.tag));
                }
            };
        }
    }
}
