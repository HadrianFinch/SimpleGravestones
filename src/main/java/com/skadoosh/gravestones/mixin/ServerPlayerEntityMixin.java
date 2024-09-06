package com.skadoosh.gravestones.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.skadoosh.gravestones.Gravestones;
import com.skadoosh.gravestones.blockentities.GravestoneBlockEntity;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin
{
    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci)
    {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;

        ServerWorld world = (ServerWorld)(player.getWorld());

        BlockPos blockPos = player.getBlockPos();

        world.setBlockState(blockPos, Gravestones.GRAVESTONE_BLOCK.getDefaultState());
        GravestoneBlockEntity gbe = world.getBlockEntity(blockPos, Gravestones.GRAVESTONE_BLOCK_ENTITY_TYPE).orElse(null);
        if (gbe != null)
        {
            gbe.setupFromDeath(player.getInventory(), player.getDisplayName().getString());
        }
    }
}
