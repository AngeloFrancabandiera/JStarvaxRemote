package com.meteor.starvaxremote;
import com.meteor.remote.core.interfaces.CoreEventListener;
import com.meteor.remote.core.models.ConnectionServerModel;

public class CoreEventsHandler implements CoreEventListener {

   private final ConnectionServerModel mConnectionData;

   public CoreEventsHandler(ConnectionServerModel connectionData) {
      mConnectionData = connectionData;
   }

   @Override
   public void onConnectionToServerEstablished() {
      System.out.println("onConnectionToServerEstablished");
   }

   @Override
   public void onLogInDone() {
      System.out.println("onLogInDone");
   }

   @Override
   public void onDisconnectedFromServer() {
      System.out.println("onDisconnectedFromServer");
   }

   @Override
   public void onShowReloaded(String showTitle) {
      System.out.println("onShowReloaded. Title:" + showTitle);
      mConnectionData.setShowTitle( showTitle);
   }
}
