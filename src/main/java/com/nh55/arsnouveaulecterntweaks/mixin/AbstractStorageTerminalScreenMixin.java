package com.nh55.arsnouveaulecterntweaks.mixin;

import com.hollingsworth.arsnouveau.client.gui.NoShadowTextField;
import com.nh55.arsnouveaulecterntweaks.Config;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for AbstractStorageTerminalScreen to disable auto-focus on the search field.
 *
 * Target class: com.hollingsworth.arsnouveau.client.container.AbstractStorageTerminalScreen
 *
 * This mixin modifies the following behaviors when disableAutoFocus config is enabled:
 * 1. init() - Prevents setting focus to searchField when GUI opens
 * 2. onPacket() - Prevents setting focus when receiving server data
 */
@Mixin(targets = "com.hollingsworth.arsnouveau.client.container.AbstractStorageTerminalScreen")
public abstract class AbstractStorageTerminalScreenMixin extends AbstractContainerScreen {

    @Shadow
    protected NoShadowTextField searchField;

    protected AbstractStorageTerminalScreenMixin() {
        super(null, null, null);
    }

    /**
     * Inject at the end of init() method.
     * If disableAutoFocus is enabled, clear the focus that was set to searchField.
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
     * Ars Nouveau 5.11.3 force-focuses the search field inside keyPressed when it is not focused.
     * Stop that auto-focus path, but keep manual typing working after the user clicks the field.
     */
    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    private void arsNouveauLecternTweaks$preventAutoFocusOnKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
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
}
