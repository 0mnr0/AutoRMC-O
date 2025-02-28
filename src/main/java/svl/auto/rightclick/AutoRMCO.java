package svl.auto.rightclick;

import com.sun.jna.Library;
import com.sun.jna.Native;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class AutoRMCO implements ModInitializer {
	public static final String MOD_ID = "autormc-o";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);



	private static int clickTimeout = 30;

	private static Timer clickTimer;
	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	ScheduledFuture<?> clickTask; // Переменная для отслеживания текущей задачи
	private static Robot robot;
	private static MinecraftClient client = null;
	@Override
	public void onInitialize() {
		System.setProperty("java.awt.headless", "false");

		try {
			robot = new Robot(); // Инициализация Robot для эмуляции кликов мыши
		} catch (AWTException e) {
			e.printStackTrace();
			return; // Завершаем метод, если произошла ошибка
		}

		ClientPlayConnectionEvents.JOIN.register((handler, sender, GameClient) -> {
			LOGGER.info("Player has joined the world!");
			client = GameClient;
			setClickTimeout(clickTimeout);
		});
	}


	// Метод для обновления таймера с новым интервалом
	private static void updateClickTimer() {
		if (clickTimer != null) {
			clickTimer.cancel(); // Останавливаем старый таймер
		}

		clickTimer = new Timer();
		clickTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (isCapsLockActive() && isGameFocused() && client.player != null && client.player.isAlive() && robot != null) {
					robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
					robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
				}
			}
		}, 0, clickTimeout);
	}

	private static void setClickTimeout(int newTimeout) {
		clickTimeout = newTimeout;
		updateClickTimer(); // Перезапускаем таймер с новым интервалом
	}


	private static boolean isGameFocused() {
		return client != null && client.isWindowFocused();
	}


	private interface User32 extends Library {
		User32 INSTANCE = Native.load("user32", User32.class);
		int GetKeyState(int nVirtKey);
	}
	private static boolean isCapsLockActive() {
		// CAPS LOCK имеет виртуальный код 0x14
		return (User32.INSTANCE.GetKeyState(0x14) & 0x0001) != 0;
	}
}