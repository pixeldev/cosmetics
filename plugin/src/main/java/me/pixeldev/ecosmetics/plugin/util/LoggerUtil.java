package me.pixeldev.ecosmetics.plugin.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LoggerUtil {

	private static final Logger logger = Logger.getLogger("electron-cosmetics");

	public static void warn(String message) {
		logger.log(Level.WARNING, message);
	}

	public static void warn(String message, Exception exception) {
		logger.log(Level.WARNING, message, exception);
	}

}
