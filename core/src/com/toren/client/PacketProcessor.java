package com.toren.client;

import com.toren.client.data.PacketReader;
import com.toren.client.handlers.PacketHandler;
import com.toren.client.handlers.login.*;
import com.toren.client.handlers.world.StateUpdateHandler;
import com.toren.screens.TorenScreen;

public class PacketProcessor {

	public static PacketProcessor instance;
  private PacketHandler[] handlers;

	public PacketProcessor() {

		int maxRecieveCode = 0;
		for (RecieveOpcodes op : RecieveOpcodes.values()) {
			if (op.getValue() > maxRecieveCode) {
				maxRecieveCode = op.getValue();
			}
		}
		handlers = new PacketHandler[maxRecieveCode + 1];

		registerHandlers();

	}
	
	public static PacketProcessor getInstance() {
		if (instance == null)
			instance = new PacketProcessor();
		return instance;
	}
	
	public void process(byte[] packet, TorenScreen ts) {
		
		PacketReader pr = new PacketReader(packet);
		short opcode = pr.readShort();
		
		PacketHandler handler = getHandler(opcode);
    if (handler != null && ts != null) {
      handler.handlePacket(pr, ts);
    }
	}

	public PacketHandler getHandler(short opcode) {

		if (opcode > handlers.length) {
			return null;
		}
		PacketHandler handler = handlers[opcode];
		return handler;
	}

	public void registerHandler(RecieveOpcodes code, PacketHandler handler) {

		try {
			handlers[code.getValue()] = handler;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error registering handler for code: " + code.name());
		}

	}

	public void registerHandlers() {

		registerHandler(RecieveOpcodes.LOGIN_RESPONSE, new LoginStatusHandler());
		registerHandler(RecieveOpcodes.REGISTER_RESPONSE, new RegisterStatusHandler());
		registerHandler(RecieveOpcodes.STATE_UPDATE, new StateUpdateHandler());

	}

}
