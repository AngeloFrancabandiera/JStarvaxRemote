package com.meteor.remote.core.replies;

import com.meteor.remote.core.interfaces.CoreEventListener;
import com.meteor.remote.core.interfaces.SystemNotification;

import java.util.Iterator;
import java.util.List;

public class SetCredentialsReply implements ReplyListener {
   public SetCredentialsReply(CoreEventListener theCoreEvents,
                              SystemNotification aSystemNotification) {
      _coreEventsListener = theCoreEvents;
      _systemNotification = aSystemNotification;
   }

   // ReplyListener interface
   @Override
   public void onReplyReceived(byte replyCode, List<String> args) {

      if (replyCode == Reply.COMMAND_ACK) {
         _coreEventsListener.onLogInDone();
      } else {
         _coreEventsListener.onDisconnectedFromServer();
      }

      // display messages into notification area

      Iterator argsIterator = args.iterator();

      while (argsIterator.hasNext()) {
         String str = (String) argsIterator.next();
         if (replyCode == Reply.COMMAND_ACK) {
            _systemNotification.message(str);
         } else {
            _systemNotification.warning(str);
         }
      }
   }

   private CoreEventListener _coreEventsListener;
   private SystemNotification _systemNotification;

};


