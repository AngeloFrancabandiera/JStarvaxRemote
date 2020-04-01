package com.meteor.starvaxremote;
import com.meteor.remote.core.interfaces.CoreEventListener;
import com.meteor.remote.core.models.ConnectionServerModel;
import com.meteor.starvaxremote.ui.main.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class CoreEventsHandler implements CoreEventListener {

   private final ConnectionServerModel mConnectionData;
   private List<ItemAdapter> mListeningAdapterList = new ArrayList<>();

   public CoreEventsHandler(ConnectionServerModel connectionData) {
      mConnectionData = connectionData;
   }

   @Override
   public void onConnectionToServerEstablished() {
      System.out.println("onConnectionToServerEstablished");
      mConnectionData.setConnectionState( ConnectionServerModel.CONNECTED);
   }

   @Override
   public void onLogInDone() {
      System.out.println("onLogInDone");
      mConnectionData.setConnectionState(ConnectionServerModel.LOGGED_IN);
   }

   @Override
   public void onDisconnectedFromServer() {
      System.out.println("onDisconnectedFromServer");
      mConnectionData.setConnectionState(ConnectionServerModel.DISCONNECTED);
   }

   @Override
   public void onShowReloaded(String showTitle) {
      System.out.println("onShowReloaded. Title:" + showTitle);
      mConnectionData.setShowTitle( showTitle);

      // notify data changed for all subscribed adapters
      for (ItemAdapter adapter : mListeningAdapterList) {
         adapter.notifyDataSetChanged();
      }
   }

   public void addListeningAdapter(ItemAdapter adapter) {
      mListeningAdapterList.add( adapter);
   }
}
