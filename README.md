# ZBGT

Addon mod for [GregTech CEu](https://github.com/GregTechCEu/GregTech)

## Features:

Creative Energy Source
- Configurable like the creative emitter, but as a multiblock part.
- You need to make the multiblock reform when you change its setting because right now because GregTech doesn't have a way for an energy container to notify the multiblock controller that its voltage/amperage has changed.

![](images/creative_energy_source.png)

Creative Energy Sink
- Behaves the same as the Creative Energy Hatch but drains energy from the multiblock instead of providing.
- Same UI as the Creative Energy Source

Creative Item Bus
- An input bus that can be set to any 16 items and provides them to the connected multiblock

![](images/creative_item_bus.png)

Creative Fluid Hatch
- Same as the normal reservoir hatch but can be changed to provide other fluids.

![](images/creative_fluid_hatch.png)

Creative Computation Provider
- Acts as an HPCA that can supply up to 2,147,483,647 CWU/t!
- Backported from GregTech Modern, and will be moved into base CEu once M-W-K finishes their PipeNet rewrite.

![](images/creative_computation_provider.png)

Dual Cover
- Combines the conveyor and pump into one cover!
- Currently has no texture because I'm not an artist <:(

![](images/dual_cover_item.png)
![](images/dual_cover_fluid.png)

Precise Dual Cover
- Combines the robot arm and fluid regulator into one cover!
- Also has no texture 😔

![](images/precise_dual_cover_item.png)
![](images/precise_dual_cover_fluid.png)

Air Intake Hatch
- Ported from GT++ but it collects dimensions specific gas now!
- Is a Gas Collector that passively generates air.

Single Item Input Bus
- Functionally identical to a ULV input bus, but it has a max stack size of 1.

## Planned:

Multi Creative Reservoir Hatch
- Will be a creative reservoir but it will have multiple slots.
- Will likely have the UI of the ME input hatch.
- May replace the creative reservoir hatch.
