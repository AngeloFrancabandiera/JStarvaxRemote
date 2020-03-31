package com.meteor.remote.core.protocol;

import com.meteor.remote.Utils.Utils;
import com.meteor.remote.core.interfaces.RequestSender;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

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


