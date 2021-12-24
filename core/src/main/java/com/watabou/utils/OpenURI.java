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
