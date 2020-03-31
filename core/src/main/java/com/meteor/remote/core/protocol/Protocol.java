package com.meteor.remote.core.protocol;

public class Protocol {

   /**
    * separate arguments in a message
    */
   public final static Byte SEPARATOR = (byte) 0x00;

   /**
    * used to terminate a request or reply message
    */
   public final static Byte TERMINATOR = (byte) 0xFF;

   /**
    * two TERMINATOR s indicate end of packet
    */
   public final static Integer TERMINATOR_SEQUENCE_SIZE = 2;
} 

