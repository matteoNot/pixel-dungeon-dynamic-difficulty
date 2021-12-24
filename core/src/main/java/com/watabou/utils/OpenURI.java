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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.SharedLibraryLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Simple utility class that opens uri using system's default browser.
 */
public class OpenURI {

    /**
     * Opens uri using system's default browser depends on uri string.
     * @param uri uri string
     */
    public static void fromString(String uri) {
        try {
            from(new URI(uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens uri using system's default browser depends on uri object.
     * @param uri uri object
     */
    public static void from(URI uri) {
        if (uri == null) throw new NullPointerException("Uri cannot be null.");
        String baseCommand;
        if (SharedLibraryLoader.isWindows) baseCommand = "start";
        else if (SharedLibraryLoader.isLinux) baseCommand = "xdg-open";
        else if (SharedLibraryLoader.isMac) baseCommand = "open";
        else {
            Gdx.net.openURI(uri.toString());
            return;
        }
        try {
            Runtime.getRuntime().exec(baseCommand + " " + uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
