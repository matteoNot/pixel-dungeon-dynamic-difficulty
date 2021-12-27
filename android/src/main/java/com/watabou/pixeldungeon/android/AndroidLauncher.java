package com.watabou.pixeldungeon.android;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.watabou.pixeldungeon.PixelDungeon;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {

	private static AndroidLauncher instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		updateImmersiveMode();

		instance = this;
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		String version;
		try {
			version = getPackageManager().getPackageInfo( getPackageName(), 0 ).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			version = "???";
		}
		initialize(new PixelDungeon(new AndroidPlatformSupport(version, null, new AndroidInputProcessor(), this)), config);
	}

	@SuppressLint("NewApi")
	public static void updateImmersiveMode() {
		if (Build.VERSION.SDK_INT >= 19) {
			try {
				// Sometime NullPointerException happens here
				instance.getWindow().getDecorView().setSystemUiVisibility(
						PixelDungeon.immersed() ?
								View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
										View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
										View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
										View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
										View.SYSTEM_UI_FLAG_FULLSCREEN |
										View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
								:
								0 );
			} catch (Exception ignored) {

			}
		}
	}

	@Override
	public void onWindowFocusChanged( boolean hasFocus ) {

		super.onWindowFocusChanged( hasFocus );

		if (hasFocus) {
			updateImmersiveMode();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

}