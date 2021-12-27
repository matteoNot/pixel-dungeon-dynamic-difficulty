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

package com.watabou.pixeldungeon.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.pixeldungeon.Preferences;
import com.watabou.utils.PDPlatformSupport;

public class Lwjgl3PlatformSupport extends PDPlatformSupport {

    public Lwjgl3PlatformSupport(String version, String basePath, NoosaInputProcessor inputProcessor) {
        super(version, basePath, inputProcessor);
    }

    @Override
    public boolean supportFullscreen() {
        return true;
    }

    @Override
    public void fullscreen(boolean value) {
        if (!supportFullscreen()) return;
        final Preferences prefs = Preferences.INSTANCE;
        if (value) {
            prefs.put(Preferences.KEY_WINDOW_FULLSCREEN, true);

            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            prefs.put(Preferences.KEY_WINDOW_FULLSCREEN, false);
            int w = prefs.getInt(Preferences.KEY_WINDOW_WIDTH, Preferences.DEFAULT_WINDOW_WIDTH);
            int h = prefs.getInt(Preferences.KEY_WINDOW_HEIGHT, Preferences.DEFAULT_WINDOW_HEIGHT);
            Gdx.graphics.setWindowedMode(w, h);
        }
    }

    @Override
    public void exit() {
        Gdx.app.exit();
    }

}
