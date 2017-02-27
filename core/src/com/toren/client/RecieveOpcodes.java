package com.toren.client;

public enum RecieveOpcodes {

	LOGIN_RESPONSE(1),
	REGISTER_RESPONSE(2),
	SPAWN_LIFE(3),
	STATE_UPDATE(4);

	private int code = -1;

	private RecieveOpcodes(int code) {
		this.code = code;
	}

	public int getValue() {
		return code;
	}

}
