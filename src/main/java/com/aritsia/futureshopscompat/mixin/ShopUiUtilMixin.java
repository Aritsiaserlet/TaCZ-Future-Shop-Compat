package com.aritsia.futureshopscompat.mixin;

import com.enviouse.futureshops.client.screen.ShopUiUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShopUiUtil.class)
public class ShopUiUtilMixin {

    @Inject(method = "renderItemTooltip", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onRenderItemTooltip(GuiGraphics graphics, Font font, String itemId, String nbtJson, int mouseX, int mouseY, CallbackInfo ci) {
        ItemStack stack = ShopUiUtil.buildItemStack(itemId, nbtJson);
        if (!stack.isEmpty()) {
            graphics.renderTooltip(font, stack, mouseX, mouseY);
            ci.cancel();
        }
    }
}
