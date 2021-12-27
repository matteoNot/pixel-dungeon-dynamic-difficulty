/*
 * Copyright (c) 2012-2015 Oleg Dolya
 *
 * Copyright (c) 2014 Edu Garcia
 *
 * Copyright (c) 2021 Yi An
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.pixeldungeon.android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import com.badlogic.gdx.Gdx;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.pixeldungeon.Preferences;
import com.watabou.utils.PDPlatformSupport;

public class AndroidPlatformSupport extends PDPlatformSupport {

    private final Activity activity;

    public AndroidPlatformSupport(String version, String basePath, NoosaInputProcessor inputProcessor, Activity activity) {
        super(version, basePath, inputProcessor);
        this.activity = activity;
    }

    @Override
    public boolean supportImmersive() {
        return Build.VERSION.SDK_INT >= 19;
    }

    @Override
    public void immerse(boolean value) {
        if (!supportImmersive()) return;
        Preferences.INSTANCE.put( Preferences.KEY_IMMERSIVE, value );

        activity.runOnUiThread( new Runnable() {
            @Override
            public void run() {
                AndroidLauncher.updateImmersiveMode();
            }
        } );
    }

    @Override
    public boolean supportLandscape() {
        return true;
    }

    @Override
    public void landscape(boolean value) {
        if (!supportLandscape()) return;
        activity.setRequestedOrientation( value ?
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        Preferences.INSTANCE.put( Preferences.KEY_LANDSCAPE, value );
    }

    @Override
    public void exit() {
        Gdx.app.exit();
        activity.finish();
        System.exit(0);
    }

}
