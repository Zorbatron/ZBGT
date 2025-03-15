package com.zorbatron.zbgt.api.util;

import java.util.Objects;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.Hash;

public interface FluidStackHashStrategy extends Hash.Strategy<FluidStack> {

    static FluidStackHashStrategyBuilder builder() {
        return new FluidStackHashStrategyBuilder();
    }

    class FluidStackHashStrategyBuilder {

        private boolean fluid, amount, tag;

        public FluidStackHashStrategyBuilder compareFluid() {
            fluid = true;
            return this;
        }

        public FluidStackHashStrategyBuilder compareAmount() {
            amount = true;
            return this;
        }

        public FluidStackHashStrategyBuilder compareTag() {
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
