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
package com.watabou.pixeldungeon;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public enum Preferences {

	INSTANCE;
	
	public static final String KEY_LANDSCAPE	= "landscape";
	public static final String KEY_IMMERSIVE	= "immersive";
	public static final String KEY_GOOGLE_PLAY	= "googlePlay";
	public static final String KEY_SCALE_UP		= "scaleup";
	public static final String KEY_MUSIC		= "music";
	public static final String KEY_SOUND_FX		= "soundfx";
	public static final String KEY_ZOOM			= "zoom";
	public static final String KEY_LAST_CLASS	= "last_class";
	public static final String KEY_CHALLENGES	= "challenges";
	public static final String KEY_DONATED		= "donated";
	public static final String KEY_INTRO		= "intro";
	public static final String KEY_BRIGHTNESS	= "brightness";
	public static final String KEY_SECOND_QUICKSLOT     = "secondQuickslot";

	public static final String KEY_WINDOW_FULLSCREEN	= "windowFullscreen";
	public static final String KEY_WINDOW_MAXIMIZED     = "windowMaximized";
	public static final String KEY_WINDOW_WIDTH			= "windowWidth";
	public static final String KEY_WINDOW_HEIGHT		= "windowHeight";
	public static final String KEY_WINDOW_POSITION_X    = "windowPositionX";
	public static final String KEY_WINDOW_POSITION_Y    = "windowPositionY";

	public static final int DEFAULT_WINDOW_WIDTH = 960;
	public static final int DEFAULT_WINDOW_HEIGHT = 640;

	public static final String FILE_NAME = "preferences";
	
	private com.badlogic.gdx.Preferences prefs;

	private com.badlogic.gdx.Preferences get() {
		if (prefs == null) {
			prefs = Gdx.app.getType() == Application.ApplicationType.Android ?
					Gdx.app.getPreferences(FILE_NAME) : Gdx.app.getPreferences(FILE_NAME + ".xml");
		}
		return prefs;
	}
	
	public int getInt( String key, int defValue  ) {
		return get().getInteger( key, defValue );
	}
	
	public boolean getBoolean( String key, boolean defValue  ) {
		return get().getBoolean( key, defValue );
	}
	
	public String getString( String key, String defValue  ) {
		return get().getString( key, defValue );
	}

	public void put( String key, int value ) {
		get().putInteger(key, value);
		get().flush();
	}

	public void put( String key, boolean value ) {
		get().putBoolean( key, value );
		get().flush();
	}

	public void put( String key, String value ) {
		get().putString(key, value);
		get().flush();
	}

}
