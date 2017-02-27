package com.toren.client.handlers.login;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.toren.Toren;
import com.toren.client.data.PacketReader;
import com.toren.client.handlers.PacketHandler;
import com.toren.screens.*;
import com.toren.world.PlayerController;

public final class LoginStatusHandler implements PacketHandler {

  @Override
  public void handlePacket(PacketReader pr, TorenScreen ts) {

    short loginStatus = pr.readShort();

    Label response = null;
    String username = "not found";
    for (Actor a : ts.getActors()) {
      if (a.getName() != null && a.getName().equals("SERVER_RESPONSE"))
        response = (Label) a;
      else if (a.getName() != null && a.getName().equals("Username"))
      	username = ((TextField) a).getText();
    }

    switch (loginStatus) {
      case 0: // SUCCESS
      	float x = pr.readFloat();
        float y = pr.readFloat();
        int dir = pr.readShort();
        int health = pr.readInt();
        short skin = pr.readShort();
        short hair = pr.readShort();
        short body = pr.readShort();
      	Toren.screenManager.loadWorldScreen(
      			new PlayerController(username, x, y, 100, dir, health, skin, hair, body));
      	Toren.screenManager.showScreen(Screens.WORLD_GAME);
        break;
      case 1: // WRONG PASSWORD
        response.setText("Incorrect password");
        break;
      case 2: // USERNAME DOESN'T EXIST
        response.setText("Account doesn't exist");
        break;
      case 3: // AREADY LOGGED IN
        response.setText("You are already logged in!");
        break;
      case 5: // DB Connection
        response.setText("Database Error");
        break;
    }

  }

}
