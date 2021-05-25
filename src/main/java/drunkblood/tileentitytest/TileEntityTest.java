package drunkblood.tileentitytest;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(TileEntityTest.MODID)
public class TileEntityTest {
    public static final String MODID = "tileentitytest";
    public static final String VERSION = "0.0.1";

    private static final DeferredRegister<Item> ITEM =
            DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<Block> BLOCK =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    private static final DeferredRegister<ContainerType<?>> CONTAINER_TYPE =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public TileEntityTest() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEM.register(modEventBus);
        BLOCK.register(modEventBus);
        TILE_ENTITY_TYPE.register(modEventBus);
        CONTAINER_TYPE.register(modEventBus);
    }


    public static final RegistryObject<Block> CHEST_BLOCK = BLOCK.register("test_chest_block",
            () -> new TestChestBlock(AbstractBlock.Properties.of(Material.WOOD)));
    public static final RegistryObject<Item> CHEST_ITEM = ITEM.register("test_chest_block",
            () -> new BlockItem(CHEST_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_FOOD)));
    public static final RegistryObject<TileEntityType<TestChestTileEntity>> CHEST_TILE = TILE_ENTITY_TYPE.register(
            "chest_tile",  () -> TileEntityType.Builder.of(TestChestTileEntity::new, CHEST_BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<TestChestContainer>> CHEST_CONTAINER = CONTAINER_TYPE.register(
            "chest_container",  () -> IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos blockPos = data.readBlockPos();
                World world = inv.player.level;
                return new TestChestContainer(windowId, world, blockPos, inv, inv.player);
            }));
}
