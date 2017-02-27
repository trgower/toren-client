package com.toren.world.life;

import com.toren.world.life.interfaces.Life;
import com.toren.world.map.AbstractEntity;

public abstract class AbstractLife extends AbstractEntity implements Life {
	
	private int health;
	private int spriteDirection;

	public AbstractLife(float pX, float pY, float boundWidth, float boundHeight, int health, 
			int direction) {
		super(pX, pY, boundWidth, boundHeight);
		
		this.health = health;
		this.spriteDirection = direction;
		
	}
	
	@Override
	public int getHealth() {
		return health;
	}
	
	@Override
	public void damage(int health) {
		this.health -= health;
	}
	
	@Override
	public void heal(int health) {
		this.health += health;
	}
	
	@Override
	public int getSpriteDirection() {
		return spriteDirection;
	}
	
	@Override
	public void setSpriteDirection(int d) {
		spriteDirection = d;
	}

}
