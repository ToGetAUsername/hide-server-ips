package togetausername.hideserverips.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.DirectConnectScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import togetausername.hideserverips.HideServerIps;

@Mixin(DirectConnectScreen.class)
public abstract class DirectConnectScreenMixin extends Screen {
	protected DirectConnectScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "init", at = @At("RETURN"))
	private void addShowIPsButton(CallbackInfo ci) {
		this.addDrawableChild(HideServerIps.button(this));
	}
}
