package com.toren.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.toren.Toren;
import com.toren.client.data.PacketCreator;
import com.toren.world.PlayerController;
import com.toren.world.map.interfaces.Entity;

public class WorldScreen extends TorenScreen implements InputProcessor {

  private PlayerController player;

  private float physicsStep = 1f / 20f;
  private float accumulator = 0;

  public WorldScreen(PlayerController player) {
    super(386, 216);

    this.player = player;
    addActor(player);

    Gdx.input.setInputProcessor(this);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    if (delta > 0.25f) delta = 0.25f;
    accumulator += delta;

    while (accumulator >= physicsStep) {
      Toren.packetListener.readPacket();
      update(physicsStep); 
      accumulator -= physicsStep;
    }

    interpolate(accumulator / physicsStep);
    render();
    updateCamera();
  }

  public void update(float delta) {
    for (Actor a : this.getActors()) {
      if (a instanceof Entity) {
        ((Entity) a).setPrevPosition();
        ((Entity) a).update(delta);
      }
    }
  }

  public void render() {
    player.getMap().setView((OrthographicCamera) getCamera());
    player.getMap().render(this);
  }

  public void interpolate(float alpha) {
    for (Actor a : this.getActors()) {
      if (a instanceof Entity) {
        if (((Entity) a).animate())
          ((Entity) a).interpolatePosition(alpha);
      }
    }
  }

  public void updateCamera() {
    getCamera().position.x += (player.getPosition().x - getCamera().position.x);
    getCamera().position.y += (player.getPosition().y - getCamera().position.y);
    getCamera().update();
  }
  
  public PlayerController getPlayer() {
    return player;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean keyDown(int keycode) {
    boolean hitCommand = false;
    switch (keycode) {
    case Keys.W:
    case Keys.UP:
      player.addDirection(0);
      hitCommand = true;
      break;
    case Keys.A:
    case Keys.LEFT:
      player.addDirection(6);
      hitCommand = true;
      break;
    case Keys.S:
    case Keys.DOWN:
      player.addDirection(4);
      hitCommand = true;
      break;
    case Keys.D:
    case Keys.RIGHT:
      player.addDirection(2);
      hitCommand = true;
      break;
    }
    if (hitCommand) {
      player.saveCommand();
      Toren.send(PacketCreator.keyPressed(keycode, player.getTick(), player.getCmdNum()));
    }
    return hitCommand;
  }

  @Override
  public boolean keyUp(int keycode) {
    boolean hitCommand = false;
    switch (keycode) {
    case Keys.W:
    case Keys.UP:
      player.removeDirection(0);
      hitCommand = true;
      break;
    case Keys.A:
    case Keys.LEFT:
      player.removeDirection(6);
      hitCommand = true;
      break;
    case Keys.S:
    case Keys.DOWN:
      player.removeDirection(4);
      hitCommand = true;
      break;
    case Keys.D:
    case Keys.RIGHT:
      player.removeDirection(2);
      hitCommand = true;
      break;
    }
    if (hitCommand) {
      player.saveCommand();
      Toren.send(PacketCreator.keyReleased(keycode, player.getTick(), player.getCmdNum()));
    }
    return hitCommand;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    return false;
  }

}
