package com.toren.world.map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.toren.world.map.interfaces.Entity;

public abstract class AbstractEntity extends Actor implements Entity {
	
	private Vector2 position, physPosition, prevPosition;
	private Vector2 velocity;
	private Rectangle bounds;
	
	private boolean moving;
	
	public AbstractEntity(float x, float y, float boundWidth, float boundHeight) {
		
		position = new Vector2(x, y);
		physPosition = new Vector2(x, y);
		prevPosition = new Vector2(x, y);
		
		velocity = new Vector2(0, 0);
		
		bounds = new Rectangle();
		bounds.setCenter(position);
		bounds.setSize(boundWidth, boundHeight);
		
		moving = false;
	}
	
	@Override
	public void interpolatePosition(float alpha) {
		position.x = physPosition.x * alpha + prevPosition.x * (1.0f - alpha);
		position.y = physPosition.y * alpha + prevPosition.y * (1.0f - alpha);
	}
	
	@Override
	public void update(float delta) {
		physPosition.add(velocity.cpy().scl(delta));
		bounds.setCenter(physPosition);
	}
	
	@Override
	public Vector2 getPosition() {
		return position;
	}
	
	@Override 
	public Vector2 getPhysPosition() {
		return physPosition;
	}
	
	@Override
	public void setPrevPosition() {
		prevPosition.set(physPosition);
	}
	
	@Override
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	@Override
	public Rectangle getBounds() {
		return bounds;
	}
	
	@Override
	public Vector2 getVelocity() {
		return velocity;
	}
	
	@Override
	public void setVelocity(float x, float y) {
		velocity.set(x, y);
	}
	
	@Override
	public boolean isMoving() {
		return moving;
	}
	
	@Override
	public void setMoving(boolean move) {
		moving = move;
	}
	
	@Override
	public boolean isMovingX() {
		return velocity.x != 0f;
	}
	
	@Override
	public boolean isMovingY() {
		return velocity.y != 0f;
	}
	
	@Override
	public void stopMovement() {
		velocity.set(0, 0);
	}
	
	@Override
	public void stopMovementX() {
		velocity.set(0, velocity.y);
	}
	
	@Override
	public void stopMovementY() {
		velocity.set(velocity.x, 0);
	}
	
	@Override
	public boolean animate() {
		return !getPhysPosition().equals(getPosition());
	}

}
