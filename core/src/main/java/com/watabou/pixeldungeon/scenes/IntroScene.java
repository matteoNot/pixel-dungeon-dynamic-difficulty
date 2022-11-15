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
package com.watabou.pixeldungeon.scenes;

import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.GamesInProgress;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.pixeldungeon.windows.WndOptions;
import com.watabou.pixeldungeon.windows.WndStory;

public class IntroScene extends PixelScene {

	private static final float GAP				= 2;
	private static final float BUTTON_HEIGHT	= 24;

	private static final float WIDTH_P	= 116;
	private static final float HEIGHT_P	= 220;

	private static final float WIDTH_L	= 224;
	private static final float HEIGHT_L	= 124;

	private static final String TXT_YES		= "Dynamic difficulty";
	private static final String TXT_NO		= "Normal version";
	private static final String TEXT = 	
		"This game is a modified version of pixel dungeon featuring dynamic difficulty (the difficulty" +
		" is adjusted in order to give the player a more fair game)." +
		"Do you want to try the dynamic difficulty?.\n\n";

	@Override
	public void create() {
		super.create();

		int w = Camera.main.width;
		int h = Camera.main.height;

		float width, height;
		if (PixelDungeon.landscape()) {
			width = WIDTH_L;
			height = HEIGHT_L;
		} else {
			width = WIDTH_P;
			height = HEIGHT_P;
		}

		float left = (w - width) / 2;
		float top = (h - height) / 2;
		float bottom = h - top;

		float buttonY = bottom - BUTTON_HEIGHT;

		add( new WndStory( TEXT ) {
			@Override
			public void hide() {
				super.hide();
				Game.switchScene( InterlevelScene.class );
			}
		} );

		GameButton yesButton = new GameButton("YES") {
			@Override
			protected void onClick() {
				Dungeon.dynamicDifficulty = true;
				Game.switchScene( InterlevelScene.class );
				fadeIn();
			}
		};
		add(yesButton);

		GameButton noButton = new GameButton("NO") {
			@Override
			protected void onClick() {
				Dungeon.dynamicDifficulty = false;
				Game.switchScene( InterlevelScene.class );
				fadeIn();
			}
		};
		add(noButton);

		yesButton.visible = true;
		yesButton.secondary( TXT_YES, false );

		noButton.visible = true;
		noButton.secondary( TXT_NO, false );

		float wd = (Camera.main.width - GAP) / 2 - left;

		yesButton.setRect(
				left, buttonY, wd, BUTTON_HEIGHT );
		noButton.setRect(
				yesButton.right() + GAP, buttonY, wd, BUTTON_HEIGHT );

		
	}

	private static class GameButton extends RedButton {

		private static final int SECONDARY_COLOR_N	= 0xCACFC2;
		private static final int SECONDARY_COLOR_H	= 0xFFFF88;

		private BitmapText secondary;

		public GameButton( String primary ) {
			super( primary );

			this.secondary.text( null );
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			secondary = createText( 6 );
			add( secondary );
		}

		@Override
		protected void layout() {
			super.layout();

			if (secondary.text().length() > 0) {
				text.y = align( y + (height - text.height() - secondary.baseLine()) / 2 );

				secondary.x = align( x + (width - secondary.width()) / 2 );
				secondary.y = align( text.y + text.height() );
			} else {
				text.y = align( y + (height - text.baseLine()) / 2 );
			}
		}

		public void secondary( String text, boolean highlighted ) {
			secondary.text( text );
			secondary.measure();

			secondary.hardlight( highlighted ? SECONDARY_COLOR_H : SECONDARY_COLOR_N );
		}
	}
}
