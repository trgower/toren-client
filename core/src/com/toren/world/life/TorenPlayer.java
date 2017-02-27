package com.toren.world.life;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class TorenPlayer extends AbstractCharacter {
	
	ShapeRenderer sr = new ShapeRenderer();
	boolean debug = false;
	
	float cW = 193,
			  cH = 108;

	public TorenPlayer(String name, float pX, float pY, int speed, int direction, int health, 
			int skin, int hair, int body, int id) {
		super(name, pX, pY, speed, health, direction, skin, hair, body);
		setName(id + "");
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if (debug) {
			batch.end();
	
	    sr.setProjectionMatrix(batch.getProjectionMatrix());
	    sr.setTransformMatrix(batch.getTransformMatrix());
	
	    sr.begin(ShapeType.Line);
	    sr.setColor(Color.BLUE);
	    sr.rect(getPhysPosition().x - getBounds().getWidth() / 2, getPhysPosition().y - getBounds().getHeight() / 2, getBounds().getWidth(), getBounds().getHeight());
	    sr.end();
	    sr.begin(ShapeType.Filled);
	    sr.setColor(Color.BLUE);
	    sr.circle(getPosition().x, getPosition().y, 1);
	    sr.end();
	
	    batch.begin();
		}
	}

}
