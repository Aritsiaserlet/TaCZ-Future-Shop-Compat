package com.aritsia.futureshopscompat;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = FutureShopsTaCZCompat.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TaCZTooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof IGun) {
            IGun iGun = (IGun) stack.getItem();
            ResourceLocation gunId = iGun.getGunId(stack);
            Optional<CommonGunIndex> indexOpt = TimelessAPI.getCommonGunIndex(gunId);
            
            if (indexOpt.isPresent()) {
                GunData data = indexOpt.get().getGunData();
                
                int rpm = data.getRoundsPerMinute();
                float damage = data.getBulletData().getDamageAmount();
                int ammo = data.getAmmoAmount();
                
                event.getToolTip().add(Component.literal(""));
                event.getToolTip().add(Component.literal("====== TaCZ Stats ======").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD));
                event.getToolTip().add(Component.literal("\u2694 Damage: ").withStyle(ChatFormatting.GRAY).append(Component.literal(String.valueOf(damage)).withStyle(ChatFormatting.RED)));
                event.getToolTip().add(Component.literal("\u26A1 Fire Rate: ").withStyle(ChatFormatting.GRAY).append(Component.literal(rpm + " RPM").withStyle(ChatFormatting.GOLD)));
                event.getToolTip().add(Component.literal("\uD83D\uDCE6 Ammo: ").withStyle(ChatFormatting.GRAY).append(Component.literal(String.valueOf(ammo)).withStyle(ChatFormatting.AQUA)));
            }
        }
    }
}
