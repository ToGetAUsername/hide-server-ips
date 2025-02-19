package togetausername.hideserverips;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

public class HideServerIps {
	public static boolean shouldShowIps = false;

	public static CyclingButtonWidget<Boolean> button(Screen screen) {
		return CyclingButtonWidget
			.onOffBuilder(shouldShowIps)
			.build(
				5,
				screen.height - 25,
				screen.getTextRenderer().getWidth("  Show IP: OFF  "),
				20,
				Text.of("Show IP"),
				(_btn, value) -> HideServerIps.shouldShowIps = value);
	}
}
