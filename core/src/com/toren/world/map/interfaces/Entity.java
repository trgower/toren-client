package com.toren.world.map.interfaces;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface Entity {
	
	Vector2 getPosition();
	void setPosition(float x, float y);
	Rectangle getBounds();
	void interpolatePosition(float alpha);
	Vector2 getPhysPosition();
	boolean isMoving();
	void setMoving(boolean move);
	Vector2 getVelocity();
	void setVelocity(float x, float y);
	void update(float delta);
	boolean isMovingX();
	boolean isMovingY();
	void stopMovement();
	void stopMovementX();
	void stopMovementY();
	void setPrevPosition();
	boolean animate();

}
