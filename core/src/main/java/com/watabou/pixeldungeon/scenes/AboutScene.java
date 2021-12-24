/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.watabou.pixeldungeon.scenes;

import com.badlogic.gdx.Gdx;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.TouchArea;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.effects.Flare;
import com.watabou.pixeldungeon.ui.Archs;
import com.watabou.pixeldungeon.ui.ExitButton;
import com.watabou.pixeldungeon.ui.Icons;
import com.watabou.pixeldungeon.ui.Window;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.utils.OpenURI;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AboutScene extends PixelScene {

	private static final String TXT_INTRO = 
		"Code & graphics: Watabou\n" +
		"LibGDX port: Arcnor, Anyicomplex\n" +
		"Music: Cube_Code\n\n" + 
		"This game is inspired by Brian Walker's Brogue. " +
		"Try it on Windows, Linux or Mac OS X - it's awesome! ;)\n ";
	
	private static final String TXT_SITE = "Official website:";
	private static final String TXT_WIKI = "Official wiki:";
	private static final String TXT_SRC = "Source code:";
	
	private static final String LNK_SITE = "pixeldungeon.watabou.ru";
	private static final String LNK_WIKI = "pixeldungeon.fandom.com";

	private static final String LNK_SRC = "github.com/watabou/pixel-dungeon";
	private static final String LNK_SRC1 = "github.com/Arcnor/pixel-dungeon-gdx";
	private static final String LNK_SRC2 = "github.com/anyicomplex/libgdx-pixel-dungeon";
	
	@Override
	public void create() {
		super.create();
		
		BitmapTextMultiline textIntro = createMultiline( TXT_INTRO, 8 );
		textIntro.maxWidth = Math.min( Camera.main.width, 180 );
		textIntro.measure();
		add( textIntro );
		
		textIntro.x = align( (Camera.main.width - textIntro.width()) / 2 );
		textIntro.y = align( (Camera.main.height - textIntro.height() / 3 * 5) / 2 );

		BitmapTextMultiline textSite = createMultiline( TXT_SITE, 8 );
		textSite.maxWidth = Math.min( Camera.main.width, 180 );
		textSite.measure();
		add( textSite );

		textSite.x = textIntro.x;
		textSite.y = textIntro.y + textIntro.height();
		
		BitmapTextMultiline linkSite = createMultiline( LNK_SITE, 8 );
		linkSite.maxWidth = Math.min( Camera.main.width, 180 );
		linkSite.measure();
		linkSite.hardlight( Window.TITLE_COLOR );
		add( linkSite );
		
		linkSite.x = textSite.x;
		linkSite.y = textSite.y + textSite.height();

		TouchArea hotAreaSite = new TouchArea( linkSite ) {
			@Override
			protected void onClick( NoosaInputProcessor.Touch touch ) {
				OpenURI.fromString("https://" + LNK_SITE);
			}
		};
		add( hotAreaSite );

		BitmapTextMultiline textWiki = createMultiline( TXT_WIKI, 8 );
		textWiki.maxWidth = Math.min( Camera.main.width, 180 );
		textWiki.measure();
		add( textWiki );

		textWiki.x = linkSite.x;
		textWiki.y = linkSite.y + linkSite.height();

		BitmapTextMultiline linkWiki = createMultiline( LNK_WIKI, 8 );
		linkWiki.maxWidth = Math.min( Camera.main.width, 180 );
		linkWiki.measure();
		linkWiki.hardlight( Window.TITLE_COLOR );
		add( linkWiki );

		linkWiki.x = textWiki.x;
		linkWiki.y = textWiki.y + textWiki.height();

		TouchArea hotAreaWiki = new TouchArea( linkWiki ) {
			@Override
			protected void onClick( NoosaInputProcessor.Touch touch ) {
				OpenURI.fromString("https://" + LNK_WIKI);
			}
		};
		add( hotAreaWiki );

		BitmapTextMultiline textSrc = createMultiline( TXT_SRC, 8 );
		textSrc.maxWidth = Math.min( Camera.main.width, 180 );
		textSrc.measure();
		add( textSrc );

		textSrc.x = linkWiki.x;
		textSrc.y = linkWiki.y + linkWiki.height();

		BitmapTextMultiline linkSrc = createMultiline( LNK_SRC, 8 );
		linkSrc.maxWidth = Math.min( Camera.main.width, 180 );
		linkSrc.measure();
		linkSrc.hardlight( Window.TITLE_COLOR );
		add( linkSrc );

		linkSrc.x = textSrc.x;
		linkSrc.y = textSrc.y + textSrc.height();

		TouchArea hotAreaSrc = new TouchArea( linkSrc ) {
			@Override
			protected void onClick( NoosaInputProcessor.Touch touch ) {
				OpenURI.fromString("https://" + LNK_SRC);
			}
		};
		add( hotAreaSrc );

		BitmapTextMultiline linkSrc1 = createMultiline( LNK_SRC1, 8 );
		linkSrc1.maxWidth = Math.min( Camera.main.width, 180 );
		linkSrc1.measure();
		linkSrc1.hardlight( Window.TITLE_COLOR );
		add( linkSrc1 );

		linkSrc1.x = linkSrc.x;
		linkSrc1.y = linkSrc.y + linkSrc.height();

		TouchArea hotAreaSrc1 = new TouchArea( linkSrc1 ) {
			@Override
			protected void onClick( NoosaInputProcessor.Touch touch ) {
				OpenURI.fromString("https://" + LNK_SRC1);
			}
		};
		add( hotAreaSrc1 );

		BitmapTextMultiline linkSrc2 = createMultiline( LNK_SRC2, 8 );
		linkSrc2.maxWidth = Math.min( Camera.main.width, 180 );
		linkSrc2.measure();
		linkSrc2.hardlight( Window.TITLE_COLOR );
		add( linkSrc2 );

		linkSrc2.x = linkSrc1.x;
		linkSrc2.y = linkSrc1.y + linkSrc1.height();

		TouchArea hotAreaSrc2 = new TouchArea( linkSrc2 ) {
			@Override
			protected void onClick( NoosaInputProcessor.Touch touch ) {
				OpenURI.fromString("https://" + LNK_SRC2);
			}
		};
		add( hotAreaSrc2 );
		
		Image wata = Icons.WATA.get();
		wata.x = align( (Camera.main.width - wata.width) / 2 );
		wata.y = textIntro.y - wata.height - 8;
		add( wata );
		
		new Flare( 7, 64 ).color( 0x112233, true ).show( wata, 0 ).angularSpeed = +20;
		
		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );
		
		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		PixelDungeon.switchNoFade( TitleScene.class );
	}
}
