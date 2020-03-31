package com.meteor.remote.Utils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

   public static byte[] ByteListToArray(final List<Byte> byteList) {

      byte[] byteArray = new byte[byteList.size()];

      for (int index = 0; index < byteList.size(); index++) {
         byteArray[index] = byteList.get(index);
      }

      return byteArray;
   }

   public static List<Byte>  ByteArrayToList( final byte[] byteArray,
                                              int validSize) {

      // assert( validSize <= byteArray.length);

      List<Byte> byteList = new ArrayList<>();

      for (int index = 0; index < validSize; index++) {
         byteList.add( byteArray[index]);
      }

      return byteList;
   }
}
