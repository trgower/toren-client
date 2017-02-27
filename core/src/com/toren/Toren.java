package com.toren;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.Net.Protocol;
import com.toren.client.PacketListener;
import com.toren.screens.*;
import com.toren.world.PlayerController;

public class Toren extends Game {

	public final static String kIpAddress = "localhost";
	public final static int kPort = 5123;

	public static Socket socket;
	public static PacketListener packetListener;
	public static ScreenManager screenManager;

	@Override
	public void create () {
		connect();
		packetListener = new PacketListener(socket.getInputStream());
		screenManager = new ScreenManager(this);
		screenManager.showScreen(Screens.LOGIN);
	}

	public static boolean connect () {
		try {
			SocketHints hints = new SocketHints();
			hints.connectTimeout = 2000;
			socket = Gdx.net.newClientSocket(Protocol.TCP, kIpAddress, kPort, hints);
		} catch (Exception e) {
			System.out.println("Could not connect to server.");
			Gdx.app.exit();
			return false;
		}
		return true;
	}
	
	public static void send(final byte[] packet) {
		new Thread(new Runnable() {
		   @Override
		   public void run() {
		  	 try {
		 			writeToServer(packet);
		 		} catch (IOException e) {
		 			e.printStackTrace();
		 		}
		   }
		}).start();
	}
	
	public static synchronized void writeToServer (final byte[] packet) throws IOException {
		socket.getOutputStream().write(packet);
	}

}
