package drunkblood.tileentitytest;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TestChestContainer extends Container{

    private final TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

    protected TestChestContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(TileEntityTest.CHEST_CONTAINER.get(), windowId);
        playerEntity = player;
        tileEntity = world.getBlockEntity(pos);
        this.playerInventory = new InvWrapper(playerInventory);

        if(tileEntity != null){
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 64, 64));
            });
        }

        layoutPlayerInventorySlots(10, 70);
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return Container.stillValid(IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()),
                playerEntity, TileEntityTest.CHEST_BLOCK.get());
    }


    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
