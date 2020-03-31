package com.meteor.remote.core.replies;

import com.meteor.remote.core.replies.ReplyListener;
import com.meteor.remote.core.interfaces.SystemNotification;

import java.util.Iterator;
import java.util.List;

/**
 * This class can be used for all replies that do not expect data
 * to be parsed, (for example to fill models).
 * On reply, any message is simply displayed to system log.
 * No message from the Core is generated
 */
public class DefaultReply implements ReplyListener {

   public DefaultReply(SystemNotification aSystemNotification) {
      _systemNotification = aSystemNotification;
   }

   // ReplyListener interface
   @Override
   public void onReplyReceived(byte replyCode, final List<String> args) {

      // just dump to system notification area whatever is received,
      // as a "message" if replay opcode is ACK, as warning otherwise

      Iterator argsIterator = args.iterator();

      while (argsIterator.hasNext()) {
         String arg = (String) argsIterator.next();

         if (replyCode == Reply.COMMAND_ACK) {
            _systemNotification.message(arg);
         } else {
            _systemNotification.warning(arg);
         }
      }
   }

   private SystemNotification _systemNotification;
};

