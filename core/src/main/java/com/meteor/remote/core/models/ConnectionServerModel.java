package com.meteor.remote.core.models;
import java.util.Observable;

public class ConnectionServerModel extends Observable {

   public static final int CONNECTED = 0;
   public static final int DISCONNECTED = 1;
   public static final int LOGGED_IN = 2;


   private String mServerIP = new String();
   private String mPassword = new String();
   private String mShowTitle = new String();
   private int mConnectionState = DISCONNECTED;

   public String getServerIP() {
      return mServerIP;
   }

   public void setServerIP(String mServerIP) {
      this.mServerIP = mServerIP;
      setChanged();
      notifyObservers();
   }

   public String getPassword() {
      return mPassword;
   }

   public void setPassword(String mPassword) {
      this.mPassword = mPassword;
      setChanged();
      notifyObservers();
   }

   public String getShowTitle() {
      return mShowTitle;
   }

   public void setShowTitle(String mShowTitle) {
      this.mShowTitle = mShowTitle;
      setChanged();
      notifyObservers();
   }

   public int getConnectionState() {
      return mConnectionState;
   }

   public void setConnectionState(int connectionState) {
      this.mConnectionState = connectionState;
      setChanged();
      notifyObservers();
   }

}
