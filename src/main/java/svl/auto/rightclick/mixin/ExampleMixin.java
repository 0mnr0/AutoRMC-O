package svl.auto.rightclick.mixin;

import com.sun.jna.Library;
import com.sun.jna.Native;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@Mixin(MinecraftServer.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "loadWorld")



	private void init(CallbackInfo info) {
		// This code is injected into the start of MinecraftServer.loadWorld()V

	}


}
