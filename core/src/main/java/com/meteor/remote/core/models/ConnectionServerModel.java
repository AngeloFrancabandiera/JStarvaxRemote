package com.meteor.remote.core.models;
import java.util.Observable;

public class ConnectionServerModel extends Observable {

   private String mServerIP = new String();
   private String mPassword = new String();
   private String mShowTitle = new String();

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
}
