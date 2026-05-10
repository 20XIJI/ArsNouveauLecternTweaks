package com.nh55.arsnouveaulecterntweaks.mixin;

import com.hollingsworth.arsnouveau.client.gui.NoShadowTextField;
import com.nh55.arsnouveaulecterntweaks.Config;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for AbstractStorageTerminalScreen to disable auto-focus on the search field
 * and allow closing the screen with the inventory key when the search field is not focused.
 */
@Mixin(targets = "com.hollingsworth.arsnouveau.client.container.AbstractStorageTerminalScreen")
public abstract class AbstractStorageTerminalScreenMixin extends AbstractContainerScreen {

    @Shadow
    protected NoShadowTextField searchField;

    protected AbstractStorageTerminalScreenMixin() {
        super(null, null, null);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void arsNouveauLecternTweaks$onInit(CallbackInfo ci) {
        if (Config.DISABLE_AUTO_FOCUS.get()) {
            this.clearFocus();
            this.searchField.setFocused(false);
        }
    }

    @Redirect(
        method = "onPacket()V",
        at = @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/client/gui/NoShadowTextField;setFocused(Z)V")
    )
    private void arsNouveauLecternTweaks$redirectSetFocused(NoShadowTextField instance, boolean focused) {
        if (!Config.DISABLE_AUTO_FOCUS.get()) {
            instance.setFocused(focused);
        }
    }

    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    private void arsNouveauLecternTweaks$handleKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
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
