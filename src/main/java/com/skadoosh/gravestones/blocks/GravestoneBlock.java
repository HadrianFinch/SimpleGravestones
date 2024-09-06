package com.skadoosh.gravestones.blocks;

import com.skadoosh.gravestones.Gravestones;
import com.skadoosh.gravestones.blockentities.GravestoneBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerContentsComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GravestoneBlock extends Block implements BlockEntityProvider
{
    public GravestoneBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new GravestoneBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity entity, BlockHitResult hitResult)
    {
        GravestoneBlockEntity gbe = world.getBlockEntity(pos, Gravestones.GRAVESTONE_BLOCK_ENTITY_TYPE).orElse(null);
        if (gbe != null)
        {
            // NOTE: you can add custom logic here to check if it is allowed to be opened
            boolean canOpen = true;

            if (canOpen)
            {
                ItemStack tokenStack = new ItemStack(Gravestones.GRAVE_TOKEN, 1);
                tokenStack.set(DataComponentTypes.CONTAINER, ContainerContentsComponent.fromStacks(gbe.getItems()));
                tokenStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(gbe.getPlayerName() + "'s Grave Token").setStyle(Style.EMPTY.withItalic(false).withBold(false)));

                if (!world.isClient)
                {
                    ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, tokenStack);
                    itemEntity.setPickupDelay(40);

                    world.spawnEntity(itemEntity);

                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }

                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }

    @Override
    protected boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos)
    {
        return false;
    }
}
