package drunkblood.tileentitytest;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class TestChestBlock extends Block {
    public TestChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new TestChestTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isClientSide){
            // BlockPos{x=-68, y=74, z=11}
            TileEntity tileEntity = world.getBlockEntity(blockPos);
            if(tileEntity instanceof TestChestTileEntity){
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Nullable
                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new TestChestContainer(i, world, blockPos, playerInventory, playerEntity);
                    }

                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen." + TileEntityTest.MODID + "test_chest_block");
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getBlockPos());
            } else {
                throw new IllegalStateException(TestChestBlock.class.getSimpleName() + " named container provider is missing: " + tileEntity.getClass().getSimpleName());
            }
        }
        return ActionResultType.SUCCESS;
    }
}
