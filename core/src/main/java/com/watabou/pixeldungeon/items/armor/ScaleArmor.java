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
package com.watabou.pixeldungeon.items.armor;

import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;


public class ScaleArmor extends Armor {

	{
		name = "scale armor";
		image = ItemSpriteSheet.ARMOR_SCALE;
	}
	
	public ScaleArmor() {
		super( 4 );
	}
	
	@Override
	public String desc() {
		return 
			"The metal scales sewn onto a leather vest create a flexible, yet protective armor.";
	}
}
