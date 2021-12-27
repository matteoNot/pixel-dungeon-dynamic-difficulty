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

package com.watabou.utils;

import com.watabou.input.NoosaInputProcessor;

public class PDPlatformSupport<GameActionType> {
	private final String version;
	private final String basePath;
	private final NoosaInputProcessor<GameActionType> inputProcessor;

	public PDPlatformSupport(String version, String basePath, NoosaInputProcessor<GameActionType> inputProcessor) {
		this.version = version;
		this.basePath = basePath;
		this.inputProcessor = inputProcessor;
	}

	public boolean supportImmersive() {
		return false;
	}

	public boolean supportLandscape() {
		return false;
	}

	public boolean supportFullscreen() {
		return false;
	}

	public void immerse(boolean value) { }

	public void landscape(boolean value) { }

	public void fullscreen(boolean value) { }

	public String getVersion() {
		return version;
	}

	public String getBasePath() {
		return basePath;
	}

	public NoosaInputProcessor<GameActionType> getInputProcessor() {
		return inputProcessor;
	}

	public void exit() { }

}
