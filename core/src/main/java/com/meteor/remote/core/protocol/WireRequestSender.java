package com.meteor.remote.core.protocol;

import com.meteor.remote.Utils.Utils;
import com.meteor.remote.core.interfaces.RequestSender;

import java.util.List;

/** This is an implementation of {@link RequestSender} that
 * uses Network to send data
  */
public class WireRequestSender implements RequestSender {

   public WireRequestSender(ServerConnection aServerConnection,
                            RequestFormatter aRequestFormatter) {
      _serverConnection = aServerConnection;
      _requestFormatter = aRequestFormatter;
   }

   @Override
   public void sendRequest(byte requestCode, List<String> params) {
      List<Byte> data = _requestFormatter.formatRequest(requestCode, params);

      _serverConnection.sendToServer(Utils.ByteListToArray(data));
   }

   private ServerConnection _serverConnection;
   private RequestFormatter _requestFormatter;
};


