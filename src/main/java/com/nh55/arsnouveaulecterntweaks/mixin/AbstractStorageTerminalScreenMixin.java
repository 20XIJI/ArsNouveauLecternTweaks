package com.nh55.arsnouveaulecterntweaks.mixin;

import com.hollingsworth.arsnouveau.client.gui.NoShadowTextField;
import com.nh55.arsnouveaulecterntweaks.Config;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.lwjgl.glfw.GLFW;
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
 * 3. keyPressed() - Prevents auto-focusing when any key is pressed
 * 4. charTyped() - Prevents auto-focusing when any character is typed
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
     * Prevent auto-focus behavior in keyPressed.
     * IMPORTANT: ESC key (keyCode 256) must pass through to allow closing the GUI.
     * For other keys: returns false early when searchField is not focused,
     * letting other handlers (JEI/REI) process the key.
     */
    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    private void arsNouveauLecternTweaks$onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        // Always allow ESC key to pass through for GUI closing
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            return;
        }

        if (Config.DISABLE_AUTO_FOCUS.get() && !this.searchField.isFocused()) {
            cir.setReturnValue(false);
        }
    }

    /**
     * Prevent auto-focus behavior in charTyped.
     * Returns false early when searchField is not focused, letting other handlers process the character.
     */
    @Inject(method = "charTyped(CI)Z", at = @At("HEAD"), cancellable = true)
    private void arsNouveauLecternTweaks$onCharTyped(char codePoint, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (Config.DISABLE_AUTO_FOCUS.get() && !this.searchField.isFocused()) {
            cir.setReturnValue(false);
        }
    }
}
