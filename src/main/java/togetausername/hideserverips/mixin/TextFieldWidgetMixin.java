package togetausername.hideserverips.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.AddServerScreen;
import net.minecraft.client.gui.screen.multiplayer.DirectConnectScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import togetausername.hideserverips.HideServerIps;

@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin {
	@ModifyExpressionValue(
		method = "renderWidget",
		at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;text:Ljava/lang/String;")
	)
	private String censorText(String original) {
		Screen screen = MinecraftClient.getInstance().currentScreen;
		if(((TextFieldWidget) (Object) this).getMessage().getContent() instanceof TranslatableTextContent content
			&& content.getKey().equals("addServer.enterIp")
			&& !HideServerIps.shouldShowIps
			&& (screen instanceof AddServerScreen || screen instanceof DirectConnectScreen))
				return "*".repeat(original.length());

		return original;
	}
}
