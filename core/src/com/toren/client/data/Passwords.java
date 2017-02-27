package com.toren.client.data;

import java.security.spec.KeySpec;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.badlogic.gdx.utils.Base64Coder;

public final class Passwords {

  public static String hash(String password) {

    try {
      Random random = new Random();

      byte[] salt = new byte[16];
      random.nextBytes(salt);
      String salts = new String(Base64Coder.encode(salt));

      return salts + "$" + hash(password, salt);

    } catch (Exception e) {
    	e.printStackTrace();
    }
    return "";
  }

  public static boolean check(String password, String stored) {

    try {
      String[] saltPass = stored.split("\\$");
      String hashOfInput = hash(password, Base64Coder.decode(saltPass[0]));
      return hashOfInput.equals(saltPass[1]);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;

  }

  private static String hash(String password, byte[] salt) {
    try {
      KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
      SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      byte[] hash = f.generateSecret(spec).getEncoded();

      String hashs = new String(Base64Coder.encode(hash));
      return hashs;

    } catch (Exception e) {
    	e.printStackTrace();
    }
    return "";
  }

}
