package com.toren.client.data;

import com.toren.client.SendOpcodes;

public class PacketCreator {

	public static byte[] loginRequest(String user, String pass) {

		PacketWriter pw = new PacketWriter();
		short len = (short) (6 + user.length() + pass.length());
		short opcode = (short) SendOpcodes.LOGIN_REQUEST.getValue();

		pw.writeShort(len);
		pw.writeShort(opcode);
		pw.writeTorenString(user);
		pw.writeTorenString(pass);
		
		return pw.getPacket();

	}

	public static byte[] registerRequest(String user, String pass) {

		PacketWriter pw = new PacketWriter();
		pass = Passwords.hash(pass);
		short len = (short) (6 + user.length() + pass.length());
		short opcode = (short) SendOpcodes.REGISTER_REQUEST.getValue();

		pw.writeShort(len);
		pw.writeShort(opcode);
		pw.writeTorenString(user);
		pw.writeTorenString(pass);

		return pw.getPacket();

	}

	public static byte[] keyPressed(int keyCode, int tick, int cmdNum) {

		PacketWriter pw = new PacketWriter();

		pw.writeShort((short) 14);
		pw.writeShort((short) SendOpcodes.KEY_PRESSED.getValue());
		pw.writeInt(tick);
		pw.writeInt(cmdNum);
		pw.writeInt(keyCode);

		return pw.getPacket();

	}

	public static byte[] keyReleased(int keyCode, int tick, int cmdNum) {

		PacketWriter pw = new PacketWriter();

		pw.writeShort((short) 14);
		pw.writeShort((short) SendOpcodes.KEY_RELEASED.getValue());
		pw.writeInt(tick);
		pw.writeInt(cmdNum);
		pw.writeInt(keyCode);

		return pw.getPacket();

	}

}
