package com.watabou.pixeldungeon.lwjgl3;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.*;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.Preferences;
import com.watabou.utils.PDPlatformSupport;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import java.io.File;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	public static void main(String[] args) {
		SingleInstanceLock.exitIfOtherInstancesRunning(Lwjgl3Launcher.class.getCanonicalName());
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		String appName = Lwjgl3Launcher.class.getPackage().getSpecificationTitle();
		String version = Lwjgl3Launcher.class.getPackage().getSpecificationVersion();
		int versionCode = Integer.parseInt(Lwjgl3Launcher.class.getPackage().getImplementationVersion());
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
		storageRelativePath += "/" + appName + "/";
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setPreferencesConfig(storageBasePath + storageRelativePath, Files.FileType.Absolute);
		// FIXME: This is a hack to get access to the preferences before we have an application setup
		com.badlogic.gdx.Preferences prefs = new Lwjgl3Preferences(
				new Lwjgl3FileHandle(storageBasePath + storageRelativePath + Preferences.FILE_NAME, Files.FileType.Absolute)
		);

		config.setWindowSizeLimits(Preferences.DEFAULT_WINDOW_WIDTH / 2, Preferences.DEFAULT_WINDOW_HEIGHT / 2, -1, -1);

		config.setWindowIcon(Files.FileType.Internal,
				"ic_launcher_128.png", "ic_launcher_32.png", "ic_launcher_16.png");
		boolean isMaximized = prefs.getBoolean(Preferences.KEY_WINDOW_MAXIMIZED, false);
		config.setMaximized(isMaximized);

		int width = prefs.getInteger(Preferences.KEY_WINDOW_WIDTH, Preferences.DEFAULT_WINDOW_WIDTH);
		int height = prefs.getInteger(Preferences.KEY_WINDOW_HEIGHT, Preferences.DEFAULT_WINDOW_HEIGHT);
		config.setWindowedMode(width, height);

		config.setWindowListener(new Lwjgl3WindowListener() {
			@Override
			public void created(Lwjgl3Window window) {
				final Preferences prefs = Preferences.INSTANCE;
				int windowPosX = prefs.getInt(Preferences.KEY_WINDOW_POSITION_X, Integer.MIN_VALUE);
				int windowPosY = prefs.getInt(Preferences.KEY_WINDOW_POSITION_Y, Integer.MIN_VALUE);
				if (windowPosX != Integer.MIN_VALUE && windowPosY != Integer.MIN_VALUE) {
					GLFW.glfwSetWindowPos(window.getWindowHandle(), windowPosX, windowPosY);
				}
				GLFW.glfwSetWindowSizeCallback(window.getWindowHandle(), new GLFWWindowSizeCallback() {
					@Override
					public void invoke(long window, int width, int height) {
						final Preferences prefs = Preferences.INSTANCE;
						if (!PixelDungeon.fullscreen()) prefs.put(Preferences.KEY_WINDOW_MAXIMIZED, PixelDungeon.maximized());

						if (!PixelDungeon.maximized() && !PixelDungeon.fullscreen()) {
							prefs.put(Preferences.KEY_WINDOW_WIDTH, width);
							prefs.put(Preferences.KEY_WINDOW_HEIGHT, height);
						}
					}
				});
				GLFW.glfwSetWindowPosCallback(window.getWindowHandle(), new GLFWWindowPosCallback() {
					@Override
					public void invoke(long window, int xpos, int ypos) {
						final Preferences prefs = Preferences.INSTANCE;
						if (!PixelDungeon.maximized() && !PixelDungeon.fullscreen()) {
							prefs.put(Preferences.KEY_WINDOW_POSITION_X, xpos);
							prefs.put(Preferences.KEY_WINDOW_POSITION_Y, ypos);
						}
					}
				});
			}
			@Override
			public void iconified(boolean isIconified) {}
			@Override
			public void maximized(boolean isMaximized) {}
			@Override
			public void focusLost() {}
			@Override
			public void focusGained() {}
			@Override
			public boolean closeRequested() {
				return true;
			}
			@Override
			public void filesDropped(String[] files) {}
			@Override
			public void refreshRequested() {}
		});
		config.useVsync(true);
		//// Limits FPS to the refresh rate of the currently active monitor.
		config.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
		//// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
		//// useful for testing performance, but can also be very stressful to some hardware.
		//// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

		// TODO: It have to be pulled from build.gradle, but I don't know how it can be done
		config.setTitle(appName);
		return new Lwjgl3Application(new PixelDungeon(
				new Lwjgl3Support(version, storageRelativePath, new Lwjgl3InputProcessor())
		), config);
	}

	private static class Lwjgl3Support extends PDPlatformSupport {
		public Lwjgl3Support( String version, String basePath, NoosaInputProcessor inputProcessor ) {
			super( version, basePath, inputProcessor );
		}
	}

}