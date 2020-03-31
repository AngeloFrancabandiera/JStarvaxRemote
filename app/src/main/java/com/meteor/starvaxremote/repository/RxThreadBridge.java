package com.meteor.starvaxremote.repository;

import android.os.Handler;
import android.widget.Toast;

import com.meteor.remote.core.interfaces.SystemNotification;
import com.meteor.remote.core.protocol.InputParser;
import com.meteor.remote.core.protocol.ServerConnection;

import java.util.List;

public class RxThreadBridge implements ServerConnection.RxListener {

   private final Handler _RxHandler;
   private final InputParser _InputParser;
   private final ServerConnection _ServerConnection;

   public RxThreadBridge(Handler rxHandler, InputParser inputParser,
                         ServerConnection serverConnection) {

      _RxHandler = rxHandler;
      _InputParser = inputParser;
      _ServerConnection = serverConnection;

      serverConnection.registerRxListener(this);
   }

   @Override
   /* this function is called in context of RX thread, but triggers
    * the main UI thread. */
   public void onDataAvailable() {

      _RxHandler.post(new Runnable() {
         @Override
         public void run() {
            // read new data, (as long as some is available) and process it
            List<Byte> rxData = _ServerConnection.receive();

            while ((rxData != null) &&
                   (rxData.size() > 0)) {

               _InputParser.onDataReceived( rxData);
               rxData = _ServerConnection.receive();
            }
         }
      });
   }
}
