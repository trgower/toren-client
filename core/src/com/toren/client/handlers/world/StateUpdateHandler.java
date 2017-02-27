package com.toren.client.handlers.world;

import com.badlogic.gdx.math.Vector2;
import com.toren.client.data.PacketReader;
import com.toren.client.handlers.PacketHandler;
import com.toren.screens.TorenScreen;
import com.toren.screens.WorldScreen;
import com.toren.world.PlayerController;
import com.toren.world.PlayerController.State;

public class StateUpdateHandler implements PacketHandler {

  @Override
  public void handlePacket(PacketReader pr, TorenScreen ts) {
    
    WorldScreen ws = (WorldScreen) ts;
    
    int tick = pr.readInt();
    float x = pr.readFloat();
    float y = pr.readFloat();
    
    ws.getPlayer().checkState(tick, x, y);
    
  }

}
