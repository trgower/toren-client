package com.toren.screens;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.toren.Toren;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public abstract class TorenScreen extends Stage implements Screen {
	
	public TorenScreen (float width, float height) {
  	super(new FitViewport(width, height));
  }

  @Override
  public void render (float delta) {

    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    Toren.packetListener.readPacket();
    //super.act(delta); // update whatever
    super.draw();
    
  }
  
  public void removeActorByName(String id) {
  	for (int i = 0; i < this.getActors().size; i++) {
  		if (this.getActors().get(i).getName().equals(id))
  			this.getActors().removeIndex(i);
  	}
  }

  @Override
  public void show () {
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public void resize (int width, int height) {
    getViewport().update(width, height, true);
  }

  @Override
  public void hide () {
  }

  @Override
  public void pause () {
  }

  @Override
  public void resume () {
  }
 
}
