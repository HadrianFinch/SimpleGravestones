package com.skadoosh.gravestones.blockentities;

import com.skadoosh.gravestones.Gravestones;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.HolderLookup.Provider;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class GravestoneBlockEntity extends BlockEntity implements SimpleInventory
{
    // 54 for double chest size
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(54, ItemStack.EMPTY);

    private static final String PLAYER_NAME = "player_name";
    private String playerName = "Unknown Player";

    public GravestoneBlockEntity(BlockPos pos, BlockState state)
    {
        super(Gravestones.GRAVESTONE_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    protected void method_11014(NbtCompound nbt, Provider lookupProvider)
    {
        super.method_11014(nbt, lookupProvider);
        Inventories.readNbt(nbt, items, lookupProvider);

        playerName = nbt.getString(PLAYER_NAME);
    }
    @Override
    protected void writeNbt(NbtCompound nbt, Provider lookupProvider)
    {
        super.writeNbt(nbt, lookupProvider);
        Inventories.writeNbt(nbt, items, lookupProvider);
        nbt.putString(PLAYER_NAME, playerName);
    }

    @Override
    public DefaultedList<ItemStack> getItems()
    {
        return items;
    }

    public void setupFromDeath(PlayerInventory inventory, String playerName)
    {
        int count = 0;
        for (int i = 0; i < inventory.main.size(); i++)
        {
            this.items.set(count, inventory.main.get(i).copy());
            inventory.main.set(i, ItemStack.EMPTY);

            count++;
        }
        for (int i = 0; i < inventory.armor.size(); i++)
        {
            this.items.set(count, inventory.armor.get(i).copy());
            inventory.armor.set(i, ItemStack.EMPTY);

            count++;
        }
        for (int i = 0; i < inventory.offHand.size(); i++)
        {
            this.items.set(count, inventory.offHand.get(i).copy());
            inventory.offHand.set(i, ItemStack.EMPTY);

            count++;
        }

        this.playerName = playerName;

        this.markDirty();
    }

    public String getPlayerName()
    {
        return this.playerName;
    }
}
