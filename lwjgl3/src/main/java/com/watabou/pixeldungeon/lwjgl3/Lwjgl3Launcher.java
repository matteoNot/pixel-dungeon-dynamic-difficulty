package com.watabou.pixeldungeon.lwjgl3;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Preferences;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.Preferences;
import com.watabou.utils.PDPlatformSupport;

import java.io.File;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		String version = Lwjgl3Launcher.class.getPackage().getSpecificationVersion();
		if (version == null) {
			version = "???";
		}
		String storageBasePath;
		String storageRelativePath = "";
		if (SharedLibraryLoader.isWindows) {
			File appDataDir = new File(System.getenv("APPDATA"));
			storageBasePath = appDataDir.getParent();
			storageRelativePath = appDataDir.getName();
			storageRelativePath += "/Watabou";
		}
		else {
			storageBasePath = System.getProperty("user.home");
		}
		if (!storageBasePath.endsWith("/")) storageBasePath += "/";
		if (SharedLibraryLoader.isLinux) {
			storageRelativePath = ".local/share/watabou";
		}
		if (SharedLibraryLoader.isMac) {
			storageRelativePath = "Library/Application Support/Watabou";
		}
		storageRelativePath += "/" + "Pixel Dungeon" + "/";
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();

		configuration.setPreferencesConfig(storageRelativePath, Files.FileType.External);
		// FIXME: This is a hack to get access to the preferences before we have an application setup
		com.badlogic.gdx.Preferences prefs = new Lwjgl3Preferences(Preferences.FILE_NAME, storageBasePath + storageRelativePath);
		boolean isFullscreen = prefs.getBoolean(Preferences.KEY_WINDOW_FULLSCREEN, false);

		configuration.setWindowSizeLimits(Preferences.DEFAULT_WINDOW_WIDTH, Preferences.DEFAULT_WINDOW_HEIGHT, -1, -1);
		if (isFullscreen) {

		}
		else {
			int width = prefs.getInteger(Preferences.KEY_WINDOW_WIDTH, Preferences.DEFAULT_WINDOW_WIDTH);
			int height = prefs.getInteger(Preferences.KEY_WINDOW_HEIGHT, Preferences.DEFAULT_WINDOW_HEIGHT);
			configuration.setWindowedMode(width, height);
		}
		configuration.setWindowIcon(Files.FileType.Internal,
				"ic_launcher_128.png", "ic_launcher_32.png", "ic_launcher_16.png");
		configuration.useVsync(true);
		//// Limits FPS to the refresh rate of the currently active monitor.
		configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
		//// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
		//// useful for testing performance, but can also be very stressful to some hardware.
		//// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

		// TODO: It have to be pulled from build.gradle, but I don't know how it can be done
		configuration.setTitle("Pixel Dungeon");
		return new Lwjgl3Application(new PixelDungeon(
				new Lwjgl3Support(version, storageRelativePath, new Lwjgl3InputProcessor())
		), configuration);
	}

	private static class Lwjgl3Support extends PDPlatformSupport {
		public Lwjgl3Support( String version, String basePath, NoosaInputProcessor inputProcessor ) {
			super( version, basePath, inputProcessor );
		}
	}
}