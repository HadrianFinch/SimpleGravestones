package com.skadoosh.gravestones;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import com.skadoosh.gravestones.screen.GraveTokenScreen;

import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ClientLoader implements ClientModInitializer
{
    @Override
    public void onInitializeClient(ModContainer mod)
    {
        HandledScreens.register(Gravestones.GRAVE_TOKEN_SCREEN_HANDLER_TYPE, GraveTokenScreen::new);
    }
}
