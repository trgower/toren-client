package com.toren.client.handlers;

import com.toren.client.data.PacketReader;
import com.toren.screens.TorenScreen;

public interface PacketHandler {

	void handlePacket(PacketReader pr, TorenScreen ts);

}
