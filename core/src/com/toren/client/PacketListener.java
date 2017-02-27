package com.toren.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.toren.screens.TorenScreen;

public class PacketListener  {
	
  private int packetLength;
  private DataInputStream in;
  private TorenScreen ts;

  public PacketListener(InputStream in) {
    this.packetLength = -1;
    this.in = new DataInputStream(in);
  }

  public void readPacket() {
  	
  	try {
			if (in.available() >= 2) {
				if (packetLength == -1)
					packetLength = Short.reverseBytes(in.readShort());
				if (in.available() >= packetLength) {
					byte[] packet = new byte[packetLength];
					in.read(packet, 0, packetLength);
					packetLength = -1;
					PacketProcessor.getInstance().process(packet, ts);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

  }
  
  public void setScreen(TorenScreen ts) {
  	this.ts = ts;
  }

}
