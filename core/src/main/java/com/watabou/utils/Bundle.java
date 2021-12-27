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

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Bundle {

	private static final String CLASS_NAME = "__className";
	
	private static HashMap<String,String> aliases = new HashMap<String, String>();
	
	private JsonValue data;

	public Bundle() {
		this( new JsonValue(JsonValue.ValueType.object) );
	}
	
	public String toString() {
		return data.toString();
	}
	
	private Bundle( JsonValue data ) {
		this.data = data;
	}
	
	public boolean isNull() {
		return data == null;
	}
	
	public boolean contains( String key ) {
		return data.has( key ) && !data.get( key ).isNull();
	}
	
	public boolean getBoolean( String key ) {
		return data.getBoolean( key, false );
	}
	
	public int getInt( String key ) {
		return data.getInt( key, 0 );
	}
	
	public float getFloat( String key ) {
		return data.getFloat( key, 0 );
	}
	
	public String getString( String key ) {
		return data.getString( key, "" );
	}
	
	public Bundle getBundle( String key ) {
		return new Bundle( data.get( key ) );
	}
	
	private Bundlable get() {
		try {
			String clName = getString( CLASS_NAME );
			if (aliases.containsKey( clName )) {
				clName = aliases.get( clName );
			}
			
			Class<?> cl = ClassReflection.forName( clName );
			if (cl != null) {
				Bundlable object = (Bundlable) ClassReflection.newInstance(cl);
				object.restoreFromBundle( this );
				return object;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}	
	}
	
	public Bundlable get( String key ) {
		return getBundle( key ).get();	
	}
	
	public <E extends Enum<E>> E getEnum( String key, Class<E> enumClass ) {
		try {
			return (E)Enum.valueOf( enumClass, data.getString( key ) );
		} catch (Exception e) {
			return enumClass.getEnumConstants()[0];
		}
	}
	
	public int[] getIntArray( String key ) {
		try {
			return data.get( key ).asIntArray();
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean[] getBooleanArray( String key ) {
		try {
			return data.get( key ).asBooleanArray();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String[] getStringArray( String key ) {
		try {
			return data.get( key ).asStringArray();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Collection<Bundlable> getCollection( String key ) {
		
		ArrayList<Bundlable> list = new ArrayList<Bundlable>();

		try {
			JsonValue array = data.get( key );
			for (JsonValue value : array) {
				list.add( new Bundle( value ).get() );
			}
		} catch (Exception e) {

		}
		
		return list;
	}
	
	public void put( String key, boolean value ) {
		try {
			data.addChild( key, new JsonValue(value) );
		} catch (Exception e) {

		}
	}
	
	public void put( String key, int value ) {
		try {
			data.addChild( key, new JsonValue(value) );
		} catch (Exception e) {

		}
	}
	
	public void put( String key, float value ) {
		try {
			data.addChild( key, new JsonValue(value) );
		} catch (Exception e) {

		}
	}
	
	public void put( String key, String value ) {
		try {
			data.addChild( key, new JsonValue(value) );
		} catch (Exception e) {

		}
	}
	
	public void put( String key, Bundle bundle ) {
		try {
			data.addChild( key, bundle.data );
		} catch (Exception e) {

		}
	}
	
	public void put( String key, Bundlable object ) {
		if (object != null) {
			try {
				Bundle bundle = new Bundle();
				bundle.put( CLASS_NAME, object.getClass().getName() );
				object.storeInBundle( bundle );
				data.addChild( key, bundle.data );
			} catch (Exception e) {
			}
		}
	}
	
	public void put( String key, Enum<?> value ) {
		if (value != null) {
			try {
				data.addChild( key, new JsonValue(value.name()) );
			} catch (Exception e) {
			}
		}
	}
	
	public void put( String key, int[] array ) {
		try {
			JsonValue jsonArray = new JsonValue(JsonValue.ValueType.array);
			for (int j : array) {
				jsonArray.addChild(new JsonValue(j));
			}
			data.addChild( key, jsonArray );
		} catch (Exception e) {
			
		}
	}
	
	public void put( String key, boolean[] array ) {
		try {
			JsonValue jsonArray = new JsonValue(JsonValue.ValueType.array);
			for (boolean j : array) {
				jsonArray.addChild(new JsonValue(j));
			}
			data.addChild( key, jsonArray );
		} catch (Exception e) {
			
		}
	}
	
	public void put( String key, String[] array ) {
		try {
			JsonValue jsonArray = new JsonValue(JsonValue.ValueType.array);
			for (String j : array) {
				jsonArray.addChild(new JsonValue(j));
			}
			data.addChild( key, jsonArray );
		} catch (Exception e) {
			
		}
	}
	
	public void put( String key, Collection<? extends Bundlable> collection ) {
		JsonValue array = new JsonValue(JsonValue.ValueType.array);
		for (Bundlable object : collection) {
			Bundle bundle = new Bundle();
			bundle.put( CLASS_NAME, object.getClass().getName() );
			object.storeInBundle( bundle );
			array.addChild( bundle.data );
		}
		try {
			data.addChild( key, array );
		} catch (Exception e) {
			
		}
	}

	private static final char XOR_KEY = 0x1F;

	public static Bundle read( InputStream stream ) {
		
		try {
			BufferedReader reader = new BufferedReader( new InputStreamReader( stream ) );

			StringBuilder builder = new StringBuilder();

			char[] buffer = new char[0x2000];
			int count = reader.read( buffer );
			while (count > 0) {
				for (int i=0; i < count; i++) {
					buffer[i] ^= XOR_KEY;
				}
				builder.append( buffer, 0, count );
				count = reader.read( buffer );
			}
			
			JsonValue json = new JsonReader().parse( builder.toString() );
			reader.close();
			
			return new Bundle( json );
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Bundle read( byte[] bytes ) {
		try {
			
			JsonValue json = new JsonReader().parse( new String( bytes ) );
			return new Bundle( json );
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public static boolean write( Bundle bundle, OutputStream stream ) {
		try {
			BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( stream ) );

			char[] chars = bundle.data.toString().toCharArray();
			for (int i=0; i < chars.length; i++) {
				chars[i] ^= XOR_KEY;
			}
			writer.write( chars );
			writer.close();
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public static void addAlias( Class<?> cl, String alias ) {
		aliases.put( alias, cl.getName() );
	}
}
