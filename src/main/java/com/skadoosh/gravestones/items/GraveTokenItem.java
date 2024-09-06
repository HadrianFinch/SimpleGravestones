package com.skadoosh.gravestones.items;

import com.skadoosh.gravestones.screen.GraveTokenScreenHandler;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GraveTokenItem extends Item
{
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        user.openHandledScreen(createScreenHandlerFactory(world, user.getBlockPos(), user.getStackInHand(hand)));
        // return redeem(world, user, hand) ? TypedActionResult.consume(user.getStackInHand(hand)) : 
        return TypedActionResult.success(user.getStackInHand(hand));
    }
    
    public GraveTokenItem(Settings settings)
    {
        super(settings);
    }
    
    // optional alternative method of getting the items
    private static boolean redeemAndDropItems(World world, PlayerEntity user, Hand hand)
    {
        if (world.isClient)
        {
            return false;
        }
        else
        {
            final var comp = user.getStackInHand(hand).get(DataComponentTypes.CONTAINER);

            final var inven = new SimpleInventory(54);
            comp.copyTo(inven.stacks);

            // Behavior here can be changed
            // shuch as to show an inventory etc in the future
            for (int i = 0; i < inven.stacks.size(); i++)
            {
                user.dropItem(inven.stacks.get(i), true, false);
            }

            user.setStackInHand(hand, ItemStack.EMPTY);

            return true;
        }
    }

    protected NamedScreenHandlerFactory createScreenHandlerFactory(World world, BlockPos pos, ItemStack stack)
    {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, player) -> {
            return new GraveTokenScreenHandler(i, playerInventory, ScreenHandlerContext.create(world, pos));
        }, stack.getName());
    }
}
