# Future Shops ↔ TaCZ Compatibility Bridge

**A Minecraft Forge 1.20.1 Mod that seamlessly integrates Timeless and Classics Zero (TaCZ) weapon statistics into Future Shops and standard tooltips.**

## 📌 Summary
When running **Future Shops** alongside **TaCZ (Timeless and Classics Zero)**, players typically cannot see the stats of guns (like Fire Rate, Damage, or Ammo) inside the shop GUI. This is because TaCZ uses a custom graphical overlay for its stats, while Future Shops forcefully renders tooltips as plain text, bypassing Forge's tooltip rendering events.

This compatibility mod solves that problem by doing two things:
1. **Injecting explicit text-based weapon stats** globally into the tooltip of all TaCZ guns.
2. **Patching Future Shops** via Mixin to properly pass the `ItemStack` during tooltip rendering, ensuring that all UI overlays, background colors, and injected texts render correctly.

Perfect for PvP, SMP, or Shooting-map servers where players need to compare weapon specifications before spending their in-game currency!

---

## ✨ Features

- **Text-Based Gun Stats:** Automatically extracts hidden `GunData` via the `TimelessAPI` and appends them as beautiful, color-coded text lines to the weapon's tooltip.
  - ⚔ **Damage:** Extracted directly from the bullet data.
  - ⚡ **Fire Rate (RPM):** Accurately reflects the gun's rounds-per-minute.
  - 📦 **Ammo:** Shows the maximum magazine capacity.
- **Universal Visibility:** These text stats are visible *everywhere*—in the player's inventory, inside chests, and most importantly, inside the Future Shops GUI.
- **Future Shops UI Fix:** Overrides `ShopUiUtil.renderItemTooltip` using Mixin to restore standard Minecraft tooltip rendering logic, preventing Future Shops from suppressing custom mod tooltips.

---

## 🚀 Installation & Usage

1. Download the latest `.jar` file.
2. Drop it into your `mods/` folder.
3. This mod is primarily **Client-side**, but it is highly recommended to install it on both the **Client and Server** to prevent Forge from displaying Mod Mismatch errors.
4. **Dependencies Required:**
   - [Timeless and Classics Zero (TaCZ)](https://modrinth.com/mod/timeless-and-classics-zero) (1.20.1)
   - [Future Shops](https://modrinth.com/mod/future-shops) (1.20.1)

---

## 🛠 Technical Details (For Developers)
- **Tooltip Event:** Listens to `ItemTooltipEvent` on the `FORGE` bus (`Dist.CLIENT`). Casts the item to `IGun`, queries the `CommonGunIndex` via `TimelessAPI`, and appends `Component.literal` lines.
- **Mixin Target:** `com.enviouse.futureshops.client.screen.ShopUiUtil`
- **Mixin Method:** `renderItemTooltip(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V`
- **Action:** Cancels the original execution which calls `graphics.renderTooltip(font, lines, Optional.empty(), mouseX, mouseY)` and replaces it with `graphics.renderTooltip(font, stack, mouseX, mouseY)`, fully restoring Forge's `RenderTooltipEvent.GatherComponents`.
