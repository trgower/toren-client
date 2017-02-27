package com.toren.world.life;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.toren.Assets;

public abstract class AbstractCharacter extends AbstractLife {
	
	private int skin, hair, body;
	private int speed;
	private Animation[] walking = new Animation[4];
	private float walkingState = 0f;
	private Label nameplate;
	
	public AbstractCharacter(String name, float pX, float pY, int speed, int health, 
			int direction, int skin, int hair, int body) {
		super(pX, pY, 12f, 14f, health, direction);
		
		this.skin = skin;
		this.hair = hair;
		this.body = body;
		
		for (int i = 0; i < 4; i++) {
			walking[i] = new Animation(0.1f, Assets.characterWalk[i]);
		}
		
		nameplate = new Label(name, Assets.defaultSkin);
		nameplate.setFontScale(0.5f);
		nameplate.setAlignment(1);
	
		this.speed = speed;
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(walking[getSpriteDirection() / 2].getKeyFrame(walkingState, true), getPosition().x - 8, getPosition().y - 8);
		if (animate())
			walkingState += Gdx.graphics.getDeltaTime();
		else
			walkingState = walking[getSpriteDirection() / 2].getFrameDuration();
		
		nameplate.setPosition(getPosition().x - (nameplate.getWidth() / 2), 
				getPosition().y + 5);
		nameplate.draw(batch, parentAlpha);
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getSkin() {
		return skin;
	}

	public int getHair() {
		return hair;
	}

	public int getBody() {
		return body;
	}

}
