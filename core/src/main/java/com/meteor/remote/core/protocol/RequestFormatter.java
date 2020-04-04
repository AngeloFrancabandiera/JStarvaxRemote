package com.meteor.remote.core.protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this class is responsible to take opCode and parameter and
 * cat them in a packet
 */
public class RequestFormatter {
   public RequestFormatter() {
   }

   public List<Byte> formatRequest(Byte opCode, final String param) {
      List<String> dummyList = new ArrayList<>();
      dummyList.add(param);

      return formatRequest( opCode, dummyList);
   }

   public List<Byte> formatRequest(Byte opCode, final List<String> params) {

//      assert( opCode != 0);
//      assert( params.size() < 256);  // must fit one byte

      List<Byte> request = new ArrayList<>();

      int sizeAsInteger = params.size();
      Byte sizeAsByte = (byte) sizeAsInteger;

      // byte 0: operative code
      request.add(opCode);

      // byte 1: number of params
      request.add(sizeAsByte);

      // parameters
      for (int i = 0; i < params.size(); i++) {
         // 'append' for char array is the plain sequence of chars
         String singleParam = params.get(i);

         if (singleParam == null) {
            // this happens when one parameter is not specified
            singleParam = "";
         }

         // string to List<Byte>
         for (byte ch : singleParam.getBytes()) {
            request.add((Byte) ch);
         }
         request.add(Protocol.SEPARATOR);
      }

      // terminators. Note that 'params.size' may be 0, so all terminators are always added.
      for (int i = 0; i < Protocol.TERMINATOR_SEQUENCE_SIZE; i++) {
         request.add(Protocol.TERMINATOR);
      }

      return request;
   }

}

