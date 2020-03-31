package com.meteor.remote.core.protocol;

import com.meteor.remote.core.protocol.Protocol;
import com.meteor.remote.core.replies.ReplyDispatcher;

import java.util.ArrayList;
import java.util.List;

/*
 * The InputParser class accepts a stream of data,
 *  (in chunks that may contain part of a message or more message).
 * When replies are recognized, a \a ReplyDispatcher is invoked.
 */
public class InputParser {
   public InputParser(ReplyDispatcher aReplyDispatcher) {
      _replyDispatcher = aReplyDispatcher;
      _terminatorCounter = 0;
   }

   ///
   /// \brief parse a chunk of a stream to detect reply messages.
   /// \param data
   ///
   public void onDataReceived( final List<Byte> data) {

      /* accumulate chars in partial string, until sequence 0xFF-0xFF is found */
      for (int i = 0; i < data.size(); i++) {
         _partialMessage.add( data.get(i));

         if (data.get(i) == Protocol.TERMINATOR) {
            ++ _terminatorCounter;
         } else {
            _terminatorCounter = 0;
         }

         if (_terminatorCounter == Protocol.TERMINATOR_SEQUENCE_SIZE) {
            parseCompleteReqMessage();

            _partialMessage.clear();
            _terminatorCounter = 0;
         }
      }

   }

   /* This function is called when \a _partialMessage holds
    * exactly one complete message
    */
   private void parseCompleteReqMessage() {
      byte result = _partialMessage.get(0);
      byte opCode = _partialMessage.get(1);
      byte numberOfParams = _partialMessage.get(2);

      /* cursor of bytes within 'message'. Parameters start at position 2 */
      int messageIndex = 3;
      int availableChars = (int) _partialMessage.size();

      List<String> paramList = new ArrayList<>();
      String param;

      for (byte i = 0; (i < numberOfParams) && (messageIndex < availableChars); i++) {
         param = "";

         /* collect data up to terminator */
         while ((messageIndex < availableChars) &&
                (_partialMessage.get(messageIndex) != '\0')) {
            param += (char)((byte)(_partialMessage.get(messageIndex)));
            ++ messageIndex;
         }

         /* skip after terminator */
         if (messageIndex < availableChars) {
            ++ messageIndex;
         }

         paramList.add( param);
      }

      _replyDispatcher.onReplyReceivedForRequest(opCode, result, paramList);
   }

   private ReplyDispatcher _replyDispatcher;
   private List<Byte> _partialMessage = new ArrayList<>();
   private int _terminatorCounter;  // counter of consecutive 0xFF characters
}
