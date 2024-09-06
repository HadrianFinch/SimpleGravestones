package com.skadoosh.gravestones.screen;

import com.skadoosh.gravestones.Gravestones;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Hand;

public class GraveTokenScreenHandler extends ScreenHandler
{
    private final ItemStack itemStack;
    private final Hand hand;
    private final SimpleInventory inventory;
    private final int rows = 6;

    public int getRows()
    {
        return rows;
    }

    public GraveTokenScreenHandler(int syncId, PlayerInventory playerInventory)
    {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public GraveTokenScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context)
    {
        super(Gravestones.GRAVE_TOKEN_SCREEN_HANDLER_TYPE, syncId);

        ItemStack stack = null;

        ItemStack mainHand = playerInventory.player.getStackInHand(Hand.MAIN_HAND);
        ItemStack offHand = playerInventory.player.getStackInHand(Hand.OFF_HAND);


        Hand h = null;
        if (mainHand != null && !mainHand.isEmpty())
        {
            stack = mainHand;
            h = Hand.MAIN_HAND;
        }
        else if (offHand != null && !offHand.isEmpty())
        {
            stack = offHand;
            h = Hand.OFF_HAND;
        }
        itemStack = stack;
        hand = h;


        ContainerContentsComponent comp = itemStack.get(DataComponentTypes.CONTAINER);
        inventory = new SimpleInventory(54);
        inventory.addListener(new InventoryChangedListener()
        {
            @Override
            public void onInventoryChanged(Inventory sender)
            {
                ContainerContentsComponent newComp = ContainerContentsComponent.fromStacks(inventory.stacks);
                GraveTokenScreenHandler.this.itemStack.set(DataComponentTypes.CONTAINER, newComp);
            }
        });
        comp.copyTo(inventory.stacks);

        /////////// slots ////////////
        int i = (rows - 4) * 18;

        int j;
        int k;
        for (j = 0; j < rows; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlot(new Slot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18)
                {
                    // NOTE: This is for my item. Remove for general use.
                    @Override
                    public boolean canInsert(ItemStack stack)
                    {
                        return false;
                    }
                });
            }
        }

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + i)
            {
                @Override
                public boolean canTakeItems(PlayerEntity playerEntity)
                {
                    if (this.getStack() == GraveTokenScreenHandler.this.itemStack)
                    {
                        return false;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return true;
    }

    @Override
    public ItemStack quickTransfer(PlayerEntity player, int fromIndex)
    {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(fromIndex);
        if (slot != null && slot.hasStack())
        {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (fromIndex < this.rows * 9)
            {
                if (!this.insertItem(itemStack2, this.rows * 9, this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                return ItemStack.EMPTY;
            }


            if (itemStack2.isEmpty())
            {
                slot.method_53512(ItemStack.EMPTY);
            }
            else
            {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    @Override
    public void close(PlayerEntity player)
    {
        super.close(player);
        if (inventory.isEmpty())
        {
            player.setStackInHand(hand, ItemStack.EMPTY);
        }
    }
}
