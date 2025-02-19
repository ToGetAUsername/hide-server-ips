package togetausername.hideserverips;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
	private final Screen parent;
	private static boolean showIps = false;

	protected ConfigScreen(Screen parent) {
		super(Text.of("Hide Server IPs configuration menu"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		CyclingButtonWidget<Boolean> onOffButton = CyclingButtonWidget
			.onOffBuilder(false)
			.build(20, 20, 120, 20,
				Text.of("Show IPs"),
				(_btn, value) -> showIps = value);
		ButtonWidget closeButton = ButtonWidget
			.builder(ScreenTexts.DONE, btn -> this.close())
			.dimensions(20, 50, 120, 20)
			.build();

		this.addDrawableChild(onOffButton);
		this.addDrawableChild(closeButton);
	}

	@Override
	public void close() {
		MinecraftClient.getInstance().setScreen(this.parent);
	}

	public static boolean shouldShowIps() { return showIps; }
}
