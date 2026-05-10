package com.nh55.arsnouveaulecterntweaks.mixin;

import com.hollingsworth.arsnouveau.client.container.StorageTerminalMenu;
import com.hollingsworth.arsnouveau.client.container.StoredItemStack;
import com.hollingsworth.arsnouveau.client.gui.NoShadowTextField;
import com.nh55.arsnouveaulecterntweaks.Config;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Mixin for AbstractStorageTerminalScreen.
 *
 * Target class: com.hollingsworth.arsnouveau.client.container.AbstractStorageTerminalScreen
 *
 * Features:
 * 1. Disable auto-focus on the search field (when disableAutoFocus config is enabled)
 * 2. Custom row count for collapsed/expanded states
 * 3. Close UI on E key when search field is not focused
 */
@Mixin(targets = "com.hollingsworth.arsnouveau.client.container.AbstractStorageTerminalScreen")
public abstract class AbstractStorageTerminalScreenMixin extends AbstractContainerScreen {

    @Shadow
    protected NoShadowTextField searchField;

    @Shadow
    protected int rowCount;

    @Shadow
    protected boolean expanded;

    @Shadow
    private float currentScroll;

    @Shadow
    private List<StoredItemStack> itemsSorted;

    @Shadow
    public abstract void scrollTo(float scrollFraction);

    protected AbstractStorageTerminalScreenMixin() {
        super(null, null, null);
    }

    /**
     * Set rowCount before the vanilla init calls scrollTo().
     */
    @Inject(method = "init()V", at = @At("HEAD"))
    private void arsNouveauLecternTweaks$setConfiguredRowsBeforeInit(CallbackInfo ci) {
        this.arsNouveauLecternTweaks$updateRowCount();
    }

    /**
     * Clear the focus that Ars Nouveau sets to the search field.
     */
    @Inject(method = "init()V", at = @At("TAIL"))
    private void arsNouveauLecternTweaks$onInit(CallbackInfo ci) {
        if (Config.DISABLE_AUTO_FOCUS.get()) {
            this.clearFocus();
            this.searchField.setFocused(false);
        }
    }

    /**
     * Redirect the setFocused(true) call in onPacket() method.
     * If disableAutoFocus is enabled, skip setting focus.
     */
    @Redirect(
        method = "onPacket()V",
        at = @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/client/gui/NoShadowTextField;setFocused(Z)V")
    )
    private void arsNouveauLecternTweaks$redirectSetFocused(NoShadowTextField instance, boolean focused) {
        if (!Config.DISABLE_AUTO_FOCUS.get()) {
            instance.setFocused(focused);
        }
    }

    /**
     * Inject at HEAD of keyPressed() method.
     * 1. Close UI on inventory key (configurable, default E) when search field is not focused.
     * 2. Prevent auto-focus on key press when disableAutoFocus is enabled.
     */
    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    private void arsNouveauLecternTweaks$preventAutoFocusOnKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        // Close UI on inventory key when search field is not focused
        KeyMapping inventoryKey = Minecraft.getInstance().options.keyInventory;
        if (inventoryKey.matches(keyCode, scanCode) && !this.searchField.isFocused()) {
            this.onClose();
            cir.setReturnValue(true);
            return;
        }

        if (!Config.DISABLE_AUTO_FOCUS.get()) {
            return;
        }

        if (keyCode == 256) {
            return;
        }

        if (!this.searchField.isFocused()) {
            cir.setReturnValue(false);
        }
    }

    /**
     * Ars Nouveau 5.11.3 force-focuses and clears the search field inside charTyped.
     * Block that path unless the user already focused the field manually.
     */
    @Inject(method = "charTyped(CI)Z", at = @At("HEAD"), cancellable = true)
    private void arsNouveauLecternTweaks$preventAutoFocusOnCharTyped(char codePoint, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.DISABLE_AUTO_FOCUS.get()) {
            return;
        }

        if (!this.searchField.isFocused()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "onExpandedChanged(Z)V", at = @At("HEAD"))
    private void arsNouveauLecternTweaks$setConfiguredRowsOnExpandedChanged(boolean expanded, CallbackInfo ci) {
        this.expanded = expanded;
        this.arsNouveauLecternTweaks$updateRowCount();
    }

    /**
     * Override scrollTo() to use config-based rowCount instead of hardcoded 3/7.
     * Original uses: visibleRows = expanded ? 7 : 3
     * We use: visibleRows = rowCount (which is already set by our init() mixin)
     */
    @Inject(method = "scrollTo(F)V", at = @At("HEAD"), cancellable = true)
    private void arsNouveauLecternTweaks$overrideScrollTo(float scrollFraction, CallbackInfo ci) {
        int visibleRows = this.rowCount;
        int totalRows = (this.itemsSorted.size() + 9 - 1) / 9;
        int scrollableRows = totalRows - visibleRows;
        int scrollOffset = (int) (scrollFraction * scrollableRows + 0.5d);
        if (scrollOffset < 0) {
            scrollOffset = 0;
        }

        StorageTerminalMenu menu = (StorageTerminalMenu) this.menu;
        for (int row = 0; row < visibleRows; row++) {
            for (int col = 0; col < 9; col++) {
                int slotIndex = col + (row + scrollOffset) * 9;
                if (slotIndex >= 0 && slotIndex < this.itemsSorted.size()) {
                    menu.setSlotContents(col + row * 9, this.itemsSorted.get(slotIndex));
                } else {
                    menu.setSlotContents(col + row * 9, null);
                }
            }
        }
        ci.cancel();
    }

    /**
     * Override mouseScrolled() to use rowCount instead of the vanilla hardcoded
     * scroll denominator.
     */
    @Inject(method = "mouseScrolled(DDDD)Z", at = @At("HEAD"), cancellable = true)
    private void arsNouveauLecternTweaks$overrideMouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY, CallbackInfoReturnable<Boolean> cir) {
        int scrollableRows = (this.itemsSorted.size() + 9 - 1) / 9 - this.rowCount;
        if (scrollableRows <= 0) {
            cir.setReturnValue(false);
            return;
        }

        double scrollDelta = com.hollingsworth.arsnouveau.setup.config.Config.INVERT_LECTERN_SCROLLING.getAsBoolean()
                ? -scrollY
                : scrollY;
        this.currentScroll = Mth.clamp((float) (this.currentScroll + scrollDelta / scrollableRows), 0.0f, 1.0f);
        this.scrollTo(this.currentScroll);
        cir.setReturnValue(true);
    }

    private void arsNouveauLecternTweaks$updateRowCount() {
        this.rowCount = this.expanded ? Config.ROW_COUNT_EXPANDED.get() : Config.ROW_COUNT_COLLAPSED.get();
    }
}
