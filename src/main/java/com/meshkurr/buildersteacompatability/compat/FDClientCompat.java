package com.meshkurr.buildersteacompatability.compat;

import com.meshkurr.buildersteacompatability.BuildersTeaCompatibility;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = BuildersTeaCompatibility.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class FDClientCompat {

    // MOD IDs (single source of truth)
    private static final String CREATE_MODID = "create";
    private static final String FD_MODID = "farmersdelight";

    // Your compat mod id (must match mods.toml)
    public static final String MODID = BuildersTeaCompatibility.MODID;

    // Item + predicate identifiers
    private static final ResourceLocation BUILDERS_TEA =
            ResourceLocation.parse(CREATE_MODID + ":builders_tea");

    private static final ResourceLocation FD_STYLE =
            ResourceLocation.parse(MODID + ":fd_style");

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(FDClientCompat::registerModelPredicate);
    }

    private static void registerModelPredicate() {

        boolean hasFD = ModList.get().isLoaded(FD_MODID);

        ItemProperties.register(
                BuiltInRegistries.ITEM.get(BUILDERS_TEA),
                FD_STYLE,
                (stack, level, entity, seed) -> hasFD ? 1.0F : 0.0F
        );
    }
}