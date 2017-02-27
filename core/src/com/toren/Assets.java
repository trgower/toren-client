package com.toren;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	
	/******SKINS******/
	public static Skin defaultSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));
	
	/******MAPS******/
	public static TiledMap[] maps = {new TmxMapLoader().load("maps/TestIsland.tmx")};
	/******CHARACTERS******/
	public static Texture characters = new Texture(Gdx.files.internal("characters/template.png"));
	
	/******FRAMES******/
	public static TextureRegion[][] characterWalk = new TextureRegion(characters, 64, 72, 48, 72).split(16, 18);
	

}
