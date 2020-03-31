package com.meteor.remote.core.protocol;

import java.util.List;

/* abstract layer for the connection to server.
 * The media used for connection is masked by this class.
 */
public interface ServerConnection {
   /**
    *  trigger a connection to server. When this function returns,
    *   the connection is not yet established.
    * @param server identifies the remote server. May be an
    *   IP address or any other identifier of remote service provider
    */
   void connectToServer( final String server);

   /** close connection with server */
   void disconnectFromServer() ;

   /** send data to Server. When this function returns, data have been
    * enqueued but may have not yet reached server
    * @param data are sent to network
    */
   void sendToServer( final byte[] data);

   /**
    *  retrieve data from the network. It is NOT guaranteed that the block
    *  of data returned by one call corresponds to a protocol packet.
    * @return some data. If no data is available, an list array is returned
    */
   List<Byte> receive();

   /** this listener can be used to detect the right moment to call 'receive' method.
    * This avoids continuous polling */
   interface RxListener {
      void onDataAvailable();
   }

   /** register a listener to be notified when data is available */
   void registerRxListener( RxListener listener);
};

