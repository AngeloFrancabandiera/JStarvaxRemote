package com.meteor.remote.core.replies;

public class Reply {

   public final static byte COMMAND_ACK = 0;
   public final static byte COMMAND_NOT_ENOUGH_ARGS = 1;
   public final static byte COMMAND_MEDIA_NOT_FOUND = 2;
   public final static byte COMMAND_LIGHTSET_NOT_FOUND = 3;
   public final static byte COMMAND_INVALID_ARGUMENT = 4;
   public final static byte COMMAND_UNSUPPORTED = 5;
   public final static byte COMMAND_NOT_GRANTED = 6;
   public final static byte COMMAND_WRONG_PASSWORD = 7;
   public final static byte COMMAND_MISSING_PASSWORD = 8;
   public final static byte CONNECTION_BUSY = (byte) 0xf0;

} 
