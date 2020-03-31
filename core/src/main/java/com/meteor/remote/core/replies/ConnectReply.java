package com.meteor.remote.core.replies;

import com.meteor.remote.core.interfaces.SystemNotification;
import com.meteor.remote.core.interfaces.CoreEventListener;
import com.meteor.remote.core.replies.Reply;
import com.meteor.remote.core.replies.ReplyListener;

import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.HTMLDocument;

public class ConnectReply implements ReplyListener {

   public ConnectReply(CoreEventListener theCoreEventListener,
                       SystemNotification aSystemNotification) {
      _coreEventListener = theCoreEventListener;
      _systemNotification = aSystemNotification;
   }

   // ReplyListener interface
   @Override
   public void onReplyReceived(byte replyCode, final List<String> args) {

      if (replyCode == Reply.COMMAND_ACK)
      {
         // first argument is supposed to hold server protocol version
         if (args.size() > 0)
         {
            checkVersion(args.get(0));
         }

         // log to user
         Iterator argsIterator = args.listIterator();

         while (argsIterator.hasNext())
         {
            String arg = (String) argsIterator.next();
            _systemNotification.message( arg);
         }

         // notify state change
         _coreEventListener.onConnectionToServerEstablished();
      }
      else
      {
         // log to user
         Iterator argsIterator = args.listIterator();

         while (argsIterator.hasNext())
         {
            String arg = (String) argsIterator.next();
            _systemNotification.warning( arg);
         }

         // notify state change
         _coreEventListener.onDisconnectedFromServer();
      }

   }

   private void checkVersion(String versionString) {

   }

   private CoreEventListener _coreEventListener;
   private SystemNotification _systemNotification;
};

