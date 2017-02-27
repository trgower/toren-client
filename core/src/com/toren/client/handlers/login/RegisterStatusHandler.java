package com.toren.client.handlers.login;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.toren.client.data.PacketReader;
import com.toren.client.handlers.PacketHandler;
import com.toren.screens.*;

public final class RegisterStatusHandler implements PacketHandler {

  @Override
  public void handlePacket(PacketReader pr, TorenScreen ts) {

    short regStatus = pr.readShort();

    Label response = null;
    for (Actor a : ts.getActors())
      if (a.getName() != null && a.getName().equals("SERVER_RESPONSE"))
        response = (Label) a;

    switch (regStatus) {
      case 0: // SUCCESS
        response.setText("Created account!");
        for (Actor a : ts.getActors()) { // lol i guess this is smart?
        	if (a instanceof TextField) {
        		TextField tf = (TextField) a;
        		tf.setText(tf.getName());
        		tf.setPasswordMode(false);
        	}
        }
        break;
      case 1: // NAME TAKEN
        response.setText("Username is already taken.");
        break;
      case 5: // DB Connection
        response.setText("Database Error");
        break;
    }

  }

}
