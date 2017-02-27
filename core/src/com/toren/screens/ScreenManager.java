package com.toren.screens;

import com.badlogic.gdx.Game;
import com.toren.Toren;
import com.toren.world.PlayerController;

public class ScreenManager {

  private Game game;
  private TorenScreen[] screens;

  public ScreenManager(Game game) {
  	this.game = game;
  	
  	// Initialize Screens
  	int max = 0;
  	for (Screens e : Screens.values())
  		if (e.getValue() > max)
  			max = e.getValue();
  	
  	screens = new TorenScreen[max + 1];
  	screens[Screens.LOGIN.getValue()] = new LoginScreen();
  	screens[Screens.REGISTER.getValue()] = new RegisterScreen();
  	
  }
  
  public Game getGame() {
  	return game;
  }

  public void showScreen(Screens screenEnum) {
  	Toren.packetListener.setScreen(screens[screenEnum.getValue()]);
    game.setScreen(screens[screenEnum.getValue()]);
  }
  
  public void loadWorldScreen(PlayerController player) {
  	screens[Screens.WORLD_GAME.getValue()] = new WorldScreen(player);
  }
}
