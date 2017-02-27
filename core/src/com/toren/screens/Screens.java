package com.toren.screens;

public enum Screens {

  LOGIN(1),
  REGISTER(0),
  WORLD_GAME(2);
	
	private int id = -1;
	
	private Screens(int id) {
		this.id = id;
	}
	
	public int getValue() {
		return id;
	}

}
