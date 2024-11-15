package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps.FISHING_PORT_RECIPES;
import static gregtech.api.GTValues.*;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FishingPort {

    private static int circuit = 1;

    protected static void init() {
        // Fish
        ItemStack fish = new ItemStack(Items.FISH, 1, 0);
        ItemStack salmon = new ItemStack(Items.FISH, 1, 1);
        ItemStack tropicalFish = new ItemStack(Items.FISH, 1, 2);
        ItemStack pufferFish = new ItemStack(Items.FISH, 1, 3);

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .outputs(fish)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .outputs(salmon)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .outputs(tropicalFish)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .outputs(pufferFish)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .outputs(fish)
                .outputs(salmon)
                .outputs(tropicalFish)
                .outputs(pufferFish)
                .EUt(VA[LV]).duration(5 * 20 * 4)
                .buildAndRegister();

        // "Treasure"
        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.BOW)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.BOOK)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.FISHING_ROD)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.NAME_TAG)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.SADDLE)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.BOW)
                .output(Items.BOOK)
                .output(Items.FISHING_ROD)
                .output(Items.NAME_TAG)
                .output(Items.SADDLE)
                .EUt(VA[LV]).duration(5 * 20 * 5)
                .buildAndRegister();

        // "Junk"
        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Blocks.WATERLILY)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.BOWL)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.LEATHER)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.LEATHER_BOOTS)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.ROTTEN_FLESH)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.STICK)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.STRING)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        ItemStack waterBottle = new ItemStack(Items.POTIONITEM);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("Potion", "minecraft:water");
        waterBottle.setTagCompound(tag);
        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .outputs(waterBottle)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.BONE)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Items.DYE)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Blocks.TRIPWIRE_HOOK)
                .EUt(VA[LV]).duration(5 * 20)
                .buildAndRegister();

        FISHING_PORT_RECIPES.recipeBuilder()
                .circuitMeta(circuit++)
                .output(Blocks.WATERLILY)
                .output(Items.BOWL)
                .output(Items.LEATHER)
                .output(Items.LEATHER_BOOTS)
                .output(Items.ROTTEN_FLESH)
                .output(Items.STICK)
                .output(Items.STRING)
                .outputs(waterBottle)
                .output(Items.BONE)
                .output(Items.DYE)
                .output(Blocks.TRIPWIRE_HOOK)
                .EUt(VA[LV]).duration(5 * 20 * 11)
                .buildAndRegister();
    }
}
