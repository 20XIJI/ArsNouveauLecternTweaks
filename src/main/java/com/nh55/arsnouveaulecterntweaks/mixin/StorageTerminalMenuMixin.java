package com.nh55.arsnouveaulecterntweaks.mixin;

import com.hollingsworth.arsnouveau.client.container.SlotStorage;
import com.hollingsworth.arsnouveau.client.container.StorageTerminalMenu;
import com.hollingsworth.arsnouveau.common.block.tile.StorageLecternTile;
import com.nh55.arsnouveaulecterntweaks.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Mixin for StorageTerminalMenu to use config-based row count for slot visibility.
 *
 * Target class: com.hollingsworth.arsnouveau.client.container.StorageTerminalMenu
 *
 * The original addStorageSlots() method uses hardcoded row counts to determine
 * which storage slots are visible. This mixin changes it to use configured row
 * counts while staying within the seven storage rows Ars Nouveau creates.
 */
@Mixin(StorageTerminalMenu.class)
public abstract class StorageTerminalMenuMixin {

    @Shadow
    protected List<SlotStorage> storageSlotList;

    @Shadow
    protected StorageLecternTile te;

    /**
     * Override addStorageSlots() to use config-based row count.
     * Always show all slots since we always have crafting grid visible.
     */
    @Inject(method = "addStorageSlots(Z)V", at = @At("HEAD"), cancellable = true)
    private void arsNouveauLecternTweaks$overrideAddStorageSlots(boolean expanded, CallbackInfo ci) {
        int maxRows = 7;
        boolean firstTime = this.storageSlotList.isEmpty();
        int visibleRows = expanded ? Config.ROW_COUNT_EXPANDED.get() : Config.ROW_COUNT_COLLAPSED.get();

        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < 9; col++) {
                int slotIndex = row * 9 + col;
                boolean show = row < visibleRows;
                if (firstTime) {
                    this.storageSlotList.add(new SlotStorage(this.te, slotIndex, 13 + col * 18, 21 + row * 18, show));
                } else {
                    this.storageSlotList.get(slotIndex).show = show;
                }
            }
        }
        ci.cancel();
    }
}
