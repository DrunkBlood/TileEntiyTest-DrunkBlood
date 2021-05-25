package drunkblood.tileentitytest;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestChestScreen extends ContainerScreen<TestChestContainer> {
    private static final ResourceLocation GUI = new ResourceLocation(TileEntityTest.MODID, "textures/gui/testblock_gui.png");

    public TestChestScreen(TestChestContainer container, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(container, playerInventory, iTextComponent);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float v) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, v);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrixStack, float p_230450_2_, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }


}
