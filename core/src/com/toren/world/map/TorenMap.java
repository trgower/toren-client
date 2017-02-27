package com.toren.world.map;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class TorenMap extends MapRenderer {
	
	private MapObjects mapColliders;
	
	public TorenMap(TiledMap map) {
		super(map);
		setMapColliders(map.getLayers().get("ColliderRectangles").getObjects());
	}
	
	public int getWidth() {
		return map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
	}
	
	public int getHeight() {
		return map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
		setMapColliders(map.getLayers().get("ColliderRectangles").getObjects());
	}

	public MapObjects getMapColliders() {
		return mapColliders;
	}

	public void setMapColliders(MapObjects mapColliders) {
		this.mapColliders = mapColliders;
	}

}
