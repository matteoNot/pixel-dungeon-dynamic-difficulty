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

package com.watabou.pixeldungeon.plants;

import com.watabou.noosa.audio.Sample;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.Blob;
import com.watabou.pixeldungeon.actors.blobs.ToxicGas;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.actors.buffs.Roots;
import com.watabou.pixeldungeon.actors.mobs.Mob;
import com.watabou.pixeldungeon.effects.CellEmitter;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.items.bags.Bag;
import com.watabou.pixeldungeon.items.potions.PotionOfStrength;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.pixeldungeon.utils.GLog;

public class Rotberry extends Plant {
	
	private static final String TXT_DESC = 
		"Berries of this shrub taste like sweet, sweet death.";
	
	{
		image = 7;
		plantName = "Rotberry";
	}
	
	@Override
	public void activate( Char ch ) {
		super.activate( ch );
		
		GameScene.add( Blob.seed( pos, 100, ToxicGas.class ) );
		
		Dungeon.level.drop( new Seed(), pos ).sprite.drop();
		
		if (ch != null) {
			Buff.prolong( ch, Roots.class, Roots.TICK * 3 );
		}
	}
	
	@Override
	public String desc() {
		return TXT_DESC;
	}
	
	public static class Seed extends Plant.Seed {
		{
			plantName = "Rotberry";
			
			name = "seed of " + plantName;
			image = ItemSpriteSheet.SEED_ROTBERRY;
			
			plantClass = Rotberry.class;
			alchemyClass = PotionOfStrength.class;
		}
		
		@Override
		public boolean collect( Bag container ) {
			if (super.collect( container )) {
				
				if (Dungeon.level != null) {
					for (Mob mob : Dungeon.level.mobs) {
						mob.beckon( Dungeon.hero.pos );
					}
					
					GLog.w( "The seed emits a roar that echoes throughout the dungeon!" );
					CellEmitter.center( Dungeon.hero.pos ).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
					Sample.INSTANCE.play( Assets.SND_CHALLENGE );
				}
				
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public String desc() {
			return TXT_DESC;
		}
	}
}