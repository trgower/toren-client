package com.toren.world.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MapRenderer extends OrthogonalTiledMapRenderer {
	
	public MapRenderer(TiledMap map) {
		super(map);
	}
	
	public void render(Stage stage) {
		beginRender();
		for (MapLayer ml : getMap().getLayers()) {
			if (ml.isVisible()) {
				if (ml instanceof TiledMapTileLayer) {
					if (ml.getName().equalsIgnoreCase("overhead")) {
						endRender();
						stage.draw();
						beginRender();
					}
					renderTileLayer((TiledMapTileLayer) ml);
				}
			}
			
		}
		endRender();
	}

}
