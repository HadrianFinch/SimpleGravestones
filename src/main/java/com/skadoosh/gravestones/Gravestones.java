package com.skadoosh.gravestones;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skadoosh.gravestones.blockentities.GravestoneBlockEntity;
import com.skadoosh.gravestones.blocks.GravestoneBlock;
import com.skadoosh.gravestones.items.GraveTokenItem;
import com.skadoosh.gravestones.screen.GraveTokenScreenHandler;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.feature_flags.FeatureFlags;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class Gravestones implements ModInitializer
{
    public static final Logger LOGGER = LoggerFactory.getLogger("Simple Gravestones");
    public static final String NAMESPACE = "simple_gravestones";

    public static final Block GRAVESTONE_BLOCK = Registry.register(Registries.BLOCK, id("gravestone"), new GravestoneBlock(AbstractBlock.Settings.copy(Blocks.BEDROCK).nonOpaque()));
    public static final BlockEntityType<GravestoneBlockEntity> GRAVESTONE_BLOCK_ENTITY_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("gravestone"), BlockEntityType.Builder.create(GravestoneBlockEntity::new, GRAVESTONE_BLOCK).build());

    public static final Item GRAVE_TOKEN = Registry.register(Registries.ITEM, id("grave_token"), new GraveTokenItem(new Item.Settings().fireproof().maxCount(1)));

    public static final ScreenHandlerType<GraveTokenScreenHandler> GRAVE_TOKEN_SCREEN_HANDLER_TYPE = Registry.register(Registries.SCREEN_HANDLER_TYPE, id("grave_token_screen_handler"), new ScreenHandlerType<>(GraveTokenScreenHandler::new, FeatureFlags.DEFAULT_SET));

    public static Identifier id(String name)
    {
        return Identifier.of(NAMESPACE, name);
    }

    @Override
    public void onInitialize(ModContainer mod)
    {
        // load constants wooooo!
    }
}
