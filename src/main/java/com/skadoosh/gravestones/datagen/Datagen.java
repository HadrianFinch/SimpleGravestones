package com.skadoosh.gravestones.datagen;

import java.util.concurrent.CompletableFuture;

import com.skadoosh.gravestones.Gravestones;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.HolderLookup.Provider;

public class Datagen implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        final FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(EnglishLanguageProvider::new);
    }

    private static class EnglishLanguageProvider extends FabricLanguageProvider
    {
        protected EnglishLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<Provider> registryLookuper)
        {
            super(dataOutput, "en_us", registryLookuper);
        }

        @Override
        public void generateTranslations(Provider registryLookup, TranslationBuilder translationBuilder)
        {
            translationBuilder.add(Gravestones.GRAVE_TOKEN, "Grave Token");
            translationBuilder.add(Gravestones.GRAVESTONE_BLOCK, "Gravestone");
        }
    }
}
