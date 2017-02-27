package com.toren.world;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.toren.Assets;
import com.toren.world.life.TorenPlayer;
import com.toren.world.map.TorenMap;

public class PlayerController extends TorenPlayer {

	private ArrayList<Integer> directions;
	private int direction;
	private Vector2 directionVector;
	private TorenMap map;
	
	private ConcurrentLinkedQueue<State> stateBuffer;
	private boolean saveCmd = false;
	private int cmdNum = 0;
	private int tick = 0;

	public PlayerController(String name, float pX, float pY, int speed, int direction, 
			int health, short skin, short hair, short body) {
		super(name, pX, pY, speed, direction, health, skin, hair, body, -1);

		this.directions = new ArrayList<Integer>();
		this.direction = direction;
		this.directionVector = new Vector2(0, 0);
		this.map = new TorenMap(Assets.maps[0]);
		
		stateBuffer = new ConcurrentLinkedQueue<State>();

	}

	@Override
	public void update(float delta) {
		Collision collision = getCollision(delta);
		setMoving(!getPhysPosition().equals(collision.position));
		getPhysPosition().set(collision.position);
		getBounds().setCenter(getPhysPosition());
		stateBuffer.offer(new State(tick, getPhysPosition()));
		if (saveCmd) {
		  saveCmd = false;
		}
		tick++;
	}
	
	public void checkState(int t, float x, float y) {
	  State popped = stateBuffer.poll();
	  System.out.println("READ: " + popped.tickNumber + " " + popped.pos);
	}
	
	public int getTick() {
	  return tick;
	}

	public void saveCommand() {
	  saveCmd = true;
	  cmdNum++;
	}
	
	public int getCmdNum() {
	  return cmdNum;
	}
	
	public void changeVelocity() {
		getVelocity().set(getSpeed() * directionVector.x, getSpeed() * directionVector.y);
	}

	public void addDirection(int d) {
		if (!directions.contains(d)) {
			directions.add(d);
			updateDirection();
		}
	}

	public void removeDirection(int d) {
		directions.remove(new Integer(d));
		updateDirection();
	}
	
	public Collision getCollision(float delta) {
		Vector2 futurePosition = new Vector2(getPhysPosition());
		Rectangle futureBounds = new Rectangle(getBounds());
		
		futurePosition.add(getVelocity().cpy().scl(delta));
		futureBounds.setCenter(futurePosition);
		
		Rectangle x = null, y = null;
		ArrayList<Rectangle> xTiles = new ArrayList<Rectangle>();
		ArrayList<Rectangle> yTiles = new ArrayList<Rectangle>();

		float cX = futurePosition.x - futureBounds.getWidth() / 2;
		float cY = futurePosition.y - futureBounds.getHeight() / 2;

		switch (direction) {
			case 0:
				y = new Rectangle(cX + 2, cY + 2, futureBounds.getWidth() - 4, map.getHeight() - cY);
				break;
			case 1:
				x = new Rectangle(cX + 2, cY + 2, map.getWidth() - cX, futureBounds.getHeight() - 4);
				y = new Rectangle(cX + 2, cY + 2, futureBounds.getWidth() - 4, map.getHeight() - cY);
				break;
			case 2:
				x = new Rectangle(cX + 2, cY + 2, map.getWidth() - cX, futureBounds.getHeight() - 4);
				break;
			case 3:
				x = new Rectangle(cX + 2, cY + 2, map.getWidth() - cX, futureBounds.getHeight() - 4);
				y = new Rectangle(cX + 2, 0, futureBounds.getWidth() - 4, cY - 1);
				break;
			case 4:
				y = new Rectangle(cX + 2, 0, futureBounds.getWidth() - 4, cY - 1);
				break;
			case 5:
				x = new Rectangle(0, cY + 2, cX, futureBounds.getHeight() - 4);
				y = new Rectangle(cX + 2, 0, futureBounds.getWidth() - 4, cY - 1);
				break;
			case 6:
				x = new Rectangle(0, cY + 2, cX, futureBounds.getHeight() - 4);
				break;
			case 7:
				x = new Rectangle(0, cY + 2, cX, futureBounds.getHeight() - 4);
				y = new Rectangle(cX + 2, cY + 2, futureBounds.getWidth() - 4, map.getHeight() - cY);
				break;

		}

		for (int i = 0; i < map.getMapColliders().getCount(); i++) {
			RectangleMapObject obj = (RectangleMapObject) map.getMapColliders().get(i);
			Rectangle rect = obj.getRectangle();
			if (y != null && rect.overlaps(y)) {
				yTiles.add(rect);
			} else if (x != null && rect.overlaps(x)) {
				xTiles.add(rect);
			}
		}

		boolean collisionX = false;
		boolean collisionY = false;
		
		for (Rectangle r : yTiles) {
			if (r.overlaps(futureBounds)) {
				collisionY = true;
			}
		} 
		for (Rectangle r : xTiles) {
			if (r.overlaps(futureBounds)) {
				collisionX = true;
			}
		}
		
		boolean fX = collisionX, fY = collisionY;
		
		while (fX || fY) {
			futurePosition.sub(getVelocity().cpy().scl(delta / 5f));
			futureBounds.setCenter(futurePosition);
			boolean rY = false;
			for (Rectangle r : yTiles) {
				if (r.overlaps(futureBounds)) {
					rY = true;
				}
			} 
			fY = rY;
			boolean rX = false;
			for (Rectangle r : xTiles) {
				if (r.overlaps(futureBounds)) {
					rX = true;
				}
			}
			fX = rX;
		}
		return new Collision(collisionX, collisionY, futurePosition);	
	}

	public void updateDirection() {
		int od = direction;
		if (directions.size() == 1) {
			setSpriteDirection(directions.get(0));
			direction = directions.get(0);
			setDirectionVector();
		} else if (directions.size() > 1) {
			setSpriteDirection(directions.get(0));
			if (getSpriteDirection() == 0 && directions.get(1) == 6 
					|| getSpriteDirection() == 6 && directions.get(1) == 0) {
				direction = 7;
				setDirectionVector();
			} else if (getSpriteDirection() == 0 && directions.get(1) == 2 
					|| getSpriteDirection() == 2 && directions.get(1) == 0) {
				direction = 1;
				setDirectionVector();
			} else if (getSpriteDirection() == 2 && directions.get(1) == 4 
					|| getSpriteDirection() == 4 && directions.get(1) == 2) {
				direction = 3;
				setDirectionVector();
			} else if (getSpriteDirection() == 4 && directions.get(1) == 6 
					|| getSpriteDirection() == 6 && directions.get(1) == 4) {
				direction = 5;
				setDirectionVector();
			} 
		}
		
		if (directions.size() == 0 && !getVelocity().isZero()) {
			stopMovement();
		} else if (od != direction || getVelocity().isZero()) {
			changeVelocity();
		}
		
	}
	
	public void setDirectionVector() {
		float splitSpeed = (float) (Math.sqrt((getSpeed() * getSpeed()) / 2) / getSpeed());
		switch (direction) {
			case 0:
				directionVector.set(0, 1);
				break;
			case 2:
				directionVector.set(1, 0);
				break;
			case 4:
				directionVector.set(0, -1);
				break;
			case 6:
				directionVector.set(-1, 0);
				break;
			case 7:
				directionVector.set(-splitSpeed, splitSpeed);
				break;
			case 1:
				directionVector.set(splitSpeed, splitSpeed);
				break;
			case 3:
				directionVector.set(splitSpeed, -splitSpeed);
				break;
			case 5:
				directionVector.set(-splitSpeed, -splitSpeed);
				break;
		}
	}
	
	public TorenMap getMap() {
		return map;
	}
	
	class Collision {
		
		boolean collisionX, collisionY;
		Vector2 position;
		
		public Collision(boolean cX, boolean cY, Vector2 pos) {
			
			collisionX = cX;
			collisionY = cY;
			position = pos;
			
		}
		
	}
	
	public class State {
	  
	  public int tickNumber;
	  public Vector2 pos;
	  
	  public State(int tick, Vector2 pos) {
	    this.tickNumber = tick;
	    this.pos = pos;
	    System.out.println("STORED: " + tick + " " + pos);
	  }
	  
	}

}
