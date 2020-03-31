package com.meteor.remote.Network;
import com.meteor.remote.core.protocol.ServerConnection;

import java.util.List;
import java.util.concurrent.BlockingQueue;


public class NetworkServerConnection implements ServerConnection {

   private final ConnectionThread _ConnectionThread;
   private final RxThread _RxThread;
   private final BlockingQueue<List<Byte>> _RxDataQueue;

   public NetworkServerConnection(ConnectionThread connectionThread,
                                  RxThread rxThread,
                                  BlockingQueue<List<Byte>> rxDataQueue) {
      _ConnectionThread = connectionThread;
      _RxThread = rxThread;
      _RxDataQueue = rxDataQueue;

      Thread t = new Thread(_ConnectionThread);
      t.start();
   }

   @Override
   public void connectToServer(final String server) {
      _ConnectionThread.connectToServer( server);
   }

   @Override
   public void disconnectFromServer() {
      _ConnectionThread.disconnectFromServer();
   }

   @Override
   public void sendToServer(final byte[] data) {
      _ConnectionThread.sendToServer(data);
   }

   @Override
   public List<Byte> receive() {
      return _RxDataQueue.poll();
   }

   @Override
   public void registerRxListener( ServerConnection.RxListener listener) {
      _RxThread.registerListener( listener);
   }
}
